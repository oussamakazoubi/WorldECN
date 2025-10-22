/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.centrale.objet.WoE;

/**
 * Interface représentant les entités pouvant se déplacer dans le monde du jeu.
 * Toute classe qui implémente cette interface doit définir la méthode {@code deplace()},
 * qui gère la logique du déplacement spécifique à l’objet.
 *
 * @author Imane
 */
public interface Deplacable {

    /**
     * Permet de déplacer l'objet selon sa propre logique de mouvement.
     * Par exemple, un personnage peut se déplacer d'une case,
     * un monstre peut poursuivre une cible, etc.
     */
    public void deplace();
    public void deplace(World monde);
}
