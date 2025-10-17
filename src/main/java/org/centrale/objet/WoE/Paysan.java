/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.centrale.objet.WoE;

import java.util.StringTokenizer;

/**
 * Représente un {@code Paysan}, un personnage non combattant du monde.
 *
 * <p>Le paysan hérite de {@link Personnage} et possède les mêmes caractéristiques
 * de base (points de vie, dégâts, parade, etc.), mais il ne participe pas
 * activement aux combats. Il sert principalement à peupler le monde et peut
 * interagir avec celui-ci (déplacement, collecte d’objets, etc.).</p>
 *
 * <ul>
 *   <li>Les paysans ne combattent généralement pas.</li>
 *   <li>Ils peuvent se déplacer librement dans le monde.</li>
 *   <li>Ils héritent de toutes les fonctionnalités de {@link Personnage}.</li>
 * </ul>
 *
 * @author Oussama
 * @see Personnage
 * @see World
 */
public class Paysan extends Personnage {

    // ===================== CONSTRUCTEURS =====================

    /**
     * Constructeur par défaut.
     * <p>Crée un paysan avec des caractéristiques neutres et positionné à (0, 0).</p>
     */
    public Paysan() {
        super();
    }

    /**
     * Constructeur complet avec paramètres.
     * <p>Permet d’initialiser toutes les caractéristiques du paysan.</p>
     *
     * @param n nom du paysan
     * @param pV points de vie
     * @param dA dégâts d’attaque
     * @param pPar points de parade
     * @param paAtt pourcentage de réussite d’attaque
     * @param paPar pourcentage de réussite de parade
     * @param dMax distance maximale d’attaque
     * @param p position initiale du paysan
     */
    public Paysan(String n, int pV, int dA, int pPar, int paAtt,
                  int paPar, int dMax, Point2D p) {
        super(n, pV, dA, pPar, paAtt, paPar, dMax, p);
    }

    /**
     * Constructeur de copie.
     *
     * @param p paysan à copier
     */
    public Paysan(Paysan p) {
        super(p);
    }

    /**
     * Constructeur utilisé lors du chargement depuis un fichier texte.
     * <p>Exemple de ligne :</p>
     * <pre>
     * Paysan peon 25 5 30 0 40 0 3 6
     * </pre>
     *
     * @param ligne ligne de texte contenant les informations du paysan
     */
    public Paysan(String ligne) {
        StringTokenizer st = new StringTokenizer(ligne, " ");
        st.nextToken(); // saute "Paysan"
        this.setNom(st.nextToken());
        this.setPtVie(Integer.parseInt(st.nextToken()));
        this.setDegAtt(Integer.parseInt(st.nextToken()));
        this.setPtPar(Integer.parseInt(st.nextToken()));
        this.setPageAtt(Integer.parseInt(st.nextToken()));
        this.setPagePar(Integer.parseInt(st.nextToken()));
        this.setDistAttMax(Integer.parseInt(st.nextToken()));
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        this.setPos(new Point2D(x, y));
    }

    // ===================== SAUVEGARDE =====================

    /**
     * Retourne la ligne de sauvegarde correspondant à ce paysan.
     *
     * <p>Format de sortie :</p>
     * <pre>
     * Paysan nom ptVie degAtt ptPar pageAtt pagePar distAttMax posX posY
     * </pre>
     *
     * @return chaîne de texte prête à être enregistrée dans un fichier de sauvegarde
     */
    public String getTexteSauvegarde() {
        return "Paysan " + getNom() + " " + getPtVie() + " " + getDegAtt() + " " +
                getPtPar() + " " + getPageAtt() + " " + getPagePar() + " " +
                getDistAttMax() + " " + getPos().getX() + " " + getPos().getY();
    }
}
