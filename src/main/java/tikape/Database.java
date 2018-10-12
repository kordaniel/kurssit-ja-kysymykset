/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author danielko
 */
public class Database {
    private String dbAddress;

    public Database(String dbAddress) {
        this.dbAddress = dbAddress;
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbAddress);
    }
}
