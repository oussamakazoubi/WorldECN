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
 * <p>Elle utilise la bibliothèque <b>JFreeChart</b> pour générer un graphique
 * en courbes représentant le temps d’accès (en nanosecondes) pour différentes
 * tailles de listes et différents modes d’accès :</p>
 *
 * <ul>
 *   <li>Accès par indice sur {@link java.util.ArrayList}</li>
 *   <li>Accès via un itérateur sur {@link java.util.ArrayList}</li>
 *   <li>Accès par indice sur {@link java.util.LinkedList}</li>
 *   <li>Accès via un itérateur sur {@link java.util.LinkedList}</li>
 * </ul>
 *
 * <p>Chaque série de données est représentée par une ligne distincte dans le graphique.
 * L’axe des X correspond à la taille des listes, et l’axe des Y indique le temps d’exécution moyen
 * mesuré en nanosecondes.</p>
 *
 * <p>Le graphique est affiché dans une fenêtre Swing autonome.</p>
 *
 * @see TestPerformance
 * @see org.jfree.chart.JFreeChart
 * @see org.jfree.data.category.DefaultCategoryDataset
 * @author Imane
 */
public class Graphiques {

    /**
     * Affiche un graphique comparatif des temps d’accès entre
     * {@link java.util.ArrayList} et {@link java.util.LinkedList}.
     *
     * <p>Cette méthode crée un diagramme en lignes à partir des données fournies,
     * où chaque ligne représente une méthode d’accès spécifique
     * (indice ou itérateur) sur une structure donnée.</p>
     *
     * @param tailles tableau des tailles de listes testées (ex : 100, 1000, 10000, 100000)
     * @param arrayListIndice liste des temps (en nanosecondes) pour les accès par indice dans {@code ArrayList}
     * @param arrayListIterator liste des temps (en nanosecondes) pour les accès via itérateur dans {@code ArrayList}
     * @param linkedListIndice liste des temps (en nanosecondes) pour les accès par indice dans {@code LinkedList}
     * @param linkedListIterator liste des temps (en nanosecondes) pour les accès via itérateur dans {@code LinkedList}
     */
    public static void afficherGraphique(int[] tailles,
                                         List<Long> arrayListIndice,
                                         List<Long> arrayListIterator,
                                         List<Long> linkedListIndice,
                                         List<Long> linkedListIterator) {

        // Création du jeu de données
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Ajout des valeurs au dataset pour chaque méthode et chaque taille
        for (int i = 0; i < tailles.length; i++) {
            dataset.addValue(arrayListIndice.get(i), "ArrayList - Indice", "" + tailles[i]);
            dataset.addValue(arrayListIterator.get(i), "ArrayList - Iterator", "" + tailles[i]);
            dataset.addValue(linkedListIndice.get(i), "LinkedList - Indice", "" + tailles[i]);
            dataset.addValue(linkedListIterator.get(i), "LinkedList - Iterator", "" + tailles[i]);
        }

        // Création du graphique en lignes
        JFreeChart chart = ChartFactory.createLineChart(
                "Performance des Structures de Liste", // titre
                "Taille de la liste",                  // axe des X
                "Temps d’accès (nanosecondes)",        // axe des Y
                dataset                                // données
        );

        // Création du panneau contenant le graphique
        ChartPanel panel = new ChartPanel(chart);

        // Création et affichage d’une fenêtre Swing contenant le graphique
        JFrame frame = new JFrame("Comparaison des performances des listes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}