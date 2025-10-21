/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.centrale.objet.WoE;

import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Classe représentant un joueur humain contrôlant un personnage dans le monde du jeu WoE.
 *
 * <p>Un {@code Joueur} possède :</p>
 * <ul>
 *   <li>Un nom réel et un pseudonyme unique</li>
 *   <li>Un {@link Personnage} jouable (ex : {@link Guerrier}, {@link Archer})</li>
 *   <li>Un inventaire contenant les {@link Objet} ramassés</li>
 *   <li>Une liste des fichiers de sauvegarde associés</li>
 * </ul>
 *
 * <p>Le joueur peut interagir avec le monde en se déplaçant, combattant, utilisant
 * des objets, ou en sauvegardant/chargeant une partie.</p>
 *
 * @author Imane
 * @see World
 * @see Personnage
 * @see Objet
 */
public class Joueur {

    /** Nom du joueur (ex. : nom réel ou identifiant personnel) */
    private String nom;

    /** Pseudonyme du joueur utilisé dans le jeu */
    private String pseudo;

    /** Liste des classes de personnages jouables (Guerrier, Archer, etc.) */
    private static final ArrayList<Class<? extends Personnage>> PersoJouable =
            new ArrayList<>(Arrays.asList(Guerrier.class, Archer.class));

    /** Personnage contrôlé par le joueur */
    private Personnage persoJoueur;

    /** Inventaire contenant les objets ramassés ou utilisables */
    private ArrayList<Objet> inventaire;

    /** Liste des noms de fichiers de sauvegarde associés au joueur */
    private ArrayList<String> listeSauvegarde;

    // ==========================
    // ===== CONSTRUCTEURS =====
    // ==========================

    /**
     * Constructeur par défaut.
     * Initialise les attributs à des valeurs neutres.
     */
    public Joueur() {
        nom = "";
        pseudo = "";
        inventaire = new ArrayList<>();
        listeSauvegarde = new ArrayList<>();
    }

    /**
     * Constructeur complet.
     *
     * @param nom Nom du joueur
     * @param pseudo Pseudonyme du joueur
     */
    public Joueur(String nom, String pseudo) {
        this();
        this.nom = nom;
        this.pseudo = pseudo;
    }

    /**
     * Constructeur par copie.
     *
     * @param j Joueur à copier
     */
    public Joueur(Joueur j) {
        this();
        this.nom = j.nom;
        this.pseudo = j.pseudo;
        this.inventaire = j.inventaire;
    }

