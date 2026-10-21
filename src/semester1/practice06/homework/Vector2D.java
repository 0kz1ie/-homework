package semester1.practice06.homework;

public class Vector2D {
    private double x;
    private double y;

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    public void multiplyByScalar(double k) {
        this.x = this.x * k;
        this.y = this.y * k;
    }

    public double dotProduct(double otherX, double otherY) {
        return this.x * otherX + this.y * otherY;
    }

    public boolean isPerpendicularTo(double otherX, double otherY) {
        double dot = dotProduct(otherX, otherY);
        return Math.abs(dot) < 1e-9;
    }

    public void normalize() {
        double length = getLength();
        if (length > 0) {
            this.x = this.x / length;
            this.y = this.y / length;
        }
    }

    public void printVector() {
        System.out.printf("(%.2f; %.2f), довжина: %.2f%n", x, y, getLength());
    }
}