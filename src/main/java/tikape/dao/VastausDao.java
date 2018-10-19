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
        if (object == null) return null;
        try (Connection conn = db.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO Vastaus (kysymys_id, teksti, oikein) VALUES (?, ?, ?)");
            stmt.setInt(1, object.getKysymysId());
            stmt.setString(2, object.getTeksti());
            stmt.setBoolean(3, object.getOikein());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR: " + e);
        }
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        if (key == null) return;
        
        try (Connection conn = db.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Vastaus WHERE id = ?");
            stmt.setInt(1, key);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR " + e);
        }
    }
    
    //pitaisi tehda suoraan avaimella mielummin..
    public void deleteAllForQuestion(Kysymys kysymys) {
        if (kysymys == null) {
            System.out.println("DEBUG(VastausDao.deleteAllForQuestion: kysymys=null" );
            return;
        }
        if (kysymys.getId() == null) {
            System.out.println("DEBUG(VastausDao.deleteAllForQuestion: kysymysId=null");
        } else {
            System.out.println("DEBUG(VastausDao.deleteAllForQuestion: kysymysId = " +kysymys.getId());
        }
        try (Connection conn = db.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Vastaus WHERE kysymys_id = ?");
            stmt.setInt(1, kysymys.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR " + e);
        }
    }
    
    private void closeAllResources(ResultSet rs, PreparedStatement stmt, Connection conn) throws SQLException {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (conn != null) conn.close();
    }
}
