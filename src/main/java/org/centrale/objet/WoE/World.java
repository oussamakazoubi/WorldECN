/* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE;

import java.util.*;

/**
 * Représente le monde du jeu. Contient les personnages, les monstres et les objets.
 * 
 * <p>La classe {@code World} permet de générer aléatoirement un ensemble d'entités 
 * (archers, guerriers, paysans, monstres, objets, etc.) sur une grille carrée. 
 * Elle gère également la détection de collisions, la génération de noms uniques 
 * et le déroulement d’un tour de jeu.</p>
 * 
 * @author Imane
 */
public class World {
    
    protected ArrayList<Nourriture> maListeNour;

    /** Taille de la grille par défaut (50x50). */
    public static final int TAILLE_PAR_DEFAUT = 50;

    /** Liste des personnages présents dans le monde. */
    protected ArrayList<Personnage> maListePers;

    /** Liste des monstres présents dans le monde. */
    protected ArrayList<Monstre> maListeMons;

    /** Liste des objets (armes, potions, etc.) présents dans le monde. */
    protected ArrayList<Objet> maListeobj;

    /** Ensemble des noms déjà utilisés pour éviter les doublons. */
    protected HashSet<String> nomsUtilises;

    /**
     * Constructeur par défaut. Initialise les listes et la structure du monde.
     */
    public World() {
        maListePers = new ArrayList<>();
        maListeMons = new ArrayList<>();
        maListeobj = new ArrayList<>();
        nomsUtilises = new HashSet<>();
        maListeNour = new ArrayList<>();
    }



    /**
     * Définit aléatoirement les statistiques de base d'une créature.
     *
     * @param c la créature à initialiser
     */
    public void definirStatsAlea(Creature c) {
        Random rand = new Random();

        // === GUERRIER ===
        if (c instanceof Guerrier) {
            c.setPtVie(80 + rand.nextInt(41));       // 80–120
            c.setDegAtt(50 + rand.nextInt(31));      // 50–80
            c.setPtPar(30 + rand.nextInt(21));       // 30–50
            c.setPageAtt(40 + rand.nextInt(21));     // 40–60 %
            c.setPagePar(30 + rand.nextInt(21));     // 30–50 %
            ((Personnage) c).setDistAttMax(1);       // corps à corps

            // === ARCHER ===
        } else if (c instanceof Archer) {
            c.setPtVie(60 + rand.nextInt(41));       // 60–100
            c.setDegAtt(25 + rand.nextInt(21));      // 25–45
            c.setPtPar(10 + rand.nextInt(11));       // 10–20
            c.setPageAtt(50 + rand.nextInt(26));     // 50–75 %
            c.setPagePar(20 + rand.nextInt(11));     // 20–30 %
            ((Personnage) c).setDistAttMax(3 + rand.nextInt(3)); // 3–5

            // === PAYSAN ===
        } else if (c instanceof Paysan) {
            c.setPtVie(40 + rand.nextInt(21));       // 40–60
            c.setDegAtt(5 + rand.nextInt(6));        // 5–10
            c.setPtPar(5 + rand.nextInt(6));         // 5–10
            c.setPageAtt(10 + rand.nextInt(11));     // 10–20 %
            c.setPagePar(10 + rand.nextInt(11));     // 10–20 %
            ((Personnage) c).setDistAttMax(1);       // faible portée

            // === MONSTRES ===
        } else if (c instanceof Loup) {
            c.setPtVie(40 + rand.nextInt(31));       // 40–70
            c.setDegAtt(15 + rand.nextInt(16));      // 15–30
            c.setPtPar(10 + rand.nextInt(11));       // 10–20
            c.setPageAtt(30 + rand.nextInt(21));     // 30–50 %
            c.setPagePar(10 + rand.nextInt(11));     // 10–20 %

        } else if (c instanceof Lapin) {
            c.setPtVie(20 + rand.nextInt(11));       // 20–30
            c.setDegAtt(5 + rand.nextInt(6));        // 5–10
            c.setPtPar(2 + rand.nextInt(3));         // 2–4
            c.setPageAtt(10 + rand.nextInt(11));     // 10–20 %
            c.setPagePar(5 + rand.nextInt(6));       // 5–10 %

            // === CAS PAR DÉFAUT ===
        } else {
            c.setPtVie(50 + rand.nextInt(51));       // 50–100
            c.setDegAtt(10 + rand.nextInt(11));      // 10–20
            c.setPtPar(5 + rand.nextInt(11));        // 5–15
            c.setPageAtt(20 + rand.nextInt(21));     // 20–40 %
            c.setPagePar(10 + rand.nextInt(11));     // 10–20 %
        }

        // Si c'est un personnage (humain), lui donner un nom unique
        if (c instanceof Personnage) {
            ((Personnage) c).setNom(genererNomUnique());
        }
    }


