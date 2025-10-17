/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.centrale.objet.WoE;

import java.util.StringTokenizer;

/**
 * Représente une {@code Epee}, un objet que les personnages peuvent ramasser et utiliser.
 *
 * <p>Lorsqu’un personnage équipe une épée, celle-ci augmente ses dégâts d’attaque
 * d’un bonus défini par l’attribut {@code bonusAtt}.</p>
 *
 * <p>Les épées peuvent être placées dans le monde, sauvegardées et chargées depuis un fichier texte.</p>
 *
 * @author Imane
 * @see Objet
 * @see Personnage
 */
public class Epee extends Objet {

    /** Bonus de dégâts d’attaque apporté par l’épée. */
    private int bonusAtt;

    // ===================== CONSTRUCTEURS =====================

    /**
     * Constructeur par défaut.
     * <p>Crée une épée sans bonus d’attaque et placée à la position (0, 0).</p>
     */
    public Epee() {
        super();
        this.bonusAtt = 0;
    }

    /**
     * Constructeur complet avec paramètres.
     *
     * @param nom nom de l’épée
     * @param position position de l’épée dans le monde
     * @param bonusAtt bonus de dégâts d’attaque conféré par l’épée
     */
    public Epee(String nom, Point2D position, int bonusAtt) {
        super(nom, position);
        this.bonusAtt = bonusAtt;
    }

    /**
     * Constructeur de copie.
     *
     * @param e épée à copier
     */
    public Epee(Epee e) {
        super(e);
        this.bonusAtt = e.bonusAtt;
    }

    /**
     * Constructeur utilisé lors du chargement depuis un fichier texte.
     * <p>Exemple de ligne :</p>
     * <pre>
     * Epee Excalibur 15 12 8
     * </pre>
     *
     * @param ligne ligne de texte contenant les informations de l’épée
     */
    public Epee(String ligne) {
        StringTokenizer st = new StringTokenizer(ligne, " ");
        st.nextToken(); // saute "Epee"
        this.setNom(st.nextToken());
        this.bonusAtt = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        this.setPosition(new Point2D(x, y));
    }

    // ===================== ACCESSEURS =====================

    /**
     * Retourne le bonus d’attaque conféré par l’épée.
     *
     * @return le bonus d’attaque
     */
    public int getBonusAtt() {
        return bonusAtt;
    }

    /**
     * Définit le bonus d’attaque de l’épée.
     *
     * @param bonusAtt le nouveau bonus
     */
    public void setBonusAtt(int bonusAtt) {
        this.bonusAtt = bonusAtt;
    }

    // ===================== UTILISATION =====================

    /**
     * Utilise l’épée sur un personnage.
     * <p>Augmente les dégâts d’attaque du personnage du montant du {@code bonusAtt}.</p>
     *
     * @param p le personnage qui équipe l’épée
     */
    @Override
    public void utiliserObjet(Personnage p) {
        p.setDegAtt(p.getDegAtt() + bonusAtt);
    }

    // ===================== AFFICHAGE =====================

    /**
     * Retourne une représentation textuelle complète de l’épée.
     *
     * @return chaîne de caractères décrivant l’épée
     */
    @Override
    public String toString() {
        return "Epee{" +
                "nom='" + getNom() + '\'' +
                ", position=" + getPosition() +
                ", bonusAtt=" + bonusAtt +
                '}';
    }

    /**
     * Affiche les informations de l’épée sur la console.
     */
    @Override
    public void affiche() {
        System.out.println(this);
    }

    // ===================== SAUVEGARDE =====================

    /**
     * Retourne la ligne de texte correspondant à la sauvegarde de cette épée.
     *
     * <p>Format de sortie :</p>
     * <pre>
     * Epee nom bonusAtt posX posY
     * </pre>
     *
     * @return chaîne de texte prête à être enregistrée dans un fichier de sauvegarde
     */
    public String getTexteSauvegarde() {
        return "Epee " + getNom() + " " + bonusAtt + " " +
                getPosition().getX() + " " + getPosition().getY();
    }
}
