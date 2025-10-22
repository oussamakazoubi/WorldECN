/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.centrale.objet.WoE;

import java.util.*;
import java.io.*;
import java.util.StringTokenizer;

/**
 * Représente le monde du jeu, contenant l’ensemble des entités (personnages, monstres et objets).
 *
 * <p>La classe {@code World} gère la création, le positionnement, le déplacement et
 * les interactions entre les différentes entités du jeu. Elle prend également en charge
 * la sauvegarde et le chargement des parties.</p>
 *
 * <p>Le monde est modélisé comme une grille bidimensionnelle dont les dimensions peuvent
 * être définies. Les entités sont réparties aléatoirement sur cette grille lors de la
 * génération du monde.</p>
 *
 * @author Oussama
 * @see Personnage
 * @see Monstre
 * @see Objet
 * @see Joueur
 */
public class World {

    // ===================== ATTRIBUTS =====================

    /** Taille du monde (longueur et largeur de la grille). */
    private int longueur;
    private int largeur;

    /** Liste des personnages présents dans le monde. */
    protected ArrayList<Personnage> maListePers;

    /** Liste des monstres présents dans le monde. */
    protected ArrayList<Monstre> maListeMons;

    /** Liste des objets (armes, potions, nourritures, etc.) présents dans le monde. */
    protected ArrayList<Objet> maListeobj;

    /** Ensemble des noms déjà utilisés, pour garantir leur unicité. */
    protected HashSet<String> nomsUtilises;

    /** Joueur contrôlant un personnage spécifique. */
    private Joueur joueur;

    // ===================== CONSTRUCTEUR =====================

    /**
     * Constructeur par défaut.
     * <p>Initialise les listes du monde et les structures de données nécessaires.</p>
     */
    public World() {
        maListePers = new ArrayList<>();
        maListeMons = new ArrayList<>();
        maListeobj = new ArrayList<>();
        nomsUtilises = new HashSet<>();
    }

    // ===================== ACCESSEURS =====================

    public ArrayList<Personnage> getMaListePers() { return maListePers; }
    public ArrayList<Monstre> getMaListeMons() { return maListeMons; }
    public ArrayList<Objet> getMaListeobj() { return maListeobj; }
    public int getLongueur() { return longueur; }
    public int getLargeur() { return largeur; }
    public Joueur getJoueur() { return joueur; }

    public void setMaListePers(ArrayList<Personnage> maListePers) { this.maListePers = maListePers; }
    public void setMaListeMons(ArrayList<Monstre> maListeMons) { this.maListeMons = maListeMons; }
    public void setMaListeobj(ArrayList<Objet> maListeobj) { this.maListeobj = maListeobj; }
    public void setLongueur(int longueur) { this.longueur = longueur; }
    public void setLargeur(int largeur) { this.largeur = largeur; }
    public void setJoueur(Joueur joueur) { this.joueur = joueur; }

    // ===================== INITIALISATION DES CRÉATURES =====================

    /**
     * Définit aléatoirement les statistiques d’une créature selon son type.
     *
     * @param c la créature à initialiser
     */
    public void definirStatsAlea(Creature c) {
        Random rand = new Random();

        if (c instanceof Guerrier) {
            // Guerrier : robuste et fort
            c.setPtVie(80 + rand.nextInt(41));
            c.setDegAtt(50 + rand.nextInt(31));
            c.setPtPar(30 + rand.nextInt(21));
            c.setPageAtt(40 + rand.nextInt(21));
            c.setPagePar(30 + rand.nextInt(21));
            ((Personnage) c).setDistAttMax(1);

        } else if (c instanceof Archer) {
            // Archer : agile, attaque à distance
            c.setPtVie(60 + rand.nextInt(41));
            c.setDegAtt(25 + rand.nextInt(21));
            c.setPtPar(10 + rand.nextInt(11));
            c.setPageAtt(50 + rand.nextInt(26));
            c.setPagePar(20 + rand.nextInt(11));
            ((Archer) c).setNbFleches(10 + rand.nextInt(11));
            ((Personnage) c).setDistAttMax(3 + rand.nextInt(3));

        } else if (c instanceof Paysan) {
            // Paysan : faible et non combattant
            c.setPtVie(40 + rand.nextInt(21));
            c.setDegAtt(5 + rand.nextInt(6));
            c.setPtPar(5 + rand.nextInt(6));
            c.setPageAtt(10 + rand.nextInt(11));
            c.setPagePar(10 + rand.nextInt(11));
            ((Personnage) c).setDistAttMax(1);

        } else if (c instanceof Loup) {
            // Loup : monstre agressif
            c.setPtVie(40 + rand.nextInt(31));
            c.setDegAtt(15 + rand.nextInt(16));
            c.setPtPar(10 + rand.nextInt(11));
            c.setPageAtt(30 + rand.nextInt(21));
            c.setPagePar(10 + rand.nextInt(11));

        } else if (c instanceof Lapin) {
            // Lapin : créature passive
            c.setPtVie(20 + rand.nextInt(11));
            c.setDegAtt(5 + rand.nextInt(6));
            c.setPtPar(2 + rand.nextInt(3));
            c.setPageAtt(10 + rand.nextInt(11));
            c.setPagePar(5 + rand.nextInt(6));

        } else {
            // Par défaut
            c.setPtVie(50 + rand.nextInt(51));
            c.setDegAtt(10 + rand.nextInt(11));
            c.setPtPar(5 + rand.nextInt(11));
            c.setPageAtt(20 + rand.nextInt(21));
            c.setPagePar(10 + rand.nextInt(11));
        }

        if (c instanceof Personnage) {
            ((Personnage) c).setNom(genererNomUnique());
        }
    }

