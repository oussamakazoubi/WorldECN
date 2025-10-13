/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE;

import java.util.Random;

/**
 * Représente un {@code Guerrier}, un personnage spécialisé dans le combat rapproché.
 * 
 * <p>Le guerrier hérite de {@link Personnage} et se distingue par sa puissance
 * d’attaque et sa capacité à infliger des dégâts importants en combat corps à corps.
 * 
 * Il ne possède pas d’arme à distance, contrairement à un {@link Archer}.</p>
 *
 * <p>Le système de combat repose sur des jets de dés aléatoires pour déterminer
 * la réussite de l’attaque et de la parade du défenseur.</p>
 *
 * @author Imane
 * @see Personnage
 * @see Creature
 */
public class Guerrier extends Personnage implements Combattant{

    /**
     * Constructeur par défaut.
     * <p>Crée un guerrier sans nom avec des caractéristiques nulles et une position (0,0).</p>
     */
    public Guerrier() {
        super();
    }

    /**
     * Constructeur paramétré permettant d'initialiser toutes les caractéristiques du guerrier.
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
    public Guerrier(String n, int pV, int dA, int pPar, int paAtt, int paPar, int dMax, Point2D p) {
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
     * Permet au guerrier d’attaquer une autre créature.
     *
     * <p>Le combat est probabiliste et suit les étapes suivantes :</p>
     * <ol>
     *   <li>Le guerrier tente une attaque (jet aléatoire comparé à {@code pageAtt}).</li>
     *   <li>Si l’attaque réussit, la cible tente une parade (jet aléatoire comparé à {@code pagePar}).</li>
     *   <li>Les dégâts sont calculés en fonction des statistiques d’attaque et de parade.</li>
     * </ol>
     * 
     * <p>Les dégâts infligés ne peuvent pas être négatifs.</p>
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
            // Attacking
            if (jetAttaque > this.getPageAtt()) {
                System.out.println("Attaque ratée");
            } else {
                System.out.println("Attaque réussie");

                int jetParade = rand.nextInt(100) + 1;
                if (jetParade > c.getPagePar()) {
                    System.out.println("La défense a échoué !");
                    degatsSubis = this.getDegAtt();
                    System.out.println(" - "+degatsSubis+" Damage");
                } else {
                    System.out.println(" - "+this.getDegAtt()+" Damage &  "
                            +c.getPtPar()+" Dmg Resiste = "+ (this.getDegAtt() - c.getPtPar()) +" Damage" );
                    degatsSubis = this.getDegAtt() - c.getPtPar();
                }

                degatsSubis = Math.max(0, degatsSubis);
                c.setPtVie(c.getPtVie() - degatsSubis);

                System.out.println("Dégâts infligés au défenseur : " + degatsSubis);
                System.out.println("PV restants du défenseur : " + c.getPtVie());
            }
        }
        else System.out.println("\n Ennemi trop éloigné pour le combat !");
    }
}
