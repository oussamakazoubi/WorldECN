/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.centrale.objet.WoE;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.util.List;

/**
 * Classe utilitaire permettant d’afficher graphiquement les résultats
 * des tests de performance réalisés dans {@link TestPerformance}.
 *
 * <p>Elle utilise la bibliothèque <b>JFreeChart</b> pour générer un graphique en courbes
 * comparant les temps d’accès (en nanosecondes) entre {@link java.util.ArrayList}
 * et {@link java.util.LinkedList}, selon deux modes d’accès :</p>
 *
 * <ul>
 *   <li>Accès par indice ({@code get(i)})</li>
 *   <li>Accès via un {@link java.util.Iterator}</li>
 * </ul>
 *
 * <p>Chaque série est représentée par une ligne distincte dans le graphique.
 * L’axe des X correspond à la taille des listes, tandis que l’axe des Y indique
 * le temps moyen d’accès mesuré.</p>
 *
 * <p>Le graphique est affiché dans une fenêtre Swing autonome.</p>
 *
 * @author Imane
 * @see TestPerformance
 * @see org.jfree.chart.JFreeChart
 * @see org.jfree.data.category.DefaultCategoryDataset
 */
public class Graphiques {

    // ===================== MÉTHODE PRINCIPALE =====================

    /**
     * Affiche un graphique comparatif des temps d’accès entre {@link java.util.ArrayList}
     * et {@link java.util.LinkedList}.
     *
     * <p>Cette méthode crée un diagramme en lignes représentant quatre séries de données :</p>
     * <ul>
     *   <li>ArrayList – Accès par indice</li>
     *   <li>ArrayList – Accès via itérateur</li>
     *   <li>LinkedList – Accès par indice</li>
     *   <li>LinkedList – Accès via itérateur</li>
     * </ul>
     *
     * <p>Les résultats sont affichés dans une fenêtre Swing grâce à un {@link ChartPanel}.</p>
     *
     * @param tailles tableau des tailles de listes testées (ex. : 100, 1 000, 10 000, 100 000)
     * @param arrayListIndice temps (en ns) pour les accès par indice dans {@code ArrayList}
     * @param arrayListIterator temps (en ns) pour les accès via itérateur dans {@code ArrayList}
     * @param linkedListIndice temps (en ns) pour les accès par indice dans {@code LinkedList}
     * @param linkedListIterator temps (en ns) pour les accès via itérateur dans {@code LinkedList}
     */
    public static void afficherGraphique(int[] tailles,
                                         List<Long> arrayListIndice,
                                         List<Long> arrayListIterator,
                                         List<Long> linkedListIndice,
                                         List<Long> linkedListIterator) {

        // ===================== CRÉATION DU DATASET =====================
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Ajout des valeurs pour chaque méthode et chaque taille testée
        for (int i = 0; i < tailles.length; i++) {
            dataset.addValue(arrayListIndice.get(i), "ArrayList - Indice", "" + tailles[i]);
            dataset.addValue(arrayListIterator.get(i), "ArrayList - Iterator", "" + tailles[i]);
            dataset.addValue(linkedListIndice.get(i), "LinkedList - Indice", "" + tailles[i]);
            dataset.addValue(linkedListIterator.get(i), "LinkedList - Iterator", "" + tailles[i]);
        }

        // ===================== CRÉATION DU GRAPHIQUE =====================
        JFreeChart chart = ChartFactory.createLineChart(
                "Performance des Structures de Liste", // Titre du graphique
                "Taille de la liste",                  // Axe des X
                "Temps d’accès (nanosecondes)",        // Axe des Y
                dataset                                // Données
        );

        // ===================== AFFICHAGE DU GRAPHIQUE =====================
        ChartPanel panel = new ChartPanel(chart);

        JFrame frame = new JFrame("Comparaison des performances des listes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
