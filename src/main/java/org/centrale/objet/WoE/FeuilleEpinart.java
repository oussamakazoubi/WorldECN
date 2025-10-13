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

    private final int bonusDegAtt;

    /**
     * Constructeur de la feuille d’épinard.
     * @param nom
     * @param position position de la nourriture sur la carte
     * @param dureeEffet
     * @param bpnusDegAtt
     */
    public FeuilleEpinart( String nom, Point2D position, int dureeEffet, int bpnusDegAtt) {
        super("Feuille d’épinard", position, 3); // effet dure 3 tours
        this.bonusDegAtt = 2;
    }

    public void utiliserObjet(Personnage p) {
      
        if (getEstActive()==false){
            System.out.println(p.getNom() + " mange une feuille d’épinard !");
            p.setDegAtt(p.getDegAtt() + bonusDegAtt);
            setEstActive(true);
        }
    }
    public void annulerEffet(Personnage p){
                System.out.println("L'effet de la feuille d’épinard sur " + p.getNom() + " s’est dissipé.");
                p.setDegAtt(p.getDegAtt() - bonusDegAtt);
                setEstActive(false);
            }

   

  
        }
    

   
    

