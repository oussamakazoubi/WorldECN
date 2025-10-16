package org.centrale.objet.WoE;

import java.util.Random;

public class NuageToxique extends Objet implements Deplacable, Combattant {

    private int degAtt;
    private int distAttMax;

    public NuageToxique(String nom, Point2D pos, int degAtt, int distAttMax) {
        super(nom,pos);
        this.degAtt = degAtt;
        this.distAttMax = distAttMax;
    }

    public int getDegAtt() {
        return degAtt;
    }

    public int getDistAttMax() {
        return distAttMax;
    }


    public void setDegAtt(int degAtt) {
        this.degAtt = degAtt;
    }

    public void setDistAttMax(int distAttMax) {
        this.distAttMax = distAttMax;
    }



    @Override
    public void combattre(Creature c) {
        Random rand = new Random();
        int degatsSubis = 0;
        double dist = this.getPosition().distance(c.getPos());
        System.out.println("Tentative de combat ");
        System.out.println("L’ennemi a " + c.getPtVie() + " points de vie.");

        // Combat corps à corps
        if (dist == 1) {
            System.out.println("Le combat au corps à corps débute !");
            int jetAttaque = rand.nextInt(100) + 1;
            // Attacking
            if (jetAttaque > c.getPageAtt()) {
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


    @Override
    public void utiliserObjet(Personnage p) {
        this.combattre(p);
    }

    @Override
    public void deplace() {
        Random rand = new Random();
        int dx = rand.nextInt(3) - 1;
        int dy = rand.nextInt(3) - 1;
        if (dx==0 && dy==0) this.deplace();
        else this.getPosition().translate(dx, dy);
    }
}
