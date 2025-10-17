/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.centrale.objet.WoE;

import java.util.StringTokenizer;

/**
 * Représente une {@code PotionSoin}, un objet que les personnages peuvent ramasser et utiliser.
 *
 * <p>Lorsqu’un personnage consomme une potion de soin, celle-ci restaure un certain
 * nombre de points de vie déterminé par l’attribut {@code ptVieRendus}.</p>
 *
 * <p>Les potions peuvent être placées dans le monde, ramassées, utilisées
 * ou sauvegardées dans un fichier texte.</p>
 *
 * @author Imane
 * @see Objet
 * @see Personnage
 */
public class PotionSoin extends Objet {

    /** Nombre de points de vie rendus par la potion. */
    private int ptVieRendus;

    // ===================== CONSTRUCTEURS =====================

    /**
     * Constructeur par défaut.
     * <p>Crée une potion sans effet (rend 0 point de vie) positionnée à (0, 0).</p>
     */
    public PotionSoin() {
        super();
        this.ptVieRendus = 0;
    }

    /**
     * Constructeur complet avec paramètres.
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
     * Constructeur utilisé lors du chargement depuis un fichier texte.
     * <p>Exemple de ligne :</p>
     * <pre>
     * PotionSoin potionVie 20 5 8
     * </pre>
     *
     * @param ligne ligne de texte contenant les informations de la potion
     */
    public PotionSoin(String ligne) {
        StringTokenizer st = new StringTokenizer(ligne, " ");
        st.nextToken(); // saute "PotionSoin"
        this.setNom(st.nextToken());
        this.ptVieRendus = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        this.setPosition(new Point2D(x, y));
    }

    // ===================== ACCESSEURS =====================

    /**
     * Retourne le nombre de points de vie rendus par la potion.
     *
     * @return le nombre de points de vie restaurés
     */
    public int getSoin() {
        return ptVieRendus;
    }

    /**
     * Définit le nombre de points de vie rendus par la potion.
     *
     * @param ptVieRendus le nouveau nombre de points de vie rendus
     */
    public void setPtVieRendus(int ptVieRendus) {
        this.ptVieRendus = ptVieRendus;
    }

    // ===================== UTILISATION =====================

    /**
     * Utilise la potion sur un personnage.
     * <p>Augmente les points de vie du personnage du montant défini
     * par {@code ptVieRendus}.</p>
     *
     * @param p le personnage qui consomme la potion
     */
    @Override
    public void utiliserObjet(Personnage p) {
        p.setPtVie(p.getPtVie() + ptVieRendus);
    }

    // ===================== AFFICHAGE =====================

    /**
     * Retourne une représentation textuelle complète de la potion.
     *
     * @return chaîne de caractères décrivant la potion
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
    @Override
    public void affiche() {
        System.out.println(this);
    }

    // ===================== SAUVEGARDE =====================

    /**
     * Retourne la ligne de texte correspondant à la sauvegarde de cette potion.
     *
     * <p>Format de sortie :</p>
     * <pre>
     * PotionSoin nom ptVieRendus posX posY
     * </pre>
     *
     * @return chaîne prête à être enregistrée dans un fichier de sauvegarde
     */
    public String getTexteSauvegarde() {
        return "PotionSoin " + getNom() + " " + ptVieRendus + " " +
                getPosition().getX() + " " + getPosition().getY();
    }
}
