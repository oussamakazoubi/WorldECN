/*
package database;

public class TestCreatureDB {
    public static void main(String[] args) {
        try {
            // Suppose Sauvegarde ID = 1
            int idElem1 = ElementDB.insertElement(1, 2, 8);
            int idElem2 = ElementDB.insertElement(1, 21, 5);

            // Example races (Humain = 2, Loup = 3)
            int idCrea1 = CreatureDB.insertCreature("Arwen", 2, idElem1);
            int idCrea2 = CreatureDB.insertCreature("Wolfie", 3, idElem2);

            System.out.println("Créatures créées : " + idCrea1 + ", " + idCrea2);

            CreatureDB.listCreatures(1);

            // Link Arwen (player) to Joueur 1
            try (var conn = DatabaseManager.getConnection();
                 var ps = conn.prepareStatement("UPDATE Joueur SET idCreature = ? WHERE idJoueur = 1")) {
                ps.setInt(1, idCrea1);
                ps.executeUpdate();
            }

            System.out.println("Joueur 1 → lié à la créature " + idCrea1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
*/
