/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.database.Database;
import tikape.domain.Kurssi;

/**
 *
 * @author danielko
 */
public class KurssiDao implements Dao<Kurssi, Integer> {
    private Database db;

    public KurssiDao(Database database) {
        this.db = database;
    }
    
    @Override
    public Kurssi findOne(Integer key) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kurssi WHERE id = ?");
        stmt.setInt(1, key);
        
        ResultSet rs = stmt.executeQuery();
        
        if (!rs.next()) {
            closeAllResources(rs, stmt, conn);
            return null;
        }
        
        Kurssi kurssi = new Kurssi(rs.getInt("id"), rs.getString("nimi"));
        
        closeAllResources(rs, stmt, conn);
        return kurssi;
    }

    @Override
    public List<Kurssi> findAll() throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kurssi");
        ResultSet rs = stmt.executeQuery();
        
        List<Kurssi> kurssit = new ArrayList<>();
        
        while (rs.next()) {
            kurssit.add(new Kurssi(rs.getInt("id"), rs.getString("nimi")));
        }
        
        closeAllResources(rs, stmt, conn);
        return kurssit;
    }

    @Override
    public Kurssi saveOrUpdate(Kurssi object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void closeAllResources(ResultSet rs, PreparedStatement stmt, Connection conn) throws SQLException {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (conn != null) conn.close();
    }
}