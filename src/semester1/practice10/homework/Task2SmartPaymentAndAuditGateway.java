package semester1.practice10.homework;

import java.util.Locale;

interface CashbackCapable {
    double MAX_CASHBACK_UAH = 500.0;

    double getCashbackRate();

    default double calculateCashback(double baseAmount) {
        return Math.min(MAX_CASHBACK_UAH, baseAmount * getCashbackRate());
    }
}

interface Refundable {
    boolean requestRefund(String reason);
    boolean isRefunded();
}

interface BlockchainVerifiable {
    String getTxHash();
    int getConfirmations();

    default boolean isFinalized() {
        return getConfirmations() >= 12;
    }
}

abstract class AbstractPaymentTransaction {
    private final String txId;
    private final double baseAmountUah;
    private boolean processed;

    public AbstractPaymentTransaction(String txId, double baseAmountUah) {
        this.txId = txId;
        this.baseAmountUah = baseAmountUah;
        this.processed = false;
    }

    public abstract double calculateFee();
    public abstract String getPaymentChannel();

    public final boolean process() {
        if (baseAmountUah <= 0 || processed) {
            return false;
        }
        processed = true;
        return true;
    }

    public final double getTotalCharged() {
        return baseAmountUah + calculateFee();
    }

    public String getTxId() {
        return txId;
    }

    public double getBaseAmountUah() {
        return baseAmountUah;
    }

    public boolean isProcessed() {
        return processed;
    }
}

class PremiumCardPayment extends AbstractPaymentTransaction implements CashbackCapable, Refundable {
    private boolean refunded;

    public PremiumCardPayment(String txId, double baseAmountUah) {
        super(txId, baseAmountUah);
        this.refunded = false;
    }

    @Override
    public double calculateFee() {
        return getBaseAmountUah() * 0.01;
    }

    @Override
    public String getPaymentChannel() {
        return "Visa Infinite";
    }

    @Override
    public double getCashbackRate() {
        return 0.03;
    }

    @Override
    public boolean requestRefund(String reason) {
        if (isProcessed() && !refunded) {
            refunded = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean isRefunded() {
        return refunded;
    }
}

class CryptoUsdtPayment extends AbstractPaymentTransaction implements CashbackCapable, BlockchainVerifiable {
    private final String txHash;
    private final int confirmations;

    public CryptoUsdtPayment(String txId, double baseAmountUah, String txHash, int confirmations) {
        super(txId, baseAmountUah);
        this.txHash = txHash;
        this.confirmations = confirmations;
    }

    @Override
    public double calculateFee() {
        return 40.0;
    }

    @Override
    public String getPaymentChannel() {
        return "USDT TRC-20";
    }

    @Override
    public double getCashbackRate() {
        return 0.01;
    }

    @Override
    public String getTxHash() {
        return txHash;
    }

    @Override
    public int getConfirmations() {
        return confirmations;
    }
}

final class TaxTreasuryPayment extends AbstractPaymentTransaction implements Refundable {
    private boolean refunded;

    public TaxTreasuryPayment(String txId, double baseAmountUah) {
        super(txId, baseAmountUah);
        this.refunded = false;
    }

    @Override
    public double calculateFee() {
        return 0.0;
    }

    @Override
    public String getPaymentChannel() {
        return "ДержКазначейство";
    }

    @Override
    public boolean requestRefund(String reason) {
        if (isProcessed() && !refunded) {
            refunded = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean isRefunded() {
        return refunded;
    }
}

public class Task2SmartPaymentAndAuditGateway {

    public static void runFinancialAudit(AbstractPaymentTransaction[] ledger) {
        double totalBaseTurnover = 0.0;
        double totalGatewayFee = 0.0;
        double totalCashbackPaid = 0.0;

        System.out.println("=============== ПРОЦЕСИНГОВИЙ ЦЕНТР FINTECH (POLYMORPHISM & INTERFACES) ===============");
        System.out.println("[1. ПРОВЕДЕННЯ ПАКЕТУ ТРАНЗАКЦІЙ (final process())]:");

        for (AbstractPaymentTransaction tx : ledger) {
            if (tx.process()) {
                double base = tx.getBaseAmountUah();
                double fee = tx.calculateFee();
                double total = tx.getTotalCharged();

                totalBaseTurnover += base;
                totalGatewayFee += fee;

                System.out.printf("  ✅ [%s | %-16s] Сума: %8.2f грн | Комісія: %6.2f грн | Всього: %.2f грн%n",
                        tx.getTxId(), tx.getPaymentChannel(), base, fee, total);
            }
        }

        System.out.println("\n[2. АВТОМАТИЧНИЙ АУДИТ МОЖЛИВОСТЕЙ (Pattern Matching instanceof)]:");

        for (AbstractPaymentTransaction tx : ledger) {
            if (tx instanceof CashbackCapable cb) {
                double rawCashback = tx.getBaseAmountUah() * cb.getCashbackRate();
                double actualCashback = cb.calculateCashback(tx.getBaseAmountUah());
                totalCashbackPaid += actualCashback;

                int ratePercent = (int) Math.round(cb.getCashbackRate() * 100);
                if (rawCashback > CashbackCapable.MAX_CASHBACK_UAH) {
                    System.out.printf("  💰 [CashbackCapable] %s: розрахунковий %d%% = %.0f грн -> нараховано %.2f грн (Ліміт MAX_CASHBACK_UAH!)%n",
                            tx.getTxId(), ratePercent, rawCashback, actualCashback);
                } else {
                    System.out.printf("  💰 [CashbackCapable] %s: нараховано %d%% крипто-бонусу = %.2f грн%n",
                            tx.getTxId(), ratePercent, actualCashback);
                }
            }

            if (tx instanceof BlockchainVerifiable bv) {
                System.out.printf("  ⛓️ [BlockchainVerifiable] %s: Hash=%s, Підтверджень=%d (Finalized: %b)%n",
                        tx.getTxId(), bv.getTxHash(), bv.getConfirmations(), bv.isFinalized());
            }
        }

        for (AbstractPaymentTransaction tx : ledger) {
            if (tx instanceof Refundable ref && tx.getTxId().equals("TX-101")) {
                String reason = "Подвійне списання";
                if (ref.requestRefund(reason)) {
                    System.out.printf("  🔄 [Refundable] Спроба повернення %s ('%s'): УСПІШНО ПОВЕРНЕНО!%n",
                            tx.getTxId(), reason);
                }
            }
        }

        System.out.println("\n[ПІДСУМКОВИЙ ЗВІТ ШЛЮЗУ]:");
        System.out.printf("  Сумарний базовий оборот:  %.2f грн%n", totalBaseTurnover);
        System.out.printf("  Сумарна комісія шлюзу:      %.2f грн%n", totalGatewayFee);
        System.out.printf("  Сумарний виплачений кешбек: %.2f грн%n", totalCashbackPaid);
        System.out.println("=======================================================================================");
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        AbstractPaymentTransaction[] ledger = {
                new PremiumCardPayment("TX-101", 20000.0),
                new CryptoUsdtPayment("TX-102", 8500.0, "0x9f8a...c4", 18),
                new TaxTreasuryPayment("TX-103", 5000.0)
        };

        runFinancialAudit(ledger);
    }
}