package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/BOOKING"; // adapte si besoin
    private static final String USER = "root"; // adapte si besoin
    private static final String PASSWORD = "azerty"; // adapte si besoin

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
