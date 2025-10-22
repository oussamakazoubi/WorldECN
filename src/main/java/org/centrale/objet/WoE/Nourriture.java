/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.centrale.objet.WoE;

/**
 * Classe abstraite représentant un objet {@code Nourriture}.
 *
 * <p>Une nourriture est un type d’{@link Objet} offrant un effet temporaire,
 * positif ou négatif, sur les caractéristiques d’un {@link Personnage}.</p>
 *
 * <p>Chaque instance possède une durée d’effet exprimée en tours
 * et un indicateur d’état ({@code estActive}) pour savoir si l’effet
 * est actuellement appliqué ou non.</p>
 *
 * <p>Les sous-classes concrètes (comme {@link FeuilleEpinart} ou {@link ChampignonPourri})
 * définissent la nature de l’effet ainsi que la manière de l’annuler.</p>
 *
 * @author Imane
 * @see Objet
 * @see Personnage
 * @see FeuilleEpinart
 * @see ChampignonPourri
 */
public abstract class Nourriture extends Objet {

    /** Nombre de tours pendant lesquels l’effet reste actif. */
    private int dureeEffet;

    /** Indique si l’effet est actuellement actif. */
    private boolean estActive;

    // ===================== CONSTRUCTEURS =====================

    /**
     * Constructeur par défaut.
     * <p>Crée une nourriture sans effet et désactivée.</p>
     */
    public Nourriture() {
        super();
        this.dureeEffet = 0;
        this.estActive = false;
    }

    /**
     * Constructeur complet avec paramètres.
     *
     * @param nom nom de la nourriture
     * @param position position de la nourriture dans le monde
     * @param dureeEffet durée de l’effet en nombre de tours
     */
    public Nourriture(String nom, Point2D position, int dureeEffet) {
        super(nom, position);
        this.dureeEffet = dureeEffet;
        this.estActive = false;
    }

    // ===================== ACCESSEURS =====================

    /**
     * Retourne la durée restante de l’effet.
     *
     * @return nombre de tours restants
     */
    public int getDureeEffet() {
        return dureeEffet;
    }

    /**
     * Définit la durée de l’effet.
     *
     * @param dureeEffet nouvelle durée en tours
     */
    public void setDureeEffet(int dureeEffet) {
        this.dureeEffet = dureeEffet;
    }

    /**
     * Indique si l’effet est actuellement actif.
     *
     * @return {@code true} si l’effet est actif, {@code false} sinon
     */
    public boolean getEstActive() {
        return estActive;
    }

    /**
     * Modifie l’état de l’effet (actif ou inactif).
     *
     * @param active nouvel état de l’effet
     */
    public void setEstActive(boolean active) {
        this.estActive = active;
    }

    // ===================== MÉTHODES ABSTRAITES =====================

    /**
     * Annule l’effet appliqué par la nourriture sur un personnage.
     *
     * <p>Chaque sous-classe doit implémenter cette méthode pour inverser
     * les effets appliqués (par exemple, retirer un bonus ou restaurer
     * les statistiques modifiées).</p>
     *
     * @param p le personnage concerné par la fin de l’effet
     */
    public abstract void annulerEffet(Personnage p);

    // ===================== MÉTHODES UTILITAIRES =====================

    /**
     * Décrémente la durée restante de l’effet d’un tour.
     * <p>Si la durée atteint 0, l’effet doit être annulé par la sous-classe
     * via {@link #annulerEffet(Personnage)}.</p>
     */
    public void decrementerEffet() {
        if (dureeEffet > 0) {
            dureeEffet--;
        }
    }

    /**
     * Retourne une représentation textuelle de la nourriture, incluant
     * sa durée d’effet restante.
     *
     * @return chaîne de caractères décrivant la nourriture et sa durée
     */
    @Override
    public String toString() {
        return super.toString() + " (effet : " + dureeEffet + " tours restants)";
    }

}
