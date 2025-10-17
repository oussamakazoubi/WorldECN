/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.centrale.objet.WoE;

/**
 * Classe abstraite représentant un {@code Objet} présent dans le monde.
 *
 * <p>Un objet possède un nom et une position dans le monde.
 * Les sous-classes concrètes (comme {@link Epee}, {@link PotionSoin}, ou {@link Nourriture})
 * définissent leur comportement spécifique lorsqu’elles sont utilisées
 * sur un {@link Personnage}.</p>
 *
 * <p>Chaque objet peut être ramassé, affiché, et sauvegardé dans un fichier
 * via la méthode {@link #getTexteSauvegarde()}.</p>
 *
 * @author Imane
 * @see Point2D
 * @see Personnage
 */
public abstract class Objet {

    /** Nom de l'objet. */
    private String nom;

    /** Position de l'objet dans le monde. */
    private Point2D position;

    // ===================== CONSTRUCTEURS =====================

    /**
     * Constructeur par défaut.
     * <p>Crée un objet nommé "Objet" positionné à (0, 0).</p>
     */
    public Objet() {
        this.nom = "Objet";
        this.position = new Point2D();
    }

    /**
     * Constructeur complet avec paramètres.
     *
     * @param nom nom de l’objet
     * @param position position de l’objet dans le monde
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

    // ===================== ACCESSEURS =====================

    /**
     * Retourne le nom de l’objet.
     *
     * @return le nom de l’objet
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom de l’objet.
     *
     * @param nom le nouveau nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne la position de l’objet.
     *
     * @return la position de l’objet
     */
    public Point2D getPosition() {
        return position;
    }

    /**
     * Définit la position de l’objet dans le monde.
     *
     * @param position la nouvelle position
     */
    public void setPosition(Point2D position) {
        this.position = position;
    }

    // ===================== MÉTHODES ABSTRAITES =====================

    /**
     * Définit le comportement de l’objet lorsqu’il est utilisé sur un personnage.
     *
     * <p>Chaque sous-classe implémente cette méthode pour définir son effet :</p>
     * <ul>
     *   <li>Une {@link PotionSoin} peut rendre des points de vie.</li>
     *   <li>Une {@link Epee} peut augmenter les dégâts d’attaque.</li>
     *   <li>Une {@link Nourriture} peut fournir un effet temporaire.</li>
     * </ul>
     *
     * @param p le personnage sur lequel l’objet est utilisé
     */
    public abstract void utiliserObjet(Personnage p);

    // ===================== MÉTHODES GÉNÉRALES =====================

    /**
     * Retourne une représentation textuelle de l’objet.
     *
     * @return une chaîne décrivant le nom et la position de l’objet
     */
    @Override
    public String toString() {
        return "Objet{" +
                "nom='" + nom + '\'' +
                ", position=" + position +
                '}';
    }

    /**
     * Affiche les informations de l’objet sur la console.
     */
    public void affiche() {
        System.out.println(this);
    }

    // ===================== SAUVEGARDE =====================

    /**
     * Retourne le texte à sauvegarder dans un fichier.
     *
     * <p>Les sous-classes doivent surcharger cette méthode pour générer
     * une ligne de sauvegarde adaptée à leur type spécifique :</p>
     * <ul>
     *   <li><b>Epee</b> → {@code "Epee nom bonusAtt posX posY"}</li>
     *   <li><b>PotionSoin</b> → {@code "PotionSoin nom ptVieRendus posX posY"}</li>
     *   <li><b>Nourriture</b> → {@code "Nourriture nom effet duree posX posY"}</li>
     * </ul>
     *
     * @return une chaîne prête à être écrite dans un fichier de sauvegarde
     */
    public String getTexteSauvegarde() {
        return "Objet " + nom + " " + position.getX() + " " + position.getY();
    }
}
