/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape;

import java.sql.*;
import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
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
        
        //Connection c = DriverManager.getConnection("jdbc:sqlite:testi.db");
        //Statement stmt = c.createStatement();
        //ResultSet resultSet = stmt.executeQuery("SELECT 1");
        
        //if (resultSet.next()) {
        //    System.out.println("toimii");
        //} else {
        //    System.out.println("virhetta pukkaa");
        //}
        
        Spark.get("*", (req, res) -> {
            HashMap map = new HashMap<>();
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
    }
    
}
