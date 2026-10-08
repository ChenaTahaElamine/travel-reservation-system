package dao;

import model.Bus;
import model.Chauffeur;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusDAO {

    private ChauffeurDAO chauffeurDAO = new ChauffeurDAO();

    // ==========================
    // CREATE
    // ==========================
    public void insert(Bus bus) throws SQLException {

        String sql = "INSERT INTO Bus (matricule, capacite, id_chauffeur) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bus.getMatricule());
            ps.setInt(2, bus.getCapacite());
            ps.setInt(3, bus.getChauffeur().getIdChauffeur());

            ps.executeUpdate();
        }
    }

    // ==========================
    // READ (by id)
    // ==========================
    public Bus findById(int id) throws SQLException {

        String sql = "SELECT * FROM Bus WHERE id_bus = ?";
        Bus bus = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Chauffeur chauffeur =
                        chauffeurDAO.findById(rs.getInt("id_chauffeur"));

                bus = new Bus(
                        rs.getInt("id_bus"),
                        rs.getString("matricule"),
                        rs.getInt("capacite"),
                        chauffeur
                );
            }
        }
        return bus;
    }

    // ==========================
    // READ (all)
    // ==========================
    public List<Bus> findAll() throws SQLException {

        List<Bus> list = new ArrayList<>();
        String sql = "SELECT * FROM Bus";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Chauffeur chauffeur =
                        chauffeurDAO.findById(rs.getInt("id_chauffeur"));

                list.add(new Bus(
                        rs.getInt("id_bus"),
                        rs.getString("matricule"),
                        rs.getInt("capacite"),
                        chauffeur
                ));
            }
        }
        return list;
    }

    // ==========================
    // UPDATE
    // ==========================
    public void update(Bus bus) throws SQLException {

        String sql = "UPDATE Bus SET matricule = ?, capacite = ?, id_chauffeur = ? WHERE id_bus = ?";
        

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bus.getMatricule());
            ps.setInt(2, bus.getCapacite());
            ps.setInt(3, bus.getChauffeur().getIdChauffeur());
            ps.setInt(4, bus.getIdBus());

            ps.executeUpdate();
        }
    }

    // ==========================
    // DELETE
    // ==========================
    public void delete(int id) throws SQLException {

        String sql = "DELETE FROM Bus WHERE id_bus = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
