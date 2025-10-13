/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE;

/**
 * Classe abstraite représentant un objet présent dans le monde.
 * <p>
 * Un objet possède un nom et une position dans le monde. 
 * Chaque sous-classe doit définir le comportement de l’objet lorsqu’il est utilisé 
 * sur une {@link Creature}.
 * </p>
 *
 * @author Imane
 */
public abstract class Objet {

    /** Nom de l'objet. */
    private String nom;

    /** Position de l'objet dans le monde. */
    private Point2D position;

    /**
     * Constructeur par défaut.
     * Crée un objet nommé "Objet" placé à la position (0,0).
     */
    public Objet() {
        this.nom = "Objet";
        this.position = new Point2D();
    }

    /**
     * Constructeur paramétré.
     *
     * @param nom nom de l'objet
     * @param position position de l'objet dans le monde
     */
    public Objet(String nom, Point2D position) {
        this.nom = nom;
        this.position = position;
    }

    /**
     * Constructeur de copie.
     *
     * @param objt objet à copier
     */
    public Objet(Objet objt) {
        this.nom = objt.nom;
        this.position = objt.position;
    }

    /**
     * Retourne le nom de l'objet.
     *
     * @return le nom de l'objet
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom de l'objet.
     *
     * @param nom le nouveau nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne la position de l'objet.
     *
     * @return la position
     */
    public Point2D getPosition() {
        return position;
    }

    /**
     * Définit la position de l'objet dans le monde.
     *
     * @param position nouvelle position
     */
    public void setPosition(Point2D position) {
        this.position = position;
    }

    /**
     * Définit le comportement de l'objet lorsqu'il est utilisé sur une créature.
     *
     * @param c la créature sur laquelle l'objet est utilisé
     */
    public abstract void utiliserObjet(Personnage p);

    /**
     * Retourne une représentation textuelle de l'objet.
     *
     * @return une chaîne de caractères décrivant l'objet
     */
    @Override
    public String toString() {
        return "Objet{" +
                "nom='" + nom + '\'' +
                ", position=" + position +
                '}';
    }

    /**
     * Affiche les informations de l'objet sur la console.
     */
    public void affiche() {
        System.out.println(this);
    }
}
