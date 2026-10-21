package semester1.practice05.homework;

import java.util.Scanner;

public class Task3CinemaBooking {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[][] hall = new char[5][6];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                hall[i][j] = 'O';
            }
        }

        int choice = -1;
        while (choice != 0) {
            System.out.println("\n=== ТЕРМІНАЛ КІНОТЕАТРУ: ЗАЛ №1 ===");
            System.out.println("1 - Показати схему залу");
            System.out.println("2 - Забронювати квиток");
            System.out.println("3 - Фінансова каса залу");
            System.out.println("0 - Завершити роботу");
            System.out.print("Ваш вибір: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n      1  2  3  4  5  6");
                    for (int i = 0; i < 5; i++) {
                        System.out.print("Р" + (i + 1) + ": ");
                        for (int j = 0; j < 6; j++) {
                            System.out.print(" [" + hall[i][j] + "]");
                        }
                        System.out.println();
                    }
                    break;
                case 2:
                    System.out.print("Введіть номер ряду (1-5): ");
                    int row = scanner.nextInt();
                    System.out.print("Введіть номер місця (1-6): ");
                    int seat = scanner.nextInt();

                    if (row < 1 || row > 5 || seat < 1 || seat > 6) {
                        System.out.println("Помилка: Некоректні координати місця!");
                    } else if (hall[row - 1][seat - 1] == 'X') {
                        System.out.println("Помилка: Місце вже заброньовано!");
                    } else {
                        hall[row - 1][seat - 1] = 'X';
                        System.out.println("✅ Місце [Ряд " + row + ", Місце " + seat + "] успішно заброньовано!");
                    }
                    break;
                case 3:
                    int totalSeats = 30;
                    int booked = 0;
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 6; j++) {
                            if (hall[i][j] == 'X') {
                                booked++;
                            }
                        }
                    }
                    int free = totalSeats - booked;
                    double occupancy = ((double) booked / totalSeats) * 100;
                    double revenue = booked * 180.0;

                    System.out.println("\n--- СТАТИСТИКА ЗАЛУ ---");
                    System.out.println("Усього місць:       " + totalSeats);
                    System.out.println("Зайнятих місць:     " + booked);
                    System.out.println("Вільних місць:      " + free);
                    System.out.printf("Заповненість залу:  %.2f%%\n", occupancy);
                    System.out.printf("Загальна виручка:   %.2f грн\n", revenue);
                    System.out.println("=======================");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Невідома команда. Спробуйте ще раз.");
            }
        }
        scanner.close();
    }
}