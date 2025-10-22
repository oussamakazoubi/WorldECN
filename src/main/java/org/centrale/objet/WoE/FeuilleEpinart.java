/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.centrale.objet.WoE;

/**
 * Représente une {@code FeuilleEpinart}, un type de {@link Nourriture} bénéfique
 * qui augmente temporairement les dégâts d’attaque d’un personnage.
 *
 * <p>Lorsqu’un personnage consomme une feuille d’épinard, il gagne un bonus de dégâts
 * pendant une durée déterminée. Lorsque la durée de l’effet expire, le bonus est retiré
 * automatiquement.</p>
 *
 * @author Imane
 * @see Nourriture
 * @see Personnage
 */
public class FeuilleEpinart extends Nourriture {

    /** Bonus de dégâts d’attaque conféré par la feuille d’épinard. */
    private final int bonusDegAtt;

    // ===================== CONSTRUCTEURS =====================

    /**
     * Constructeur complet avec paramètres.
     *
     * @param nom nom de la nourriture
     * @param position position de la nourriture sur la carte
     * @param dureeEffet durée de l’effet en tours
     * @param bonusDegAtt bonus d’attaque conféré par la feuille
     */
    public FeuilleEpinart(String nom, Point2D position, int dureeEffet, int bonusDegAtt) {
        super(nom, position, dureeEffet);
        this.bonusDegAtt = bonusDegAtt;
    }

    /**
     * Retourne le bonus de dégâts d’attaque apporté par la feuille.
     *
     * @return le bonus d’attaque
     */
    public int getBonusDegAtt() {
        return bonusDegAtt;
    }

    // ===================== UTILISATION =====================

    /**
     * Utilise la feuille d’épinard sur un personnage.
     * <p>Augmente les dégâts d’attaque du personnage du montant du bonus
     * et active l’effet pour une durée déterminée.</p>
     *
     * @param p le personnage consommant la feuille
     */
    @Override
    public void utiliserObjet(Personnage p) {
        if (!getEstActive()) {
            p.getUtilisables().add(this);
            System.out.println(p.getNom() + " mange une feuille d’épinard !");
            p.setDegAtt(p.getDegAtt() + bonusDegAtt);
            setEstActive(true);
        }
    }

    /**
     * Annule l’effet de la feuille d’épinard une fois la durée expirée.
     * <p>Rend au personnage les points d’attaque retirés et désactive l’effet.</p>
     *
     * @param p le personnage affecté
     */
    public void annulerEffet(Personnage p) {
        System.out.println("L'effet de la feuille d’épinard sur " + p.getNom() + " s’est dissipé.");
        p.setDegAtt(p.getDegAtt() - bonusDegAtt);
        setEstActive(false);
    }

    /**
     * Constructeur utilisé lors du chargement depuis un fichier texte.
     *
     * <p>Exemple de ligne :</p>
     * <pre>
     * FeuilleEpinart nom x y dureeEffet bonusDegAtt
     * </pre>
     *
     * @param ligne ligne contenant les informations de la feuille d’épinard
     */
    public FeuilleEpinart(String ligne) {
        super("FeuilleEpinart", new Point2D(0, 0), 3); // valeurs temporaires
        String[] tokens = ligne.split(" ");
        int n = tokens.length;

        int x = Integer.parseInt(tokens[n - 4]);
        int y = Integer.parseInt(tokens[n - 3]);
        int duree = Integer.parseInt(tokens[n - 2]);
        int bonus = Integer.parseInt(tokens[n - 1]);

        // Nom = tout ce qui reste au début de la ligne
        StringBuilder nomBuilder = new StringBuilder();
        for (int i = 1; i < n - 4; i++) {
            if (i > 1) nomBuilder.append(" ");
            nomBuilder.append(tokens[i]);
        }

        super.setNom(nomBuilder.toString());
        super.setPosition(new Point2D(x, y));
        super.setDureeEffet(duree);
        this.bonusDegAtt = bonus;
    }

    // ===================== SAUVEGARDE =====================

    /**
     * Retourne la ligne de texte correspondant à la sauvegarde de cet objet.
     *
     * <p>Format de sortie :</p>
     * <pre>
     * FeuilleEpinart nom x y dureeEffet bonusDegAtt
     * </pre>
     *
     * @return chaîne prête à être enregistrée dans un fichier de sauvegarde
     */
    public String getTexteSauvegarde() {
        return "FeuilleEpinart " + super.getNom() + " " +
                getPosition().getX() + " " + getPosition().getY() + " " +
                getDureeEffet() + " " + bonusDegAtt;
    }
}
