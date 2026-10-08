package dao;

import model.Bus;
import model.Voyage;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class VoyageDAO {

    private BusDAO busDAO = new BusDAO();

    // ==========================
    // CREATE
    // ==========================
    public void insert(Voyage voyage) throws SQLException {

        String sql = "INSERT INTO Voyage (date_v, h_depart, prix_v, id_bus) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(voyage.getDateVoyage()));
            ps.setTime(2, Time.valueOf(voyage.getHeureDepart()));
            ps.setDouble(3, voyage.getPrix());
            ps.setInt(4, voyage.getBus().getIdBus());

            ps.executeUpdate();
        }
    }

    // ==========================
    // READ (by id)
    // ==========================
    public Voyage findById(int id) throws SQLException {

        String sql = "SELECT * FROM Voyage WHERE id_voyage = ?";
        Voyage voyage = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Bus bus = busDAO.findById(rs.getInt("id_bus"));

                voyage = new Voyage(
                        rs.getInt("id_voyage"),
                        rs.getDate("date_v").toLocalDate(),
                        rs.getTime("h_depart").toLocalTime(),
                        rs.getDouble("prix_v"),
                        bus
                );
            }
        }
        return voyage;
    }

    // ==========================
    // READ (all)
    // ==========================
    public List<Voyage> findAll() throws SQLException {

        List<Voyage> list = new ArrayList<>();
        String sql = "SELECT * FROM Voyage";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Bus bus = busDAO.findById(rs.getInt("id_bus"));

                list.add(new Voyage(
                        rs.getInt("id_voyage"),
                        rs.getDate("date_v").toLocalDate(),
                        rs.getTime("h_depart").toLocalTime(),
                        rs.getDouble("prix_v"),
                        bus
                ));
            }
        }
        return list;
    }

    // ==========================
    // UPDATE
    // ==========================
    public void update(Voyage voyage) throws SQLException {

        String sql = " UPDATE Voyage SET date_v = ?, h_depart = ?, prix_v = ?, id_bus = ? WHERE id_voyage = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(voyage.getDateVoyage()));
            ps.setTime(2, Time.valueOf(voyage.getHeureDepart()));
            ps.setDouble(3, voyage.getPrix());
            ps.setInt(4, voyage.getBus().getIdBus());
            ps.setInt(5, voyage.getIdVoyage());

            ps.executeUpdate();
        }
    }

    // ==========================
    // DELETE
    // ==========================
    public void delete(int id) throws SQLException {

        String sql = "DELETE FROM Voyage WHERE id_voyage = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