    /**
     * Génère un nom unique composé de lettres aléatoires.
     *
     * @return un nom non encore utilisé
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

    /**
     * Crée un monde aléatoire avec un nombre défini de personnages, monstres et objets.
     *
     * @param nbArcher   nombre d'archers à générer
     * @param nbPaysan   nombre de paysans à générer
     * @param nbLapin    nombre de lapins (monstres) à générer
     * @param nbGuerrier nombre de guerriers à générer
     * @param nbLoup     nombre de loups (monstres) à générer
     * @param nbPotion   nombre de potions de soin à générer
     * @param nbEpee     nombre d'épées à générer
     */
    public void creerMondeAlea(int nbArcher, int nbPaysan, int nbLapin,
                               int nbGuerrier, int nbLoup, int nbPotion, int nbEpee) {
        Random rand = new Random();
        Point2D newpos;

        // Archers
        for (int i = 0; i < nbArcher; i++) {
            Archer a = new Archer();
            definirStatsAlea(a);
            do {
                newpos = new Point2D(rand.nextInt(TAILLE_PAR_DEFAUT), rand.nextInt(TAILLE_PAR_DEFAUT));
            } while (estOccupee(newpos));
            a.setPos(newpos);
            maListePers.add(a);
        }

        // Paysans
        for (int i = 0; i < nbPaysan; i++) {
            Paysan p = new Paysan();
            definirStatsAlea(p);
            do {
                newpos = new Point2D(rand.nextInt(TAILLE_PAR_DEFAUT), rand.nextInt(TAILLE_PAR_DEFAUT));
            } while (estOccupee(newpos));
            p.setPos(newpos);
            maListePers.add(p);
        }

        // Lapins
        for (int i = 0; i < nbLapin; i++) {
            Lapin l = new Lapin();
            definirStatsAlea(l);
            do {
                newpos = new Point2D(rand.nextInt(TAILLE_PAR_DEFAUT), rand.nextInt(TAILLE_PAR_DEFAUT));
            } while (estOccupee(newpos));
            l.setPos(newpos);
            maListeMons.add(l);
        }

        // Guerriers
        for (int i = 0; i < nbGuerrier; i++) {
            Guerrier g = new Guerrier();
            definirStatsAlea(g);
            do {
                newpos = new Point2D(rand.nextInt(TAILLE_PAR_DEFAUT), rand.nextInt(TAILLE_PAR_DEFAUT));
            } while (estOccupee(newpos));
            g.setPos(newpos);
            maListePers.add(g);
        }

        // Loups
        for (int i = 0; i < nbLoup; i++) {
            Loup l = new Loup();
            definirStatsAlea(l);
            do {
                newpos = new Point2D(rand.nextInt(TAILLE_PAR_DEFAUT), rand.nextInt(TAILLE_PAR_DEFAUT));
            } while (estOccupee(newpos));
            l.setPos(newpos);
            maListeMons.add(l);
        }

        // Potions
        for (int i = 0; i < nbPotion; i++) {
            String nomPotion = "Potion" + (i + 1);
            int ptVieRendus = 10 + rand.nextInt(41); // 10 à 50 PV
            do {
                newpos = new Point2D(rand.nextInt(TAILLE_PAR_DEFAUT), rand.nextInt(TAILLE_PAR_DEFAUT));
            } while (estOccupee(newpos));
            PotionSoin p = new PotionSoin(nomPotion, newpos, ptVieRendus);
            maListeobj.add(p);
        }

        // Épées
        for (int i = 0; i < nbEpee; i++) {
            String nomEpee = "Epée" + (i + 1);
            int bonusAtt = 5 + rand.nextInt(16); // 5 à 20
            do {
                newpos = new Point2D(rand.nextInt(TAILLE_PAR_DEFAUT), rand.nextInt(TAILLE_PAR_DEFAUT));
            } while (estOccupee(newpos));
            Epée e = new Epée(nomEpee, newpos, bonusAtt);
            maListeobj.add(e);
        }
    }



