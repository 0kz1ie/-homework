package semester1.practice06.homework;

public class Task3Vector2D {
    public static void main(String[] args) {
        Vector2D a = new Vector2D();
        a.set(3, 4);
        System.out.print("Вектор a: ");
        a.printVector();
        System.out.println("Довжина вектора a дорівнює 5.0? " + (Math.abs(a.getLength() - 5.0) < 1e-9));
        System.out.println("-------------------------");

        Vector2D b = new Vector2D();
        b.set(-4, 3);
        System.out.print("Вектор b: ");
        b.printVector();

        boolean isOrtho = a.isPerpendicularTo(b.getX(), b.getY());
        System.out.println("Вектори a та b ортогональні (перпендикулярні)? " + isOrtho);
        System.out.println("Скалярний добуток: " + a.dotProduct(b.getX(), b.getY()));
        System.out.println("-------------------------");

        System.out.println("Нормалізація вектора a...");
        a.normalize();
        System.out.print("Нормалізований вектор a: ");
        a.printVector();
        System.out.println("Довжина вектора a після нормалізації стала рівною 1.0? " + (Math.abs(a.getLength() - 1.0) < 1e-9));
    }
}