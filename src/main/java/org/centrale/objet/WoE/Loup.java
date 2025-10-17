/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.centrale.objet.WoE;

import java.util.StringTokenizer;
import java.util.Random;

/**
 * Représente un {@code Loup}, un type de monstre agressif capable d’attaquer d’autres créatures.
 *
 * <p>Le loup hérite de {@link Monstre} et implémente l’interface {@link Combattant},
 * lui permettant de participer à des combats selon un système de jets d’attaque
 * et de parade.</p>
 *
 * <p>Ce type de créature est généralement hostile et attaque les personnages
 * ou autres créatures proches d’elle dans le monde.</p>
 *
 * @author Imane
 * @see Monstre
 * @see Creature
 * @see Combattant
 */
public class Loup extends Monstre implements Combattant {

    // ===================== CONSTRUCTEURS =====================

    /**
     * Constructeur par défaut.
     * <p>Crée un loup avec des caractéristiques nulles et une position initiale (0, 0).</p>
     */
    public Loup() {
        super();
    }

    /**
     * Constructeur complet avec paramètres.
     *
     * @param pV points de vie du loup
     * @param dA dégâts d’attaque
     * @param pPar points de parade
     * @param paAtt pourcentage de réussite d’attaque
     * @param paPar pourcentage de réussite de parade
     * @param p position initiale du loup
     */
    public Loup(int pV, int dA, int pPar, int paAtt, int paPar, Point2D p) {
        super(pV, dA, pPar, paAtt, paPar, p);
    }

    /**
     * Constructeur de copie.
     *
     * @param l loup à copier
     */
    public Loup(Loup l) {
        super(l);
    }

    /**
     * Constructeur utilisé lors du chargement depuis un fichier texte.
     * <p>Exemple de ligne :</p>
     * <pre>
     * Loup 30 80 50 50 50 19 3
     * </pre>
     *
     * @param ligne ligne de texte contenant les informations du loup
     */
    public Loup(String ligne) {
        StringTokenizer st = new StringTokenizer(ligne, " ");
        st.nextToken(); // saute "Loup"
        this.setPtVie(Integer.parseInt(st.nextToken()));
        this.setDegAtt(Integer.parseInt(st.nextToken()));
        this.setPtPar(Integer.parseInt(st.nextToken()));
        this.setPageAtt(Integer.parseInt(st.nextToken()));
        this.setPagePar(Integer.parseInt(st.nextToken()));
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        this.setPos(new Point2D(x, y));
    }

    // ===================== COMBAT =====================

    /**
     * Permet au loup d’attaquer une autre créature selon un système de jets d’attaque et de parade.
     *
     * <p>Le combat se déroule en plusieurs étapes :</p>
     * <ol>
     *   <li>Un jet aléatoire détermine si l’attaque du loup réussit.</li>
     *   <li>Si l’attaque réussit, la créature attaquée tente une parade.</li>
     *   <li>Les dégâts sont calculés selon les caractéristiques d’attaque et de parade.</li>
     * </ol>
     *
     * <p>Les dégâts infligés ne peuvent pas être négatifs (valeur minimale : 0),
     * et le combat ne peut avoir lieu que si la cible est adjacente (distance = 1).</p>
     *
     * @param c la créature cible du combat
     */
    @Override
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
                            + c.getPtPar() + " Dmg Resiste = "
                            + (this.getDegAtt() - c.getPtPar()) + " Damage");
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
     * Retourne le texte de sauvegarde correspondant à ce loup.
     *
     * <p>Format de sortie :</p>
     * <pre>
     * Loup ptVie degAtt ptPar pageAtt pagePar posX posY
     * </pre>
     *
     * @return chaîne de texte prête à être enregistrée dans un fichier de sauvegarde
     */
    public String getTexteSauvegarde() {
        return "Loup " + getPtVie() + " " + getDegAtt() + " " + getPtPar() + " " +
                getPageAtt() + " " + getPagePar() + " " +
                getPos().getX() + " " + getPos().getY();
    }
}