    /**
     * Vérifie si une position donnée est déjà occupée par une entité (personnage ou monstre).
     *
     * @param p la position à tester
     * @return true si la case est occupée, false sinon
     */
    public boolean estOccupee(Point2D p) {
        for (Personnage pers : maListePers) {
            if (pers.getPos().equals(p)) return true;
        }
        for (Monstre mon : maListeMons) {
            if (mon.getPos().equals(p)) return true;
        }
        return false;
    }

    /**
     * Permet à une créature de chercher et utiliser un objet présent sur sa position.
     *
     * @param c la créature concernée
     */
    public void chercherObjet(Personnage p) {
        for (Objet o : maListeobj) { // Boucle for-each
            if (o.getPosition().equals(p.getPos())) {
                o.utiliserObjet(p);
       
                System.out.println(o.getNom()+ " est ramassé ");
                maListeobj.remove(o);
            }
        }
    
        for (Nourriture n : maListeNour) { // Boucle for-each
            if (n.getPosition().equals(p.getPos())) {
                n.utiliserObjet(p);
                p.getUtilisables().add(n);
                p.mettreAJourEffets();
                n.annulerEffet(p);
                
                }
               
            }
        }
    
    public Creature ChercherCible(Personnage p){
        for(Monstre m: maListeMons){
            if( (m.getPos().distance(p.getPos())<p.getDistAttMax() )){
                return m;
            }  
        }
        return null;
    }
    


    /**
     * Effectue un tour de jeu :
     * <ul>
     *   <li>Déplace les personnages et monstres.</li>
     *   <li>Déclenche les combats entre entités proches.</li>
     * </ul>
     */
    
    public void tourDeJeu() {
        List<Creature> maListeCreatures = new ArrayList<>();
        maListeCreatures.addAll(maListePers);
        maListeCreatures.addAll(maListeMons);

        for (Creature c : maListeCreatures) {
            c.deplace(this);
        }


        // Combats entre Creatures (simplifié)
        for (int i = 0; i < maListeCreatures.size(); i++) {
            for (int j = 0; j < maListeCreatures.size(); j++) {
                if (i != j) {
                    Creature c1 = maListeCreatures.get(i);
                    Creature c2 = maListeCreatures.get(j);
                    if (c1 instanceof Combattant) {
                        ((Combattant) c1).combattre(c2);
                    }

                }
            }
        }
    }

    
     public void tourDeJeuHumain(Joueur j) {
        j.choisirPreference(this);
    }

    /**
     * Affiche les informations de toutes les entités du monde :
     * personnages, monstres et objets.
     */
    public void affiche() {
        for (Personnage p : maListePers) {
            p.affiche();
        }
        for (Monstre m : maListeMons) {
            m.affiche();
        }
        for (Objet o : maListeobj) {
            o.affiche();
        }
    }


}
