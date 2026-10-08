package dao;

import model.Admin;
import util.DBConnection;

import java.sql.*;

public class AdminDAO {

    private Connection cnx;

    public AdminDAO() throws SQLException {
        cnx = DBConnection.getConnection();
    }

    // ===============================
    // Authentification admin
    // ===============================
    public Admin login(String email, String password) throws SQLException {

        String sql = "SELECT * FROM admin WHERE email_a = ? AND password_a = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, password); // idéalement hashé

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Admin(
                rs.getInt("id_admin"),
                rs.getString("nom_a"),
                rs.getString("prenom_a"),
                rs.getString("telephone_a"),
                rs.getString("email_a"),
                rs.getString("password_a")
            );
        }
        return null;
    }

    // ===============================
    // Création admin
    // ===============================
    public void insert(Admin admin) throws SQLException {
        String sql = "INSERT INTO admin(nom_a, prenom_a, telephone_a, email_a, password_a) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, admin.getNom());
        ps.setString(2, admin.getPrenom());
        ps.setString(3, admin.getTelephone());
        ps.setString(4, admin.getEmail());
        ps.setString(5, admin.getPassword());
        ps.executeUpdate();
    }
}
