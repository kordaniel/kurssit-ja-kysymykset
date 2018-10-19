/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author danielko
 */
public class Database {
    private String dbAddress;

    public Database(String dbAddress) throws ClassNotFoundException {
        this.dbAddress = dbAddress;
    }
    
    public Connection getConnection() throws SQLException {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        }
        //return DriverManager.getConnection("jdbc:sqlite:" + dbAddress);
        return null;
    }
}
