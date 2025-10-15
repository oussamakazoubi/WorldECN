package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles saving and loading of in-game objects (weapons, potions, food, etc.)
 * in the database, linked indirectly to a Sauvegarde through Element.
 */
public class ObjetDB {

    /**
     * Inserts a new object linked to an element (position).
     *
     * @param nomObjet   name of the object (e.g. "Potion Soin", "Épée acier")
     * @param idTypeObjet the type ID from TypeObjet (e.g. 1=PotionSoin, 2=PotionMagie, …)
     * @param idElement   the element ID where the object is placed
     * @return the generated idObjet
     */
    public static int insertObjet(String nomObjet, int idTypeObjet, int idElement) throws SQLException {
        String sql = """
            INSERT INTO Objet (nomObjet, idTypeObjet, idElement)
            VALUES (?, ?, ?)
            RETURNING idObjet;
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nomObjet);
            ps.setInt(2, idTypeObjet);
            ps.setInt(3, idElement);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idObjet = rs.getInt("idObjet");
                System.out.println("Objet inséré : " + nomObjet + " → ID = " + idObjet);
                return idObjet;
            }
        }
        return -1;
    }

    /**
     * Lists all objects for a given save (Sauvegarde),
     * joining through Element → Sauvegarde.
     */
    public static void listObjets(int idSauvegarde) throws SQLException {
        String sql = """
            SELECT o.idObjet, o.nomObjet, t.libelleObjet, e.posX, e.posY
            FROM Objet o
            JOIN TypeObjet t ON o.idTypeObjet = t.idTypeObjet
            JOIN Element e ON o.idElement = e.idElement
            WHERE e.idSauvegarde = ?
            ORDER BY o.idObjet;
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idSauvegarde);
            ResultSet rs = ps.executeQuery();

            System.out.println("=== Objets de la sauvegarde " + idSauvegarde + " ===");
            while (rs.next()) {
                System.out.printf("ID: %d | Nom: %s | Type: %s | Pos: (%d,%d)%n",
                        rs.getInt("idObjet"),
                        rs.getString("nomObjet"),
                        rs.getString("libelleObjet"),
                        rs.getInt("posX"),
                        rs.getInt("posY"));
            }
        }
    }
}
