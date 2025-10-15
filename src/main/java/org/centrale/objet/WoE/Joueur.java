/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author user
 */


public class Joueur {
    private String nom;
    private String pseudo;
    private String email;
    private int mdp;
    private static final ArrayList<Class<? extends Personnage>> PersoJouable = new ArrayList<>(Arrays.asList(Guerrier.class, Archer.class));
    private Personnage persoJoueur;
    private ArrayList<Objet> inventaire;

    public Joueur() {
        nom = "";
        pseudo = "";
        email = "";
        mdp = 0;
        inventaire = new ArrayList<>();
    }

    public Joueur(String nom, String pseudo, String email, int mdp) {
        this();
        this.nom = nom;
        this.pseudo = pseudo;
        this.email = email;
        this.mdp = mdp;
    }

    public Joueur(Joueur j) {
        this();
        this.nom = j.nom;
        this.pseudo = j.pseudo;
        this.email = j.email;
        this.mdp = j.mdp;
        this.inventaire = j.inventaire;
    }

    public Personnage getPersoJoueur() {
        return persoJoueur;
    }

    public void setPersoJoueur(Personnage persoJoueur) {
        this.persoJoueur = persoJoueur;
    }

    public ArrayList<Objet> getInventaire() {
        return inventaire;
    }

    public void setInventaire(ArrayList<Objet> inventaire) {
        this.inventaire = inventaire;
    }

    public ArrayList<Class<? extends Personnage>> getPersoJouable() {
        return PersoJouable;
    }


