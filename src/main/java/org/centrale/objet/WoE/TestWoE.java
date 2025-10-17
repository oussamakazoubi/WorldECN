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

                MainMenu.startGame();
            }
        }

}
