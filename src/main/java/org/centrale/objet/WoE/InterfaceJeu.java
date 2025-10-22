/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.centrale.objet.WoE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Classe {@code InterfaceJeu} représentant l’interface graphique principale du jeu <b>World of ECN</b>.
 *
 * <p>Elle affiche la grille du monde, les informations sur le joueur et les créatures voisines,
 * et permet d’interagir avec le jeu via des boutons et des touches du clavier.</p>
 *
 * <h2>Fonctionnalités principales :</h2>
 * <ul>
 *   <li>Affichage graphique du monde (grille avec personnages, monstres et objets).</li>
 *   <li>Déplacement du joueur via le clavier (touches ZQSD / flèches directionnelles).</li>
 *   <li>Boutons d’action : tour de jeu, combat, inventaire, sauvegarde et quitter.</li>
 *   <li>Chargement et suppression de sauvegardes existantes.</li>
 *   <li>Création de nouvelles parties avec génération aléatoire du monde.</li>
 * </ul>
 *
 * <p>Les éléments du monde sont redessinés en temps réel après chaque action ou déplacement.
 * Les statistiques du joueur et des créatures proches sont également mises à jour.</p>
 *
 * @author Oussama
 * @see World
 * @see Joueur
 * @see Personnage
 * @see Creature
 */
public class InterfaceJeu extends JFrame implements KeyListener {

    private final World monde;
    private final Joueur joueur;
    private final JPanel grillePanel;
    private final JTextArea statsJoueur;
    private final JTextArea statsCreature;

    /**
     * Constructeur principal de l’interface graphique.
     * <p>Initialise la fenêtre, la grille, les panneaux d’informations et les boutons d’action.</p>
     *
     * @param monde  le monde dans lequel le jeu se déroule
     * @param joueur le joueur associé à la partie en cours
     */
    public InterfaceJeu(World monde, Joueur joueur) {
        this.monde = monde;
        this.joueur = joueur;

        setTitle("World of ECN - Interface Graphique");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());

