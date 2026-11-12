package semester1.practice10.homework;

import java.util.Arrays;
import java.util.Locale;

interface Evaluable1D {
    double EPSILON = 1e-7;
    double evaluate(double x);
}

interface Integrable1D extends Evaluable1D {
    default double integrateSimpson(double a, double b, int n) {
        if (n < 2) {
            n = 100;
        }
        if (n % 2 != 0) {
            n++;
        }
        double h = (b - a) / n;
        double sumOdd = 0.0;
        double sumEven = 0.0;

        for (int i = 1; i < n; i += 2) {
            sumOdd += evaluate(a + i * h);
        }
        for (int i = 2; i < n; i += 2) {
            sumEven += evaluate(a + i * h);
        }

        return (h / 3.0) * (evaluate(a) + evaluate(b) + 4.0 * sumOdd + 2.0 * sumEven);
    }
}

interface Differentiable1D extends Evaluable1D {
    double derivative(double x);

    default double numericalDerivative(double x) {
        double h = 1e-5;
        return (evaluate(x + h) - evaluate(x - h)) / (2.0 * h);
    }
}

interface NewtonRootFindable extends Differentiable1D {
    default double findRootNewton(double x0, int maxIter) {
        double xk = x0;
        int iter = 0;
        while (Math.abs(evaluate(xk)) > EPSILON && iter < maxIter) {
            double df = derivative(xk);
            if (Math.abs(df) < 1e-12) {
                break;
            }
            xk = xk - evaluate(xk) / df;
            iter++;
        }
        return xk;
    }
}

abstract class AbstractMathFunction implements Integrable1D {
    private final String formulaTitle;

    public AbstractMathFunction(String formulaTitle) {
        this.formulaTitle = formulaTitle;
    }

    public final String getFormulaTitle() {
        return formulaTitle;
    }
}

final class PolynomialFunction extends AbstractMathFunction implements NewtonRootFindable {
    private final double[] coeffs;

    public PolynomialFunction(String formulaTitle, double[] coeffs) {
        super(formulaTitle);
        this.coeffs = Arrays.copyOf(coeffs, coeffs.length);
    }

    public double[] getCoeffs() {
        return Arrays.copyOf(coeffs, coeffs.length);
    }

    @Override
    public double evaluate(double x) {
        double result = 0.0;
        double xPower = 1.0;
        for (double c : coeffs) {
            result += c * xPower;
            xPower *= x;
        }
        return result;
    }

    @Override
    public double derivative(double x) {
        double result = 0.0;
        double xPower = 1.0;
        for (int i = 1; i < coeffs.length; i++) {
            result += i * coeffs[i] * xPower;
            xPower *= x;
        }
        return result;
    }
}

class DampedHarmonyFunction extends AbstractMathFunction implements NewtonRootFindable {
    private final double a;
    private final double gamma;
    private final double omega;

    public DampedHarmonyFunction(String formulaTitle, double a, double gamma, double omega) {
        super(formulaTitle);
        this.a = a;
        this.gamma = gamma;
        this.omega = omega;
    }

    @Override
    public double evaluate(double x) {
        return a * Math.exp(-gamma * x) * Math.cos(omega * x);
    }

    @Override
    public double derivative(double x) {
        return -a * Math.exp(-gamma * x) * (gamma * Math.cos(omega * x) + omega * Math.sin(omega * x));
    }
}

class StepHeavisideFunction extends AbstractMathFunction {
    private final double threshold;
    private final double lowValue;
    private final double highValue;

    public StepHeavisideFunction(String formulaTitle, double threshold, double lowValue, double highValue) {
        super(formulaTitle);
        this.threshold = threshold;
        this.lowValue = lowValue;
        this.highValue = highValue;
    }

    @Override
    public double evaluate(double x) {
        return x < threshold ? lowValue : highValue;
    }
}

public class Task3NumericalCalculusAndRootPolymorphism {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        AbstractMathFunction[] functions = {
                new PolynomialFunction(
                        "P(x) = -5.00 - 2.00*x + 0.00*x^2 + 1.00*x^3 [final PolynomialFunction]",
                        new double[]{-5.0, -2.0, 0.0, 1.0}
                ),
                new DampedHarmonyFunction(
                        "D(x) = 4.00 * exp(-0.50*x) * cos(2.00*x) [DampedHarmonyFunction]",
                        4.0, 0.5, 2.0
                ),
                new StepHeavisideFunction(
                        "Step(x) = {2.0 при x < 1.5; 5.0 при x >= 1.5} [StepHeavisideFunction]",
                        1.5, 2.0, 5.0
                )
        };

        System.out.println("================ ЧИСЕЛЬНИЙ АНАЛІЗ: ІНТЕРФЕЙСИ ТА ПОЛІМОРФІЗМ ================");
        System.out.println("Дослідження поліморфного масиву AbstractMathFunction[" + functions.length + "] на відрізку [0.00; 3.00]:\n");

        for (int i = 0; i < functions.length; i++) {
            AbstractMathFunction fn = functions[i];
            System.out.println((i + 1) + ". Функція: " + fn.getFormulaTitle());

            int n = (fn instanceof StepHeavisideFunction) ? 1000 : 100;
            double integral = fn.integrateSimpson(0.0, 3.0, n);

            if (fn instanceof PolynomialFunction) {
                System.out.printf("   -> Інтеграл Сімпсона на [0.0; 3.0] (N=%d): %.6f (Точне значення: -3.750000)%n", n, integral);
            } else {
                System.out.printf("   -> Інтеграл Сімпсона на [0.0; 3.0] (N=%d): %.6f%n", n, integral);
            }

            if (fn instanceof NewtonRootFindable rf) {
                if (fn instanceof PolynomialFunction) {
                    double xDeriv = 2.0;
                    double x0 = 2.0;
                    double root = rf.findRootNewton(x0, 100);
                    System.out.printf("   -> [Differentiable1D] Похідна P'(%.1f): аналітична = %.6f | чисельна = %.6f%n",
                            xDeriv, rf.derivative(xDeriv), rf.numericalDerivative(xDeriv));
                    System.out.printf("   -> [NewtonRootFindable] Корінь P(x) = 0 (старт x0=%.1f): x* = %.7f (Перевірка P(x*) = %.2e)%n",
                            x0, root, Math.abs(rf.evaluate(root)));
                } else if (fn instanceof DampedHarmonyFunction) {
                    double xDeriv = 1.0;
                    double x0 = 0.7;
                    double root = rf.findRootNewton(x0, 100);
                    System.out.printf("   -> [Differentiable1D] Похідна D'(%.1f): аналітична = %.6f | чисельна = %.6f%n",
                            xDeriv, rf.derivative(xDeriv), rf.numericalDerivative(xDeriv));
                    System.out.printf("   -> [NewtonRootFindable] Корінь D(x) = 0 (старт x0=%.1f): x* = %.7f (pi/4 = %.7f)%n",
                            x0, root, Math.PI / 4.0);
                }
            } else {
                System.out.println("   -> [Info] Функція розривна (не реалізує Differentiable1D / NewtonRootFindable) — пошук дотичних пропущено.");
            }

            if (i < functions.length - 1) {
                System.out.println();
            }
        }
        System.out.println("=============================================================================");
    }
}