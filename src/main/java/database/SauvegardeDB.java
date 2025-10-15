package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class SauvegardeDB {

    public static int createSauvegarde(int idPartie, String nom, boolean isQuickSave) throws SQLException {
        String sql = """
            INSERT INTO Sauvegarde (idPartie, nom, isQuickSave)
            VALUES (?, ?, ?)
            RETURNING idSauvegarde;
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPartie);

            if (isQuickSave)
                ps.setNull(2, Types.VARCHAR);
            else
                ps.setString(2, nom);

            ps.setBoolean(3, isQuickSave);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("idSauvegarde");
            }
        }
        return -1;
    }

    public static void listSauvegardes(int idPartie) throws SQLException {
        String sql = """
            SELECT idSauvegarde, nom, isQuickSave, datee, dateMaj
            FROM Sauvegarde
            WHERE idPartie = ?
            ORDER BY dateMaj DESC;
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPartie);
            ResultSet rs = ps.executeQuery();

            System.out.println("=== Liste des sauvegardes ===");
            while (rs.next()) {
                String type = rs.getBoolean("isQuickSave") ? "QUICK" : "MANUAL";
                System.out.printf("ID: %d | Type: %s | Nom: %s | Dernière maj: %s%n",
                        rs.getInt("idSauvegarde"),
                        type,
                        rs.getString("nom"),
                        rs.getTimestamp("dateMaj"));
            }
        }
    }
}
