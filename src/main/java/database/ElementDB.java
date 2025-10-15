package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ElementDB {

    /**
     * Saves one element's position for a given save (Sauvegarde).
     * @param idSauvegarde the ID of the current save
     * @param posX X coordinate
     * @param posY Y coordinate
     * @return the generated idElement
     */
    public static int insertElement(int idSauvegarde, int posX, int posY) throws SQLException {
        String sql = """
            INSERT INTO Element (idSauvegarde, posX, posY)
            VALUES (?, ?, ?)
            RETURNING idElement;
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idSauvegarde);
            ps.setInt(2, posX);
            ps.setInt(3, posY);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("idElement");
            }
        }
        return -1;
    }

    /**
     * Lists all elements (positions) for a save.
     */
    public static void listElements(int idSauvegarde) throws SQLException {
        String sql = """
            SELECT idElement, posX, posY
            FROM Element
            WHERE idSauvegarde = ?
            ORDER BY idElement;
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idSauvegarde);
            ResultSet rs = ps.executeQuery();

            System.out.println("=== Éléments de la sauvegarde " + idSauvegarde + " ===");
            while (rs.next()) {
                System.out.printf("ID: %d | X: %d | Y: %d%n",
                        rs.getInt("idElement"),
                        rs.getInt("posX"),
                        rs.getInt("posY"));
            }
        }
    }
}