    /**
     * Constructeur chargeant un joueur à partir d'une ligne de sauvegarde.
     *
     * @param ligne ligne de texte contenant les données du joueur
     */
    public Joueur(String ligne) {
        this();
        try {
            StringTokenizer st = new StringTokenizer(ligne, " ");
            st.nextToken(); // "Joueur"
            String typePerso = st.nextToken();

            if (typePerso.equals("Guerrier")) {
                persoJoueur = new Guerrier();
            } else if (typePerso.equals("Archer")) {
                persoJoueur = new Archer();
            } else {
                persoJoueur = new Paysan(); // fallback
            }

            String nomPerso = st.nextToken();
            persoJoueur.setNom(nomPerso);
            persoJoueur.setPtVie(Integer.parseInt(st.nextToken()));
            persoJoueur.setDegAtt(Integer.parseInt(st.nextToken()));
            persoJoueur.setPtPar(Integer.parseInt(st.nextToken()));
            persoJoueur.setPageAtt(Integer.parseInt(st.nextToken()));
            persoJoueur.setPagePar(Integer.parseInt(st.nextToken()));
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            persoJoueur.setPos(new Point2D(x, y));

            if (persoJoueur instanceof Guerrier g) {
                g.setDistAttMax(Integer.parseInt(st.nextToken()));
            } else if (persoJoueur instanceof Archer a) {
                a.setDistAttMax(Integer.parseInt(st.nextToken()));
                a.setNbFleches(Integer.parseInt(st.nextToken()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==========================
    // ===== GETTERS/SETTERS ====
    // ==========================

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Personnage getPersoJoueur() {
        return persoJoueur;
    }

    public void setPersoJoueur(Personnage persoJoueur) {
        this.persoJoueur = persoJoueur;
    }

    public ArrayList<Objet> getInventaire() {
        return inventaire;
    }

    public void setInventaire(ArrayList<Objet> inventaire) {
        this.inventaire = inventaire;
    }

    public ArrayList<Class<? extends Personnage>> getPersoJouable() {
        return PersoJouable;
    }

    // ==========================
    // ===== MÉTHODES JEU ======
    // ==========================

    /**
     * Permet au joueur de choisir un personnage jouable.
     *
     * @param monde Monde dans lequel le personnage sera ajouté
     */
    public void ChoisirPersonnage(World monde) {
        inventaire = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        Personnage perso = null;

        while (perso == null) {
            System.out.println("Rentrer un personnage jouable :");
            String persoChoisi = scanner.nextLine();

            for (Class<? extends Personnage> cl : PersoJouable) {
                if (persoChoisi.equalsIgnoreCase(cl.getSimpleName())) {
                    System.out.println("Rentrer le nom du personnage choisi :");
                    String NomPersoChoisi = scanner.nextLine();
                    try {
                        perso = cl.getDeclaredConstructor().newInstance();
                        monde.definirStatsAlea(perso);
                        perso.setNom(NomPersoChoisi);
                        this.persoJoueur = perso;
                        monde.maListePers.add(perso);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            if (perso == null) {
                System.out.println("Classe non jouable. Réessayez !");
                ChoisirPersonnage(monde);
            }
        }
    }

    /**
     * Gère le menu d’action du joueur (déplacement, combat, inventaire...).
     *
     * @param monde Monde dans lequel se trouve le joueur
     */
    public void choisirPreference(World monde) {
        if (this.persoJoueur == null) {
            System.out.println("Aucun personnage valide choisi. Fin du tour.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        String choix;

        

        do {
            Joueur.afficherGrille(monde, this);
            System.out.println("""
                === MENU DU JOUEUR ===
                1 = Se déplacer
                2 = Combattre
                3 = Tour suivante
                4 = Utiliser inventaire
                5 = Afficher le monde
                6 = Sauvegarder la partie
                7 = Charger une partie
                8 = Quitter le jeu
            """);

            System.out.print("Votre choix : ");
            choix = scanner.nextLine();



            monde.maListePers.removeIf(p -> p.getPtVie() <= 0);
            monde.maListeMons.removeIf(m -> m.getPtVie() <= 0);


            switch (choix) {
                case "1", "deplacer" -> this.deplaceJoueur(monde);
                case "2", "combattre" -> {this.combattreJoueur(monde);
                                          monde.nettoyerCreaturesMortes(); }
                case "3", "Tour Suivante" -> monde.tourDeJeu();
                case "4", "Inventaire" -> utiliserObjetInventaire();
                case "5", "Afficher monde" -> monde.affiche();
                case "6", "Sauvegarder partie" ->  {
                    System.out.print("Nom du fichier de sauvegarde : ");
                    String fichier = scanner.nextLine();
                    monde.SauvegardePartie(fichier);
                    listeSauvegarde.add(fichier);
                    System.out.println("Partie sauvegardée dans " + fichier);
                }
                case "7", "Charger partie" -> {
                    System.out.print("Nom du fichier à charger : ");
                    String fichier = scanner.nextLine();
                    monde.ChargementPartie(fichier);
                    System.out.println("Partie chargée depuis " + fichier);
                }
                case "8", "quitter" -> System.out.println("Fin du jeu. À bientôt !");
                default -> System.out.println("Choix invalide. Réessayez !");
            }

        } while (!choix.equals("8"));

        monde.chercherObjet(this.persoJoueur);
    }

    /**
     * Permet au joueur d’utiliser un objet de son inventaire.
     */
    public void utiliserObjetInventaire() {
        if (inventaire.isEmpty()) {
            System.out.println("Votre inventaire est vide !");
            return;
        }

        System.out.println("=== Inventaire ===");
        for (int i = 0; i < inventaire.size(); i++) {
            System.out.println((i + 1) + " - " + inventaire.get(i).toString());
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Entrez le numéro de l’objet à utiliser : ");

        try {
            int choix = sc.nextInt();
            if (choix < 1 || choix > inventaire.size()) {
                System.out.println("Numéro invalide !");
                return;
            }

            Objet objet = inventaire.get(choix - 1);
            System.out.println("Vous utilisez : " + objet.getClass().getSimpleName());

            objet.utiliserObjet(this.persoJoueur);
            inventaire.remove(choix - 1);

            if (objet instanceof Nourriture n) {
                persoJoueur.getUtilisables().add(n);
            }

        } catch (Exception e) {
            System.out.println("Entrée invalide. Veuillez entrer un numéro.");
            utiliserObjetInventaire();
        }
    }

    /**
     * Permet de déplacer le joueur sur la carte.
     *
     * @param monde Monde dans lequel le déplacement s’effectue
     */
    public void deplaceJoueur(World monde) {
        System.out.println("\nVotre position est : " + persoJoueur.getPos().toString());
        System.out.println("Choisissez une direction : (Z=haut, S=bas, Q=gauche, D=droite)");
        System.out.print("Votre choix : ");

        Scanner sc = new Scanner(System.in);
        char choix = sc.next().toLowerCase().charAt(0);
        int persoX = persoJoueur.getPos().getX();
        int persoY = persoJoueur.getPos().getY();

        int dx = 0, dy = 0;
        if (choix == 'z' && persoY < monde.getLargeur() - 1) dy = 1;
        else if (choix == 's' && persoY > 0) dy = -1;
        else if (choix == 'q' && persoX > 0) dx = -1;
        else if (choix == 'd' && persoX < monde.getLargeur() - 1) dx = 1;
        else {
            System.out.println("Entrée invalide. Vous restez sur place.");
            deplaceJoueur(monde);
            return;
        }

        Point2D newPos = new Point2D(persoX + dx, persoY + dy);
        if (monde.estOccupee(newPos)) {
            System.out.println("Position occupée !");
            deplaceJoueur(monde);
            return;
        }

        persoJoueur.getPos().translate(dx, dy);
        monde.chercherObjet(this.persoJoueur);
        System.out.println(persoJoueur.getNom() + " se déplace en " + persoJoueur.getPos().toString());
    }

    /**
     * Retourne le texte de sauvegarde représentant le joueur et son personnage.
     *
     * @return Chaîne de caractères à sauvegarder
     */
    public String getTexteSauvegarde() {
        if (persoJoueur == null) return "Joueur " + nom;

        StringBuilder sb = new StringBuilder();
        sb.append("Joueur ");
        sb.append(persoJoueur.getClass().getSimpleName()).append(" ");
        sb.append(persoJoueur.getNom()).append(" ");
        sb.append(persoJoueur.getPtVie()).append(" ");
        sb.append(persoJoueur.getDegAtt()).append(" ");
        sb.append(persoJoueur.getPtPar()).append(" ");
        sb.append(persoJoueur.getPageAtt()).append(" ");
        sb.append(persoJoueur.getPagePar()).append(" ");
        sb.append(persoJoueur.getPos().getX()).append(" ");
        sb.append(persoJoueur.getPos().getY());

        if (persoJoueur instanceof Guerrier g) {
            sb.append(" ").append(g.getDistAttMax());
        } else if (persoJoueur instanceof Archer a) {
            sb.append(" ").append(a.getDistAttMax());
            sb.append(" ").append(a.getNbFleches());
        }

        return sb.toString();
    }

    // ==========================
    // ===== COMBAT =============
    // ==========================

    /**
     * Permet au joueur de choisir et d’attaquer une cible.
     *
     * @param monde Monde dans lequel le combat se déroule
     */
    private void combattreJoueur(World monde) {
        ArrayList<Creature> cibles = monde.ChercherCibles(this.persoJoueur);
        if (cibles.isEmpty()) {
            System.out.println("Aucune cible à portée !");
            return;
        }

        System.out.println("=== Cibles disponibles ===");
        for (int i = 0; i < cibles.size(); i++) {
            Creature c = cibles.get(i);
            String nomCible = (c instanceof Personnage p) ? p.getNom() : "";
            System.out.println((i + 1) + " - " + nomCible + " (" + c.getClass().getSimpleName() +
                    ") à la position " + c.getPos());
        }
        System.out.println((cibles.size() + 1) + " - Revenir au menu précédent.");

        Scanner input = new Scanner(System.in);
        System.out.print("Choisissez la cible à attaquer (numéro) : ");

        try {
            int choixCible = input.nextInt();
            if (choixCible < 1 || choixCible > cibles.size() + 1) {
                System.out.println("Numéro invalide. Aucun combat effectué.");
                combattreJoueur(monde);
                return;
            } else if (choixCible == cibles.size() + 1) {
                return;
            }

            Creature cible = cibles.get(choixCible - 1);
            this.persoJoueur.getClass()
                    .getMethod("combattre", Creature.class)
                    .invoke(this.persoJoueur, cible);

        } catch (Exception e) {
            System.out.println("Erreur de combat : " + e.getMessage());
        }
    }



    /**
     * Affiche la grille du monde sous forme de caractères.
     *
     * @param monde  le monde à afficher
     * @param joueur le joueur actif (pour distinguer son personnage)
     */
   /* public static void afficherGrille(World monde, Joueur joueur) {
        int longueur = monde.getLongueur();
        int largeur = monde.getLargeur();

        // Initialisation de la grille vide
        char[][] grille = new char[largeur][longueur];
        for (int y = 0; y < largeur; y++) {
            for (int x = 0; x < longueur; x++) {
                grille[y][x] = '.';
            }
        }

        // === Placement des entités ===
        for (Personnage p : monde.getMaListePers()) {
            if (p.getPos() == null) continue;
            int x = p.getPos().getX();
            int y = p.getPos().getY();
            if (joueur != null && joueur.getPersoJoueur() == p) {
                grille[y][x] = 'J'; // Joueur
            } else {
                grille[y][x] = 'P'; // Autre personnage
            }
        }

        for (Monstre m : monde.getMaListeMons()) {
            if (m.getPos() == null) continue;
            int x = m.getPos().getX();
            int y = m.getPos().getY();
            grille[y][x] = 'M';
        }

        for (Objet o : monde.getMaListeobj()) {
            if (o.getPosition() == null) continue;
            int x = o.getPosition().getX();
            int y = o.getPosition().getY();
            grille[y][x] = 'O';
        }

        // === Affichage de la grille ===
        System.out.println("\n=== CARTE DU MONDE ===");
        for (int y = largeur - 1; y >= 0; y--) {
            for (int x = 0; x < longueur; x++) {
                System.out.print(grille[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println("========================\n");
    }*/
    /**
 * Affiche la grille du monde sous forme de caractères, avec une légende détaillée.
 *
 * Symboles utilisés :
 *  J  : Joueur (personnage contrôlé par l'utilisateur)
 *  A  : Archer (PNJ)
 *  G  : Guerrier (PNJ)
 *  p  : Paysan (PNJ)
 *  P  : Autre personnage générique (si d'autres sous-classes existent)
 *  L  : Loup (monstre)
 *  r  : Lapin (monstre)
 *  M  : Monstre générique
 *  H  : Potion de soin (Heal)
 *  E  : Épée
 *  F  : Feuille d'épinard
 *  C  : Champignon pourri
 *  X  : Nuage toxique / objet déplaçable toxique
 *  O  : Objet générique
 *
 * Priorité d'affichage si plusieurs entités sur une même case :
 *  Joueur > Personnage non-joueur > Monstre > Objet
 *
 * @param monde  le monde à afficher
 * @param joueur le joueur actif (pour distinguer son personnage)
 */
public static void afficherGrille(World monde, Joueur joueur) {
    int longueur = monde.getLongueur();
    int largeur = monde.getLargeur();

    // grille de chaînes pour pouvoir avoir 1 ou 2 caractères par case
    String[][] grille = new String[largeur][longueur];
    for (int y = 0; y < largeur; y++) {
        for (int x = 0; x < longueur; x++) {
            grille[y][x] = ". "; // case vide (avec un espace pour alignement)
        }
    }

    // ===== Placement des objets (les objets ont la plus faible priorité d'affichage) =====
    for (Objet o : monde.getMaListeobj()) {
        if (o.getPosition() == null) continue;
        int x = o.getPosition().getX();
        int y = o.getPosition().getY();
        String sym = "O ";
        if (o instanceof PotionSoin) sym = "H "; // Heal
        else if (o instanceof Epee) sym = "E ";
        else if (o instanceof FeuilleEpinart) sym = "F ";
        else if (o instanceof ChampignonPourri) sym = "C ";
        else if (o instanceof NuageToxique) sym = "X ";
        // n'écrase que le vide pour garder priorité
        if (grille[y][x].equals(". ")) grille[y][x] = sym;
    }

    // ===== Placement des monstres (écrase objets si nécessaire) =====
    for (Monstre m : monde.getMaListeMons()) {
        if (m.getPos() == null) continue;
        int x = m.getPos().getX();
        int y = m.getPos().getY();
        String sym = "M ";
        if (m instanceof Loup) sym = "L ";
        else if (m instanceof Lapin) sym = "r ";
        // écrase l'objet éventuel
        grille[y][x] = sym;
    }

    // ===== Placement des personnages non-joueurs (écrase monstres/objets) =====
    for (Personnage p : monde.getMaListePers()) {
        if (p.getPos() == null) continue;
        int x = p.getPos().getX();
        int y = p.getPos().getY();

        // Si c'est le personnage du joueur, on traitera ensuite (priorité)
        if (joueur != null && joueur.getPersoJoueur() == p) {
            continue;
        }

        String sym = "P ";
        if (p instanceof Archer) sym = "A ";
        else if (p instanceof Guerrier) sym = "G ";
        else if (p instanceof Paysan) sym = "p ";
        // écrase ce qu'il y avait (monstre ou objet)
        grille[y][x] = sym;
    }

    // ===== Placement du joueur (plus haute priorité) =====
    if (joueur != null && joueur.getPersoJoueur() != null && joueur.getPersoJoueur().getPos() != null) {
        int x = joueur.getPersoJoueur().getPos().getX();
        int y = joueur.getPersoJoueur().getPos().getY();
        grille[y][x] = "J ";
    }

    // ===== Légende détaillée =====
    System.out.println("\n=== LÉGENDE DE LA CARTE ===");
    System.out.println("J  : Joueur (votre personnage)");
    System.out.println("A  : Archer (personnage non-joueur)");
    System.out.println("G  : Guerrier (personnage non-joueur)");
    System.out.println("p  : Paysan (personnage non-joueur)");
    System.out.println("L  : Loup (monstre)");
    System.out.println("r  : Lapin (monstre)");
    System.out.println("H  : Potion de soin (Heal)");
    System.out.println("E  : Épée");
    System.out.println("F  : Feuille d'épinard");
    System.out.println("C  : Champignon pourri");
    System.out.println("X  : Nuage toxique (objet mobile)");
    System.out.println(".  : Case vide\n");

    // ===== Affichage de la grille =====
    System.out.println("=== CARTE DU MONDE ===");
    // on affiche de haut en bas (y = largeur-1 -> 0) comme dans la version initiale
    for (int y = largeur - 1; y >= 0; y--) {
        StringBuilder ligne = new StringBuilder();
        for (int x = 0; x < longueur; x++) {
            ligne.append(grille[y][x]);
        }
        System.out.println(ligne.toString());
    }
    System.out.println("========================\n");
}

}