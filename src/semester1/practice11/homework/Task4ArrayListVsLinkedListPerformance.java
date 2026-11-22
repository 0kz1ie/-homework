package semester1.practice11.homework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Task4ArrayListVsLinkedListPerformance {

    public static void main(String[] args) {
        System.out.println("================ БЕНЧМАРК ТА РОБОТА ЗІ СПИСКАМИ ================");

        int elementsCount = 20_000;
        runInsertBeginningBenchmark(elementsCount);
        runInsertMiddleBenchmark(elementsCount);

        List<Integer> numbers = demonstrateSorting();
        demonstrateSubList(numbers);

        System.out.println("================================================================");
    }

    public static void runInsertBeginningBenchmark(int count) {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        long startArray = System.nanoTime();
        for (int i = 0; i < count; i++) {
            arrayList.add(0, i);
        }
        long endArray = System.nanoTime();
        double arrayTimeMs = (endArray - startArray) / 1_000_000.0;

        long startLinked = System.nanoTime();
        for (int i = 0; i < count; i++) {
            linkedList.add(0, i);
        }
        long endLinked = System.nanoTime();
        double linkedTimeMs = (endLinked - startLinked) / 1_000_000.0;

        System.out.printf("1. БЕНЧМАРК ВСТАВКИ У ПОЧАТОК СПИСКУ (на %,d елементів):%n", count);
        System.out.printf("  - Час для ArrayList:  %7.2f мс%n", arrayTimeMs);
        System.out.printf("  - Час для LinkedList: %7.2f мс%n", linkedTimeMs);
        String winner = (linkedTimeMs < arrayTimeMs)
                ? "LinkedList суттєво виграє при частих вставках на початку"
                : "ArrayList виявився швидшим при вставці на початку";
        System.out.println("  (Висновок: " + winner + ").");
    }

    public static void runInsertMiddleBenchmark(int count) {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        long startArray = System.nanoTime();
        for (int i = 0; i < count; i++) {
            arrayList.add(arrayList.size() / 2, i);
        }
        long endArray = System.nanoTime();
        double arrayTimeMs = (endArray - startArray) / 1_000_000.0;

        long startLinked = System.nanoTime();
        for (int i = 0; i < count; i++) {
            linkedList.add(linkedList.size() / 2, i);
        }
        long endLinked = System.nanoTime();
        double linkedTimeMs = (endLinked - startLinked) / 1_000_000.0;

        System.out.printf("%n1.2. БЕНЧМАРК ВСТАВКИ В СЕРЕДИНУ СПИСКУ (на %,d елементів):%n", count);
        System.out.printf("  - Час для ArrayList:  %7.2f мс%n", arrayTimeMs);
        System.out.printf("  - Час для LinkedList: %7.2f мс%n", linkedTimeMs);
        String winner = (arrayTimeMs < linkedTimeMs)
                ? "ArrayList швидший, оскільки LinkedList витрачає O(n) на пошук середини"
                : "LinkedList виявився швидшим при вставці в середину";
        System.out.println("  (Висновок: " + winner + ").");
    }

    public static List<Integer> demonstrateSorting() {
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            list.add(random.nextInt(100));
        }

        System.out.println("\n2. СОРТУВАННЯ СПИСКУ:");
        System.out.println("  До сортування: " + list);

        Collections.sort(list);

        System.out.println("  Після сортування: " + list);
        return list;
    }

    public static void demonstrateSubList(List<Integer> originalList) {
        System.out.println("\n3. РОБОТА З subList():");
        System.out.println("  Оригінальний список: " + originalList);

        List<Integer> subList = originalList.subList(1, 4);
        System.out.println("  Виділений subList (від індексу 1 до 4): " + subList);

        System.out.println("  Змінюємо елемент у subList на 999...");
        subList.set(0, 999);

        System.out.println("  Оновлений оригінальний список: " + originalList);
    }
}