package semester1.practice08.homework;

import java.util.Locale;

class Vector2D {
    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D() {
        this(0.0, 0.0);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    public double dotProduct(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Vector2D(%.2f; %.2f) |L|=%.2f", x, y, getLength());
    }
}

class Vector3D extends Vector2D {
    private double z;

    public Vector3D(double x, double y, double z) {
        super(x, y);
        this.z = z;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public double getLength() {
        return Math.hypot(super.getLength(), z);
    }

    public double dotProduct(Vector3D other) {
        return super.dotProduct(other) + this.z * other.z;
    }

    public Vector3D crossProduct(Vector3D other) {
        double cx = this.getY() * other.z - this.z * other.getY();
        double cy = this.z * other.getX() - this.getX() * other.z;
        double cz = this.getX() * other.getY() - this.getY() * other.getX();
        return new Vector3D(cx, cy, cz);
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Vector3D(%.2f; %.2f; %.2f) |L|=%.2f", getX(), getY(), z, getLength());
    }
}

class ForceVector3D extends Vector3D {
    private String forceName;

    public ForceVector3D(String forceName, double fx, double fy, double fz) {
        super(fx, fy, fz);
        this.forceName = forceName;
    }

    public String getForceName() {
        return forceName;
    }

    public void setForceName(String forceName) {
        this.forceName = forceName;
    }

    public double calculateWork(Vector3D displacement) {
        return super.dotProduct(displacement);
    }

    public double calculateAcceleration(double massKg) {
        if (massKg <= 0) {
            return 0.0;
        }
        return getLength() / massKg;
    }

    @Override
    public String toString() {
        String baseStr = super.toString().replace("|L|=", "|F|=");
        return String.format(Locale.US, "Force[name='%s', %s Н]", forceName, baseStr);
    }
}

public class Task3Vector3DHierarchy {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        System.out.println("=============== ІЄРАРХІЯ ВЕКТОРІВ (2D -> 3D -> FORCE) ===============");

        Vector2D v2d = new Vector2D(3.0, 4.0);
        System.out.println("1. Рівень 1 (2D): " + v2d);

        Vector3D v3d = new Vector3D(3.0, 4.0, 12.0);
        Vector3D displacement = new Vector3D(2.0, 0.0, 5.0);
        System.out.println("2. Рівень 2 (3D): " + v3d);
        System.out.println("   Вектор переміщення S: " + displacement);
        System.out.println();

        ForceVector3D force = new ForceVector3D("Тяга ракети", 3.0, 4.0, 12.0);
        double work = force.calculateWork(displacement);
        double mass = 2.5;
        double acceleration = force.calculateAcceleration(mass);

        System.out.println("3. Рівень 3 (Фізична сила):");
        System.out.println("   " + force);
        System.out.printf("   -> Виконана механічна робота A = F · S = %.2f Дж%n", work);
        System.out.printf("   -> Прискорення тіла масою %.1f кг: a = %.2f м/с²%n", mass, acceleration);
        System.out.println("=====================================================================");
    }
}