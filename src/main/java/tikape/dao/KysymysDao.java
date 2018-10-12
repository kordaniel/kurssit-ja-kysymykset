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

/**
 *
 * @author danielko
 */
public class KysymysDao implements Dao<Kysymys, Integer> {
    private Database db;

    public KysymysDao(Database db) {
        this.db = db;
    }
    
    @Override
    public Kysymys findOne(Integer key) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kysymys WHERE id = ?");
        stmt.setInt(1, key);
        
        ResultSet rs = stmt.executeQuery();
        
        if (!rs.next()) {
            closeAllResources(rs, stmt, conn);
            return null;
        }
        
        Kysymys kysymys = new Kysymys(rs.getInt("id"), 
                rs.getString("teksti"), rs.getInt("kurssi_id"), rs.getInt("aihe_id"));
        
        closeAllResources(rs, stmt, conn);
        return kysymys;
    }

    @Override
    public List<Kysymys> findAll() throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kysymys");
        ResultSet rs = stmt.executeQuery();
        
        List<Kysymys> kysymykset = new ArrayList<>();
        
        while (rs.next()) {
            kysymykset.add(new Kysymys(rs.getInt("id"), 
                    rs.getString("teksti"), rs.getInt("kurssi_id"), rs.getInt("aihe_id")));
        }
        
        closeAllResources(rs, stmt, conn);
        //return kysymykset.isEmpty() ? null : kysymykset;
        return kysymykset;
    }
    
    public List<Kysymys> findAllForCourse(Kurssi k) throws SQLException {
        if (k == null) return new ArrayList<>();
        
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kysymys WHERE kurssi_id = ?");
        stmt.setInt(1, k.getId());
        
        ResultSet rs = stmt.executeQuery();
        
        List<Kysymys> kysymykset = new ArrayList<>();
        
        while (rs.next()) {
            kysymykset.add(new Kysymys(rs.getInt("id"), 
                    rs.getString("teksti"), rs.getInt("kurssi_id"), rs.getInt("aihe_id")));
        }
        
        closeAllResources(rs, stmt, conn);
        return kysymykset;
    }

    @Override
    public Kysymys saveOrUpdate(Kysymys object) throws SQLException {
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