    /**
     * Génère un nom unique composé de lettres aléatoires.
     *
     * @return un nom non encore utilisé dans le monde
     */
    private String genererNomUnique() {
        Random rand = new Random();
        String nom;
        do {
            int longueur = 3 + rand.nextInt(5);
            StringBuilder nomBuilder = new StringBuilder();
            for (int i = 0; i < longueur; i++) {
                char lettre = (char) ('a' + rand.nextInt(26));
                nomBuilder.append(lettre);
            }
            nom = nomBuilder.toString();
        } while (nomsUtilises.contains(nom));

        nomsUtilises.add(nom);
        return nom;
    }

    // ===================== GÉNÉRATION DU MONDE =====================

    /**
     * Crée un monde aléatoire peuplé de créatures et d’objets.
     *
     * @param nbArcher   nombre d’archers à générer
     * @param nbPaysan   nombre de paysans à générer
     * @param nbLapin    nombre de lapins à générer
     * @param nbGuerrier nombre de guerriers à générer
     * @param nbLoup     nombre de loups à générer
     * @param nbPotion   nombre de potions de soin
     * @param nbEpee     nombre d’épées
     * @param nbChamp    nombre de champignons pourris
     * @param nbFeuille  nombre de feuilles d’épinard
     */
    public void creerMondeAlea(int nbArcher, int nbPaysan, int nbLapin,
                               int nbGuerrier, int nbLoup,
                               int nbPotion, int nbEpee, int nbChamp, int nbFeuille) {
        Random rand = new Random();
        Point2D newpos;

        // === Création des personnages et monstres ===
        for (int i = 0; i < nbArcher; i++) {
            Archer a = new Archer();
            definirStatsAlea(a);
            do { newpos = new Point2D(rand.nextInt(longueur), rand.nextInt(largeur)); }
            while (estOccupee(newpos));
            a.setPos(newpos);
            maListePers.add(a);
        }

        for (int i = 0; i < nbPaysan; i++) {
            Paysan p = new Paysan();
            definirStatsAlea(p);
            do { newpos = new Point2D(rand.nextInt(longueur), rand.nextInt(largeur)); }
            while (estOccupee(newpos));
            p.setPos(newpos);
            maListePers.add(p);
        }

        for (int i = 0; i < nbLapin; i++) {
            Lapin l = new Lapin();
            definirStatsAlea(l);
            do { newpos = new Point2D(rand.nextInt(longueur), rand.nextInt(largeur)); }
            while (estOccupee(newpos));
            l.setPos(newpos);
            maListeMons.add(l);
        }

        for (int i = 0; i < nbGuerrier; i++) {
            Guerrier g = new Guerrier();
            definirStatsAlea(g);
            do { newpos = new Point2D(rand.nextInt(longueur), rand.nextInt(largeur)); }
            while (estOccupee(newpos));
            g.setPos(newpos);
            maListePers.add(g);
        }

        for (int i = 0; i < nbLoup; i++) {
            Loup l = new Loup();
            definirStatsAlea(l);
            do { newpos = new Point2D(rand.nextInt(longueur), rand.nextInt(longueur)); }
            while (estOccupee(newpos));
            l.setPos(newpos);
            maListeMons.add(l);
        }

        // === Objets ===
        for (int i = 0; i < nbPotion; i++) {
            String nomPotion = "Potion" + (i + 1);
            int ptVieRendus = 10 + rand.nextInt(41);
            do { newpos = new Point2D(rand.nextInt(longueur), rand.nextInt(longueur)); }
            while (estOccupee(newpos));
            maListeobj.add(new PotionSoin(nomPotion, newpos, ptVieRendus));
        }

        for (int i = 0; i < nbEpee; i++) {
            String nomEpee = "Epée" + (i + 1);
            int bonusAtt = 5 + rand.nextInt(16);
            do { newpos = new Point2D(rand.nextInt(longueur), rand.nextInt(longueur)); }
            while (estOccupee(newpos));
            maListeobj.add(new Epee(nomEpee, newpos, bonusAtt));
        }

        for (int i = 0; i < nbChamp; i++) {
            String nomChamp = "ChampignonPourri" + (i + 1);
            int dureeEffet = 2 + rand.nextInt(5);
            int malusDefense = 10 + rand.nextInt(32);
            do { newpos = new Point2D(rand.nextInt(longueur), rand.nextInt(longueur)); }
            while (estOccupee(newpos));
            maListeobj.add(new ChampignonPourri(nomChamp, newpos, dureeEffet, malusDefense));
        }

        for (int i = 0; i < nbFeuille; i++) {
            String nomFeuille = "FeuilleEpinart" + (i + 1);
            int dureeEffet = 2 + rand.nextInt(5);
            int bonusDegAtt = 10 + rand.nextInt(32);
            do { newpos = new Point2D(rand.nextInt(longueur), rand.nextInt(longueur)); }
            while (estOccupee(newpos));
            maListeobj.add(new FeuilleEpinart(nomFeuille, newpos, dureeEffet, bonusDegAtt));
        }
    }

