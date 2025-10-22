package org.centrale.objet.WoE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

public class InterfaceJeu extends JFrame implements KeyListener {

    private final World monde;
    private final Joueur joueur;
    private final JPanel grillePanel;
    private final JTextArea statsJoueur;
    private final JTextArea statsCreature;

    public InterfaceJeu(World monde, Joueur joueur) {
        this.monde = monde;
        this.joueur = joueur;

        setTitle("World of ECN - Interface Graphique");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());

        // === Center: world grid ===
        grillePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawWorld(g);
            }
        };
        grillePanel.setPreferredSize(new Dimension(600, 600));
        add(grillePanel, BorderLayout.CENTER);

        // === Right: Stats ===
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        statsJoueur = new JTextArea();
        statsJoueur.setEditable(false);
        statsCreature = new JTextArea();
        statsCreature.setEditable(false);
        infoPanel.add(new JScrollPane(statsJoueur));
        infoPanel.add(new JScrollPane(statsCreature));
        add(infoPanel, BorderLayout.EAST);

        // === Bottom: Controls ===
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

        // === Keyboard listener ===
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        // === Button actions ===
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
                dispose(); // close current window
                TestWoE.main(null); // reopen the main menu
            }
        });


        refresh();
        setVisible(true);
    }

    /** === Draw the world grid === */
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

    /** === Refresh UI === */
    private void refresh() {
        Personnage perso = joueur.getPersoJoueur();

        // === Check if player is dead ===
        if (perso.getPtVie() <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "💀 Vous êtes mort !\nPartie terminée.",
                    "Game Over",
                    JOptionPane.ERROR_MESSAGE
            );
            dispose(); // close the current game window
            TestWoE.main(null); // return to main menu
            return; // stop refresh logic
        }

        // === Update player stats ===
        statsJoueur.setText("=== Joueur ===\n" +
                "Nom : " + perso.getNom() + "\n" +
                "PV : " + perso.getPtVie() + "\n" +
                "Attaque : " + perso.getDegAtt() + "\n" +
                "Parade : " + perso.getPtPar() + "\n" +
                "Pos : " + perso.getPos() + "\n");

        // === Update nearby creature stats ===
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

        // === Redraw ===
        grillePanel.repaint();
        requestFocusInWindow();
    }


    /** === Combat === */
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
                monde.supprimerMorts();  // 🧹 instantly remove dead creatures
                JOptionPane.showMessageDialog(this, "Combat terminé !");
                refresh();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur de combat : " + ex.getMessage());
            }
            refresh();
        }
    }

    /** === Inventory === */
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

    /** === Keyboard Movement (ZQSD / WASD) === */
    @Override
    public void keyPressed(KeyEvent e) {
        int dx = 0, dy = 0;
        Personnage perso = joueur.getPersoJoueur();
        int x = perso.getPos().getX();
        int y = perso.getPos().getY();

        switch (e.getKeyCode()) {
            // Z or ↑ → move up
            case KeyEvent.VK_Z, KeyEvent.VK_UP -> {
                if (y < monde.getLargeur() - 1) dy = 1;
            }
            // S or ↓ → move down
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                if (y > 0) dy = -1;
            }
            // Q or ← → move left
            case KeyEvent.VK_Q, KeyEvent.VK_LEFT -> {
                if (x > 0) dx = -1;
            }
            // D or → → move right
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> {
                if (x < monde.getLongueur() - 1) dx = 1;
            }
        }

        if (dx != 0 || dy != 0) {
            joueur.movePlayer(monde, dx, dy);
            refresh();
        }
    }


    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    private static java.util.List<File> getSaveFiles() {
        File dir = new File("."); // current directory
        File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".txt"));
        java.util.List<File> saves = new ArrayList<>();
        if (files != null) saves.addAll(java.util.Arrays.asList(files));
        return saves;
    }


    public static void mainInterface(String[] args) {

        //MainMenu.startGame();
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

            if (choix == 0) {
                World monde = new World();
                monde.setLongueur(20);
                monde.setLargeur(20);

                // Player creation
                Joueur joueur = new Joueur();
                String nom = JOptionPane.showInputDialog("Entrez votre nom :");
                joueur.setNom(nom);

                // Character selection
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

                // Generate world
                monde.creerMondeAlea(2, 2, 3, 1, 2, 2, 2, 1, 1);

                new InterfaceJeu(monde, joueur);
            }
            else if (choix == 1) {
                java.util.List<File> saves = getSaveFiles();

                if (saves.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Aucune sauvegarde trouvée !");
                    InterfaceJeu.mainInterface(null); // return to menu
                    return;
                }

                // Convert to list of file names
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

                // === Actions ===
                loadBtn.addActionListener(ev -> {
                    String selected = list.getSelectedValue();
                    if (selected != null) {
                        dialog.dispose();
                        try {
                            World monde = new World();
                            monde.ChargementPartie(selected);
                            Joueur joueur = monde.getJoueur();

                            // 🔧 Ensure player character is added to world
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
                                mainInterface(null); // refresh menu
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
                    mainInterface(null); // back to menu
                });

                dialog.setVisible(true);
            }
            else {
                System.exit(0);
            }
        });

    }

}
