/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.centrale.objet.WoE;

/**
 * La classe {@code Paysan} représente un personnage non combattant du monde.
 * <p>
 * Le paysan hérite de la classe {@link Personnage} et possède les mêmes caractéristiques
 * de base (points de vie, dégâts, parade, etc.). Il sert principalement à
 * peupler le monde sans participer activement aux combats.
 * </p>
 *
 * <ul>
 *   <li>Les paysans ne combattent généralement pas</li>
 *   <li>Ils peuvent interagir avec le monde (se déplacer, ramasser des objets)</li>
 *   <li>Ils héritent de tous les comportements de {@link Personnage}</li>
 * </ul>
 *
 * @author Imane
 * @see Personnage
 * @see World
 */
public class Paysan extends Personnage {

    /**
     * Constructeur par défaut.
     * <p>
     * Initialise un paysan avec des caractéristiques neutres.
     * </p>
     */
    public Paysan() {
        super();
    }

    /**
     * Constructeur complet permettant d’initialiser toutes les caractéristiques du paysan.
     *
     * @param n nom du paysan
     * @param pV points de vie
     * @param dA dégâts d’attaque
     * @param pPar points de parade
     * @param paAtt points d’attaque spéciale
     * @param paPar points de parade spéciale
     * @param dMax distance maximale d’attaque
     * @param p position initiale du paysan
     */
    public Paysan(String n, int pV, int dA, int pPar, int paAtt, int paPar, int dMax, Point2D p) {
        super(n, pV, dA, pPar, paAtt, paPar, dMax, p);
    }

    /**
     * Constructeur de copie.
     *
     * @param p paysan à copier
     */
    public Paysan(Paysan p) {
        super(p);
    }
}





