    public void ChoisirPersonnage(World monde) {
        inventaire = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        Personnage perso = null;
        String persoChoisi;

        while (perso == null) {
            System.out.println("Rentrer un personnage jouable :");
            persoChoisi = scanner.nextLine();

            for (Class<? extends Personnage> cl : PersoJouable) {
                if (persoChoisi.equalsIgnoreCase(cl.getSimpleName())) {
                    System.out.println("Rentrer le nom du personnage choisi :");
                    String NomPersoChoisi = scanner.nextLine();
                    try {
                        perso = cl.getDeclaredConstructor().newInstance();
                        monde.definirStatsAlea(perso);
                        perso.setNom(NomPersoChoisi);
                        this.persoJoueur = perso;
                        monde.maListePers.add(perso);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            if (perso == null) {
                System.out.println("Classe non jouable. Réessayez !");
                ChoisirPersonnage(monde);
            }
    }

}


    private void combattreJoueur(World monde) {
        ArrayList<Creature> cibles = monde.ChercherCibles(this.persoJoueur);
        int taillesCibles = cibles.size();

        if (cibles.isEmpty()) {
            System.out.println("Aucune cible à portée !");
            return;
        }

        String msg = "";
        System.out.println("=== Cibles disponibles ===");
        for (int i = 0; i < taillesCibles; i++) {
            Creature c = cibles.get(i);
            if (c instanceof Personnage){
                msg = msg + ((Integer) (i +1)).toString() + " - " + ((Personnage) c).getNom();
            }
            else {
                msg = msg + ((Integer) (i + 1)).toString() + " - ";
            }
            msg = "\n" + msg + "  (" + c.getClass().getSimpleName() +
                    ") à la position " + c.getPos().toString();
        }
        msg = msg + "\n" + ((Integer) (taillesCibles+1)).toString() + " -  Revenir au menu precedent.";
        System.out.println(msg);

        Scanner input = new Scanner(System.in);
        System.out.print("Choisissez la cible à attaquer (numéro) : ");
        int choixCible=0;
        try {
            choixCible = input.nextInt();
            if (choixCible < 1 || choixCible > cibles.size() + 1) {
                System.out.println("Numéro invalide. Aucun combat effectué.");
                combattreJoueur(monde);
            }
            else if (choixCible == cibles.size() + 1) {
                return;
            }
        } catch (Exception e) {
            System.out.println("Entrée invalide. Aucun combat effectué.");
            combattreJoueur(monde);
        }

        Creature cible = cibles.get(choixCible - 1);

        try {
            this.persoJoueur.getClass()
                    .getMethod("combattre", Creature.class)
                    .invoke(this.persoJoueur, cible);

        } catch (Exception e) {
            System.out.println("Ce personnage ne peut pas combattre !");
            System.out.println(e.getMessage());
        }
    }


    public void choisirPreference(World monde) {
        if (this.persoJoueur == null) {
            System.out.println("Aucun personnage valide choisi. Fin du tour.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        String choix;

        do {
            System.out.println("Choisir action : 1=deplacer, 2=combattre, 3=Tour Suivante, 4=Inventaire , 5=Afficher monde, 6=quitter");
            choix = scanner.nextLine();

            switch (choix) {
                case "1", "deplacer" -> this.deplaceJoueur(monde);

                case "2", "combattre" -> this.combattreJoueur(monde);

                case "3", "Tour Suivante" -> monde.tourDeJeu();

                case "4", "Iventaire" -> utiliserObjetInventaire();

                case "5", "Afficher monde" -> monde.affiche();

                case "6", "quitter" -> System.out.println("Fin de jeu");

                default -> System.out.println("Refais ton choix !");
            }

        } while (!choix.equals("6"));

        monde.chercherObjet(this.persoJoueur);
    }


    public void utiliserObjetInventaire() {
        if (inventaire.isEmpty()) {
            System.out.println("Votre inventaire est vide !");
            return;
        }

        System.out.println("=== Inventaire ===");
        for (int i = 0; i < inventaire.size(); i++) {
            System.out.println((i + 1) + " - " + inventaire.get(i).toString());
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Entrez le numéro de l’objet à utiliser : ");

        try {
            int choix = sc.nextInt();
            if (choix < 1 || choix > inventaire.size()) {
                System.out.println("Numéro invalide !");
                return;
            }

            Objet objet = inventaire.get(choix - 1);
            System.out.println("Vous utilisez : " + objet.getClass().getSimpleName());

            // L’objet est utilisé : applique ses effets
            objet.utiliserObjet(this.persoJoueur);

            // L’objet est retiré de l’inventaire
            inventaire.remove(choix - 1);

            // L’objet rejoint la liste des effets actifs si applicable
            if (objet instanceof Nourriture) {
                persoJoueur.getUtilisables().add((Nourriture) objet);
            }

        } catch (Exception e) {
            System.out.println("Entrée invalide. Veuillez entrer un numéro.");
            utiliserObjetInventaire();
        }
    }



        public void deplaceJoueur(World monde) {
            System.out.println("\nVotre position est : " + persoJoueur.getPos().toString());
            System.out.println("Choisissez une direction pour vous déplacer :");
            System.out.println("  Z  : haut");
            System.out.println("  S  : bas");
            System.out.println("  Q  : gauche");
            System.out.println("  D  : droite");
            System.out.print("Votre choix : ");

            Scanner sc = new Scanner(System.in);
            char choix = sc.next().toLowerCase().charAt(0);
            int persoX = persoJoueur.getPos().getX();
            int persoY = persoJoueur.getPos().getY();

            int dx = 0, dy = 0;
            if (choix=='z' && persoY<World.TAILLE_PAR_DEFAUT-1) {
                dy = 1;
            }
            else if (choix=='s' && persoY>0) {
                dy = -1;
            }
            else if (choix=='q' && persoX>0) {
                dx = -1;
            }
            else if (choix=='d' && persoX<World.TAILLE_PAR_DEFAUT-1) {
                dx = 1;
            }
            else {
                System.out.println("Entrée invalide. Vous restez sur place.");
                deplaceJoueur(monde);
                return;
            }

            Point2D checkPosition = new Point2D(persoX+dx, persoY+dy);
            if (monde.estOccupee(checkPosition)) {
                deplaceJoueur(monde);
                return;
            }
            persoJoueur.getPos().translate(dx, dy);
            monde.chercherObjet(this.persoJoueur);
            System.out.println(persoJoueur.getNom() + " se déplace en " + persoJoueur.getPos().toString());
        }


}
