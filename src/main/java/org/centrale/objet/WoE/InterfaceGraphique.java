/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author user
 */


public class InterfaceGraphique extends JFrame {

    private World monde;
    private JTextArea zoneTexte;

    public InterfaceGraphique(World monde) {
        this.monde = monde;

        setTitle("World of ECN");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Zone d'affichage du monde
        zoneTexte = new JTextArea();
        zoneTexte.setFont(new Font("Monospaced", Font.PLAIN, 14));
        zoneTexte.setEditable(false);
        add(new JScrollPane(zoneTexte), BorderLayout.CENTER);

        // Boutons
        JPanel panelBoutons = new JPanel();
        JButton btnTour = new JButton("Tour de jeu");
        JButton btnSauvegarder = new JButton("Sauvegarder");
        JButton btnCharger = new JButton("Charger");

        panelBoutons.add(btnTour);
        panelBoutons.add(btnSauvegarder);
        panelBoutons.add(btnCharger);
        add(panelBoutons, BorderLayout.SOUTH);

        // Actions des boutons
        btnTour.addActionListener(e -> {
            monde.tourDeJeu();
            afficherMonde();
        });

        btnSauvegarder.addActionListener(e -> {
            monde.SauvegardePartie("sauvegarde.txt");
            JOptionPane.showMessageDialog(this, "Partie sauvegardée !");
        });

        btnCharger.addActionListener(e -> {
            monde.ChargementPartie("sauvegarde.txt");
            afficherMonde();
            JOptionPane.showMessageDialog(this, "Partie chargée !");
        });

        afficherMonde();
        setVisible(true);
    }

    /** Affiche le monde dans la zone de texte */
    private void afficherMonde() {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < monde.getLargeur(); y++) {
            for (int x = 0; x < monde.getLongueur(); x++) {
                String symbole = ".";
                // Personnages
                for (Personnage p : monde.getMaListePers()) {
                    if ((int) p.getPos().getX() == x && (int) p.getPos().getY() == y)
                        symbole = "P";
                }
                // Monstres
                for (Monstre m : monde.getMaListeMons()) {
                    if ((int) m.getPos().getX() == x && (int) m.getPos().getY() == y)
                        symbole = "M";
                }
                // Objets
                for (Objet o : monde.getMaListeobj()) {
                    if ((int) o.getPosition().getX() == x && (int) o.getPosition().getY() == y)
                        symbole = "O";
                }
                sb.append(symbole).append(" ");
            }
            sb.append("\n");
        }

        zoneTexte.setText(sb.toString());
    }
}
