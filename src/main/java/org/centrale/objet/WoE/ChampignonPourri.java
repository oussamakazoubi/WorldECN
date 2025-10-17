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

    public ChampignonPourri(String nom, Point2D position, int dureeEffet, int malusDefense) {
        super(nom, position, dureeEffet);
        this.malusDefense = malusDefense;
    }
    
    // --- Constructeur pour chargement depuis fichier ---
    public ChampignonPourri(String ligne) {
        // Exemple de ligne : "ChampignonPourri nom x y duree malusDefense"
        String[] parts = ligne.split(" ");
        // parts[0] = "ChampignonPourri"
        this.setNom(parts[1]);
        int x = Integer.parseInt(parts[2]);
        int y = Integer.parseInt(parts[3]);
        this.setPosition(new Point2D(x, y));
        this.setDureeEffet(Integer.parseInt(parts[4]));
        this.malusDefense = Integer.parseInt(parts[5]);
        this.setEstActive(false); // par défaut
    }

    public int getMalusDefense() {
        return malusDefense;
    }

    public void setMalusDefense(int malusDefense) {
        this.malusDefense = malusDefense;
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

     // --- Méthode pour sauvegarde ---
    @Override
    public String getTexteSauvegarde() {
        // Format : "ChampignonPourri nom x y duree malusDefense"
        Point2D pos = getPosition();
        return "ChampignonPourri " + getNom() + " " + pos.getX() + " " + pos.getY() + " "
                + getDureeEffet() + " " + malusDefense;
    }
}

