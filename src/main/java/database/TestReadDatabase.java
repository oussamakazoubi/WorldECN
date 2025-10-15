package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestReadDatabase {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseManager.getConnection();

            Statement stmt = conn.createStatement();

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
