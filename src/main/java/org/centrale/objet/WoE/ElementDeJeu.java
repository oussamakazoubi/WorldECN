/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.centrale.objet.WoE;

/**
 * Représente un élément générique du monde de jeu possédant une position.
 *
 * <p>La classe {@code ElementDeJeu} sert de classe de base pour tout objet
 * ou entité positionné sur la carte, comme les {@link Creature}, {@link Objet},
 * ou tout autre élément interactif du monde.</p>
 *
 * <p>Elle contient une position (sous forme d’un {@link Point2D}) et des
 * méthodes utilitaires pour manipuler ou comparer cette position.</p>
 *
 * @author Imane
 * @see Point2D
 */
public class ElementDeJeu {

    /** Position de l’élément dans le monde. */
    private Point2D position;

    // ===================== CONSTRUCTEURS =====================

    /**
     * Constructeur par défaut.
     * <p>Initialise la position de l’élément à (0, 0).</p>
     */
    public ElementDeJeu() {
        this.position = new Point2D(0, 0);
    }

    /**
     * Constructeur avec position donnée.
     *
     * @param position position initiale de l’élément
     */
    public ElementDeJeu(Point2D position) {
        // Crée une copie pour éviter les effets de bord (aliasing)
        this.position = new Point2D(position.getX(), position.getY());
    }

    // ===================== ACCESSEURS =====================

    /**
     * Retourne la position actuelle de l’élément.
     * <p>Une copie défensive est renvoyée pour éviter toute modification externe directe.</p>
     *
     * @return une nouvelle instance de {@link Point2D} représentant la position
     */
    public Point2D getPosition() {
        return new Point2D(position.getX(), position.getY());
    }

    /**
     * Définit la position de l’élément.
     * <p>Une copie défensive est créée pour garantir l’intégrité des données.</p>
     *
     * @param position nouvelle position à assigner
     */
    public void setPosition(Point2D position) {
        this.position = new Point2D(position.getX(), position.getY());
    }

    // ===================== MÉTHODES GÉNÉRALES =====================

    /**
     * Déplace l’élément en ajoutant un déplacement relatif à sa position actuelle.
     *
     * @param dx déplacement sur l’axe X
     * @param dy déplacement sur l’axe Y
     */
    public void translate(int dx, int dy) {
        this.position.setX(this.position.getX() + dx);
        this.position.setY(this.position.getY() + dy);
    }

    /**
     * Calcule la distance euclidienne entre cet élément et un autre.
     *
     * @param other autre élément du jeu dont on veut connaître la distance
     * @return la distance euclidienne entre les deux éléments
     */
    public double distance(ElementDeJeu other) {
        return this.position.distance(other.position);
    }

    /**
     * Retourne une représentation textuelle de l’élément,
     * indiquant son type et sa position actuelle.
     *
     * @return une chaîne décrivant la position de l’élément
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " à la position " + position;
    }
}
