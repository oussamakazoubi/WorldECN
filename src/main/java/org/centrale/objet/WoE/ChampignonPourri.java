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
    public void utiliserObjet(Personnage p) {
      
        if (!getEstActive()){
            System.out.println(p.getNom() + " mange une feuille d’épinard !");
            p.setDegAtt(p.getDegAtt() - malusDefense);
            setEstActive(true);
        }
    }
    public void annulerEffet(Personnage p){
                System.out.println("L'effet de la feuille d’épinard sur " + p.getNom() + " s’est dissipé.");
                p.setDegAtt(p.getDegAtt() + malusDefense);
                setEstActive(false);
            }

}

