package database;

import java.sql.*;
import java.util.*;
import org.centrale.objet.WoE.*;
/**
 * Handles full loading of all game objects from the database
 * and reconstructs the exact Java subclasses (Epee, PotionSoin, etc.)
 * with all their attribute values restored.
 */
public class ObjetDBLoader {

    /**
     * Loads every object for the given save ID and adds them to the world.
     *
     * @param world        World to populate
     * @param idSauvegarde ID of the save
     */
    public static void loadObjets(World world, int idSauvegarde) throws SQLException {
        String sql = """
                    SELECT o.idObjet, o.nomObjet, t.libelleObjet,
                           e.posX, e.posY
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

            while (rs.next()) {
                int idObjet = rs.getInt("idObjet");
                String nom = rs.getString("nomObjet");
                String typeObjet = rs.getString("libelleObjet");
                Point2D pos = new Point2D(rs.getInt("posX"), rs.getInt("posY"));

                // Fetch all effects (attributes) for this object
                Map<String, Double> effets = getEffetsForObjet(conn, idObjet);

                // Rebuild the correct subclass instance
                Objet objet = createObjet(typeObjet, nom, pos, effets);

                // Add to the world
                world.getMaListeobj().add(objet);

                System.out.println("✅ Loaded " + typeObjet + " (" + nom + ") @ " + pos);
            }
        }
    }

    /**
     * Fetches all (libelleCarac, effet) pairs for a given object.
     */
    private static Map<String, Double> getEffetsForObjet(Connection conn, int idObjet) throws SQLException {
        Map<String, Double> effets = new HashMap<>();
        String sql = """
                    SELECT c.libelleCarac, e.effet
                    FROM Effet e
                    JOIN CaracObjet c ON e.idCaracObjet = c.idCaracObjet
                    WHERE e.idObjet = ?
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idObjet);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                effets.put(rs.getString("libelleCarac").toLowerCase(), rs.getDouble("effet"));
            }
        }
        return effets;
    }

    /**
     * Constructs the correct subclass with all attributes set from its effect map.
     */
    private static Objet createObjet(String typeObjet, String nom, Point2D pos, Map<String, Double> effets) {
        switch (typeObjet.toLowerCase()) {
            case "epee":
                return new Epee(nom, pos, effets.getOrDefault("bonusatt", 0.0).intValue());

            case "potionsoin":
                return new PotionSoin(nom, pos, effets.getOrDefault("ptvierendus", 0.0).intValue());

            case "feuilleepinart":
                return new FeuilleEpinart(nom, pos,
                        effets.getOrDefault("bonusdegatt", 0.0).intValue(),
                        effets.getOrDefault("dureeeffet", 0.0).intValue());

            case "champignonpourri":
                return new ChampignonPourri(nom, pos,
                        effets.getOrDefault("malusdefense", 0.0).intValue(),
                        effets.getOrDefault("dureeeffet", 0.0).intValue());

            case "nuagetoxique":
                NuageToxique n = new NuageToxique(nom, pos,
                        effets.getOrDefault("degatt", 0.0).intValue(),
                        effets.getOrDefault("distattmax", 0.0).intValue());
                return n;

            default:
                return null;
        }
    }
}
