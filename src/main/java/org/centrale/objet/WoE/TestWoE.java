/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE;


/**
 * Classe de test pour le projet "World of ECN".
 * 
 * 
 * Teste la création du monde, l'affichage des entités, 
 * la sauvegarde et le chargement.
 */


public class TestWoE {

    public static void main(String[] args) {
        

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
    }
}
