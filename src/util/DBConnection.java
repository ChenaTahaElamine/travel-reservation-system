package util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
        "jdbc:mysql://localhost:3306/bdd_reservation_voyage";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Empêche l’instanciation
    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL introuvable", e);
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
   
    
}
