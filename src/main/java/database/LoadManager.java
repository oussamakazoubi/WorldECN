package database;

import org.centrale.objet.WoE.; // adjust to your actual package where World, Creature, Objet, etc. are

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Reconstructs a game world from the database for a given save (Sauvegarde).
 */
public class LoadManager {

    /**
     * Loads a complete World from a given save ID.
     *
     * @param idSauvegarde the save ID
     * @return a reconstructed World object
     */
    public static World loadWorld(int idSauvegarde) throws SQLException {
        World world = new World();

        try (Connection conn = DatabaseManager.getConnection()) {

            // 1️⃣ Load Elements (positions)
            String sqlElements = """
                SELECT e.idElement, e.posX, e.posY
                FROM Element e
                WHERE e.idSauvegarde = ?
                ORDER BY e.idElement;
            """;

            try (PreparedStatement psElem = conn.prepareStatement(sqlElements)) {
                psElem.setInt(1, idSauvegarde);
                ResultSet rsElem = psElem.executeQuery();

                while (rsElem.next()) {
                    int idElement = rsElem.getInt("idElement");
                    int x = rsElem.getInt("posX");
                    int y = rsElem.getInt("posY");

                    Point2D pos = new Point2D(x, y);

                    // 2️⃣ Check if this element is a Creature
                    Creature creature = loadCreatureByElement(conn, idElement, pos);
                    if (creature != null) {
                        if (creature instanceof Personnage) {
                            world.getMaListePers().add((Personnage) creature);
                        }
                        else if (creature instanceof Monstre) {
                            world.getMaListeMons().add((Monstre) creature);
                        }
                        continue; // skip checking object if we found a creature
                    }

                    // 3️⃣ Otherwise, check if this element is an Object
                    Objet objet = loadObjetByElement(conn, idElement, pos);
                    if (objet != null) {
                        world.getMaListeobj().add(objet);
                    }
                }
            }
        }

        System.out.println("✅ Monde chargé depuis la sauvegarde " + idSauvegarde);
        return world;
    }

    /**
     * Loads a Creature for a given element if one exists.
     */
    private static Creature loadCreatureByElement(Connection conn, int idElement, Point2D pos) throws SQLException {
        String sql = """
            SELECT c.nom, r.libelleRace
            FROM Creature c
            JOIN Race r ON c.idRace = r.idRace
            WHERE c.idElement = ?;
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idElement);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nom = rs.getString("nom");
                String race = rs.getString("libelleRace");

                // 🧠 Here we decide which subclass to instantiate based on race/type
                Creature creature;
                switch (race.toLowerCase()) {
                    case "loup" -> {
                        creature = new Loup();
                        creature.setPos(pos);
                    }
                    case "lapin" -> {
                        creature = new Lapin();
                        creature =
                    }
                    case "humain" -> creature = new Guerrier(pos, nom); // example
                    default -> creature = new Monstre(pos); // generic fallback
                }

                System.out.println("🧍 Créature chargée : " + (nom != null ? nom : race) +
                        " (" + race + ") @ " + pos);
                return creature;
            }
        }
        return null;
    }

    /**
     * Loads an Objet for a given element if one exists.
     */
    private static Objet loadObjetByElement(Connection conn, int idElement, Point2D pos) throws SQLException {
        String sql = """
            SELECT o.nomObjet, t.libelleObjet
            FROM Objet o
            JOIN TypeObjet t ON o.idTypeObjet = t.idTypeObjet
            WHERE o.idElement = ?;
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idElement);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nomObjet = rs.getString("nomObjet");
                String type = rs.getString("libelleObjet");

                Objet objet;
                switch (type.toLowerCase()) {
                    case "potionsoin" -> objet = new PotionSoin(pos);
                    case "nourriture" -> objet = new Nourriture(pos);
                    case "epee" -> objet = new Epee(pos);
                    case "nuagetoxique" -> objet = new NuageToxique(pos);
                    default -> objet = new Objet(pos);
                }

                objet.setNom(nomObjet);
                System.out.println("🎒 Objet chargé : " + nomObjet + " (" + type + ") @ " + pos);
                return objet;
            }
        }
        return null;
    }
}
