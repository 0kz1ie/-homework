package semester1.practice08.homework;

import java.util.Locale;

class BankAccount {
    private String iban;
    private String ownerName;
    protected double balance;

    public BankAccount(String iban, String ownerName, double initialBalance) {
        this.iban = iban;
        this.ownerName = ownerName;
        this.balance = initialBalance < 0 ? 0.0 : initialBalance;
    }

    public String getIban() {
        return iban;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        } else {
            System.out.printf(Locale.US, "   [Відмова] Недостатньо коштів або некоректна сума (Запит: %.2f грн, Доступно: %.2f грн)%n", amount, balance);
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Рахунок[%s | Власник: %s | Баланс: %.2f грн]", iban, ownerName, balance);
    }
}

class SavingsAccount extends BankAccount {
    private double interestRate;
    private double minBalance;

    public SavingsAccount(String iban, String ownerName, double initialBalance, double interestRate, double minBalance) {
        super(iban, ownerName, initialBalance);
        this.interestRate = interestRate;
        this.minBalance = minBalance;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getMinBalance() {
        return minBalance;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && (balance - amount >= minBalance)) {
            return super.withdraw(amount);
        } else {
            System.out.printf(Locale.US, "   [Відмова] Порушення незнижуваного залишку %.2f грн (Запит: %.2f грн, Поточний баланс: %.2f грн)%n", minBalance, amount, balance);
            return false;
        }
    }

    public void applyInterest() {
        if (balance > 0) {
            double interest = balance * (interestRate / 100.0);
            deposit(interest);
        }
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Ощадний%s [Ставка: %.1f%% | Мін. залишок: %.2f грн]",
                super.toString(), interestRate, minBalance);
    }
}

class CreditAccount extends BankAccount {
    private double creditLimit;
    private double feePercent;

    public CreditAccount(String iban, String ownerName, double initialBalance, double creditLimit, double feePercent) {
        super(iban, ownerName, initialBalance);
        this.creditLimit = creditLimit;
        this.feePercent = feePercent;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public double getFeePercent() {
        return feePercent;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("   [Відмова] Сума зняття має бути більшою за 0.");
            return false;
        }

        if (balance >= amount) {
            return super.withdraw(amount);
        } else {
            double borrowed = amount - Math.max(0.0, balance);
            double fee = borrowed * (feePercent / 100.0);
            double newBalance = balance - amount - fee;

            if (newBalance >= -creditLimit) {
                this.balance = newBalance;
                return true;
            } else {
                System.out.printf(Locale.US, "   [Відмова] Перевищено кредитний ліміт %.2f грн (Запит: %.2f грн, Комісія: %.2f грн)%n",
                        creditLimit, amount, fee);
                return false;
            }
        }
    }

    public double getCurrentDebt() {
        return balance < 0 ? Math.abs(balance) : 0.0;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Кредитний%s [Ліміт: %.2f грн | Комісія: %.1f%% | Борг: %.2f грн]",
                super.toString(), creditLimit, feePercent, getCurrentDebt());
    }
}

public class Task2BankingHierarchy {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        System.out.println("=============== БАНКІВСЬКА ІЄРАРХІЯ РАХУНКІВ ===============");

        System.out.println("\n1. Базовий поточний рахунок:");
        BankAccount baseAcc = new BankAccount("UA111111000000001", "Олександр Коваль", 2000.0);
        System.out.println("   Створено: " + baseAcc);
        baseAcc.withdraw(500.0);
        System.out.println("   Після зняття 500 грн: " + baseAcc);
        baseAcc.withdraw(2000.0);

        System.out.println("\n2. Ощадний рахунок:");
        SavingsAccount savingsAcc = new SavingsAccount("UA222222000000002", "Марія Шевченко", 5000.0, 12.0, 1000.0);
        System.out.println("   Створено: " + savingsAcc);
        savingsAcc.withdraw(3500.0);
        System.out.println("   Після зняття 3500 грн: " + savingsAcc);
        savingsAcc.withdraw(600.0);
        savingsAcc.applyInterest();
        System.out.println("   Після нарахування відсотків: " + savingsAcc);

        System.out.println("\n3. Кредитний рахунок:");
        CreditAccount creditAcc = new CreditAccount("UA333333000000003", "Іван Бондар", 1000.0, 15000.0, 2.0);
        System.out.println("   Створено: " + creditAcc);
        creditAcc.withdraw(600.0);
        System.out.println("   Зняття власних коштів (600 грн): " + creditAcc);
        creditAcc.withdraw(5400.0);
        System.out.println("   Вихід в овердрафт на 5000 грн (+ 2% комісії = 100 грн): " + creditAcc);
        creditAcc.withdraw(10000.0);
        System.out.println("   Фінальний стан: " + creditAcc);

        System.out.println("============================================================");
    }
}