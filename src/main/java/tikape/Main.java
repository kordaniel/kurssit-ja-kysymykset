package tikape;

import tikape.database.Database;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.dao.AiheDao;
import tikape.dao.KurssiDao;
import tikape.dao.KysymysDao;
import tikape.dao.VastausDao;
import tikape.domain.Aihe;
import tikape.domain.Kurssi;
import tikape.domain.Kysymys;
import tikape.domain.Vastaus;

public class Main {
    //kurssit-ja-kysymykset.herokuapp.com

    public static void main(String[] args) throws Exception {
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        

        Database database = new Database("kehitysTietokanta.db");
        
        VastausDao vastausDao = new VastausDao(database);
        KysymysDao kysymysDao = new KysymysDao(database,vastausDao);        
        KurssiDao kurssiDao = new KurssiDao(database, kysymysDao);
        AiheDao aiheDao = new AiheDao(database);
        
        Spark.post("/uusikurssi", (req, res) -> {
            String nimi = req.queryParams("kurssinimi");
            String aihe = req.queryParams("kurssiaihe");
            
            if (nimi.isEmpty() || aihe.isEmpty()) {
                return "Olet syöttänyt joko tyhjän kurssinimen tai et syöttänyt aihetta, yritäppä uudelleen";
            }
            
            Aihe aiheolio = aiheDao.saveOrUpdate(new Aihe(-1, aihe));
            Kurssi uusiKurssi = new Kurssi(aiheolio.getId(), nimi);
            kurssiDao.saveOrUpdate(uusiKurssi);
            
            res.redirect("/");
            return "";
        });

        Spark.post("/poistakurssi", (req, res) -> {
            String saatu = req.queryParams("kurssiId");
            Integer kurssiId = -1;
            try {
                kurssiId = Integer.parseInt(saatu);
            } catch (NumberFormatException e) {
                res.redirect("/");
                return "";
            }
            kurssiDao.delete(kurssiId);
            res.redirect("/");
            return "";
        });
        
        Spark.post("/poistavastaus", (req, res) -> {
            Integer vastausId = null;
            try {
                vastausId = new Integer(req.queryParams("vastausId"));
            } catch (NumberFormatException e) {
                System.out.println("virhe vastausta poistettaessa: " + e);
                res.redirect("/");
                return "";
            }
            
            Vastaus v = vastausDao.findOne(vastausId);
            vastausDao.delete(vastausId);
            res.redirect("/kysymys/" + v.getKysymysId());
            return "";
        });

        Spark.post("/luokysymys/:id", (req, res) -> {
            int kurssiId = -1;
            String kysymysTeksti = req.queryParams("kysymysTeksti");
            String aiheTeksti = req.queryParams("aihe");
            if (kysymysTeksti.isEmpty() || aiheTeksti.isEmpty()) {
                //res.redirect("/");
                //return "";
                return "Virhe joko kysymystekstissä tai aihetekstissä, puuttuivat?";
            }
            
            try {
                kurssiId = Integer.parseInt(req.params("id"));
            } catch (NumberFormatException e) {
                System.out.println("ei int: " + e);
                res.redirect("/");
                return "";
            }
            
            
            Aihe aihe = aiheDao.saveOrUpdate(new Aihe(-1, aiheTeksti));
            Kysymys uusiKysymys = new Kysymys(-1, kysymysTeksti, kurssiId, aihe.getId());
            kysymysDao.saveOrUpdate(uusiKysymys);
            res.redirect("/kurssi/" + kurssiId);
            return "";
        });
        
        Spark.post("/poistakysymys/:id", (req, res) -> {
            String saatuKurssiId = req.queryParams("kurssiId");
            int kurssiId  = -1;
            int kysymysId = -1;
            
            try {
                kysymysId = Integer.parseInt(req.params("id"));
                kurssiId = Integer.parseInt(saatuKurssiId);
            } catch (NumberFormatException e) {
                System.out.println("ei int: " + e);
                res.redirect("/");
                return "";
            }
            
            
            kysymysDao.delete(kysymysId);
            res.redirect("/kurssi/" + kurssiId);
            return "";
        });

        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("kurssit", kurssiDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.get("/kurssi/:id", (req, res) -> {
            Integer kurssiId = null;
            try {
                kurssiId = Integer.parseInt(req.params("id"));
            } catch (NumberFormatException e) {
                res.redirect("/");
            }

            Kurssi kurssi = kurssiDao.findOne(kurssiId);
            if (kurssi == null) {
                res.redirect("/");
            }

            List<Kysymys> kysymykset = kysymysDao.findAllForCourse(kurssi);
            Aihe aihe = aiheDao.findOne(kurssi.getAihe_id());
            
            HashMap map = new HashMap<>();
            map.put("kurssi", kurssi);
            map.put("kysymykset", kysymykset);
            map.put("aihe", aihe);

            return new ModelAndView(map, "kurssi");
        }, new ThymeleafTemplateEngine());

        Spark.get("/kysymys/:id", (req, res) -> {
            Integer kysymysId = null;
            try {
                kysymysId = Integer.parseInt(req.params("id"));
            } catch (NumberFormatException e) {
                res.redirect("/");
            }
            Kysymys kysymys = kysymysDao.findOne(kysymysId);
            if (kysymys == null) {
                res.redirect("/");
            }

            Aihe aihe = aiheDao.findOne(kysymys.getAihe_id());
            List<Vastaus> vastaukset = vastausDao.findAllForQuestion(kysymys);

            HashMap map = new HashMap<>();
            map.put("kysymys", kysymys);
            map.put("aihe", aihe);
            map.put("vastaukset", vastaukset);

            return new ModelAndView(map, "kysymys");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/kysymys/:id", (req, res) -> {
            Integer kysymysId = null;
            boolean oikea;
            
            try {
                kysymysId = Integer.parseInt(req.params("id"));
            } catch (NumberFormatException e) {
                System.out.println("ei saatu kysymysId:ta: " + e);
                res.redirect("/");
                return "";
            }
            String teksti = req.queryParams("vastausTeksti");
            String oikein = req.queryParams("oikein");
            
            if (teksti.isEmpty()) {
                //res.redirect("/kysymys/" + kysymysId);
                return "Ei voida luoda vastausta ilman tekstiä, lisää teksti ja yritä uudelleen ";
            }
            
            if (oikein == null) {
                oikea = false;
            } else {
                oikea = "oikea".equals(oikein);
            }
             
            
            Vastaus v = new Vastaus(-1, kysymysId, teksti, oikea);
            vastausDao.saveOrUpdate(v);
            res.redirect("/kysymys/" + kysymysId);
            return "";
        });
    }
    
}
