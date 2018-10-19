package tikape.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private String dbAddress;

    public Database(String dbAddress) throws ClassNotFoundException {
        this.dbAddress = dbAddress;
    }
    
    public Connection getConnection() throws SQLException {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        } else {
            //JOS EI OLE HEROKU-TIETOKANTAA, KAYTETAAN SQLITE
            return DriverManager.getConnection("jdbc:sqlite:" + dbAddress);
        }
        
        //return null;
    }
}