    // ===================== MÉCANIQUES DU JEU =====================

    /**
     * Vérifie si une position donnée est déjà occupée par une entité.
     *
     * @param p la position à tester
     * @return {@code true} si la case est occupée, {@code false} sinon
     */
    public boolean estOccupee(Point2D p) {
        for (Personnage pers : maListePers) if (pers.getPos().equals(p)) return true;
        for (Monstre mon : maListeMons) if (mon.getPos().equals(p)) return true;
        if (p.getX()<0 || p.getY()<0 || p.getX()>=this.longueur || p.getY()>=this.largeur) return true;
        return false;
    }

    /**
     * Permet à une créature de chercher et d’utiliser un objet présent sur sa case.
     *
     * @param p le personnage concerné
     */
    public void chercherObjet(Personnage p) {
        Iterator<Objet> it = maListeobj.iterator();
        while (it.hasNext()) {
            Objet o = it.next();
            if (o.getPosition().equals(p.getPos())) {
                if (p == joueur.getPersoJoueur()) {
                    joueur.getInventaire().add(o);
                    System.out.println(o.getNom() + " est ramassé.");
                } else {
                    o.utiliserObjet(p);
                    System.out.println(o.getNom() + " est utilisé.");
                }
                if (!(o instanceof NuageToxique)) it.remove();
            }
        }
    }

    /**
     * Retourne la liste des cibles qu’un personnage peut attaquer.
     *
     * @param p le personnage attaquant
     * @return liste des créatures à portée
     */
    public ArrayList<Creature> ChercherCibles(Personnage p) {
        ArrayList<Creature> toutes = new ArrayList<>();
        toutes.addAll(maListePers);
        toutes.addAll(maListeMons);
        ArrayList<Creature> cibles = new ArrayList<>();

        for (Creature cible : toutes) {
            double dist = p.getPos().distance(cible.getPos());
            if (dist <= p.getDistAttMax() && p != cible) {
                cibles.add(cible);
            }
        }
        return cibles;
    }

    /**
     * Effectue un tour complet du jeu :
     * <ul>
     *   <li>Déplacement des créatures et objets déplaçables.</li>
     *   <li>Détection et résolution des combats.</li>
     * </ul>
     */
    public void tourDeJeu() {
        ArrayList<Creature> toutes = new ArrayList<>();
        toutes.addAll(maListePers);
        toutes.addAll(maListeMons);

        // Déplacement de toutes les créatures
        for (Creature c : toutes) c.deplace(this);

        // Déplacement des objets déplaçables (ex. : NuageToxique)
        for (Objet o : maListeobj) {
            if (o instanceof Deplacable) ((Deplacable) o).deplace(this);
        }

        // Résolution des combats
        for (int i = 0; i < toutes.size(); i++) {
            Creature c1 = toutes.get(i);
            for (int j = 0; j < toutes.size(); j++) {
                if (i != j) {
                    Creature c2 = toutes.get(j);
                    if (c1 instanceof Combattant) {
                        ((Combattant) c1).combattre(c2);
                    }
                }
            }
            for (Objet o : maListeobj) {
                if (o instanceof Combattant) ((Combattant) o).combattre(c1);
            }
        }
    }

