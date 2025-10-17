/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.centrale.objet.WoE;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Iterator;

/**
 * Représente un personnage jouable ou contrôlable dans le monde de jeu.
 *
 * <p>Un {@code Personnage} hérite de {@link Creature} et possède des caractéristiques
 * supplémentaires telles que :</p>
 * <ul>
 *   <li>Un nom unique identifiant le personnage</li>
 *   <li>Une distance maximale d’attaque ({@code distAttMax})</li>
 *   <li>Une liste de nourritures ou objets utilisables ayant des effets temporaires</li>
 * </ul>
 *
 * <p>Les sous-classes (comme {@code Guerrier}, {@code Archer}, {@code Paysan}, etc.)
 * héritent de cette structure de base.</p>
 *
 * @author Oussama
 * @see Creature
 */
public class Personnage extends Creature {

    /** Liste des nourritures actuellement actives ou utilisables par le personnage. */
    private ArrayList<Nourriture> Utilisables;

    /** Nom du personnage. */
    private String nom;

    /** Distance maximale d’attaque du personnage. */
    private int distAttMax;

    // ===================== CONSTRUCTEURS =====================

    /**
     * Constructeur par défaut.
     * <p>Initialise un personnage sans nom, avec des caractéristiques nulles
     * et une liste vide d’objets utilisables.</p>
     */
    public Personnage() {
        super();
        this.nom = "Non nommee";
        this.distAttMax = 0;
        Utilisables = new ArrayList<>();
    }

    /**
     * Constructeur complet avec paramètres.
     * <p>Permet d’initialiser toutes les caractéristiques du personnage.</p>
     *
     * @param n nom du personnage
     * @param pV points de vie
     * @param dA dégâts d’attaque
     * @param pPar points de parade
     * @param paAtt chance de réussite d’attaque
     * @param paPar chance de réussite de parade
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
     * Constructeur utilisé lors du chargement depuis un fichier texte.
     * <p>Les données du personnage sont lues à partir d’une ligne sauvegardée.</p>
     *
     * @param ligne ligne de texte contenant les informations du personnage
     */
    public Personnage(String ligne) {
        StringTokenizer st = new StringTokenizer(ligne, " ");
        st.nextToken(); // saute le mot-clé "Personnage" ou "Guerrier"/"Archer"/etc.
        this.nom = st.nextToken();
        this.distAttMax = Integer.parseInt(st.nextToken());
        chargerDepuisTokenizer(st); // méthode héritée de Creature
        Utilisables = new ArrayList<>();
    }

    // ===================== GETTERS & SETTERS =====================

    /** @return la liste des nourritures ou objets utilisables */
    public ArrayList<Nourriture> getUtilisables() {
        return Utilisables;
    }

    /** @param Utilisables nouvelle liste d’objets utilisables */
    public void setUtilisables(ArrayList<Nourriture> Utilisables) {
        this.Utilisables = Utilisables;
    }

    /** @return le nom du personnage */
    public String getNom() {
        return nom;
    }

    /** @param nom le nouveau nom du personnage */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /** @return la distance maximale d’attaque du personnage */
    public int getDistAttMax() {
        return distAttMax;
    }

    /** @param distAttMax nouvelle distance maximale d’attaque */
    public void setDistAttMax(int distAttMax) {
        this.distAttMax = distAttMax;
    }

    // ===================== AFFICHAGE =====================

    /**
     * Retourne une représentation textuelle complète du personnage.
     *
     * @return chaîne décrivant toutes les caractéristiques du personnage
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
     * Affiche les informations du personnage dans la console.
     */
    public void affiche() {
        System.out.println(this);
    }

    // ===================== DÉPLACEMENT =====================

    /**
     * Déplace le personnage dans le monde.
     * <p>Le déplacement est effectué via {@link Creature#deplace(World)}.
     * Après le déplacement, le personnage vérifie la présence d’un objet
     * sur la case actuelle via {@link World#chercherObjet(Creature)}.</p>
     *
     * @param monde le monde dans lequel le personnage se déplace
     */
    @Override
    public void deplace(World monde) {
        super.deplace(monde);
        monde.chercherObjet(this);
    }

    // ===================== GESTION DES EFFETS =====================

    /**
     * Met à jour les effets temporaires des nourritures consommées.
     * <p>Chaque nourriture active voit sa durée d’effet diminuer d’un tour.
     * Lorsque sa durée arrive à 0, son effet est annulé et elle est retirée
     * de la liste des objets actifs.</p>
     */
    public void mettreAJourEffets() {
        Iterator<Nourriture> it = Utilisables.iterator();
        while (it.hasNext()) {
            Nourriture n = it.next();
            if (n.getEstActive()) {
                n.decrementerEffet();
                if (n.getDureeEffet() == 0) {
                    System.out.println("L'effet de " + n.getNom() + " sur " + nom + " est terminé.");
                    n.annulerEffet(this);
                    it.remove();
                }
            }
        }
    }

    // ===================== SAUVEGARDE =====================

    /**
     * Retourne le texte de sauvegarde commun à tous les personnages.
     * <p>Format : {@code "ptVie degAtt ptPar pageAtt pagePar posX posY"}</p>
     *
     * @return chaîne de texte contenant les caractéristiques communes
     */
    public String getTexteSauvegardeCommun() {
        return getPtVie() + " " + getDegAtt() + " " + getPtPar() + " " +
                getPageAtt() + " " + getPagePar() + " " +
                getPos().getX() + " " + getPos().getY();
    }

    /**
     * Retourne le texte de sauvegarde complet pour un personnage générique.
     * <p>Les sous-classes peuvent surcharger cette méthode pour inclure leurs propres attributs.</p>
     *
     * @return chaîne de texte contenant toutes les informations du personnage
     */
    public String getTexteSauvegarde() {
        return "Personnage " + nom + " " + distAttMax + " " + getTexteSauvegardeCommun();
    }
}
