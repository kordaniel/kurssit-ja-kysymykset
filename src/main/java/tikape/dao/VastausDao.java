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
import tikape.domain.Kysymys;
import tikape.domain.Vastaus;

/**
 *
 * @author danielko
 */
public class VastausDao implements Dao<Vastaus, Integer> {
    private Database db;

    public VastausDao(Database db) {
        this.db = db;
    }
    
    @Override
    public Vastaus findOne(Integer key) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vastaus WHERE id = ?");
        stmt.setInt(1, key);
        
        ResultSet rs = stmt.executeQuery();
        
        if (!rs.next()) {
            closeAllResources(rs, stmt, conn);
            return null;
        }
        
        Vastaus vastaus = new Vastaus(rs.getInt("id"), rs.getInt("kysymys_id"), 
                rs.getString("teksti"), rs.getBoolean("oikein"));
        
        closeAllResources(rs, stmt, conn);
        return vastaus;
    }

    @Override
    public List<Vastaus> findAll() throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vastaus");
        ResultSet rs = stmt.executeQuery();
        
        List<Vastaus> vastaukset = new ArrayList<>();
        
        while (rs.next()) {
            vastaukset.add(new Vastaus(rs.getInt("id"), rs.getInt("kysymys_id"), 
                    rs.getString("teksti"), rs.getBoolean("oikein")));
        }
        
        closeAllResources(rs, stmt, conn);
        return vastaukset;
    }
    
    public List<Vastaus> findAllForQuestion(Kysymys kysymys) throws SQLException {
        if (kysymys == null) return new ArrayList<>();
        
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vastaus WHERE kysymys_id = ?");
        stmt.setInt(1, kysymys.getId());
        
        ResultSet rs = stmt.executeQuery();
        
        List<Vastaus> vastaukset = new ArrayList<>();
        
        while (rs.next()) {
            vastaukset.add(new Vastaus(rs.getInt("id"), rs.getInt("kysymys_id"), 
                    rs.getString("teksti"), rs.getBoolean("oikein")));
        }
        
        closeAllResources(rs, stmt, conn);
        return vastaukset;
    }

    @Override
    public Vastaus saveOrUpdate(Vastaus object) throws SQLException {
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
