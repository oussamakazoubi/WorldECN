/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
    private Personnage persoJoueur;
    private ArrayList<Objet> inventaire;

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

    public void setPersoJouable(ArrayList<Class<? extends Personnage>> persoJouable) {
        PersoJouable = persoJouable;
    }

    public void ChoisirPersonnage(World monde) {
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
                ChoisirPersonnage(monde);
            }
    }

    this.persoJoueur = new Personnage(perso);
}


    public void choisirPreference(World monde) {
        if (this.persoJoueur == null) {
            System.out.println("Aucun personnage valide choisi. Fin du tour.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        String choix;

        do {
            System.out.println("Choisir action : 1=deplacer, 2=combattre, 3=inventaire, 4=quitter");
            choix = scanner.nextLine();

            switch (choix) {
                case "1", "deplacer" -> this.persoJoueur.deplace(monde);

                case "2", "combattre" -> {
                    Creature cible = monde.ChercherCible(this.persoJoueur);
                    if (cible != null) {
                        try {
                            // Utilisation de réflexion pour invoquer combattre si existant
                            this.persoJoueur.getClass()
                                 .getMethod("combattre", Creature.class)
                                 .invoke(this.persoJoueur, cible);
                        } catch (Exception e) {
                            System.out.println("Ce personnage ne peut pas combattre !");
                        }
                    } else {
                        System.out.println("Aucune cible à portée !");
                    }
                }

                case "3", "Iventaire" -> utiliserObjetInventaire();

                case "4", "quitter" -> System.out.println("Fin de jeu");

                default -> System.out.println("Refais ton choix !");
            }

        } while (!choix.equals("4"));

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
}
