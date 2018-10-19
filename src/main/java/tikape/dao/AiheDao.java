package tikape.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.database.Database;
import tikape.domain.Aihe;

public class AiheDao implements Dao<Aihe, Integer> {
    private Database db;

    public AiheDao(Database db) {
        this.db = db;
    }
    
    @Override
    public Aihe findOne(Integer key) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Aihe WHERE id = ?");
        stmt.setInt(1, key);
        
        ResultSet rs = stmt.executeQuery();
        
        if (!rs.next()) {
            closeAllResources(rs, stmt, conn);
            return null;
        }
        
        Aihe aihe = new Aihe(rs.getInt("id"), rs.getString("teksti"));
        closeAllResources(rs, stmt, conn);
        return aihe;
    }
    
    public Aihe findOneByAiheteksti(Aihe object) {
        Aihe palautus = null;
        try (Connection conn = db.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Aihe WHERE teksti = ?");
            stmt.setString(1, object.getTeksti());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                palautus = new Aihe(rs.getInt("id"), rs.getString("teksti"));
            }
        } catch (SQLException e) {
            System.out.println("VIRHE" + e);
        }
        return palautus;
    }

    @Override
    public List<Aihe> findAll() throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Aihe");
        ResultSet rs = stmt.executeQuery();
        
        List<Aihe> aiheet = new ArrayList<>();
        
        while (rs.next()) {
            aiheet.add(new Aihe(rs.getInt("id"), rs.getString("teksti")));
        }
        
        closeAllResources(rs, stmt, conn);
        return aiheet.isEmpty() ? null : aiheet;
    }

    @Override
    public Aihe saveOrUpdate(Aihe object) throws SQLException {
        Aihe palautus = findOneByAiheteksti(object);
        if (palautus != null) return palautus;
        
        try (Connection conn = db.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Aihe (teksti) VALUES (?)");
            stmt.setString(1, object.getTeksti());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("VIRHE " + e);
        }
        return findOneByAiheteksti(object);
    }

    @Override
    public void delete(Integer key) throws SQLException {
        //TILA ON HALPAA -> AIHEITA EI TARVITSE POISTAA!!
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void closeAllResources(ResultSet rs, PreparedStatement stmt, Connection conn) throws SQLException {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (conn != null) conn.close();
    }
    
}
