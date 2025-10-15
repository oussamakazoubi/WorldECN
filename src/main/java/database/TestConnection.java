package database;



import java.sql.*;
import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseManager.getConnection();

            String sql = "INSERT INTO Joueur (pseudo, email, password) VALUES (?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "Tatsuya");
            ps.setString(2, "tatsuya@ecn.fr");
            ps.setString(3, "12345");

            int rows = ps.executeUpdate();
            System.out.println("✅ Joueur ajouté avec succès (" + rows + " ligne insérée)");

            ps.close();
            DatabaseManager.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
