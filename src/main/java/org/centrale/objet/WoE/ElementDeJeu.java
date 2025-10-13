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
 * Classe de base pour tous les éléments présents dans le monde (position).
 */
public class ElementDeJeu {
    private Point2D position;

    /** Constructeur par défaut : position (0,0). */
    public ElementDeJeu() {
        this.position = new Point2D(0, 0);
    }

    /** Constructeur avec position donnée. */
    public ElementDeJeu(Point2D position) {
        // garder une copie pour éviter aliasing
        this.position = new Point2D(position.getX(), position.getY());
    }

    /** Retourne la position (copie). */
    public Point2D getPosition() {
        return new Point2D(position.getX(), position.getY());
    }

    /** Définit la position (copie). */
    public void setPosition(Point2D position) {
        this.position = new Point2D(position.getX(), position.getY());
    }

    /** Déplace l'élément d'un delta (dx, dy). */
    public void translate(int dx, int dy) {
        this.position.setX(this.position.getX() + dx);
        this.position.setY(this.position.getY() + dy);
    }

    /** Distance euclidienne vers un autre élément. */
    public double distance(ElementDeJeu other) {
        return this.position.distance(other.position);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " at " + position;
    }
}

