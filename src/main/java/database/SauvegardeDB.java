package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class SauvegardeDB {

    /**
     * Creates a new save (manual or quicksave) for a given Partie.
     * If it's a manual save, give it a name (nom).
     * If it's a quicksave, pass nom = null and isQuickSave = true.
     *
     * @param idPartie the Partie ID
     * @param nom      the save name (null for quicksave)
     * @param isQuickSave true for quicksave
     * @return idSauvegarde created or found
     */
    public static int createSauvegarde(int idPartie, String nom, boolean isQuickSave) throws SQLException {
        String sql = """
            INSERT INTO Sauvegarde (idPartie, nom, isQuickSave)
            VALUES (?, ?, ?)
            ON CONFLICT (idPartie, nom) WHERE nom IS NOT NULL DO UPDATE
                SET dateMaj = CURRENT_TIMESTAMP
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

    /**
     * Lists all saves (manual and quick) for a Partie, newest first.
     */
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
