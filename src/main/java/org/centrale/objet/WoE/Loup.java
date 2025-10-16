/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE;
import java.util.StringTokenizer;
import java.util.Random;

/**
 * Représente un loup, un type de monstre agressif capable de combattre d'autres créatures.
 * 
 * <p>Le loup hérite de {@link Monstre} et implémente une méthode de combat aléatoire
 * basée sur des jets d’attaque et de parade. S’il réussit son attaque, il inflige des
 * dégâts à sa cible selon ses statistiques.</p>
 * 
 * <p>Le combat est probabiliste : chaque tentative dépend de la chance d’attaque
 * et de la chance de parade des deux créatures impliquées.</p>
 * 
 * @author Imane
 */
public class Loup extends Monstre implements Combattant{

    /**
     * Constructeur par défaut.
     * <p>Crée un loup avec des caractéristiques nulles et une position (0,0).</p>
     */
    public Loup() {
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
     * @param p position du loup
     */
    public Loup(int pV, int dA, int pPar, int paAtt, int paPar, Point2D p) {
        super(pV, dA, pPar, paAtt, paPar, p);
    }

    /**
     * Constructeur de copie.
     *
     * @param l loup à copier
     */
    public Loup(Loup l) {
        super(l);
    }
    
    /**
     * Constructeur à partir d'une ligne de sauvegarde texte.
     * <p>Exemple de ligne :
     * <pre>
     * Loup 30 80 50 50 50 19 3
     * </pre>
     * </p>
     */
    public Loup(String ligne) {
        StringTokenizer st = new StringTokenizer(ligne, " ");
        st.nextToken(); // saute "Loup"
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
     * Permet au loup d’attaquer une autre créature selon un système de jets d’attaque et de parade.
     * <p>
     * Le combat se déroule en plusieurs étapes :
     * </p>
     * <ol>
     *   <li>Un jet aléatoire détermine si l’attaque du loup réussit.</li>
     *   <li>Si l’attaque réussit, la créature attaquée tente de parer.</li>
     *   <li>Les dégâts sont calculés en fonction des caractéristiques d’attaque et de parade.</li>
     * </ol>
     * <p>
     * Les dégâts infligés ne peuvent pas être négatifs (valeur minimale : 0).
     * </p>
     *
     * @param c la créature cible du combat
     */
    @Override
    public void combattre(Creature c) {
        Random rand = new Random();
        int degatsSubis = 0;
        double dist = this.getPos().distance(c.getPos());
        System.out.println("Tentative de combat ");
        System.out.println("L’ennemi a " + c.getPtVie() + " points de vie.");

        // Combat corps à corps
        if (dist == 1) {
            System.out.println("Le combat au corps à corps débute !");
            int jetAttaque = rand.nextInt(100) + 1;
            // Attacking
            if (jetAttaque > this.getPageAtt()) {
                System.out.println("Attaque ratée");
            } else {
                System.out.println("Attaque réussie");

                int jetParade = rand.nextInt(100) + 1;
                if (jetParade > c.getPagePar()) {
                    System.out.println("La défense a échoué !");
                    degatsSubis = this.getDegAtt();
                    System.out.println(" - "+degatsSubis+" Damage");
                } else {
                    System.out.println(" - "+this.getDegAtt()+" Damage &  "
                            +c.getPtPar()+" Dmg Resiste = "+ (this.getDegAtt() - c.getPtPar()) +" Damage" );
                    degatsSubis = this.getDegAtt() - c.getPtPar();
                }

                degatsSubis = Math.max(0, degatsSubis);
                c.setPtVie(c.getPtVie() - degatsSubis);

                System.out.println("Dégâts infligés au défenseur : " + degatsSubis);
                System.out.println("PV restants du défenseur : " + c.getPtVie());
            }
        }
        else System.out.println("\n Ennemi trop éloigné pour le combat !");
    }
    
    /**
     * Retourne le texte de sauvegarde correspondant à ce loup.
     * <p>Format :
     * <pre>
     * Loup ptVie degAtt ptPar pageAtt pagePar posX posY
     * </pre>
     * </p>
     */
    public String getTexteSauvegarde() {
        return "Loup " + getPtVie() + " " + getDegAtt() + " " + getPtPar() + " " +
               getPageAtt() + " " + getPagePar() + " " +
               getPos().getX() + " " + getPos().getY();
    }
}
