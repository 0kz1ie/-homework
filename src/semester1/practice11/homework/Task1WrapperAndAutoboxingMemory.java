package semester1.practice11.homework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"UnnecessaryBoxing", "UnnecessaryUnboxing", "NumberEquality", "ConstantValue", "SequencedCollectionMethodCanBeUsed"})
public class Task1WrapperAndAutoboxingMemory {

    public static void main(String[] args) {
        System.out.println("=============== ДОСЛІДЖЕННЯ WRAPPER CLASSES ТА INTEGER CACHE ===============");

        int[] testValues = {-128, 127, 128, 500};
        inspectIntegerCache(testValues);

        demonstrateAutoboxingUnderTheHood();

        System.out.println("\n[3. ПАРСИНГ РЯДКІВ (Integer.parseInt) ТА ЕКСТРЕМУМИ (MAX_VALUE / MIN_VALUE)]:");
        System.out.println("  Початкові межі пошуку: min = " + Integer.MAX_VALUE + " (MAX_VALUE), max = " + Integer.MIN_VALUE + " (MIN_VALUE)");
        String[] rawPackets = {" 145 ", "-30", "ERR_404", "980", "", null, "2147483647", "-85"};
        List<Integer> validData = parseTelemetryStream(rawPackets);
        printExtremesAndStats(validData);

        System.out.println("============================================================================");
    }

    public static void inspectIntegerCache(int[] testValues) {
        System.out.println("[1. ЕКСПЕРИМЕНТ: INTEGER CACHE [-128..127] (== проти .equals())]:");
        for (int val : testValues) {
            Integer first = Integer.valueOf(val);
            Integer second = Integer.valueOf(val);
            boolean refEquals = (first == second);
            boolean valEquals = first.equals(second);
            String source = refEquals ? "IntegerCache" : "Новий об'єкт у Heap!";
            System.out.printf("  Значення: %5d | (a == b): %-5b | a.equals(b): %-5b | Джерело: %s%n",
                    val, refEquals, valEquals, source);
        }
    }

    public static void demonstrateAutoboxingUnderTheHood() {
        System.out.println("\n[2. AUTOBOXING / UNBOXING ПІД КАПОТОМ ТА ЗАХИСТ ВІД NULL]:");

        Integer implicitInt = 42;
        Integer explicitInt = Integer.valueOf(42);
        int unboxedIntImplicit = implicitInt;
        int unboxedIntExplicit = explicitInt.intValue();
        System.out.println("  Явне пакування: Integer.valueOf(42) -> " + explicitInt
                + " | Розпакування: .intValue() -> " + unboxedIntExplicit
                + " (неявне: " + unboxedIntImplicit + ")");

        Double implicitDouble = 3.14;
        Double explicitDouble = Double.valueOf(3.14);
        double unboxedDoubleImplicit = implicitDouble;
        double unboxedDoubleExplicit = explicitDouble.doubleValue();
        System.out.println("  Явне пакування: Double.valueOf(3.14) -> " + explicitDouble
                + " | Розпакування: .doubleValue() -> " + unboxedDoubleExplicit
                + " (неявне: " + unboxedDoubleImplicit + ")");

        Boolean implicitBool = true;
        Boolean explicitBool = Boolean.valueOf(true);
        boolean unboxedBoolImplicit = implicitBool;
        boolean unboxedBoolExplicit = explicitBool.booleanValue();
        System.out.println("  Явне пакування: Boolean.valueOf(true) -> " + explicitBool
                + " | Розпакування: .booleanValue() -> " + unboxedBoolExplicit
                + " (неявне: " + unboxedBoolImplicit + ")");

        List<Integer> sensorReadings = Arrays.asList(45, null, -12, 88, null, 102);
        System.out.println("  Вхідний список із датчиків (з null): " + sensorReadings);

        int sum = 0;
        List<Integer> nullIndices = new ArrayList<>();
        for (int i = 0; i < sensorReadings.size(); i++) {
            Integer reading = sensorReadings.get(i);
            if (reading == null) {
                nullIndices.add(i);
                continue;
            }
            sum += reading.intValue();
        }

        if (!nullIndices.isEmpty()) {
            StringBuilder indicesText = new StringBuilder();
            for (int i = 0; i < nullIndices.size(); i++) {
                indicesText.append(nullIndices.get(i));
                if (i < nullIndices.size() - 1) {
                    indicesText.append(" та ");
                }
            }
            System.out.println("  ⚠️ Пропущено null-елемент на індексі " + indicesText + " (запобігання NullPointerException при intValue()!)");
        }
        System.out.println("  Сума валідних розпакованих елементів: " + sum);
    }

    public static List<Integer> parseTelemetryStream(String[] rawPackets) {
        List<Integer> parsedList = new ArrayList<>();
        for (String packet : rawPackets) {
            if (packet == null || packet.trim().isEmpty()) {
                System.out.println("  ❌ Порожній або null-пакет відхилено");
                continue;
            }
            String trimmed = packet.trim();
            try {
                int value = Integer.parseInt(trimmed);
                parsedList.add(value);
            } catch (NumberFormatException e) {
                System.out.println("  ❌ Пакет '" + trimmed + "' відхилено (NumberFormatException)");
            }
        }
        return parsedList;
    }

    public static void printExtremesAndStats(List<Integer> validData) {
        System.out.println("  Успішно розпізнаний список Integer: " + validData);
        if (validData.isEmpty()) {
            System.out.println("  Список порожній, обчислення неможливі.");
            return;
        }

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        long sum = 0;

        for (Integer value : validData) {
            if (value == null) {
                continue;
            }
            int primitive = value;
            if (primitive < min) {
                min = primitive;
            }
            if (primitive > max) {
                max = primitive;
            }
            sum += primitive;
        }

        System.out.println("  Знайдений мінімум: " + min + " | Знайдений максимум: " + max + " | Сума: " + sum);

        List<Integer> copyForRemoval = new ArrayList<>(validData);
        copyForRemoval.remove(Integer.valueOf(-30));
        System.out.println("  Після remove(Integer.valueOf(-30)) (за значенням): " + copyForRemoval);

        copyForRemoval.remove(0);
        System.out.println("  Після remove(0) (за індексом 0): " + copyForRemoval);
    }
}