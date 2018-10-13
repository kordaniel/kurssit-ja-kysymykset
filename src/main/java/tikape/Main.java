/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape;

import tikape.database.Database;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.dao.AiheDao;
import tikape.dao.KurssiDao;
import tikape.dao.KysymysDao;
import tikape.domain.Kurssi;
import tikape.domain.Kysymys;
/**
 *
 * @author danielko
 */

public class Main {
    //kurssit-ja-kysymykset.herokuapp.com
    
    public static void main(String[] args) throws Exception {
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        
        Database database = new Database("kehitysTietokanta.db");
        KurssiDao kurssiDao = new KurssiDao(database);
        KysymysDao kysymysDao = new KysymysDao(database);
        AiheDao aiheDao = new AiheDao(database);
        
        //System.out.println(kysymysDao.findAllForCourse(kurssiDao.findOne(3)));
        //System.exit(0);
        
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
            for (Kysymys kysymys : kysymykset) {
                System.out.println(kysymys.getId());
            }
            System.out.println("");
            HashMap map = new HashMap<>();
            map.put("kurssi", kurssi);
            map.put("kysymykset",kysymykset);
            
            return new ModelAndView(map, "kurssi");
        }, new ThymeleafTemplateEngine());
    }
    
}
