/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.centrale.objet.WoE;

import java.util.*;

/**
 * Classe de test des performances des implémentations de listes {@link ArrayList}
 * et {@link LinkedList} en Java.
 *
 * <p>Cette classe mesure le temps d'accès aux éléments des deux structures
 * en utilisant deux modes d'itération :</p>
 *
 * <ul>
 *   <li>Par indice (méthode {@code get(i)})</li>
 *   <li>Via un itérateur ({@link Iterator})</li>
 * </ul>
 *
 * <p>Les résultats sont ensuite affichés en console et représentés graphiquement
 * à l’aide de la classe {@link Graphiques}.</p>
 *
 * <p><strong>Remarque :</strong> Les tests démontrent que
 * {@code LinkedList.get(i)} est extrêmement lent car cette méthode
 * est de complexité O(n), contrairement à {@code ArrayList.get(i)} qui est O(1).</p>
 *
 * @author Imane
 */
public class TestPerformance {

    /**
     * Méthode principale du test de performance.
     *
     * <p>Elle effectue les opérations suivantes :</p>
     * <ol>
     *     <li>Crée des listes de tailles croissantes (100, 1 000, 10 000, 100 000).</li>
     *     <li>Remplit les listes avec des objets {@link Personnage} (ici des {@link Archer}).</li>
     *     <li>Mesure le temps d’accès aux éléments :
     *         <ul>
     *             <li>Accès par indice dans {@link ArrayList}</li>
     *             <li>Accès via un itérateur dans {@link ArrayList}</li>
     *             <li>Accès par indice dans {@link LinkedList}</li>
     *             <li>Accès via un itérateur dans {@link LinkedList}</li>
     *         </ul>
     *     </li>
     *     <li>Affiche les résultats bruts dans la console.</li>
     *     <li>Affiche un graphique récapitulatif via {@link Graphiques#afficherGraphique(int[], List, List, List, List)}.</li>
     * </ol>
     *
     * <p>Ce test met en évidence les différences de performance entre les deux types de listes
     * et entre les deux modes d’itération.</p>
     */
    public static void tester() {

        // Tailles des listes à tester
        int[] tailles = {100, 1000, 10000, 100000};
        Random rand = new Random();

        // Listes pour stocker les temps mesurés
        List<Long> tempsArrayListIndice = new ArrayList<>();
        List<Long> tempsArrayListIterator = new ArrayList<>();
        List<Long> tempsLinkedListIndice = new ArrayList<>();
        List<Long> tempsLinkedListIterator = new ArrayList<>();

        // Boucle principale de test
        for (int taille : tailles) {
            ArrayList<Personnage> arrayList = new ArrayList<>();
            LinkedList<Personnage> linkedList = new LinkedList<>();

            // Remplissage des listes avec des Archers
            for (int i = 0; i < taille; i++) {
                Archer a = new Archer();
                a.setNom("Perso" + i);
                a.setPtVie(50 + rand.nextInt(51));
                arrayList.add(a);
                linkedList.add(a);
            }

            System.out.println("=== Taille : " + taille + " ===");

            // Test ArrayList par indice
            long start = System.nanoTime();
            for (int i = 0; i < arrayList.size(); i++) {
                arrayList.get(i).getPtVie();
            }
            long end = System.nanoTime();
            long t1 = end - start;
            tempsArrayListIndice.add(t1);
            System.out.println("ArrayList par indice : " + t1 + " ns");

            // Test ArrayList via Iterator
            start = System.nanoTime();
            Iterator<Personnage> itArray = arrayList.iterator();
            while (itArray.hasNext()) {
                itArray.next().getPtVie();
            }
            end = System.nanoTime();
            long t2 = end - start;
            tempsArrayListIterator.add(t2);
            System.out.println("ArrayList avec Iterator : " + t2 + " ns");

            // Test LinkedList par indice
            start = System.nanoTime();
            for (int i = 0; i < linkedList.size(); i++) {
                linkedList.get(i).getPtVie();
            }
            end = System.nanoTime();
            long t3 = end - start;
            tempsLinkedListIndice.add(t3);
            System.out.println("LinkedList par indice : " + t3 + " ns");

            // Test LinkedList via Iterator
            start = System.nanoTime();
            Iterator<Personnage> itLinked = linkedList.iterator();
            while (itLinked.hasNext()) {
                itLinked.next().getPtVie();
            }
            end = System.nanoTime();
            long t4 = end - start;
            tempsLinkedListIterator.add(t4);
            System.out.println("LinkedList avec Iterator : " + t4 + " ns");
        }

        // Résumé des résultats dans la console
        System.out.println("=== Résultats ===");
        System.out.println("Taille\tArrayList(Indice)\tArrayList(Iterator)\tLinkedList(Indice)\tLinkedList(Iterator)");
        for (int i = 0; i < tailles.length; i++) {
            System.out.println(tailles[i] + "\t" + tempsArrayListIndice.get(i) + "\t\t" +
                    tempsArrayListIterator.get(i) + "\t\t" +
                    tempsLinkedListIndice.get(i) + "\t\t" +
                    tempsLinkedListIterator.get(i));
        }

        // Génération du graphique
        Graphiques.afficherGraphique(tailles, tempsArrayListIndice, tempsArrayListIterator,
                                     tempsLinkedListIndice, tempsLinkedListIterator);
    }
}
