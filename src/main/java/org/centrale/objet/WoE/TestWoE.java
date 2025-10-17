/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.centrale.objet.WoE;

import java.util.Scanner;

/**
 * Classe principale contenant la méthode {@code main} pour lancer le jeu World of ECN.
 *
 * <p>Elle affiche un menu principal avec trois options :</p>
 * <ul>
 *   <li>1 - Nouvelle partie : création d’un monde aléatoire et d’un joueur</li>
 *   <li>2 - Charger partie : chargement d’un fichier de sauvegarde existant</li>
 *   <li>3 - Quitter : fermeture du jeu</li>
 * </ul>
 *
 * <p>Avant d’afficher le menu du joueur (déplacement, combat, etc.), une grille du monde est
 * affichée à l’écran. Elle contient les symboles suivants :</p>
 * <ul>
 *   <li><b>J</b> : personnage du joueur</li>
 *   <li><b>P</b> : autre personnage</li>
 *   <li><b>M</b> : monstre</li>
 *   <li><b>O</b> : objet</li>
 *   <li><b>.</b> : case vide</li>
 * </ul>
 *
 * @author Imane
 */
public class TestWoE {

    public static void main(String[] args) {

        
/*
        System.out.println("=== Lancement du jeu World of ECN ===");

        // --- Création du monde ---
        World monde = new World();
        monde.setLongueur(20);
        monde.setLargeur(20);

        // --- Création du joueur ---
        Joueur joueur = new Joueur("Imane", "Imane123", "imane@ecn.fr", 1234);
        joueur.ChoisirPersonnage(monde);
        monde.setJoueur(joueur);

        // --- Création d’un monde aléatoire ---
        monde.creerMondeAlea(
                2, // nb Archers
                2, // nb Paysans
                3, // nb Lapins
                2, // nb Guerriers
                2, // nb Loups
                2, // nb Potions
                2, // nb Épées
                1, // nb ChampignonsPourris
                1  // nb Feuilles d’épinard
        );

        System.out.println("\n=== Monde initial créé ===");
        monde.affiche();
        
        //Scanner scan= new Scanner();
        

        // --- Déroulement de quelques tours ---
        System.out.println("\n=== Début du jeu ===");
        for (int i = 0; i < 2; i++) {
            System.out.println("\n--- Tour " + (i + 1) + " ---");
            monde.tourDeJeuHumain(joueur);
        }
        System.out.println("\n=== Fin du test ===");
    }*/
    


        World monde = new World();
        monde.setLongueur(10);
        monde.setLargeur(10);
        monde.creerMondeAlea(2, 2, 2, 2, 2, 2, 2, 1, 1);

        new InterfaceGraphique(monde);
        monde.affiche();
    


            

}
}


