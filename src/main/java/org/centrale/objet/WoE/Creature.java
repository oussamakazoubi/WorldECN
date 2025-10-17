/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.centrale.objet.WoE;
import java.util.StringTokenizer;
import java.util.Random;

/**
 * Représente une créature générique du monde {@link World}.
 *
 * <p>Une créature possède des caractéristiques communes telles que :</p>
 * <ul>
 *   <li>Ses points de vie ({@code ptVie})</li>
 *   <li>Sa capacité d’attaque ({@code degAtt})</li>
 *   <li>Sa capacité de parade ({@code ptPar})</li>
 *   <li>Ses chances d’attaque et de parade ({@code pageAtt}, {@code pagePar})</li>
 *   <li>Sa position dans le monde ({@link Point2D pos})</li>
 * </ul>
 *
 * <p>Cette classe sert de classe mère pour les personnages et les monstres du jeu.</p>
 *
 * @author Imane
 */
public class Creature implements Deplacable{

    /** Points de vie de la créature. */
    private int ptVie;

    /** Dégâts infligés lors d'une attaque. */
    private int degAtt;

    /** Points de parade (défense). */
    private int ptPar;

    /** Pourcentage de chance de réussir une attaque. */
    private int pageAtt;

    /** Pourcentage de chance de réussir une parade. */
    private int pagePar;

    /** Position de la créature dans le monde. */
    private Point2D pos;


    /**
     * Constructeur avec paramètres.
     *
     * @param pV Points de vie
     * @param dA Dégâts d’attaque
     * @param pPar Points de parade
     * @param paAtt Pourcentage de chance d’attaque
     * @param paPar Pourcentage de chance de parade
     * @param p Position initiale de la créature
     */
    public Creature(int pV, int dA, int pPar, int paAtt, int paPar, Point2D p) {
        this.ptVie = pV;
        this.degAtt = dA;
        this.ptPar = pPar;
        this.pageAtt = paAtt;
        this.pagePar = paPar;
        this.pos = new Point2D(p);
    }

    /**
     * Constructeur de copie.
     *
     * @param c Créature à copier
     */
    public Creature(Creature c) {
        this(c.ptVie, c.degAtt, c.ptPar, c.pageAtt, c.pagePar, c.pos);
    }

    /**
     * Constructeur par défaut.
     * <p>Initialise tous les attributs à 0 et positionne la créature à (0,0).</p>
     */
    public Creature() {
        this(0, 0, 0, 0, 0, new Point2D(0, 0));
    }

    /**
 * Initialise les attributs communs d'une créature à partir d'un StringTokenizer.
 * Utilisé par les constructeurs des sous-classes lors du chargement.
 */
protected void chargerDepuisTokenizer(StringTokenizer st) {
    this.ptVie = Integer.parseInt(st.nextToken());
    this.degAtt = Integer.parseInt(st.nextToken());
    this.ptPar = Integer.parseInt(st.nextToken());
    this.pageAtt = Integer.parseInt(st.nextToken());
    this.pagePar = Integer.parseInt(st.nextToken());
    int x = Integer.parseInt(st.nextToken());
    int y = Integer.parseInt(st.nextToken());
    this.pos = new Point2D(x, y);
}

    // --- Getters et Setters ---

    /** @return les points de vie actuels de la créature */
    public int getPtVie() {
        return ptVie;
    }

    /** @param ptVie nouveaux points de vie */
    public void setPtVie(int ptVie) {
        this.ptVie = ptVie;
    }

    /** @return les dégâts d’attaque */
    public int getDegAtt() {
        return degAtt;
    }

    /** @param degAtt nouveaux dégâts d’attaque */
    public void setDegAtt(int degAtt) {
        this.degAtt = degAtt;
    }

    /** @return les points de parade */
    public int getPtPar() {
        return ptPar;
    }

    /** @param ptPar nouveaux points de parade */
    public void setPtPar(int ptPar) {
        this.ptPar = ptPar;
    }

