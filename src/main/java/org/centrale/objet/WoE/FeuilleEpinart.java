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
    
    public FeuilleEpinart(String ligne) {
    super("FeuilleEpinart", new Point2D(0,0), 3); // valeurs temporaires

    String[] tokens = ligne.split(" ");
    int n = tokens.length;

    // Derniers éléments : coordonnées et durée
    int x = Integer.parseInt(tokens[n - 4]);
    int y = Integer.parseInt(tokens[n - 3]);
    int duree = Integer.parseInt(tokens[n - 2]);
    int bonus = Integer.parseInt(tokens[n - 1]);

    // Nom = tout ce qui reste au début
    StringBuilder nomBuilder = new StringBuilder();
    for (int i = 1; i < n - 4; i++) {
        if (i > 1) nomBuilder.append(" ");
        nomBuilder.append(tokens[i]);
    }

    super.setNom(nomBuilder.toString());
    super.setPosition(new Point2D(x, y));
    super.setDureeEffet(duree);
    this.bonusDegAtt = bonus;
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
   
public String getTexteSauvegarde() {
    // Format : FeuilleEpinart nom x y dureeEffet bonusDegAtt
    return "FeuilleEpinart " + super.getNom() + " " 
           + getPosition().getX() + " " + getPosition().getY() + " " 
           + getDureeEffet() + " " + bonusDegAtt;
}

   

  
        }
    

   
    

