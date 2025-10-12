/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE;

/**
 * Représente une épée que les créatures peuvent ramasser.
 * <p>
 * Lorsqu'une créature utilise une {@code Epée}, ses dégâts d'attaque 
 * sont augmentés d'un certain bonus défini par {@code bonusAtt}.
 * </p>
 *
 * @author Imane
 */
public class Epée extends Objet {

    /** Bonus de dégâts d'attaque apporté par l'épée. */
    private int bonusAtt;
    
    /**
     * Constructeur par défaut.
     * Crée une épée sans bonus d'attaque.
     */
    public Epée() {
        super();
        this.bonusAtt = 0;
    }

    /**
     * Constructeur paramétré.
     *
     * @param nom nom de l'épée
     * @param position position de l'épée dans le monde
     * @param bonusAtt bonus de dégâts d'attaque conféré
     */
    public Epée(String nom, Point2D position, int bonusAtt) {
        super(nom, position);
        this.bonusAtt = bonusAtt;
    }

    /**
     * Constructeur de copie.
     *
     * @param e épée à copier
     */
    public Epée(Epée e) {
        super(e);
        this.bonusAtt = e.bonusAtt;
    }

    /**
     * Définit le bonus d'attaque de l'épée.
     *
     * @param bonusAtt le nouveau bonus
     */
    public void setBonusAtt(int bonusAtt) {
        this.bonusAtt = bonusAtt;
    }

    /**
     * Retourne le bonus d'attaque de l'épée.
     *
     * @return le bonus d'attaque
     */
    public int getBonusAtt() {
        return bonusAtt;
    }

    /**
     * Utilise l'épée sur une créature, augmentant ses dégâts d'attaque.
     *
     * @param c la créature équipée de l'épée
     */
    @Override
    public void utiliserObjet(Creature c) {
        c.setDegAtt(c.getDegAtt() + bonusAtt);
    }

    /**
     * Retourne une représentation textuelle de l'épée.
     *
     * @return une chaîne de caractères décrivant l'épée
     */
    @Override
    public String toString() {
        return "Epée{" +
                "nom='" + getNom() + '\'' +
                ", position=" + getPosition() +
                ", bonusAtt=" + bonusAtt +
                '}';
    }

    /**
     * Affiche les informations de l'épée sur la console.
     */
    @Override
    public void affiche() {
        System.out.println(this);
    }
}
