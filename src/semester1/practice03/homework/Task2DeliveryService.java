package semester1.practice03.homework;

import java.util.Scanner;

public class Task2DeliveryService {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Логістичний тарифікатор служби доставки ===");

        System.out.print("Введіть вагу відправлення (кг): ");
        double weight = scanner.nextDouble();

        System.out.print("Введіть відстань доставки (км): ");
        int distance = scanner.nextInt();

        System.out.println("Оберіть тип доставки:");
        System.out.println("  1 — Відділення (базово 50.0 грн)");
        System.out.println("  2 — Поштомат (базово 60.0 грн, макс. вага 15 кг)");
        System.out.println("  3 — Кур'єрська доставка (базово 100.0 грн)");
        System.out.print("Ваш вибір (1-3): ");
        int deliveryType = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Чи має клієнт картку 'Premium'? (так/ні): ");
        String premiumInput = scanner.nextLine();

        if (weight <= 0 || weight > 50.0) {
            System.out.println("\n[Помилка]: Вага відправлення має бути від 0 до 50.0 кг!");
            scanner.close();
            return;
        }

        if (distance <= 0) {
            System.out.println("\n[Помилка]: Відстань доставки має бути більшою за 0 км!");
            scanner.close();
            return;
        }

        double baseTariff = switch (deliveryType) {
            case 1 -> 50.0;
            case 2 -> {
                if (weight > 15.0) {
                    System.out.println("\n[Помилка]: Поштомат не приймає посилки вагою понад 15.0 кг!");
                    yield -1.0;
                }
                yield 60.0;
            }
            case 3 -> 100.0;
            default -> -1.0;
        };

        if (baseTariff < 0) {
            System.out.println("[Помилка]: Некоректний тип доставки або перевищено ліміти!");
            scanner.close();
            return;
        }

        double distanceFee = 0.0;
        if (distance <= 50) {
            distanceFee = 0.0;
        } else if (distance <= 200) {
            distanceFee = 35.0;
        } else {
            distanceFee = 80.0;
        }

        double totalBeforeDiscount = baseTariff + distanceFee;

        boolean isPremium = premiumInput.trim().equalsIgnoreCase("так");
        double finalCost = isPremium ? totalBeforeDiscount * 0.80 : totalBeforeDiscount;
        double discountAmount = totalBeforeDiscount - finalCost;

        String deliveryName = switch (deliveryType) {
            case 1 -> "Відділення";
            case 2 -> "Поштомат";
            case 3 -> "Кур'єрська доставка";
            default -> "Невідомо";
        };

        System.out.println("\n================ КВИТАНЦІЯ ДОСТАВКИ ================");
        System.out.printf("Вага відправлення:       %.2f кг%n", weight);
        System.out.printf("Відстань:                %d км%n", distance);
        System.out.printf("Тип доставки:            %s%n", deliveryName);
        System.out.printf("Статус Premium:          %s%n", isPremium ? "Так (-20%)" : "Ні");
        System.out.println("-----------------------------------------------------");
        System.out.printf("Базовий тариф:           %.2f грн%n", baseTariff);
        System.out.printf("Доплата за кілометраж:   %.2f грн%n", distanceFee);
        System.out.printf("Сума до знижки:          %.2f грн%n", totalBeforeDiscount);
        System.out.printf("Знижка:                  -%.2f грн%n", discountAmount);
        System.out.println("-----------------------------------------------------");
        System.out.printf("ПІДСУМКОВА ВАРТІСТЬ:     %.2f грн%n", finalCost);
        System.out.println("=====================================================");

        scanner.close();
    }
}