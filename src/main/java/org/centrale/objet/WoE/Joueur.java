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
    Point2D pos;
    private String nom;
    private String pseudo;
    private String email;
    private int mdp;
    private Personnage persoJoueur;
    private ArrayList<Class<? extends Personnage>> PersoJouable;
    private ArrayList<Objet> Inventaire;
    
    

    public Joueur() {
        
        nom = "";
        pseudo = "";
        email = "";
        mdp = 0;
        PersoJouable = new ArrayList<>(Arrays.asList(Guerrier.class, Archer.class));
        Inventaire = new ArrayList<>();
        pos=new Point2D();
    }

    public Point2D getPos() {
        return pos;
    }

    public void setPos(Point2D pos) {
        this.pos = pos;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMdp() {
        return mdp;
    }

    public void setMdp(int mdp) {
        this.mdp = mdp;
    }

    

    public ArrayList<Class<? extends Personnage>> getPersoJouable() {
        return PersoJouable;
    }

    public void setPersoJouable(ArrayList<Class<? extends Personnage>> PersoJouable) {
        this.PersoJouable = PersoJouable;
    }

    public ArrayList<Objet> getInventaire() {
        return Inventaire;
    }

    public void setInventaire(ArrayList<Objet> Inventaire) {
        this.Inventaire = Inventaire;
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
    
    

   public void ChoisirPersonnage(World monde) {
       String persoChoisi;
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

    persoJoueur= new Personnage(perso);
}
   public void utiliserObjetInventaire(World monde,int index){
       if (index < 0 || index >= Inventaire.size()) {
            System.out.println("Index invalide !");
            return;
        }

        Objet obj = Inventaire.get(index);
        obj.utiliserObjet(persoJoueur);
        System.out.println("Objet utilisé : " + obj.getNom());
        Inventaire.remove(index);
        
   }


    public void choisirPreference(World monde) {
        
        if (persoJoueur == null) {
            System.out.println("Aucun personnage valide choisi. Fin du tour.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        String choix;

        do {
            System.out.println("Choisir action : 1=deplacer, 2=combattre, 3=quitter");
            choix = scanner.nextLine();

            switch (choix) {
                case "1", "deplacer" -> persoJoueur.deplace(monde);
                      

                case "2", "combattre" -> {
                    Creature cible = monde.ChercherCible(persoJoueur);
                    if (cible != null) {
                        try {
                            // Utilisation de réflexion pour invoquer combattre si existant
                            persoJoueur.getClass()
                                 .getMethod("combattre", Creature.class)
                                 .invoke(persoJoueur, cible);
                        } catch (Exception e) {
                            System.out.println("Ce personnage ne peut pas combattre !");
                        }
                    } else {
                        System.out.println("Aucune cible à portée !");
                    }
                }

                case "3", "quitter" -> System.out.println("Fin de jeu");
                case "4", "utiliObjetInventaire" -> System.out.println("Fin de jeu");
                default -> System.out.println("Refais ton choix !");
            }

        } while (!choix.equals("3"));

        monde.chercherObjet(persoJoueur);
    }
}
