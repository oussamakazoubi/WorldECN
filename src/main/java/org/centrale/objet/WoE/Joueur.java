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
    private String persoChoisi;
    private ArrayList<Class<? extends Personnage>> PersoJouable;

    public Joueur() {
        nom = "";
        pseudo = "";
        email = "";
        mdp = 0;
        PersoJouable = new ArrayList<>(Arrays.asList(Guerrier.class, Archer.class));
    }

    public Joueur(String nom, String pseudo, String email, int mdp, ArrayList<Class<? extends Personnage>> PersoJouable) {
        this.nom = nom;
        this.pseudo = pseudo;
        this.email = email;
        this.mdp = mdp;
        this.PersoJouable = PersoJouable;
    }

    public Joueur(Joueur j) {
        this.nom = j.nom;
        this.pseudo = j.pseudo;
        this.email = j.email;
        this.mdp = j.mdp;
        this.PersoJouable = j.PersoJouable;
    }

   public Personnage ChoisirPersonnage(World monde) {
    Scanner scanner = new Scanner(System.in);
    Personnage perso = null;

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
                    monde.maListePers.add(perso);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (perso == null) {
            System.out.println("Classe non jouable. Réessayez !");
        }
    }

    return perso;
}


    public void choisirPreference(World monde) {
        Personnage perso = ChoisirPersonnage(monde);
        if (perso == null) {
            System.out.println("Aucun personnage valide choisi. Fin du tour.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        String choix;

        do {
            System.out.println("Choisir action : 1=deplacer, 2=combattre, 3=quitter");
            choix = scanner.nextLine();

            switch (choix) {
                case "1", "deplacer" -> perso.deplace(monde);

                case "2", "combattre" -> {
                    Creature cible = monde.ChercherCible(perso);
                    if (cible != null) {
                        try {
                            // Utilisation de réflexion pour invoquer combattre si existant
                            perso.getClass()
                                 .getMethod("combattre", Creature.class)
                                 .invoke(perso, cible);
                        } catch (Exception e) {
                            System.out.println("Ce personnage ne peut pas combattre !");
                        }
                    } else {
                        System.out.println("Aucune cible à portée !");
                    }
                }

                case "3", "quitter" -> System.out.println("Fin de jeu");

                default -> System.out.println("Refais ton choix !");
            }

        } while (!choix.equals("3"));

        monde.chercherObjet(perso);
    }
}