    /**
     * Lance un tour contrôlé par le joueur humain.
     *
     * <p>Cette méthode affiche le menu d’action du joueur, puis, à la fin du tour,
     * supprime toutes les créatures mortes (ayant des points de vie ≤ 0)
     * des listes du monde.</p>
     *
     * @param j le joueur actif
     */
    public void tourDeJeuHumain(Joueur j) {
        // Tour du joueur
        j.choisirPreference(this);

        // === Nettoyage des entités mortes ===
        // Supprime tous les personnages ou monstres ayant 0 PV ou moins


        System.out.println("\n[Mise à jour] Les créatures mortes ont été retirées du monde.");
    }


    // ===================== SAUVEGARDE & CHARGEMENT =====================

    /**
     * Sauvegarde l’état complet du monde dans un fichier texte.
     *
     * @param nomFichier chemin du fichier de sauvegarde
     */
    public void SauvegardePartie(String nomFichier) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichier))) {
            writer.write("Largeur " + this.largeur);
            writer.newLine();
            writer.write("Longueur " + this.longueur);
            writer.newLine();

            for (Personnage p : maListePers) {
                if (joueur == null || !p.equals(joueur.getPersoJoueur())) {
                    writer.write(p.getTexteSauvegarde());
                    writer.newLine();
                }
            }

            for (Monstre m : maListeMons) {
                writer.write(m.getTexteSauvegarde());
                writer.newLine();
            }

            for (Objet o : maListeobj) {
                writer.write(o.getTexteSauvegarde());
                writer.newLine();
            }

            if (joueur != null) {
                writer.write(joueur.getTexteSauvegarde());
                writer.newLine();
                for (Objet o : joueur.getInventaire()) {
                    writer.write("Inventaire " + o.getTexteSauvegarde());
                    writer.newLine();
                }
            }

            System.out.println("Sauvegarde effectuée dans le fichier : " + nomFichier);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    /**
     * Recharge un monde depuis un fichier de sauvegarde.
     *
     * @param nomFichier chemin du fichier à charger
     */
    public void ChargementPartie(String nomFichier) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomFichier))) {
            maListePers.clear();
            maListeMons.clear();
            maListeobj.clear();

            String ligne;
            while ((ligne = reader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(ligne, " ");
                String type = st.nextToken();

                switch (type) {
                    case "Largeur": this.largeur = Integer.parseInt(st.nextToken()); break;
                    case "Longueur": this.longueur = Integer.parseInt(st.nextToken()); break;
                    case "Guerrier": maListePers.add(new Guerrier(ligne)); break;
                    case "Archer": maListePers.add(new Archer(ligne)); break;
                    case "Paysan": maListePers.add(new Paysan(ligne)); break;
                    case "Loup": maListeMons.add(new Loup(ligne)); break;
                    case "Lapin": maListeMons.add(new Lapin(ligne)); break;
                    case "PotionSoin": maListeobj.add(new PotionSoin(ligne)); break;
                    case "Epee": maListeobj.add(new Epee(ligne)); break;
                    case "FeuilleEpinart": maListeobj.add(new FeuilleEpinart(ligne)); break;
                    case "ChampignonPourri": maListeobj.add(new ChampignonPourri(ligne)); break;
                    case "Joueur": this.joueur = new Joueur(ligne); break;
                    case "Inventaire":
                        String reste = ligne.substring("Inventaire".length()).trim();
                        Objet obj = null;
                        if (reste.startsWith("PotionSoin")) obj = new PotionSoin(reste);
                        else if (reste.startsWith("Epee")) obj = new Epee(reste);
                        else if (reste.startsWith("FeuilleEpinart")) obj = new FeuilleEpinart(reste);
                        else if (reste.startsWith("ChampignonPourri")) obj = new ChampignonPourri(reste);
                        if (obj != null && joueur != null) joueur.getInventaire().add(obj);
                        break;
                }
            }
            System.out.println("Chargement du fichier " + nomFichier + " terminé avec succès.");
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement : " + e.getMessage());
        }
    }

    // ===================== AFFICHAGE =====================

    /**
     * Affiche les informations de toutes les entités présentes dans le monde.
     */
    public void affiche() {
        for (Personnage p : maListePers) p.affiche();
        for (Monstre m : maListeMons) m.affiche();
        for (Objet o : maListeobj) o.affiche();
    }
}
