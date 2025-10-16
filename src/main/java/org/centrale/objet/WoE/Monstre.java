/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE;
import java.util.StringTokenizer;
/**
 * Représente un monstre générique dans le monde du jeu.
 * 
 * <p>Un monstre est une {@link Creature} non-jouable contrôlée par le système.
 * Il sert généralement d’adversaire aux personnages et peut se déplacer ou combattre
 * selon son comportement propre (défini dans ses sous-classes).</p>
 * 
 * <p>Les classes dérivées comme {@link Loup} ou {@link Lapin} implémentent des
 * comportements spécifiques (par exemple le combat ou la fuite).</p>
 * 
 * @author Imane
 */
public class Monstre extends Creature {

    /**
     * Constructeur par défaut.
     * <p>Crée un monstre avec des caractéristiques nulles et une position (0,0).</p>
     */
    public Monstre() {
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
     * @param p position du monstre
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
     * Constructeur à partir d'une ligne de sauvegarde texte.
     * <p>
     * Format attendu : 
     * <pre>Monstre ptVie degAtt ptPar pageAtt pagePar posX posY</pre>
     * </p>
     *
     * @param ligne ligne contenant les informations du monstre
     */
    public Monstre(String ligne) {
        StringTokenizer st = new StringTokenizer(ligne, " ");
        st.nextToken(); // saute le mot "Monstre"
        chargerDepuisTokenizer(st); // méthode utilitaire dans Creature
    }
    
    
    /**
     * Retourne le texte de sauvegarde correspondant à ce monstre.
     * <p>
     * Format : <pre>Monstre ptVie degAtt ptPar pageAtt pagePar posX posY</pre>
     * </p>
     *
     * @return la chaîne prête à écrire dans le fichier de sauvegarde
     */
    public String getTexteSauvegarde() {
        return "Monstre " + getPtVie() + " " + getDegAtt() + " " + getPtPar() + " " +
               getPageAtt() + " " + getPagePar() + " " +
               getPos().getX() + " " + getPos().getY();
    }
}



