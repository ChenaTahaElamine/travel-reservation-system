package dao;

import model.Client;
import model.Reservation;
import model.Voyage;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

	private ClientDAO clientDAO = new ClientDAO();
	private VoyageDAO voyageDAO = new VoyageDAO();
	
	public void createReservation(int idClient, int idVoyage) throws SQLException {

        String sql = "INSERT INTO Reservation (id_client, id_voyage, date_r ) VALUES (?, ?, CURRENT_TIMESTAMP)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idClient);
            ps.setInt(2, idVoyage);
            ps.executeUpdate();
        }
    }

	public boolean exists(int idClient, int idVoyage) throws SQLException {
		String sql = "SELECT COUNT(*) FROM Reservation WHERE id_client = ? AND id_voyage = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, idClient);
			ps.setInt(2, idVoyage);

			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1) > 0;
		}
	}

	// `id_client`, `id_voyage`, `date_r`
	public void insert(Reservation reservation) throws SQLException {

		int idClient = reservation.getClient().getIdClient();
		int idVoyage = reservation.getVoyage().getIdVoyage();

		if (exists(idClient, idVoyage)) {
			System.out.println("Réservation déjà existante !");
			return; // 🔴 IMPORTANT : arrêter ici
		}

		String sql = "INSERT INTO Reservation (id_client, id_voyage, date_r) VALUES (?, ?, ?)";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, idClient);
			ps.setInt(2, idVoyage);
			ps.setDate(3, Date.valueOf(reservation.getDateReservation()));

			ps.executeUpdate();
			System.out.println("Réservation ajoutée avec succès.");
		}
	}

	public List<Reservation> findAll() throws SQLException {
		List<Reservation> list = new ArrayList<>();
		String sql = "SELECT * FROM Reservation";

		try (Connection conn = DBConnection.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql)) {

			while (rs.next()) {
				Client client = clientDAO.findById(rs.getInt("id_client"));
				Voyage voyage = voyageDAO.findById(rs.getInt("id_voyage"));

				list.add(new Reservation(client, voyage, rs.getDate("date_r").toLocalDate()));
			}
		}
		return list;
	}
}
