/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE;
import java.util.*;

/**
 * 
 * @author Imane
 */
/**
 * Classe de test pour le projet "World of ECN".
 * Cette classe permet de tester la création du monde, l'affichage des entités 
 * (personnages, monstres et objets), ainsi que le calcul du total des points de vie des personnages.
 * Elle exécute également les tests de performance concernant les conteneurs utilisés pour stocker 
 * les entités du monde.
 * 
 * <p>Le programme effectue les actions suivantes :</p>
 * <ul>
 *   <li>Création du monde avec un nombre aléatoire d'entités (personnages, monstres, objets)</li>
 *   <li>Affichage des entités : personnages, monstres, objets</li>
 *   <li>Calcul du total des points de vie des personnages</li>
 *   <li>Exécution des tests de performance pour comparer les structures de données utilisées</li>
 * </ul>
 * 
 * @param args Arguments en ligne de commande (non utilisés dans cette méthode).
 */
public class TestWoE {
    /**
     * Méthode principale qui exécute les tests et l'affichage des entités du monde.
     * 
     * <p>Cette méthode crée un monde aléatoire avec un certain nombre de personnages, monstres et objets.
     * Elle affiche ensuite les entités présentes dans le monde, calcule le total des points de vie des personnages, 
     * et lance des tests de performance pour comparer les structures de données utilisées dans le monde.</p>
     *
     * @param args Arguments en ligne de commande (non utilisés ici).
     */
    
      // Exemple de méthode pour calculer la somme des points de vie dans TestWoE
    
    /**
     * Calcule la somme des points de vie des personnages dans la liste par indice.
     * 
     * @param liste Liste de personnages.
     * @return La somme des points de vie.
     */
    public static long totalPvParIndice(List<Personnage> liste) {
        long somme = 0;
        for (int i = 0; i < liste.size(); i++) {
            somme += liste.get(i).getPtVie();  // Accède au personnage par indice
        }
        return somme;
    }


    /**
     * Calcule la somme des points de vie des personnages dans la liste via un itérateur.
     * 
     * @param liste Liste de personnages.
     * @return La somme des points de vie.
     */
    /*public static long totalPvParIterator(List<Personnage> liste) {
        long somme = 0;
        Iterator<Personnage> it = liste.iterator();  // Créer un itérateur pour la liste
        while (it.hasNext()) {
            somme += it.next().getPtVie();  // Accède au prochain personnage via l'itérateur
        }
        return somme;
    }

    public static void main(String[] args) {
        // Création du monde avec des entités aléatoires
        World monde = new World();
        monde.creerMondeAlea(1, 1, 1, 2, 0, 1, 1);  
        monde.affiche();  // Affichage des entités du monde
        
        // Affichage des personnages
        System.out.println("=== Personnages ===");
        int totalPtVie = 0;
        for (Personnage p : monde.maListePers) {  // Accès à la liste protégée des personnages
            System.out.println(p);
            totalPtVie += p.getPtVie();
        }
        System.out.println("Total des points de vie des personnages : " + totalPtVie);

        // Affichage des monstres
        System.out.println("\n=== Monstres ===");
        for (Monstre m : monde.maListeMons) {  // Accès à la liste protégée des monstres
            System.out.println(m);
        }

        // Affichage des objets
        System.out.println("\n=== Objets ===");
        for (Objet o : monde.maListeobj) {  // Accès à la liste protégée des objets
            System.out.println(o);
        }

         // Appel des fonctions pour calculer la somme des points de vie des personnages
        long sommeParIndice = totalPvParIndice(monde.maListePers);
        System.out.println("Total des points de vie des personnages (par indice) : " + sommeParIndice);
        
        long sommeParIterator = totalPvParIterator(monde.maListePers);
        System.out.println("Total des points de vie des personnages (par itérateur) : " + sommeParIterator);

        // Exécution des tests de performance 
        TestPerformance.tester();  // Exécution de la méthode de test de performance
   

        }*/
    public static void main(String[] args) {


        // === 1. Créer le monde ===
        System.out.println("=== Initialisation du Monde ===");
        World monde = new World();

        // Génération aléatoire de créatures et objets
        // (Archers, Paysans, Lapins, Guerriers, Loups, Potions, Épées)
        monde.creerMondeAlea(
                2, // nbArchers
                2, // nbPaysans
                2, // nbLapins
                2, // nbGuerriers
                2, // nbLoups
                3, // nbPotions
                2, // nbÉpées
                2, // nbChampignonPourri
                1 // FeuilleEpinart
        );

        System.out.println("=== Monde généré ===");
        monde.affiche();

        // === 2. Créer un joueur humain ===
        Joueur joueur = new Joueur("Imane", "Heroine", "imane@woe.com", 1234);
        monde.setJoueur(joueur);

        // === 3. Choisir un personnage jouable ===
        System.out.println("\n=== Création du personnage joueur ===");
        joueur.ChoisirPersonnage(monde);
        System.out.println("Votre personnage : ");
        joueur.getPersoJoueur().affiche();

        // === 4. Tester les actions du joueur ===
        System.out.println("\n=== Début du jeu ===");
        joueur.choisirPreference(monde);

        System.out.println("\n=== Fin du programme ===");

        /*
        Joueur j=new Joueur();
        World monde= new World();
        monde.creerMondeAlea(2, 3, 3, 5, 2, 1,  1);
        j.ChoisirPersonnage(monde);
        j.choisirPreference(monde);
        //monde.tourDeJeuHumain(j);
        //monde.tourDeJeu();
    
    
    
        // 1️⃣ NullPointerException
        try {
            String s = null;
            System.out.println(s.length());
        } catch (NullPointerException e) {
            System.out.println("Erreur : objet null !");
        }

        // 2️⃣ ArrayIndexOutOfBoundsException
        try {
            int[] tab = {1, 2, 3};
            System.out.println(tab[5]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Erreur : indice en dehors du tableau !");
        }

        // 3️⃣ ArithmeticException
        try {
            int x = 5 / 0;
        } catch (ArithmeticException e) {
            System.out.println("Erreur : division par zéro !");
        }

        // 4️⃣ ClassCastException
        try {
            Object o = new Integer(10);
            String str = (String) o; // Mauvais cast
        } catch (ClassCastException e) {
            System.out.println("Erreur : mauvais transtypage !");
        }

        // 5️⃣ NumberFormatException
        try {
            String nombre = "abc";
            int n = Integer.parseInt(nombre);
        } catch (NumberFormatException e) {
            System.out.println("Erreur : mauvais format de nombre !");
        }

        // 6️⃣ StackOverflowError
        try {
            recursive();
        } catch (StackOverflowError e) {
            System.out.println("Erreur : pile d'appels débordée !");
        }

        // 7️⃣ ConcurrentModificationException
        try {
            List<String> liste = new ArrayList<>();
            liste.add("A");
            liste.add("B");
            for (String s : liste) {
                liste.remove(s); // On modifie pendant qu'on parcourt
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("Erreur : modification concurrente !");
        }

        System.out.println("\nTous les tests sont terminés !");
    }

    // méthode récursive sans fin
    public static void recursive() {
        recursive(); // s'appelle elle-même sans condition -> StackOverflowError


         */
    
    }
}


