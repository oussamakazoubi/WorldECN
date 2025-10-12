/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE;

import java.util.Random;

/**
 * Représente un {@code Archer}, un personnage capable d’attaquer à distance grâce à ses flèches.
 * 
 * <p>L’archer hérite de {@link Personnage} et ajoute la notion de nombre de flèches disponibles.
 * Il peut attaquer :</p>
 * <ul>
 *   <li>au corps à corps (distance = 1), comme un guerrier ;</li>
 *   <li>à distance (1 &lt; distance &lt; distAttMax), à condition d’avoir encore des flèches.</li>
 * </ul>
 * 
 * <p>Chaque tir à distance consomme une flèche. Si le stock de flèches atteint zéro,
 * l’archer ne peut plus attaquer à distance.</p>
 * 
 * @author Imane
 * @see Personnage
 * @see Creature
 */
public class Archer extends Personnage implements Combattant{

    /** Nombre de flèches encore disponibles pour attaquer à distance. */
    private int nbFleches;

    /**
     * Constructeur par défaut.
     * <p>Crée un archer avec 0 flèche et des caractéristiques nulles.</p>
     */
    public Archer() {
        super();
        this.nbFleches = 0;
    }

    /**
     * Constructeur paramétré permettant d'initialiser toutes les caractéristiques de l’archer.
     *
     * @param n nom de l’archer
     * @param pV points de vie
     * @param dA dégâts d’attaque
     * @param pPar points de parade
     * @param paAtt pourcentage de réussite d’attaque
     * @param paPar pourcentage de réussite de parade
     * @param dMax distance maximale d’attaque
     * @param p position initiale de l’archer
     * @param nbFleches nombre initial de flèches
     */
    public Archer(String n, int pV, int dA, int pPar, int paAtt, int paPar, int dMax, Point2D p, int nbFleches) {
        super(n, pV, dA, pPar, paAtt, paPar, dMax, p);
        this.nbFleches = nbFleches;
    }

    /**
     * Constructeur de copie.
     *
     * @param a archer à copier
     */
    public Archer(Archer a) {
        super(a);
        this.nbFleches = a.nbFleches;
    }

    /**
     * Permet à l’archer de combattre une autre créature.
     * 
     * <p>Le comportement dépend de la distance entre les deux combattants :</p>
     * <ul>
     *   <li><b>Combat corps à corps</b> : si la distance = 1, il agit comme un guerrier.</li>
     *   <li><b>Combat à distance</b> : si 1 &lt; distance &lt; distAttMax et qu’il reste des flèches.</li>
     * </ul>
     * 
     * <p>À distance, une flèche est consommée à chaque tir. Si l’attaque réussit, 
     * l’adversaire subit des dégâts aléatoires selon les statistiques de l’archer.</p>
     *
     * @param c la créature ciblée par l’attaque
     */
    public void combattre(Creature c) {
        Random rand = new Random();
        int degatsSubis = 0;
        double dist = this.getPos().distance(c.getPos());

        // Combat corps à corps
        if (dist == 1) {
            int jetAttaque = rand.nextInt(100) + 1;
            if (jetAttaque > this.getPageAtt()) {
                System.out.println("Attaque ratée");
            } else {
                System.out.println("Attaque réussie");

                int jetParade = rand.nextInt(100) + 1;
                if (jetParade > c.getPagePar()) {
                    degatsSubis = this.getDegAtt();
                } else {
                    degatsSubis = this.getDegAtt() - c.getPtPar();
                }

                degatsSubis = Math.max(0, degatsSubis);
                c.setPtVie(c.getPtVie() - degatsSubis);

                System.out.println("Dégâts infligés au défenseur : " + degatsSubis);
                System.out.println("PV restants du défenseur : " + c.getPtVie());
            }
        }

        // Combat à distance
        else if (nbFleches > 0 && dist > 1 && dist < this.getDistAttMax()) {
            this.nbFleches--;
            int jetAttaque = rand.nextInt(100) + 1;
            if (jetAttaque > this.getPageAtt()) {
                System.out.println("Attaque à distance ratée");
            } else {
                degatsSubis = this.getDegAtt();
                c.setPtVie(c.getPtVie() - degatsSubis);
                System.out.println("Tir réussi ! Dégâts infligés : " + degatsSubis);
            }
            System.out.println("PV restants du défenseur : " + c.getPtVie());
            System.out.println("Flèches restantes : " + this.nbFleches);
        } else if (nbFleches <= 0 && dist > 1) {
            System.out.println("Impossible d’attaquer à distance : plus de flèches !");
        }
    }
}




