/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE;

import java.util.StringTokenizer;
import java.util.Random;

/**
 * Représente un {@code Guerrier}, un personnage spécialisé dans le combat rapproché.
 *
 * <p>Le guerrier hérite de {@link Personnage} et se distingue par sa puissance
 * d’attaque et sa capacité à infliger des dégâts importants en combat corps à corps.</p>
 *
 * <p>Contrairement à un {@link Archer}, le guerrier ne possède aucune arme à distance
 * et ne peut attaquer que les ennemis adjacents.</p>
 *
 * <p>Le système de combat repose sur des jets aléatoires simulant les chances
 * d’attaque et de parade pour déterminer le résultat d’un affrontement.</p>
 *
 * @author Oussama
 * @see Personnage
 * @see Creature
 */
public class Guerrier extends Personnage implements Combattant {

    // ===================== CONSTRUCTEURS =====================

    /**
     * Constructeur par défaut.
     * <p>Crée un guerrier sans nom, avec des caractéristiques nulles et positionné en (0, 0).</p>
     */
    public Guerrier() {
        super();
    }

    /**
     * Constructeur complet avec paramètres.
     * <p>Permet d’initialiser toutes les caractéristiques du guerrier.</p>
     *
     * @param n nom du guerrier
     * @param pV points de vie
     * @param dA dégâts d’attaque
     * @param pPar points de parade
     * @param paAtt pourcentage de réussite d’attaque
     * @param paPar pourcentage de réussite de parade
     * @param dMax distance maximale d’attaque
     * @param p position initiale du guerrier
     */
    public Guerrier(String n, int pV, int dA, int pPar, int paAtt, int paPar,
                    int dMax, Point2D p) {
        super(n, pV, dA, pPar, paAtt, paPar, dMax, p);
    }

    /**
     * Constructeur de copie.
     *
     * @param g guerrier à copier
     */
    public Guerrier(Guerrier g) {
        super(g);
    }

    /**
     * Constructeur utilisé lors du chargement depuis un fichier texte.
     * <p>Exemple de ligne :</p>
     * <pre>Guerrier Arthur 5 100 50 20 60 30 10 5</pre>
     *
     * @param ligne ligne contenant les informations du guerrier
     */
    public Guerrier(String ligne) {
        StringTokenizer st = new StringTokenizer(ligne, " ");
        st.nextToken(); // saute "Guerrier"
        this.setNom(st.nextToken());
        this.setDistAttMax(Integer.parseInt(st.nextToken()));
        chargerDepuisTokenizer(st); // méthode utilitaire de Creature
    }

    // ===================== COMBAT =====================

    /**
     * Permet au guerrier de combattre une autre créature.
     *
     * <p>Le combat est probabiliste et suit les étapes suivantes :</p>
     * <ol>
     *   <li>Le guerrier tente une attaque (jet aléatoire comparé à {@code pageAtt}).</li>
     *   <li>Si l’attaque réussit, la cible tente une parade (jet aléatoire comparé à {@code pagePar}).</li>
     *   <li>Les dégâts infligés sont calculés selon les valeurs d’attaque et de parade.</li>
     * </ol>
     *
     * <p>Les dégâts infligés ne peuvent pas être négatifs, et le combat ne peut avoir lieu
     * que si la cible est adjacente (distance = 1).</p>
     *
     * @param c la créature attaquée
     */
    public void combattre(Creature c) {
        Random rand = new Random();
        int degatsSubis = 0;
        double dist = this.getPos().distance(c.getPos());

        System.out.println("Tentative de combat ");
        System.out.println("L’ennemi a " + c.getPtVie() + " points de vie.");

        // Combat corps à corps
        if (dist == 1) {
            System.out.println("Le combat au corps à corps débute !");
            int jetAttaque = rand.nextInt(100) + 1;

            if (jetAttaque > this.getPageAtt()) {
                System.out.println("Attaque ratée");
            } else {
                System.out.println("Attaque réussie");

                int jetParade = rand.nextInt(100) + 1;
                if (jetParade > c.getPagePar()) {
                    System.out.println("La défense a échoué !");
                    degatsSubis = this.getDegAtt();
                    System.out.println(" - " + degatsSubis + " Damage");
                } else {
                    System.out.println(" - " + this.getDegAtt() + " Damage &  "
                            + c.getPtPar() + " Dmg Resiste = " + (this.getDegAtt() - c.getPtPar()) + " Damage");
                    degatsSubis = this.getDegAtt() - c.getPtPar();
                }

                degatsSubis = Math.max(0, degatsSubis);
                c.setPtVie(c.getPtVie() - degatsSubis);

                System.out.println("Dégâts infligés au défenseur : " + degatsSubis);
                System.out.println("PV restants du défenseur : " + c.getPtVie());
            }
        } else {
            System.out.println("\n Ennemi trop éloigné pour le combat !");
        }
    }

    // ===================== SAUVEGARDE =====================

    /**
     * Retourne le texte de sauvegarde correspondant à ce guerrier.
     *
     * <p>Format de sortie :</p>
     * <pre>
     * Guerrier nom ptVie degAtt ptPar pageAtt pagePar distAttMax posX posY
     * </pre>
     *
     * @return une chaîne prête à être écrite dans un fichier de sauvegarde
     */
    public String getTexteSauvegarde() {
        return "Guerrier " + getNom() + " " + getPtVie() + " " + getDegAtt() + " " +
                getPtPar() + " " + getPageAtt() + " " + getPagePar() + " " +
                getDistAttMax() + " " + getPos().getX() + " " + getPos().getY();
    }
}
