/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.centrale.objet.WoE;

/**
 * Représente un {@code ChampignonPourri}, un type de {@link Nourriture} ayant un effet négatif.
 *
 * <p>Lorsqu’un personnage consomme un champignon pourri, il subit une pénalité temporaire
 * sur sa défense (réduction de ses points de parade). Cet effet dure un nombre de tours
 * déterminé par {@code dureeEffet}.</p>
 *
 * <p>Lorsque l’effet expire, la pénalité est automatiquement annulée.</p>
 *
 * @author Imane
 * @see Nourriture
 * @see Personnage
 */
public class ChampignonPourri extends Nourriture {

    /** Valeur du malus de défense appliqué lors de la consommation. */
    private int malusDefense;

    // ===================== CONSTRUCTEURS =====================

    /**
     * Constructeur principal.
     * <p>Crée un champignon pourri à une position donnée, appliquant un malus
     * de 3 points de défense pendant 2 tours.</p>
     *
     * @param position position du champignon dans le monde
     */
    public ChampignonPourri(Point2D position) {
        super("Champignon pourri", position, 2); // effet dure 2 tours
        this.malusDefense = 3;
    }

    /**
     * Constructeur complet avec paramètres personnalisés.
     *
     * @param nom nom de la nourriture
     * @param position position dans le monde
     * @param dureeEffet durée de l’effet en tours
     * @param malusDefense valeur du malus de défense
     */
    public ChampignonPourri(String nom, Point2D position, int dureeEffet, int malusDefense) {
        super(nom, position, dureeEffet);
        this.malusDefense = malusDefense;
    }

    /**
     * Constructeur utilisé lors du chargement depuis un fichier texte.
     *
     * <p>Exemple de ligne :</p>
     * <pre>
     * ChampignonPourri nom x y duree malusDefense
     * </pre>
     *
     * @param ligne ligne de texte contenant les informations de l’objet
     */
    public ChampignonPourri(String ligne) {
        String[] parts = ligne.split(" ");
        // parts[0] = "ChampignonPourri"
        this.setNom(parts[1]);
        int x = Integer.parseInt(parts[2]);
        int y = Integer.parseInt(parts[3]);
        this.setPosition(new Point2D(x, y));
        this.setDureeEffet(Integer.parseInt(parts[4]));
        this.malusDefense = Integer.parseInt(parts[5]);
        this.setEstActive(false); // effet inactif par défaut
    }

    // ===================== ACCESSEURS =====================

    /** @return le malus de défense appliqué par le champignon */
    public int getMalusDefense() {
        return malusDefense;
    }

    /** @param malusDefense nouveau malus de défense */
    public void setMalusDefense(int malusDefense) {
        this.malusDefense = malusDefense;
    }

    // ===================== UTILISATION =====================

    /**
     * Utilise le champignon pourri sur un personnage.
     * <p>Réduit temporairement les dégâts d’attaque du personnage du montant du malus,
     * puis active l’effet pour la durée indiquée.</p>
     *
     * @param p le personnage consommant le champignon
     */
    @Override
    public void utiliserObjet(Personnage p) {
        p.getUtilisables().add(this);
        if (!getEstActive()) {
            System.out.println(p.getNom() + " mange un champignon pourri !");
            p.setDegAtt(p.getDegAtt() - malusDefense);
            setEstActive(true);
        }
    }

    /**
     * Annule l’effet du champignon pourri une fois la durée expirée.
     * <p>Rend au personnage les points d’attaque perdus et désactive l’effet.</p>
     *
     * @param p le personnage sur lequel l’effet s’annule
     */
    public void annulerEffet(Personnage p) {
        System.out.println("L'effet du champignon pourri sur " + p.getNom() + " s’est dissipé.");
        p.setDegAtt(p.getDegAtt() + malusDefense);
        setEstActive(false);
    }

    // ===================== SAUVEGARDE =====================

    /**
     * Retourne la ligne de texte correspondant à la sauvegarde de cet objet.
     *
     * <p>Format de sortie :</p>
     * <pre>
     * ChampignonPourri nom x y duree malusDefense
     * </pre>
     *
     * @return chaîne prête à être enregistrée dans un fichier de sauvegarde
     */
    @Override
    public String getTexteSauvegarde() {
        Point2D pos = getPosition();
        return "ChampignonPourri " + getNom() + " " +
                pos.getX() + " " + pos.getY() + " " +
                getDureeEffet() + " " + malusDefense;
    }
}
