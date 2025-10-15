package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    // === Modify these for your local setup ===
    private static final String URL = "jdbc:postgresql://appli-pfe.ec-nantes.fr:5432/infosi_05";
    private static final String USER = "infosi_05";
    private static final String PASSWORD = "frieren";

    private static Connection connection = null;

    // Get (or create) a single shared connection
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected to the database!");
            } catch (SQLException e) {
                System.err.println("Database connection failed: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }

    // Close connection cleanly
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                System.err.println("Failed to close DB connection: " + e.getMessage());
            }
        }
    }
}
