/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE;

/**
 * Représente une potion de soin que les créatures peuvent ramasser dans le monde.
 * <p>
 * Lorsqu'une créature utilise une {@code PotionSoin}, ses points de vie 
 * sont augmentés d'une certaine quantité déterminée par {@code ptVieRendus}.
 * </p>
 *
 * @author Imane
 */
public class PotionSoin extends Objet {
    
    /** Nombre de points de vie rendus par la potion. */
    private int ptVieRendus;
    
    /**
     * Constructeur par défaut.
     * Initialise une potion sans effet (0 point de vie rendu).
     */
    public PotionSoin() {
        super();
        this.ptVieRendus = 0;
    }
    
    /**
     * Constructeur paramétré.
     *
     * @param nom nom de la potion
     * @param position position de la potion dans le monde
     * @param ptVieRendus nombre de points de vie rendus lors de son utilisation
     */
    public PotionSoin(String nom, Point2D position, int ptVieRendus) {
        super(nom, position);
        this.ptVieRendus = ptVieRendus;
    }
    
    /**
     * Constructeur de copie.
     *
     * @param pS potion à copier
     */
    public PotionSoin(PotionSoin pS) {
        super(pS);
        this.ptVieRendus = pS.ptVieRendus;
    }

    /**
     * Retourne le nombre de points de vie rendus par la potion.
     *
     * @return le nombre de points de vie rendus
     */
    public int getSoin() {
        return ptVieRendus;
    }
    
    /**
     * Utilise la potion sur une créature, augmentant ses points de vie.
     *
     * @param c la créature sur laquelle la potion est utilisée
     */
    @Override
    public void utiliserObjet(Creature c) {
        c.setPtVie(c.getPtVie() + ptVieRendus);
    }

    /**
     * Définit le nombre de points de vie rendus par la potion.
     *
     * @param ptVieRendus le nouveau nombre de points de vie rendus
     */
    public void setPtVieRendus(int ptVieRendus) {
        this.ptVieRendus = ptVieRendus;
    }

    /**
     * Retourne une représentation textuelle de la potion.
     *
     * @return une chaîne de caractères décrivant la potion
     */
    @Override
    public String toString() {
        return "PotionSoin{" +
                "nom='" + getNom() + '\'' +
                ", position=" + getPosition() +
                ", ptVieRendus=" + ptVieRendus +
                '}';
    }

    /**
     * Affiche les informations de la potion sur la console.
     */
    public void affiche() {
        System.out.println(this);
    }
}
