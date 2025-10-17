/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE;

import java.util.StringTokenizer;

/**
 * Représente un monstre générique dans le monde du jeu.
 *
 * <p>Un {@code Monstre} est une {@link Creature} non jouable contrôlée par le système.
 * Il agit comme adversaire ou élément interactif pour les personnages joueurs.</p>
 *
 * <p>Les classes dérivées (telles que {@link Loup} ou {@link Lapin}) définissent des
 * comportements spécifiques : combat, fuite, déplacement aléatoire, etc.</p>
 *
 * @author Imane
 * @see Creature
 */
public class Monstre extends Creature {

    // ===================== CONSTRUCTEURS =====================

    /**
     * Constructeur par défaut.
     * <p>Crée un monstre avec des caractéristiques nulles et une position initiale (0, 0).</p>
     */
    public Monstre() {
        super();
    }

    /**
     * Constructeur complet avec paramètres.
     *
     * @param pV points de vie du monstre
     * @param dA dégâts d’attaque
     * @param pPar points de parade
     * @param paAtt pourcentage de réussite d’attaque
     * @param paPar pourcentage de réussite de parade
     * @param p position initiale du monstre
     */
    public Monstre(int pV, int dA, int pPar, int paAtt, int paPar, Point2D p) {
        super(pV, dA, pPar, paAtt, paPar, p);
    }

    /**
     * Constructeur de copie.
     *
     * @param m monstre à copier
     */
    public Monstre(Monstre m) {
        super(m);
    }

    /**
     * Constructeur utilisé lors du chargement depuis un fichier texte.
     * <p>Lit les caractéristiques d’un monstre sauvegardé.</p>
     *
     * <p>Format attendu :<br>
     * {@code Monstre ptVie degAtt ptPar pageAtt pagePar posX posY}</p>
     *
     * @param ligne ligne de texte contenant les informations du monstre
     */
    public Monstre(String ligne) {
        StringTokenizer st = new StringTokenizer(ligne, " ");
        st.nextToken(); // saute le mot "Monstre"
        chargerDepuisTokenizer(st); // méthode utilitaire de Creature
    }

    // ===================== SAUVEGARDE =====================

    /**
     * Retourne le texte de sauvegarde correspondant à ce monstre.
     *
     * <p>Format de sortie :<br>
     * {@code Monstre ptVie degAtt ptPar pageAtt pagePar posX posY}</p>
     *
     * @return une chaîne prête à être écrite dans un fichier de sauvegarde
     */
    public String getTexteSauvegarde() {
        return "Monstre " + getPtVie() + " " + getDegAtt() + " " + getPtPar() + " " +
                getPageAtt() + " " + getPagePar() + " " +
                getPos().getX() + " " + getPos().getY();
    }
}
