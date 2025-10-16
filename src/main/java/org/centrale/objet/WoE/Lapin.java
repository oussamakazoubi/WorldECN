/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE;
import java.util.StringTokenizer;
/**
 * Représente un lapin, un type de monstre passif du monde.
 * 
 * <p>Le {@code Lapin} hérite de la classe {@link Monstre}, mais ne possède pas de
 * comportement offensif par défaut. Il sert principalement d’animal non agressif
 * pouvant être chassé ou interagir passivement dans le monde.</p>
 * 
 * <p>Ses caractéristiques sont généralement faibles comparées à celles des loups
 * ou des créatures jouables.</p>
 * 
 * @author Imane
 */
public class Lapin extends Monstre {

    /**
     * Constructeur par défaut.
     * <p>Crée un lapin avec des caractéristiques nulles et une position (0,0).</p>
     */
    public Lapin() {
        super();
    }

    /**
     * Constructeur paramétré.
     *
     * @param pV points de vie
     * @param dA dégâts d’attaque
     * @param pPar points de parade
     * @param paAtt pourcentage de réussite d’attaque
     * @param paPar pourcentage de réussite de parade
     * @param p position du lapin
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
     * Constructeur à partir d'une ligne de sauvegarde texte.
     * <p>Exemple :
     * <pre>
     * Lapin 30 20 20 40 10 23 23
     * </pre>
     * </p>
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
    
     /**
     * Retourne le texte de sauvegarde correspondant à ce lapin.
     * <p>Format :
     * <pre>
     * Lapin ptVie degAtt ptPar pageAtt pagePar posX posY
     * </pre>
     * </p>
     */
    public String getTexteSauvegarde() {
        return "Lapin " + getPtVie() + " " + getDegAtt() + " " + getPtPar() + " " +
               getPageAtt() + " " + getPagePar() + " " +
               getPos().getX() + " " + getPos().getY();
    }
}
























