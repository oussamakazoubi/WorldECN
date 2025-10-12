/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE;

/**
 * Représente un monstre générique dans le monde du jeu.
 * 
 * <p>Un monstre est une {@link Creature} non-jouable contrôlée par le système.
 * Il sert généralement d’adversaire aux personnages et peut se déplacer ou combattre
 * selon son comportement propre (défini dans ses sous-classes).</p>
 * 
 * <p>Les classes dérivées comme {@link Loup} ou {@link Lapin} implémentent des
 * comportements spécifiques (par exemple le combat ou la fuite).</p>
 * 
 * @author Imane
 */
public class Monstre extends Creature {

    /**
     * Constructeur par défaut.
     * <p>Crée un monstre avec des caractéristiques nulles et une position (0,0).</p>
     */
    public Monstre() {
        super();
    }

    /**
     * Constructeur paramétré.
     *
     * @param pV points de vie
     * @param dA dégâts d’attaque
     * @param pPar points de parade
     * @param paAtt pourcentage de réussite d’attaque
     * @param paPar pourcentage de réussite de parade
     * @param p position du monstre
     */
    public Monstre(int pV, int dA, int pPar, int paAtt, int paPar, Point2D p) {
        super(pV, dA, pPar, paAtt, paPar, p);
    }

    /**
     * Constructeur de copie.
     *
     * @param m monstre à copier
     */
    public Monstre(Monstre m) {
        super(m);
    }
}





























