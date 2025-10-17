/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.centrale.objet.WoE;

import java.util.*;

/**
 * Classe de test des performances entre les structures de données {@link ArrayList}
 * et {@link LinkedList} en Java.
 *
 * <p>Cette classe mesure le temps d’accès aux éléments des deux implémentations
 * selon deux modes :</p>
 * <ul>
 *   <li>Accès direct par indice avec {@code get(i)}</li>
 *   <li>Itération séquentielle via un {@link Iterator}</li>
 * </ul>
 *
 * <p>Les temps d’exécution sont affichés dans la console, puis représentés graphiquement
 * à l’aide de la classe {@link Graphiques}.</p>
 *
 * <p><strong>Remarque :</strong> {@code LinkedList.get(i)} est une opération coûteuse
 * (complexité O(n)), contrairement à {@code ArrayList.get(i)} qui est O(1).</p>
 *
 * @author Imane
 * @see ArrayList
 * @see LinkedList
 * @see Personnage
 * @see Graphiques
 */
public class TestPerformance {

    // ===================== MÉTHODE PRINCIPALE DE TEST =====================

    /**
     * Exécute une série de tests comparant les performances d’accès aux éléments
     * entre {@link ArrayList} et {@link LinkedList}.
     *
     * <p>Les opérations effectuées sont les suivantes :</p>
     * <ol>
     *   <li>Création de listes de tailles croissantes (100, 1 000, 10 000, 100 000).</li>
     *   <li>Remplissage des listes avec des instances de {@link Archer}.</li>
     *   <li>Mesure du temps d’accès :
     *     <ul>
     *       <li>Accès par indice sur {@code ArrayList}</li>
     *       <li>Accès via {@code Iterator} sur {@code ArrayList}</li>
     *       <li>Accès par indice sur {@code LinkedList}</li>
     *       <li>Accès via {@code Iterator} sur {@code LinkedList}</li>
     *     </ul>
     *   </li>
     *   <li>Affichage des résultats détaillés en console.</li>
     *   <li>Visualisation graphique via {@link Graphiques#afficherGraphique(int[], List, List, List, List)}.</li>
     * </ol>
     *
     * <p>Ce test met en évidence la différence de performance entre les deux types
     * de listes et les deux modes d’itération.</p>
     */
    public static void tester() {

        // ===================== PARAMÈTRES DU TEST =====================
        int[] tailles = {100, 1000, 10000, 100000};
        Random rand = new Random();

        // Stockage des temps mesurés
        List<Long> tempsArrayListIndice = new ArrayList<>();
        List<Long> tempsArrayListIterator = new ArrayList<>();
        List<Long> tempsLinkedListIndice = new ArrayList<>();
        List<Long> tempsLinkedListIterator = new ArrayList<>();

        // ===================== BOUCLE PRINCIPALE =====================
        for (int taille : tailles) {
            ArrayList<Personnage> arrayList = new ArrayList<>();
            LinkedList<Personnage> linkedList = new LinkedList<>();

            // Remplissage des listes avec des archers aléatoires
            for (int i = 0; i < taille; i++) {
                Archer a = new Archer();
                a.setNom("Perso" + i);
                a.setPtVie(50 + rand.nextInt(51)); // PV entre 50 et 100
                arrayList.add(a);
                linkedList.add(a);
            }

            System.out.println("=== Taille : " + taille + " ===");

            // --- ArrayList : accès par indice ---
            long start = System.nanoTime();
            for (int i = 0; i < arrayList.size(); i++) {
                arrayList.get(i).getPtVie();
            }
            long end = System.nanoTime();
            long t1 = end - start;
            tempsArrayListIndice.add(t1);
            System.out.println("ArrayList par indice : " + t1 + " ns");

            // --- ArrayList : accès via Iterator ---
            start = System.nanoTime();
            Iterator<Personnage> itArray = arrayList.iterator();
            while (itArray.hasNext()) {
                itArray.next().getPtVie();
            }
            end = System.nanoTime();
            long t2 = end - start;
            tempsArrayListIterator.add(t2);
            System.out.println("ArrayList avec Iterator : " + t2 + " ns");

            // --- LinkedList : accès par indice ---
            start = System.nanoTime();
            for (int i = 0; i < linkedList.size(); i++) {
                linkedList.get(i).getPtVie();
            }
            end = System.nanoTime();
            long t3 = end - start;
            tempsLinkedListIndice.add(t3);
            System.out.println("LinkedList par indice : " + t3 + " ns");

            // --- LinkedList : accès via Iterator ---
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

        // ===================== AFFICHAGE DES RÉSULTATS =====================
        System.out.println("\n=== Résultats récapitulatifs ===");
        System.out.println("Taille\tArrayList(Indice)\tArrayList(Iterator)\tLinkedList(Indice)\tLinkedList(Iterator)");
        for (int i = 0; i < tailles.length; i++) {
            System.out.println(tailles[i] + "\t" +
                    tempsArrayListIndice.get(i) + "\t\t" +
                    tempsArrayListIterator.get(i) + "\t\t" +
                    tempsLinkedListIndice.get(i) + "\t\t" +
                    tempsLinkedListIterator.get(i));
        }

        // ===================== VISUALISATION GRAPHIQUE =====================
        Graphiques.afficherGraphique(
                tailles,
                tempsArrayListIndice,
                tempsArrayListIterator,
                tempsLinkedListIndice,
                tempsLinkedListIterator
        );
    }
}
