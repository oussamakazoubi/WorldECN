/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.centrale.objet.WoE;

/**
 * Représente un point en deux dimensions dans le monde.
 * <p>
 * La classe {@code Point2D} permet de manipuler des coordonnées sur un plan cartésien.
 * Elle offre des méthodes pour déplacer le point, calculer la distance entre deux points,
 * et afficher ses coordonnées.
 * </p>
 *
 * <p>Elle est notamment utilisée pour représenter la position des créatures et objets
 * dans le monde du jeu.</p>
 *
 * @author Imane
 */
public class Point2D {

    /** Coordonnée en abscisse (axe X). */
    private int x;

    /** Coordonnée en ordonnée (axe Y). */
    private int y;

    /**
     * Constructeur par défaut.
     * Initialise un point à l'origine (0, 0).
     */
    public Point2D() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Constructeur paramétré.
     *
     * @param x coordonnée en abscisse
     * @param y coordonnée en ordonnée
     */
    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructeur de copie.
     *
     * @param p point à copier
     */
    public Point2D(Point2D p) {
        this.x = p.x;
        this.y = p.y;
    }

    /**
     * Retourne la coordonnée en abscisse (X).
     *
     * @return la valeur de X
     */
    public int getX() {
        return x;
    }

    /**
     * Définit la coordonnée en abscisse (X).
     *
     * @param x nouvelle valeur de X
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Retourne la coordonnée en ordonnée (Y).
     *
     * @return la valeur de Y
     */
    public int getY() {
        return y;
    }

    /**
     * Définit la coordonnée en ordonnée (Y).
     *
     * @param y nouvelle valeur de Y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Modifie simultanément les coordonnées du point.
     *
     * @param x nouvelle valeur de X
     * @param y nouvelle valeur de Y
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Translate (déplace) le point selon un vecteur donné.
     *
     * @param dx déplacement selon l'axe X
     * @param dy déplacement selon l'axe Y
     */
    public void translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    /**
     * Calcule la distance euclidienne entre ce point et un autre point.
     *
     * @param p autre point
     * @return la distance entre les deux points
     */
    public double distance(Point2D p) {
        double resx = this.x - p.x;
        double resy = this.y - p.y;
        return Math.sqrt(resx * resx + resy * resy);
    }
    
        /**
     * Vérifie si ce point est égal à un autre point.
     * <p>
     * Deux points sont considérés comme égaux s’ils possèdent les mêmes
     * coordonnées en X et en Y.
     * </p>
     *
     * @param p le point à comparer avec ce point
     * @return {@code true} si les deux points ont les mêmes coordonnées,
     *         {@code false} sinon
     */
    boolean equals(Point2D p) {
        return x == p.getX() && y == p.getY();
    }


    /**
     * Retourne une représentation textuelle du point.
     *
     * @return une chaîne de caractères contenant les coordonnées du point
     */
    @Override
    public String toString() {
        return "Point2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    /**
     * Affiche les coordonnées du point sur la console.
     */
    public void affiche() {
        System.out.println(this);
    }
}
