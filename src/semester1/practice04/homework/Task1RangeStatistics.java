package semester1.practice04.homework;

import java.util.Scanner;

public class Task1RangeStatistics {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = 0;

        do {
            System.out.print("Введіть додатне ціле число N (більше за 0): ");

            while (!scanner.hasNextInt()) {
                System.out.println("Помилка! Потрібно ввести ціле число.");
                System.out.print("Введіть додатне ціле число N (більше за 0): ");
                scanner.next();
            }

            n = scanner.nextInt();

            if (n <= 0) {
                System.out.println("Попередження: число має бути більшим за нуль! Спробуйте знову.\n");
            }
        } while (n <= 0);

        long totalSum = 0;
        long evenSum = 0;
        long oddSum = 0;
        int evenCount = 0;
        int oddCount = 0;

        for (int i = 1; i <= n; i++) {
            totalSum += i;

            if (i % 2 == 0) {
                evenSum += i;
                evenCount++;
            } else {
                oddSum += i;
                oddCount++;
            }
        }

        double average = (double) totalSum / n;

        System.out.println("\n=== СТАТИСТИКА ДЛЯ ДІАПАЗОНУ ВІД 1 ДО " + n + " ===");
        System.out.println("Загальна сума всіх чисел: \t" + totalSum);
        System.out.println("Сума парних чисел: \t\t" + evenSum);
        System.out.println("Сума непарних чисел: \t\t" + oddSum);
        System.out.println("Кількість парних чисел: \t" + evenCount);
        System.out.println("Кількість непарних чисел: \t" + oddCount);
        System.out.println("Середнє арифметичне: \t\t" + average);
        System.out.println("==========================================");

        scanner.close();
    }
}