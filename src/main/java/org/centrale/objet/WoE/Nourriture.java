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
 * Classe abstraite représentant un objet Nourriture.
 * La nourriture offre un effet temporaire (bonus ou malus)
 * sur certaines caractéristiques d’un personnage.
 */
public abstract class Nourriture extends Objet {

    
    private int dureeEffet; // nombre de tours pendant lesquels l'effet dure
    private boolean estActive; // indique si l'effet est encore actif

    /**
     * Constructeur par défaut.
     */
    public Nourriture() {
        super();
        this.dureeEffet = 0;
        this.estActive = false;
    }

    /**
     * Constructeur paramétré.
     * @param nom Nom de la nourriture
     * @param position Position de la nourriture sur le plateau
     * @param dureeEffet Durée de l'effet en tours
     */
    public Nourriture(String nom, Point2D position, int dureeEffet) {
        super(nom, position);
        this.dureeEffet = dureeEffet;
        this.estActive = false;
    }

    // --- Accesseurs / Mutateurs ---
    
    

    public int getDureeEffet() {
        return dureeEffet;
    }

    public void setDureeEffet(int dureeEffet) {
        this.dureeEffet = dureeEffet;
    }

    public boolean getEstActive() {
        return estActive;
    }

    public void setEstActive(boolean active) {
        this.estActive = active;
    }

    // --- Méthodes abstraites ---
    /**
     * Applique l’effet de la nourriture sur un personnage.
     * @param p le personnage qui consomme la nourriture
     */
    public abstract void utiliserNourriture(Personnage p);

    // --- Méthodes utilitaires ---
    /**
     * Décrémente la durée de l'effet d'un tour.
     * Renvoie true si l'effet est encore actif, false sinon.
     */
    public void decrementerEffet() {
        if (dureeEffet > 0) {
            dureeEffet--;
        }
        
    }

    @Override
    public String toString() {
        return super.toString() + " (effet : " + dureeEffet + " tours restants)";
    }
}
