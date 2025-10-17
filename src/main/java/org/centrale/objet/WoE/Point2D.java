/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.centrale.objet.WoE;

/**
 * Représente un point en deux dimensions dans le monde du jeu.
 *
 * <p>La classe {@code Point2D} permet de manipuler des coordonnées sur un plan cartésien.
 * Elle fournit des méthodes pour :</p>
 * <ul>
 *   <li>modifier les coordonnées d’un point,</li>
 *   <li>déplacer un point (translation),</li>
 *   <li>calculer la distance entre deux points,</li>
 *   <li>et comparer deux points pour vérifier leur égalité.</li>
 * </ul>
 *
 * <p>Cette classe est utilisée pour représenter la position de tous les éléments
 * du jeu, qu’il s’agisse de {@link Creature}, d’{@link Objet}, ou d’éléments du décor.</p>
 *
 * @author Oussama
 */
public class Point2D {

    /** Coordonnée en abscisse (axe X). */
    private int x;

    /** Coordonnée en ordonnée (axe Y). */
    private int y;

    // ===================== CONSTRUCTEURS =====================

    /**
     * Constructeur par défaut.
     * <p>Initialise un point à l’origine (0, 0).</p>
     */
    public Point2D() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Constructeur complet avec paramètres.
     *
     * @param x coordonnée en abscisse (axe X)
     * @param y coordonnée en ordonnée (axe Y)
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

    // ===================== ACCESSEURS =====================

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

    // ===================== MÉTHODES DE POSITION =====================

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
     * @param dx déplacement sur l’axe X
     * @param dy déplacement sur l’axe Y
     */
    public void translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    // ===================== MÉTHODES GÉOMÉTRIQUES =====================

    /**
     * Calcule la distance euclidienne entre ce point et un autre.
     *
     * @param p autre point à comparer
     * @return la distance entre les deux points
     */
    public double distance(Point2D p) {
        double resx = this.x - p.x;
        double resy = this.y - p.y;
        return Math.sqrt(resx * resx + resy * resy);
    }

    /**
     * Vérifie si ce point est égal à un autre point.
     * <p>Deux points sont considérés comme égaux s’ils possèdent les mêmes coordonnées X et Y.</p>
     *
     * @param p le point à comparer
     * @return {@code true} si les deux points ont les mêmes coordonnées, {@code false} sinon
     */
    boolean equals(Point2D p) {
        return x == p.getX() && y == p.getY();
    }

    // ===================== AFFICHAGE =====================

    /**
     * Retourne une représentation textuelle du point.
     *
     * @return une chaîne contenant les coordonnées du point
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
