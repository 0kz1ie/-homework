package semester1.practice03.homework;

import java.util.Scanner;

public class Task1FuelCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Розумний калькулятор витрати пального ===");

        System.out.print("Введіть відстань поїздки (км): ");
        double distance = scanner.nextDouble();

        System.out.print("Введіть середню витрату пального на 100 км (л): ");
        double fuelConsumption = scanner.nextDouble();

        System.out.println("Оберіть тип пального:");
        System.out.println("  1 — Бензин А-95 (56.50 грн/л)");
        System.out.println("  2 — Дизель (52.80 грн/л)");
        System.out.println("  3 — Газ (29.40 грн/л)");
        System.out.print("Ваш вибір (1-3): ");
        int fuelType = scanner.nextInt();

        if (distance <= 0 || fuelConsumption <= 0) {
            System.out.println("\n[Помилка]: Відстань та витрата пального мають бути більшими за нуль!");
            scanner.close();
            return;
        }

        double pricePerLiter = 0.0;
        String fuelName = "";

        switch (fuelType) {
            case 1 -> {
                pricePerLiter = 56.50;
                fuelName = "Бензин А-95";
            }
            case 2 -> {
                pricePerLiter = 52.80;
                fuelName = "Дизель";
            }
            case 3 -> {
                pricePerLiter = 29.40;
                fuelName = "Газ";
            }
            default -> {
                System.out.println("\n[Помилка]: Невідомий тип пального! Оберіть значення від 1 до 3.");
                scanner.close();
                return;
            }
        }

        double neededFuel = (distance * fuelConsumption) / 100.0;
        double baseCost = neededFuel * pricePerLiter;

        double finalCost = (baseCost >= 2000.0) ? baseCost * 0.95 : baseCost;
        int discountPercent = (baseCost >= 2000.0) ? 5 : 0;
        double savedMoney = baseCost - finalCost;

        System.out.println("\n================ ЗВІТ ПОЇЗДКИ ================");
        System.out.printf("Відстань:               %.2f км%n", distance);
        System.out.printf("Середня витрата:        %.2f л/100 км%n", fuelConsumption);
        System.out.printf("Обране пальне:          %s (%.2f грн/л)%n", fuelName, pricePerLiter);
        System.out.println("----------------------------------------------");
        System.out.printf("Необхідний об'єм:       %.2f л%n", neededFuel);
        System.out.printf("Базова вартість:        %.2f грн%n", baseCost);
        System.out.printf("Знижка на АЗС:          %d%% (-%.2f грн)%n", discountPercent, savedMoney);
        System.out.println("----------------------------------------------");
        System.out.printf("ПІДСУМКОВА ВАРТІСТЬ:    %.2f грн%n", finalCost);
        System.out.println("==============================================");

        scanner.close();
    }
}