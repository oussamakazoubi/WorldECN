/*package database;

public class TestObjetDB {
    public static void main(String[] args) {
        try {
            // Suppose Sauvegarde ID = 1
            int idElem1 = ElementDB.insertElement(1, 4, 2);
            int idElem2 = ElementDB.insertElement(1, 8, 7);

            // Example: TypeObjet IDs — 1=PotionSoin, 2=PotionMagie, 3=Arbre, 4=NuageToxique
            int idObj1 = ObjetDB.insertObjet("Potion Soin +10", 1, idElem1);
            int idObj2 = ObjetDB.insertObjet("Épée en acier", 3, idElem2);

            System.out.println("Objets créés : " + idObj1 + ", " + idObj2);

            ObjetDB.listObjets(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
*/