package database;

public class TestSauvegardeDB {
    public static void main(String[] args) {
        try {
            // Example: suppose your Partie ID is 1
            int idManual = SauvegardeDB.createSauvegarde(1, "DebutAventure", false);
            System.out.println("✅ Sauvegarde manuelle ID = " + idManual);

            int idQuick = SauvegardeDB.createSauvegarde(1, null, true);
            System.out.println("✅ Quicksave ID = " + idQuick);

            SauvegardeDB.listSauvegardes(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