    /** @return le pourcentage de réussite d’attaque */
    public int getPageAtt() {
        return pageAtt;
    }

    /** @param pageAtt nouveau pourcentage de réussite d’attaque */
    public void setPageAtt(int pageAtt) {
        this.pageAtt = pageAtt;
    }

    /** @return le pourcentage de réussite de parade */
    public int getPagePar() {
        return pagePar;
    }

    /** @param pagePar nouveau pourcentage de réussite de parade */
    public void setPagePar(int pagePar) {
        this.pagePar = pagePar;
    }

    /** @return la position actuelle de la créature */
    public Point2D getPos() {
        return pos;
    }

    /** @param pos nouvelle position de la créature */
    public void setPos(Point2D pos) {
        this.pos = pos;
    }

    // --- Méthodes de déplacement ---


    /**
     * Déplace la créature d’un pas aléatoire (entre -1 et +1 en x et y).
     * <p>Le déplacement continue tant que les deux coordonnées sont nulles
     * (la créature doit effectivement bouger).</p>
     */

    @Override
    public void deplace() {
        Random rand = new Random();
        int dx = rand.nextInt(3) - 1;
        int dy = rand.nextInt(3) - 1;
        if (dx==0 && dy==0) this.deplace();
        else this.getPos().translate(dx, dy);
    }



    /**
     * Déplace la créature dans le monde aléatoirement, en évitant les cases occupées.
     *
     * <p>Si la nouvelle position est libre, la créature s’y déplace et vérifie
     * la présence d’un objet sur cette case via {@link World#chercherObjet(Creature)}.</p>
     *
     * @param monde le monde dans lequel la créature évolue
     */

    public void deplace(World monde){
        int dx, dy;
        Random rand = new Random();
        Point2D newpos;
        do{
            dx = rand.nextInt(3) - 1;
            dy = rand.nextInt(3) - 1;
            newpos = new Point2D(this.pos.getX() + dx, this.pos.getY() + dy);
        }while(monde.estOccupee(newpos) || (dx==0 && dy==0));
        this.pos=newpos;
    }



    // --- Méthodes utilitaires ---

    /**
     * Retourne une représentation textuelle complète de la créature et de ses caractéristiques.
     *
     * @return une chaîne contenant les valeurs de tous les attributs
     */
    @Override
    public String toString() {
        return "Points de vie : " + this.ptVie
                + "  Dégâts : " + this.degAtt
                + "  Points de parade : " + this.ptPar
                + "  Chances d’attaque : " + this.pageAtt
                + "  Chances de parade : " + this.pagePar
                + "  Position : [" + this.pos.getX() + ", " + this.pos.getY() + "]";
    }

    /**
     * Affiche les caractéristiques de la créature dans la console.
     */
    public void affiche() {
        System.out.println(this);
    }

    /**
     * Retourne la partie commune de la ligne de sauvegarde pour une créature.
     * Ex: "100 50 20 60 30 10 5"
     */
    protected String getTexteSauvegardeCommun() {
        return ptVie + " " + degAtt + " " + ptPar + " " +
               pageAtt + " " + pagePar + " " +
               pos.getX() + " " + pos.getY();
    }


    /**
     * Vérifie si la créature est morte (points de vie <= 0) et la retire du monde si nécessaire.
     *
     * <p>Cette méthode doit être appelée après chaque combat ou perte de points de vie.
     * Elle affiche un message indiquant la mort de la créature et la supprime
     * des listes correspondantes du monde ({@link World#getMaListePers()} ou
     * {@link World#getMaListeMons()}).</p>
     *
     * @param monde le monde dans lequel se trouve la créature
     */
    public void checkMort(World monde) {
        if (this.ptVie <= 0) {
            System.out.println(this.getClass().getSimpleName() + " est mort !");

            // Suppression de la créature du monde
            if (this instanceof Personnage p) {
                monde.getMaListePers().remove(p);
            } else if (this instanceof Monstre m) {
                monde.getMaListeMons().remove(m);
            }
        }
    }

}
