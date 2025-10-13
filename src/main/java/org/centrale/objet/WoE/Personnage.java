/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.centrale.objet.WoE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * La classe {@code Personnage} représente un être jouable ou contrôlable dans le monde.
 * <p>
 * Un personnage hérite de {@link Creature} et possède des caractéristiques
 * supplémentaires comme un nom et une distance maximale d’attaque.
 * </p>
 *
 * <ul>
 *   <li>Un nom unique identifiant le personnage</li>
 *   <li>Une distance maximale d’attaque ({@code distAttMax})</li>
 *   <li>Les caractéristiques héritées : points de vie, dégâts, parade, etc.</li>
 * </ul>
 *
 * @author Imane
 * @see Creature
 */
public class Personnage extends Creature {

    private HashMap<String, Integer> effetsActifs = new HashMap<>();

    /** Nom du personnage */
    private String nom;

    /** Distance maximale d’attaque du personnage */
    private int distAttMax;

    /**
     * Constructeur par défaut.
     * <p>
     * Initialise un personnage sans nom et avec des caractéristiques nulles.
     * </p>
     */
    public Personnage() {
        super();
        this.nom = "Nom Nommee"; // C'est mieux Comme ca trust me
        this.distAttMax = 0;
    }

    /**
     * Constructeur avec paramètres permettant d’initialiser toutes les caractéristiques.
     *
     * @param n nom du personnage
     * @param pV points de vie
     * @param dA dégâts d’attaque
     * @param pPar points de parade
     * @param paAtt points d’attaque spéciale
     * @param paPar points de parade spéciale
     * @param dMax distance maximale d’attaque
     * @param p position initiale du personnage
     */
    public Personnage(String n, int pV, int dA, int pPar, int paAtt, int paPar, int dMax, Point2D p) {
        super(pV, dA, pPar, paAtt, paPar, p);
        this.nom = n;
        this.distAttMax = dMax;
    }

    /**
     * Constructeur de copie.
     *
     * @param perso personnage à copier
     */
    public Personnage(Personnage perso) {
        super(perso);
        this.nom = perso.nom;
        this.distAttMax = perso.distAttMax;
    }

    /**
     * Retourne le nom du personnage.
     *
     * @return le nom du personnage
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom du personnage.
     *
     * @param nom le nouveau nom du personnage
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne la distance maximale d’attaque.
     *
     * @return la distance maximale d’attaque
     */
    public int getDistAttMax() {
        return distAttMax;
    }

    /**
     * Définit la distance maximale d’attaque.
     *
     * @param distAttMax nouvelle distance d’attaque maximale
     */
    public void setDistAttMax(int distAttMax) {
        this.distAttMax = distAttMax;
    }

    /**
     * Retourne une représentation textuelle complète du personnage.
     *
     * @return chaîne de caractères décrivant le personnage
     */
    @Override
    public String toString() {
        return "Personnage{" +
                "nom='" + nom + '\'' +
                ", distAttMax=" + distAttMax +
                ", " + super.toString() +
                '}';
    }

    /**
     * Affiche les informations du personnage sur la console.
     */
    public void affiche() {
        System.out.println(this);
    }

    /**
     * Déplace le personnage dans le monde.
     * <p>
     * Le déplacement est géré par {@link Creature#deplace(World)}.
     * Après le déplacement, le personnage cherche les objets
     * présents à sa nouvelle position dans le {@link World}.
     * </p>
     *
     * @param monde monde dans lequel le personnage se déplace
     */
    @Override
    public void deplace(World monde) {
        super.deplace(monde);
        monde.chercherObjet(this);
    }
    
    private static class Effet {
    String nomEffet;
    String caract;  // nom de la caractéristique modifiée
    int valeur;     // valeur modifiée (+/-)
    int duree;      // durée restante

    Effet(String nomEffet, String caract, int valeur, int duree) {
        this.nomEffet = nomEffet;
        this.caract = caract;
        this.valeur = valeur;
        this.duree = duree;
    }
}
    public void setEffetActif(String nomEffet, int duree, int valeur) {
    effetsActifs.put(nomEffet, duree);
}

    public void miseAJourEffets() {
        List<String> aSupprimer = new ArrayList<>();
        for (Map.Entry<String, Integer> effet : effetsActifs.entrySet()) {
            int reste = effet.getValue() - 1;
            if (reste <= 0) {
                // Rétablir la caractéristique initiale ici
                System.out.println("Effet " + effet.getKey() + " terminé !");
                aSupprimer.add(effet.getKey());
            } else {
                effetsActifs.put(effet.getKey(), reste);
            }
        }
        for (String e : aSupprimer) {
            effetsActifs.remove(e);
        }
    }
}
