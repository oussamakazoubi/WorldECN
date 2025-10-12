/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE;

/**
 *
 * @author user
 */


/**
 * Représente une feuille d’épinard qui augmente les dégâts d’attaque.
 */
public class FeuilleEpinart extends Nourriture {

    private int bonusDegAtt;

    /**
     * Constructeur de la feuille d’épinard.
     * @param position position de la nourriture sur la carte
     */
    public FeuilleEpinart(Point2D position) {
        super("Feuille d’épinard", position, 3); // effet dure 3 tours
        this.bonusDegAtt = 2;
    }

    @Override
    public void utiliserNourriture(Personnage p) {
        System.out.println(p.getNom() + " mange une feuille d’épinard !");
        p.setDegAtt(p.getDegAtt() + bonusDegAtt);
        setActive(true);
    }

    /**
     * Supprime l’effet quand la durée est écoulée.
     */
    public void finEffet(Personnage p) {
        if (isActive() && getDureeEffet() == 0) {
            System.out.println("L'effet de la feuille d’épinard sur " + p.getNom() + " s’est dissipé.");
            p.setDegAtt(p.getDegAtt() - bonusDegAtt);
            setActive(false);
        }
    }
}

