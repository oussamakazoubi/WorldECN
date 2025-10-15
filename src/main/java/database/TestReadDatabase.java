package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestReadDatabase {
    public static void main(String[] args) {
        try {
            // Get the connection from your DatabaseManager
            Connection conn = DatabaseManager.getConnection();

            // Create a simple statement
            Statement stmt = conn.createStatement();

            // Query something small — for example all players
            String sql = "SELECT idJoueur, pseudo, email FROM Joueur";
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("=== Liste des Joueurs ===");
            while (rs.next()) {
                int id = rs.getInt("idJoueur");
                String pseudo = rs.getString("pseudo");
                String email = rs.getString("email");
                System.out.println("ID: " + id + " | Pseudo: " + pseudo + " | Email: " + email);
            }

            rs.close();
            stmt.close();
            DatabaseManager.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
