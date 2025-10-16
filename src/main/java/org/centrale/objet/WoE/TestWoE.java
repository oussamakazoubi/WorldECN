/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE;
import java.util.*;

/**
 * Classe de test pour le projet "World of ECN".
 * Teste la création du monde, l'affichage des entités, 
 * la sauvegarde et le chargement.
 */
public class TestWoE {

    public static void main(String[] args) {
 // ========================
        // 1️⃣ Création du monde
        // ========================
        World monde = new World();
        monde.setLongueur(50); // par exemple 50x50
        monde.setLargeur(50);

        // ========================
        // 2️⃣ Création du joueur
        // ========================
        Joueur joueur = new Joueur();
        joueur.setNom("Hero");
        joueur.setPersoJoueur(new Guerrier());
        joueur.getPersoJoueur().setPos(new Point2D(5, 5));
        monde.setJoueur(joueur);

        // ========================
        // 3️⃣ Création aléatoire d'autres entités
        // ========================
        monde.creerMondeAlea(
            2, // archers
            2, // paysans
            2, // lapins
            2, // guerriers
            2, // loups
            2, // potions
            2, // épées
            2, // champignons
            2  // feuilles d’épinard
        );

        // ========================
        // 4️⃣ Affichage du monde initial
        // ========================
        System.out.println("=== Monde initial ===");
        monde.affiche();

        // ========================
        // 5️⃣ Sauvegarde
        // ========================
        String fichier = "sauvegarde_test.txt";
        monde.sauvegardePartie(fichier);

        // ========================
        // 6️⃣ Création d’un nouveau monde vide et chargement
        // ========================
        World mondeCharge = new World();
        mondeCharge.chargementPartie(fichier);

        // ========================
        // 7️⃣ Affichage du monde chargé
        // ========================
        System.out.println("\n=== Monde chargé ===");
        mondeCharge.affiche();

        // ========================
        // Tout le reste du code de test, exceptions, anciens tests, etc.
        // est commenté pour l'instant.
        // ========================
    }
}
       