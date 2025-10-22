package org.centrale.objet.WoE;

import java.util.Scanner;

/**
 * Classe principale gérant le menu textuel du jeu <b>World of ECN</b>.
 *
 * <p>Elle permet au joueur de :</p>
 * <ul>
 *   <li>Démarrer une nouvelle partie</li>
 *   <li>Charger une partie sauvegardée</li>
 *   <li>Quitter le jeu</li>
 * </ul>
 *
 * <p>Cette classe constitue le point d’entrée du mode console du jeu.</p>
 *
 * @author Oussama
 * @see World
 * @see Joueur
 */
public class MainMenu {

    /**
     * Lance le menu principal du jeu et gère les interactions avec le joueur.
     *
     * <p>Le joueur peut choisir entre :
     * <ul>
     *   <li><b>Nouvelle partie</b> — crée un nouveau monde, un joueur et son personnage.</li>
     *   <li><b>Charger partie</b> — charge une sauvegarde depuis un fichier texte.</li>
     *   <li><b>Quitter</b> — termine le programme.</li>
     * </ul>
     * </p>
     */
    public static void startGame() {
        System.out.println("=== Lancement du jeu World of ECN ===");
        Scanner scanner = new Scanner(System.in);

        World monde = new World();
        Joueur joueur = null;
        boolean quitter = false;

        while (!quitter) {
            System.out.println("""
                        ==============================
                        === MENU PRINCIPAL DU JEU ===
                        1 - Nouvelle partie
                        2 - Charger partie
                        3 - Quitter
                        ==============================
                    """);

            System.out.print("Votre choix : ");
            String choix = scanner.nextLine();

            switch (choix) {
                case "1" -> {
                    // ======== Nouvelle Partie ========
                    System.out.println("\n=== Nouvelle partie ===");

                    monde.setLongueur(20);
                    monde.setLargeur(20);

                    // Création du joueur
                    joueur = new Joueur();
                    System.out.print("Entrez votre nom : ");
                    joueur.setNom(scanner.nextLine());
                    joueur.ChoisirPersonnage(monde);
                    monde.setJoueur(joueur);

                    // Génération du monde aléatoire
                    monde.creerMondeAlea(
                            2, // Archers
                            2, // Paysans
                            3, // Lapins
                            2, // Guerriers
                            2, // Loups
                            2, // Potions
                            2, // Épées
                            1, // Champignons pourris
                            1  // Feuilles d’épinard
                    );

                    System.out.println("\n=== Monde créé ===");
                    Joueur.afficherGrille(monde, joueur);

                    // Lancer la boucle de jeu humaine
                    monde.tourDeJeuHumain(joueur);
                }

                case "2" -> {
                    // ======== Charger Partie ========
                    System.out.println("\n=== Chargement d'une partie ===");
                    System.out.print("Nom du fichier de sauvegarde à charger : ");
                    String fichier = scanner.nextLine();

                    monde.ChargementPartie(fichier);
                    joueur = monde.getJoueur();

                    if (joueur == null) {
                        System.out.println("Aucun joueur trouvé dans la sauvegarde !");
                        break;
                    }

                    System.out.println("Partie chargée avec succès depuis " + fichier);

                    monde.tourDeJeuHumain(joueur);
                }

                case "3" -> {
                    System.out.println("Fermeture du jeu. À bientôt !");
                    quitter = true;
                }

                default -> System.out.println("Choix invalide. Veuillez réessayer !");
            }
        }
    }
}
