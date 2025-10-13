/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.centrale.objet.WoE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
    
    private ArrayList<Nourriture> Utilisables;
    
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
        this.nom = "Non nommee"; // Better this way Trust me
        this.distAttMax = 0;
        Utilisables= new ArrayList<>();
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

    public ArrayList<Nourriture> getUtilisables() {
        return Utilisables;
    }

    public void setUtilisables(ArrayList<Nourriture> Utilisables) {
        this.Utilisables = Utilisables;
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
    
    public void mettreAJourEffets() {
        Iterator<Nourriture> it = Utilisables.iterator();
        while (it.hasNext()) {
            Nourriture n = it.next();
            if (n.getEstActive()) {
                n.decrementerEffet();
                if (n.getDureeEffet() == 0) {
                    System.out.println("L'effet de " + n.getNom() + " sur " + nom + " est terminé.");
                    n.annulerEffet(this); // retire le bonus (ex: -2 degAtt)
                    it.remove(); // supprime la nourriture expirée
                }
            }
        }
    }
    
    }