        // === Panneau central : Grille du monde ===
        grillePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawWorld(g);
            }
        };
        grillePanel.setPreferredSize(new Dimension(600, 600));
        add(grillePanel, BorderLayout.CENTER);

        // === Panneau droit : Informations joueur et créature ===
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        statsJoueur = new JTextArea();
        statsJoueur.setEditable(false);
        statsCreature = new JTextArea();
        statsCreature.setEditable(false);
        infoPanel.add(new JScrollPane(statsJoueur));
        infoPanel.add(new JScrollPane(statsCreature));
        add(infoPanel, BorderLayout.EAST);

        // === Panneau bas : Contrôles ===
        JPanel controlPanel = new JPanel();
        JButton tourBtn = new JButton("Tour de jeu");
        JButton combattreBtn = new JButton("Combattre");
        JButton inventaireBtn = new JButton("Inventaire");
        JButton sauvegarderBtn = new JButton("Sauvegarder");
        JButton quitterBtn = new JButton("Quitter");

        controlPanel.add(tourBtn);
        controlPanel.add(combattreBtn);
        controlPanel.add(inventaireBtn);
        controlPanel.add(sauvegarderBtn);
        controlPanel.add(quitterBtn);
        add(controlPanel, BorderLayout.SOUTH);

        // === Gestion clavier ===
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        // === Actions des boutons ===
        tourBtn.addActionListener(e -> {
            monde.tourDeJeu();
            refresh();
        });

        combattreBtn.addActionListener(e -> combattreCible());
        inventaireBtn.addActionListener(e -> ouvrirInventaire());

        sauvegarderBtn.addActionListener(e -> {
            String fichier = JOptionPane.showInputDialog(this, "Nom du fichier de sauvegarde :", "sauvegarde.txt");
            if (fichier != null && !fichier.isBlank()) {
                monde.SauvegardePartie(fichier);
                JOptionPane.showMessageDialog(this, "Partie sauvegardée dans " + fichier);
            }
        });

        quitterBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Voulez-vous vraiment quitter la partie ?",
                    "Quitter",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                TestWoE.main(null);
            }
        });

        refresh();
        setVisible(true);
    }

    /**
     * Dessine la grille du monde avec ses éléments : joueur, monstres et objets.
     *
     * @param g contexte graphique utilisé pour le dessin
     */
    private void drawWorld(Graphics g) {
        int tailleCase = 25;
        int longueur = monde.getLongueur();
        int largeur = monde.getLargeur();

        for (int y = 0; y < largeur; y++) {
            for (int x = 0; x < longueur; x++) {
                Point2D pos = new Point2D(x, y);
                Color color = Color.LIGHT_GRAY;

                Creature c = monde.getCreatureAt(pos);
                if (c != null) {
                    if (c == joueur.getPersoJoueur()) color = Color.BLUE;
                    else if (c instanceof Monstre) color = Color.RED;
                    else if (c instanceof Personnage) color = Color.ORANGE;
                } else {
                    for (Objet o : monde.getMaListeobj()) {
                        if (o.getPosition().equals(pos)) {
                            color = Color.GREEN;
                            break;
                        }
                    }
                }

                g.setColor(color);
                g.fillRect(x * tailleCase, (largeur - 1 - y) * tailleCase, tailleCase, tailleCase);
                g.setColor(Color.BLACK);
                g.drawRect(x * tailleCase, (largeur - 1 - y) * tailleCase, tailleCase, tailleCase);
            }
        }
    }

    /**
     * Rafraîchit l’affichage et met à jour les informations du joueur et des créatures proches.
     * <p>Si le joueur meurt, la partie est terminée et le menu principal est rouvert.</p>
     */
    private void refresh() {
        Personnage perso = joueur.getPersoJoueur();

        if (perso.getPtVie() <= 0) {
            JOptionPane.showMessageDialog(this, "💀 Vous êtes mort !\nPartie terminée.", "Game Over", JOptionPane.ERROR_MESSAGE);
            dispose();
            TestWoE.main(null);
            return;
        }

        statsJoueur.setText("=== Joueur ===\n" +
                "Nom : " + perso.getNom() + "\n" +
                "PV : " + perso.getPtVie() + "\n" +
                "Attaque : " + perso.getDegAtt() + "\n" +
                "Parade : " + perso.getPtPar() + "\n" +
                "Pos : " + perso.getPos() + "\n");

        ArrayList<Creature> cibles = monde.ChercherCibles(perso);
        if (!cibles.isEmpty()) {
            Creature cible = cibles.get(0);
            statsCreature.setText("=== Cible proche ===\n" +
                    cible.getClass().getSimpleName() + "\n" +
                    "PV : " + cible.getPtVie() + "\n" +
                    "Pos : " + cible.getPos());
        } else {
            statsCreature.setText("Aucune créature à portée");
        }

        grillePanel.repaint();
        requestFocusInWindow();
    }

    /**
     * Ouvre une boîte de dialogue permettant de choisir et d’attaquer une cible à portée.
     */
    private void combattreCible() {
        Personnage perso = joueur.getPersoJoueur();
        ArrayList<Creature> cibles = monde.ChercherCibles(perso);

        if (cibles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucune cible à portée !");
            return;
        }

        String[] noms = new String[cibles.size()];
        for (int i = 0; i < cibles.size(); i++) {
            Creature c = cibles.get(i);
            String nom = (c instanceof Personnage p) ? p.getNom() : c.getClass().getSimpleName();
            noms[i] = (i + 1) + " - " + nom + " (" + c.getPos() + ")";
        }

        String choix = (String) JOptionPane.showInputDialog(
                this,
                "Choisissez la cible à attaquer :",
                "Combat",
                JOptionPane.PLAIN_MESSAGE,
                null,
                noms,
                noms[0]
        );

        if (choix != null) {
            int idx = Integer.parseInt(choix.split(" ")[0]) - 1;
            Creature cible = cibles.get(idx);
            try {
                perso.getClass().getMethod("combattre", Creature.class).invoke(perso, cible);
                monde.supprimerMorts();
                JOptionPane.showMessageDialog(this, "Combat terminé !");
                refresh();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur de combat : " + ex.getMessage());
            }
            refresh();
        }
    }

    /**
     * Ouvre l’inventaire du joueur et permet d’utiliser un objet.
     */
    private void ouvrirInventaire() {
        ArrayList<Objet> inv = joueur.getInventaire();
        if (inv.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Inventaire vide !");
            return;
        }

        String[] noms = new String[inv.size()];
        for (int i = 0; i < inv.size(); i++) {
            noms[i] = (i + 1) + " - " + inv.get(i).getNom();
        }

        String choix = (String) JOptionPane.showInputDialog(
                this,
                "Sélectionnez un objet à utiliser :",
                "Inventaire",
                JOptionPane.PLAIN_MESSAGE,
                null,
                noms,
                noms[0]
        );

        if (choix != null) {
            int idx = Integer.parseInt(choix.split(" ")[0]) - 1;
            Objet o = inv.get(idx);
            o.utiliserObjet(joueur.getPersoJoueur());
            inv.remove(o);
            JOptionPane.showMessageDialog(this, o.getNom() + " utilisé !");
            refresh();
        }
    }

    /** Gère les déplacements du joueur via le clavier (touches ZQSD ou flèches). */
    @Override
    public void keyPressed(KeyEvent e) {
        int dx = 0, dy = 0;
        Personnage perso = joueur.getPersoJoueur();
        int x = perso.getPos().getX();
        int y = perso.getPos().getY();

        switch (e.getKeyCode()) {
            case KeyEvent.VK_Z, KeyEvent.VK_UP -> { if (y < monde.getLargeur() - 1) dy = 1; }
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> { if (y > 0) dy = -1; }
            case KeyEvent.VK_Q, KeyEvent.VK_LEFT -> { if (x > 0) dx = -1; }
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> { if (x < monde.getLongueur() - 1) dx = 1; }
        }

        if (dx != 0 || dy != 0) {
            joueur.movePlayer(monde, dx, dy);
            refresh();
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    /**
     * Retourne la liste des fichiers de sauvegarde disponibles dans le répertoire courant.
     *
     * @return liste des fichiers de sauvegarde (.txt)
     */
    private static java.util.List<File> getSaveFiles() {
        File dir = new File(".");
        File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".txt"));
        java.util.List<File> saves = new ArrayList<>();
        if (files != null) saves.addAll(java.util.Arrays.asList(files));
        return saves;
    }

    /**
     * Point d’entrée principal de l’interface graphique du jeu.
     * <p>Affiche le menu principal permettant de démarrer une nouvelle partie,
     * de charger une sauvegarde ou de quitter le jeu.</p>
     *
     * @param args arguments de la ligne de commande (non utilisés)
     */
    public static void mainInterface(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String[] options = {"Nouvelle Partie", "Charger Partie", "Quitter"};
            int choix = JOptionPane.showOptionDialog(null,
                    "=== WORLD OF ECN ===",
                    "Menu principal",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]);

            // === Création d'une nouvelle partie ===
            if (choix == 0) {
                World monde = new World();
                monde.setLongueur(20);
                monde.setLargeur(20);

                Joueur joueur = new Joueur();
                String nom = JOptionPane.showInputDialog("Entrez votre nom :");
                joueur.setNom(nom);

                String[] classes = {"Guerrier", "Archer"};
                String persoType = (String) JOptionPane.showInputDialog(null,
                        "Choisissez votre personnage :",
                        "Sélection du personnage",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        classes,
                        classes[0]);

                Personnage perso = switch (persoType) {
                    case "Archer" -> new Archer();
                    default -> new Guerrier();
                };
                monde.definirStatsAlea(perso);
                perso.setNom(nom);
                perso.setPos(new Point2D(10, 10));
                joueur.setPersoJoueur(perso);
                monde.setJoueur(joueur);
                monde.getMaListePers().add(perso);
                monde.creerMondeAlea(2, 2, 3, 1, 2, 2, 2, 1, 1);
                new InterfaceJeu(monde, joueur);
            }

            // === Chargement d'une partie existante ===
            else if (choix == 1) {
                java.util.List<File> saves = getSaveFiles();

                if (saves.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Aucune sauvegarde trouvée !");
                    InterfaceJeu.mainInterface(null);
                    return;
                }

                // Affichage de la liste des sauvegardes
                String[] nomsSaves = saves.stream().map(File::getName).toArray(String[]::new);
                JPanel panel = new JPanel(new BorderLayout());
                JList<String> list = new JList<>(nomsSaves);
                list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                JScrollPane scroll = new JScrollPane(list);

                JButton loadBtn = new JButton("Charger");
                JButton deleteBtn = new JButton("Supprimer");
                JButton cancelBtn = new JButton("Annuler");

                JPanel btnPanel = new JPanel();
                btnPanel.add(loadBtn);
                btnPanel.add(deleteBtn);
                btnPanel.add(cancelBtn);

                panel.add(new JLabel("Sélectionnez une sauvegarde :"), BorderLayout.NORTH);
                panel.add(scroll, BorderLayout.CENTER);
                panel.add(btnPanel, BorderLayout.SOUTH);

                JDialog dialog = new JDialog((Frame) null, "Gestion des sauvegardes", true);
                dialog.setContentPane(panel);
                dialog.setSize(400, 300);
                dialog.setLocationRelativeTo(null);

                // === Boutons de gestion des sauvegardes ===
                loadBtn.addActionListener(ev -> {
                    String selected = list.getSelectedValue();
                    if (selected != null) {
                        dialog.dispose();
                        try {
                            World monde = new World();
                            monde.ChargementPartie(selected);
                            Joueur joueur = monde.getJoueur();

                            if (joueur != null && joueur.getPersoJoueur() != null &&
                                    !monde.getMaListePers().contains(joueur.getPersoJoueur())) {
                                monde.getMaListePers().add(joueur.getPersoJoueur());
                            }

                            if (joueur != null) {
                                new InterfaceJeu(monde, joueur);
                            } else {
                                JOptionPane.showMessageDialog(null, "Aucun joueur trouvé dans cette sauvegarde !");
                                InterfaceJeu.mainInterface(null);
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Erreur lors du chargement : " + ex.getMessage());
                            mainInterface(null);
                        }
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Veuillez sélectionner une sauvegarde !");
                    }
                });

                deleteBtn.addActionListener(ev -> {
                    String selected = list.getSelectedValue();
                    if (selected != null) {
                        int confirm = JOptionPane.showConfirmDialog(
                                dialog,
                                "Supprimer la sauvegarde " + selected + " ?",
                                "Confirmation",
                                JOptionPane.YES_NO_OPTION
                        );
                        if (confirm == JOptionPane.YES_OPTION) {
                            File f = new File(selected);
                            if (f.delete()) {
                                JOptionPane.showMessageDialog(dialog, "Sauvegarde supprimée !");
                                dialog.dispose();
                                mainInterface(null);
                            } else {
                                JOptionPane.showMessageDialog(dialog, "Échec de la suppression !");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Aucune sauvegarde sélectionnée !");
                    }
                });

                cancelBtn.addActionListener(ev -> {
                    dialog.dispose();
                    mainInterface(null);
                });

                dialog.setVisible(true);
            }

            // === Quitter le jeu ===
            else {
                System.exit(0);
            }
        });
    }
}
