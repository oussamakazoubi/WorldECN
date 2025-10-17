/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.centrale.objet.WoE;

import java.util.StringTokenizer;

/**
 * Représente un {@code Lapin}, un type de monstre passif du monde.
 *
 * <p>Le lapin hérite de la classe {@link Monstre}, mais ne possède pas de
 * comportement offensif. Il est principalement utilisé comme créature non agressive
 * pouvant être chassée ou simplement errer dans le monde.</p>
 *
 * <p>Ses caractéristiques sont généralement faibles comparées à celles d’un
 * {@link Loup} ou d’un personnage jouable.</p>
 *
 * @author Imane
 * @see Monstre
 * @see Creature
 */
public class Lapin extends Monstre {

    // ===================== CONSTRUCTEURS =====================

    /**
     * Constructeur par défaut.
     * <p>Crée un lapin avec des caractéristiques nulles et une position initiale (0, 0).</p>
     */
    public Lapin() {
        super();
    }

    /**
     * Constructeur complet avec paramètres.
     *
     * @param pV points de vie du lapin
     * @param dA dégâts d’attaque
     * @param pPar points de parade
     * @param paAtt pourcentage de réussite d’attaque
     * @param paPar pourcentage de réussite de parade
     * @param p position initiale du lapin
     */
    public Lapin(int pV, int dA, int pPar, int paAtt, int paPar, Point2D p) {
        super(pV, dA, pPar, paAtt, paPar, p);
    }

    /**
     * Constructeur de copie.
     *
     * @param l lapin à copier
     */
    public Lapin(Lapin l) {
        super(l);
    }

    /**
     * Constructeur utilisé lors du chargement depuis un fichier texte.
     * <p>Exemple de ligne :</p>
     * <pre>
     * Lapin 30 20 20 40 10 23 23
     * </pre>
     *
     * @param ligne ligne de texte contenant les informations du lapin
     */
    public Lapin(String ligne) {
        StringTokenizer st = new StringTokenizer(ligne, " ");
        st.nextToken(); // saute "Lapin"
        this.setPtVie(Integer.parseInt(st.nextToken()));
        this.setDegAtt(Integer.parseInt(st.nextToken()));
        this.setPtPar(Integer.parseInt(st.nextToken()));
        this.setPageAtt(Integer.parseInt(st.nextToken()));
        this.setPagePar(Integer.parseInt(st.nextToken()));
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        this.setPos(new Point2D(x, y));
    }

    // ===================== SAUVEGARDE =====================

    /**
     * Retourne le texte de sauvegarde correspondant à ce lapin.
     *
     * <p>Format de sortie :</p>
     * <pre>
     * Lapin ptVie degAtt ptPar pageAtt pagePar posX posY
     * </pre>
     *
     * @return chaîne de texte prête à être enregistrée dans un fichier de sauvegarde
     */
    public String getTexteSauvegarde() {
        return "Lapin " + getPtVie() + " " + getDegAtt() + " " + getPtPar() + " " +
                getPageAtt() + " " + getPagePar() + " " +
                getPos().getX() + " " + getPos().getY();
    }
}
