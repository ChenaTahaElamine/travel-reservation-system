package dao;

import model.Client;
import util.AuthResult;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

	// ==========================
	// CREATE
	// ==========================
	public void insert(Client client) throws SQLException {

		String sql = "INSERT INTO Client (nom, prenom, telephone, email, password) VALUES (?, ?, ?, ?, ?) ";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getTelephone());
			ps.setString(4, client.getEmail());
			ps.setString(5, client.getPassword());

			ps.executeUpdate();
		}
	}

	// ==========================
	// READ (by id)
	// ==========================
	public Client findById(int id) throws SQLException {

		String sql = "SELECT * FROM Client WHERE id_client = ?";
		Client client = null;

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				client = new Client(rs.getInt("id_client"), rs.getString("nom"), rs.getString("prenom"),
						rs.getString("telephone"), rs.getString("email"), rs.getString("password"));
			}
		}
		return client;
	}

	// ==========================
	// READ (all)
	// ==========================
	public List<Client> findAll() throws SQLException {

		List<Client> clients = new ArrayList<>();
		String sql = "SELECT * FROM Client";

		try (Connection conn = DBConnection.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql)) {

			while (rs.next()) {
				clients.add(new Client(rs.getInt("id_client"), rs.getString("nom"), rs.getString("prenom"),
						rs.getString("telephone"), rs.getString("email"), rs.getString("password")));
			}
		}
		return clients;
	}

	// ==========================
	// UPDATE
	// ==========================
	public void update(Client client) throws SQLException {

		String sql = "UPDATE Client SET nom = ?, prenom = ?, telephone = ?, email = ?, password = ? WHERE id_client = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getTelephone());
			ps.setString(4, client.getEmail());
			ps.setString(5, client.getPassword());
			ps.setInt(6, client.getIdClient());

			ps.executeUpdate();
		}
	}

	// ==========================
	// DELETE
	// ==========================
	public void delete(int id) throws SQLException {

		String sql = "DELETE FROM Client WHERE id_client = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, id);
			ps.executeUpdate();
		}
	}

	// ==========================
	// (OPTIONNEL) Recherche par email
	// ==========================
	public Client findByEmail(String email) throws SQLException {

		String sql = "SELECT * FROM Client WHERE email = ?";
		Client client = null;

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				client = new Client(rs.getInt("id_client"), rs.getString("nom"), rs.getString("prenom"),
						rs.getString("telephone"), rs.getString("email"), rs.getString("password"));
			}
		}
		return client;
	}

	public AuthResult authenticate(String email, String password) {

		String sql = "SELECT * FROM Client WHERE email = ? AND password = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, email);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Client client = new Client(rs.getInt("id_client"), rs.getString("nom"), rs.getString("prenom"),
						rs.getString("telephone"), rs.getString("email"), rs.getString("password"));

				return new AuthResult(true, "Authentification réussie", client);
			} else {
				return new AuthResult(false, "Email ou mot de passe incorrect", null);
			}

		} catch (SQLException e) {
			return new AuthResult(false, "Erreur de connexion à la base de données", null);
		}
	}

}
