package semester1.practice04.homework;

import java.util.Scanner;

public class Task3FibonacciSeries {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = 0;

        do {
            System.out.print("Введіть кількість чисел Фібоначчі N (від 3 до 30): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Помилка! Потрібно ввести ціле число.");
                System.out.print("Введіть кількість чисел Фібоначчі N (від 3 до 30): ");
                scanner.next();
            }
            n = scanner.nextInt();
        } while (n < 3);

        int f1 = 1;
        int f2 = 1;

        System.out.println("1: " + f1);
        System.out.println("2: " + f2);

        double finalRatio = 0.0;

        for (int i = 3; i <= n; i++) {
            int next = f1 + f2;
            double ratio = (double) next / f2;

            System.out.printf("%d: %d, Відношення: %.7f\n", i, next, ratio);

            finalRatio = ratio;
            f1 = f2;
            f2 = next;
        }

        System.out.println("\nФінальне наближення Золотого перетину: " + finalRatio);
        System.out.println("Математична константа Золотого перетину: ≈ 1.6180339");

        scanner.close();
    }
}