/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.centrale.objet.WoE;

/**
 * Interface représentant les entités capables de combattre dans le monde du jeu.
 * Toute classe qui implémente cette interface doit définir la méthode {@code combattre(Creature c)},
 * qui gère la logique d’un combat entre cette entité et une autre créature.
 *
 * @author Imane
 */
public interface Combattant {

    /**
     * Permet à l'objet de combattre une autre créature.
     * L'implémentation de cette méthode dépend du type de combattant
     * (par exemple : un guerrier, un archer, un monstre, etc.).
     *
     * @param c la créature adverse à affronter
     */
    public void combattre(Creature c);
}
