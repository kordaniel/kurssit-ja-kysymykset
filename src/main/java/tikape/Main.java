/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape;

import tikape.database.Database;
import java.sql.*;
import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.dao.KurssiDao;
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
        
        System.out.println(kurssiDao.findOne(3));
        System.out.println("");
        System.out.println(kurssiDao.findAll());
        
        
        //Connection c = DriverManager.getConnection("jdbc:sqlite:testi.db");
        /*
        Connection c = database.getConnection();
        Statement stmt = c.createStatement();
        ResultSet resltSet = stmt.executeQuery("SELECT * FROM Kurssi");
        while (resltSet.next()) {
            System.out.println(resltSet.getInt("id") + "\t" + resltSet.getString("nimi"));
        }
        
        Spark.get("*", (req, res) -> {
            HashMap map = new HashMap<>();
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());*/
    }
    
}
