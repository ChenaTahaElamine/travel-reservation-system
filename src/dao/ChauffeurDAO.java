package dao;

import model.Chauffeur;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChauffeurDAO {

    // ==========================
    // CREATE
    // ==========================
    public void insert(Chauffeur chauffeur) throws SQLException {

        String sql ="INSERT INTO Chauffeur (nom, prenom, email, telephone) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, chauffeur.getNom());
            ps.setString(2, chauffeur.getPrenom());
            ps.setString(3, chauffeur.getEmail());
            ps.setString(4, chauffeur.getTelephone());

            ps.executeUpdate();
        }
    }

    // ==========================
    // READ (by id)
    // ==========================
    public Chauffeur findById(int id) throws SQLException {

        String sql = "SELECT * FROM Chauffeur WHERE id_chauffeur = ?";
        Chauffeur chauffeur = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                chauffeur = new Chauffeur(
                        rs.getInt("id_chauffeur"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone")
                );
            }
        }
        return chauffeur;
    }

    // ==========================
    // READ (all)
    // ==========================
    public List<Chauffeur> findAll() throws SQLException {

        List<Chauffeur> list = new ArrayList<>();
        String sql = "SELECT * FROM Chauffeur";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Chauffeur(
                        rs.getInt("id_chauffeur"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone")
                ));
            }
        }
        return list;
    }

    // ==========================
    // UPDATE
    // ==========================
    public void update(Chauffeur chauffeur) throws SQLException {

        String sql = "UPDATE Chauffeur SET nom = ?, prenom = ?, email = ?, telephone = ? WHERE id_chauffeur = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, chauffeur.getNom());
            ps.setString(2, chauffeur.getPrenom());
            ps.setString(3, chauffeur.getEmail());
            ps.setString(4, chauffeur.getTelephone());
            ps.setInt(5, chauffeur.getIdChauffeur());

            ps.executeUpdate();
        }
    }

    // ==========================
    // DELETE
    // ==========================
    public void delete(int id) throws SQLException {

        String sql = "DELETE FROM Chauffeur WHERE id_chauffeur = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
