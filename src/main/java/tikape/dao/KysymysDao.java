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
    private VastausDao vastausDao;

    public KysymysDao(Database db, VastausDao vastausDao) {
        this.db = db;
        this.vastausDao = vastausDao;
    }
    
    @Override
    public Kysymys findOne(Integer key) throws SQLException {
        if (key == null) return null;
        
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
    
    public void deleteAllForCourse(Kurssi k) throws SQLException {
        for (Kysymys kysymys : findAllForCourse(k)) {
            delete(kysymys.getId());
        }
    }

    @Override
    public Kysymys saveOrUpdate(Kysymys object) throws SQLException {
        try (Connection conn = db.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Kysymys (kurssi_id, aihe_id, teksti) VALUES (?, ?, ?)");
            stmt.setInt(1, object.getKurssi_id());
            stmt.setInt(2, object.getAihe_id());
            stmt.setString(3, object.getTeksti());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("VIRHE LISATTAESSA KYSYMYSTA: " + e);
        }
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        if (key == null) return;
        //System.out.println("poistetaan(ei): " + findOne(key));
        vastausDao.deleteAllForQuestion(findOne(key));
        
        try (Connection conn = db.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Kysymys WHERE id = ?");
            stmt.setInt(1, key);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("VIRHE POISTETTAESSA KYSYMYSTA " + e);
        }
    }
    
    private void closeAllResources(ResultSet rs, PreparedStatement stmt, Connection conn) throws SQLException {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (conn != null) conn.close();
    }
}
