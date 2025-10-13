/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE;

/**
 *
 * @author user
 */
public class ChampignonPourri extends Nourriture {
    private int malusDefense;

    public ChampignonPourri(Point2D position) {
        super("Champignon pourri", position, 2); // effet dure 2 tours
        this.malusDefense = 3;
    }

    @Override
    public void utiliserNourriture(Personnage p) {
        System.out.println(p.getNom() + " mange un champignon pourri... beurk !");
        p.setPtPar(p.getPtPar() - malusDefense); // diminue la défense
        setActive(true);
    }

    public void finEffet(Personnage p) {
        System.out.println("Le malus du champignon pourri sur " + p.getNom() + " s’est dissipé.");
        p.setPtPar(p.getPtPar() + malusDefense); // remet la défense normale
        setActive(false);
    }
}

