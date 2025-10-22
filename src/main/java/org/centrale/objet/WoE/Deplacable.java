/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.centrale.objet.WoE;

/**
 * Interface représentant les entités pouvant se déplacer dans le monde du jeu.
 * <p>
 * Toute classe qui implémente cette interface doit définir les méthodes :
 * </p>
 * <ul>
 *   <li>{@code deplace()} — déplace l'objet selon sa propre logique interne (ex. mouvement simple aléatoire) ;</li>
 *   <li>{@code deplace(World monde)} — déplace l'objet dans le monde en tenant compte des contraintes (ex. cases occupées).</li>
 * </ul>
 *
 * @author Imane
 */
public interface Deplacable {

    /**
     * Permet de déplacer l'objet selon sa propre logique de mouvement.
     * Par exemple, un personnage peut se déplacer d'une case aléatoirement.
     */
    public void deplace();

    /**
     * Permet de déplacer l'objet dans le monde, en prenant en compte
     * les contraintes du terrain ou la présence d'autres entités.
     *
     * @param monde le monde dans lequel l'objet se déplace
     */
    public void deplace(World monde);
}
