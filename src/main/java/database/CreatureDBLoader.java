package database;

import org.centrale.objet.WoE.*;
import java.sql.*;

/**
 * Loads all creatures (Personnage + Monstre) for a given Sauvegarde,
 * restoring every real attribute exactly as defined in your Java classes.
 */
public class CreatureDBLoader {

    /**
     * Loads all creatures for a given save and adds them into the provided World.
     *
     * @param world        the World to populate
     * @param idSauvegarde the save ID
     */
    public static void loadCreatures(World world, int idSauvegarde) throws SQLException {

        String sql = """
            SELECT c.idCreature, c.nom, r.libelleRace, r.idTypeCreature,
                   tm.libelleMetier, e.posX, e.posY
            FROM Creature c
            JOIN Race r ON c.idRace = r.idRace
            JOIN Element e ON c.idElement = e.idElement
            LEFT JOIN CreatureHumaine h ON c.idCreature = h.idCreaHumaine
            LEFT JOIN TypeMetier tm ON h.idMetier = tm.idMetier
            WHERE e.idSauvegarde = ?
            ORDER BY c.idCreature;
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idSauvegarde);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idCreature = rs.getInt("idCreature");
                String nom = rs.getString("nom");
                String race = rs.getString("libelleRace");
                int typeCreature = rs.getInt("idTypeCreature");
                String metier = rs.getString("libelleMetier");
                Point2D pos = new Point2D(rs.getInt("posX"), rs.getInt("posY"));

                Creature creature;

                // 1️⃣ Build correct subclass
                if (typeCreature == 1) { // Humanoide
                    creature = createPersonnage(nom, metier, pos);
                } else { // Monstre
                    creature = createMonstre(race, pos);
                }

                // 2️⃣ Load its numeric attributes
                loadStats(conn, idCreature, creature);

                // 3️⃣ Add to the world
                if (creature instanceof Personnage){
                    world.getMaListePers().add((Personnage) creature);
                }

                if (creature instanceof Monstre){
                    world.getMaListeMons().add((Monstre) creature);
                }

                System.out.println("Loaded " + creature.getClass().getSimpleName()
                        + " (" + (nom != null ? nom : race) + ") @ " + pos);
            }
        }
    }

    /** Creates the proper Personnage subclass (Guerrier, Archer, Paysan). */
    private static Creature createPersonnage(String nom, String metier, Point2D pos) {
        if (metier == null) {
            Personnage P = new Personnage();
            P.setNom(nom);
            P.setPos(pos);
            return P;
        }
        switch (metier.toLowerCase()) {
            case "guerrier" -> {
                Guerrier P = new Guerrier();
                P.setNom(nom);
                P.setPos(pos);
                return P;
            }
            case "archer" -> {
                Archer P = new Archer();
                P.setNom(nom);
                P.setPos(pos);
                return P;
            }
            case "paysan" -> {
                Paysan P = new Paysan();
                P.setNom(nom);
                P.setPos(pos);
                return P;
            }
            default -> {
                Personnage P = new Personnage();
                P.setNom(nom);
                P.setPos(pos);
                return P;
            }
        }
    }

    /** Creates the proper Monstre subclass (Loup, Lapin, generic). */
    private static Creature createMonstre(String race, Point2D pos) {
        switch (race.toLowerCase()) {
            case "loup" -> {
                Loup M = new Loup();
                M.setPos(pos);
                return M;
            }
            case "lapin" -> {
                Lapin M = new Lapin();
                M.setPos(pos);
                return M;
            }
            default -> {
                Monstre M = new Monstre();
                M.setPos(pos);
                return M;
            }
        }
    }

    /** Restores all attributes from CaracValues into the Creature object. */
    private static void loadStats(Connection conn, int idCreature, Creature c) throws SQLException {
        String sql = "SELECT idCarac, caracValue FROM CaracValues WHERE idCreaHumaine = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCreature);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idCarac = rs.getInt("idCarac");
                int val = rs.getInt("caracValue");

                switch (idCarac) {
                    case 1 -> c.setPtVie((int) val);
                    case 2 -> c.setDegAtt((int) val);
                    case 3 -> c.setPtPar((int) val);
                    case 4 -> c.setPageAtt(val);
                    case 5 -> c.setPagePar(val);
                    case 6 -> {
                        if (c instanceof Personnage p) p.setDistAttMax((int) val);
                    }
                    case 7 -> {
                        if (c instanceof Archer a) a.setNbFleches((int) val);
                    }
                    default -> {}
                }
            }
        }
    }
}

