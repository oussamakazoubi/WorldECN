package org.centrale.objet.WoE;

import java.util.Random;

/**
 * Représente un {@code NuageToxique}, un objet mobile et dangereux dans le monde.
 *
 * <p>Le nuage toxique agit comme une entité hybride :
 * <ul>
 *   <li>Il est un {@link Objet} pouvant se déplacer aléatoirement dans le monde ({@link Deplacable}).</li>
 *   <li>Il peut attaquer les {@link Creature} proches de lui via l’interface {@link Combattant}.</li>
 * </ul>
 * </p>
 *
 * <p>Ses attaques simulent une propagation de gaz toxique capable d’infliger des dégâts
 * aux créatures situées à proximité. Il dispose d’une puissance d’attaque
 * ({@code degAtt}) et d’une distance maximale d’attaque ({@code distAttMax}).</p>
 *
 * @author Oussama
 * @see Objet
 * @see Creature
 * @see Deplacable
 * @see Combattant
 */
public class NuageToxique extends Objet implements Deplacable, Combattant {

    /** Dégâts d’attaque infligés par le nuage toxique. */
    private int degAtt;

    /** Distance maximale d’attaque du nuage toxique. */
    private int distAttMax;

    // ===================== CONSTRUCTEURS =====================

    /**
     * Constructeur complet avec paramètres.
     *
     * @param nom nom du nuage toxique
     * @param pos position initiale du nuage dans le monde
     * @param degAtt dégâts infligés lors d’une attaque
     * @param distAttMax distance maximale à laquelle le nuage peut infliger des dégâts
     */
    public NuageToxique(String nom, Point2D pos, int degAtt, int distAttMax) {
        super(nom, pos);
        this.degAtt = degAtt;
        this.distAttMax = distAttMax;
    }

    // ===================== ACCESSEURS =====================

    /** @return les dégâts infligés par le nuage toxique */
    public int getDegAtt() {
        return degAtt;
    }

    /** @return la distance maximale d’attaque du nuage toxique */
    public int getDistAttMax() {
        return distAttMax;
    }

    /** @param degAtt nouveaux dégâts d’attaque */
    public void setDegAtt(int degAtt) {
        this.degAtt = degAtt;
    }

    /** @param distAttMax nouvelle distance maximale d’attaque */
    public void setDistAttMax(int distAttMax) {
        this.distAttMax = distAttMax;
    }

    // ===================== COMBAT =====================

    /**
     * Permet au nuage toxique d’attaquer une {@link Creature} à proximité.
     *
     * <p>Le combat repose sur un système probabiliste :
     * <ol>
     *   <li>Un jet d’attaque aléatoire détermine la réussite de l’attaque.</li>
     *   <li>Si l’attaque réussit, la cible tente une parade (jet de défense).</li>
     *   <li>Les dégâts subis sont calculés selon les valeurs d’attaque et de défense.</li>
     * </ol>
     *
     * <p>Le nuage ne peut attaquer qu’au corps à corps (distance = 1).</p>
     *
     * @param c la créature ciblée par le nuage toxique
     */
    @Override
    public void combattre(Creature c) {
        Random rand = new Random();
        int degatsSubis = 0;
        double dist = this.getPosition().distance(c.getPos());

        System.out.println("Tentative de combat ");
        System.out.println("L’ennemi a " + c.getPtVie() + " points de vie.");

        // Combat au corps à corps uniquement
        if (dist == 1) {
            System.out.println("Le nuage toxique enveloppe sa cible !");
            int jetAttaque = rand.nextInt(100) + 1;

            if (jetAttaque > c.getPageAtt()) {
                System.out.println("L’attaque échoue — la cible évite le nuage.");
            } else {
                System.out.println("L’attaque réussit !");
                int jetParade = rand.nextInt(100) + 1;

                if (jetParade > c.getPagePar()) {
                    System.out.println("La défense a échoué !");
                    degatsSubis = this.getDegAtt();
                    System.out.println(" - " + degatsSubis + " points de dégâts.");
                } else {
                    System.out.println(" - " + this.getDegAtt() + " dégâts infligés, "
                            + c.getPtPar() + " absorbés, soit "
                            + (this.getDegAtt() - c.getPtPar()) + " dégâts nets.");
                    degatsSubis = this.getDegAtt() - c.getPtPar();
                }

                degatsSubis = Math.max(0, degatsSubis);
                c.setPtVie(c.getPtVie() - degatsSubis);

                System.out.println("Dégâts infligés : " + degatsSubis);
                System.out.println("PV restants du défenseur : " + c.getPtVie());
            }
        } else {
            System.out.println("\nLa cible est trop éloignée pour être atteinte par le nuage.");
        }
    }

    // ===================== UTILISATION =====================

    /**
     * Utilise l’objet sur un personnage.
     * <p>Dans le cas du nuage toxique, cela déclenche simplement une attaque
     * contre le personnage passé en paramètre.</p>
     *
     * @param p le personnage affecté par le nuage
     */
    @Override
    public void utiliserObjet(Personnage p) {
        this.combattre(p);
    }

    // ===================== DÉPLACEMENT =====================

    /**
     * Déplace le nuage toxique d’un pas aléatoire dans une direction aléatoire.
     *
     * <p>Le déplacement est compris entre -1 et +1 sur chaque axe.
     * Si le déplacement généré est nul, la méthode se relance récursivement
     * pour garantir un mouvement effectif.</p>
     */
    @Override
    public void deplace() {
        Random rand = new Random();
        int dx = rand.nextInt(3) - 1;
        int dy = rand.nextInt(3) - 1;

        if (dx == 0 && dy == 0) {
            this.deplace();
        } else {
            this.getPosition().translate(dx, dy);
        }
    }

    /**
     * Déplace le nuage toxique dans le monde aléatoirement,
     * en évitant les cases déjà occupées.
     *
     * <p>Le nuage essaie jusqu’à 50 positions différentes pour trouver
     * une case libre. Si aucune n’est disponible après 50 essais,
     * il reste sur place.</p>
     *
     * @param monde le monde dans lequel le nuage se déplace
     */
    public void deplace(World monde) {
        int dx, dy;
        int count = 0;
        Random rand = new Random();
        Point2D newpos;
        do {
            dx = rand.nextInt(3) - 1;
            dy = rand.nextInt(3) - 1;
            newpos = new Point2D(this.getPosition().getX() + dx, this.getPosition().getY() + dy);
            count++;
        } while (monde.estOccupee(newpos) || (dx == 0 && dy == 0) && count < 50);
        if (count < 50) {
            this.setPosition(newpos);
        }
    }

}
