package homework2;

public class hw {
    static void main() {
        String studentName = "Сесь Саша";

        int strength = (int) (Math.random() * 11) + 8;
        int agility = (int) (Math.random() * 11) + 8;
        int intelligence = (int) (Math.random() * 11) + 8;

        int hp = strength * 10 + agility * 2;
        int mp = intelligence * 15;
        int armor = agility / 2;

        int dice1 = (int) (Math.random() * 6) + 1;
        int dice2 = (int) (Math.random() * 6) + 1;

        int playerAttack = dice1 + dice2 + strength;
        final int MONSTER_ATTACK = 22;

        boolean isPlayerVictory = playerAttack > MONSTER_ATTACK;

        System.out.println("===================================");
        System.out.println("          ПАСПОРТ ГЕРОЯ            ");
        System.out.println("        Студент: " + studentName + "        ");
        System.out.println("===================================");
        System.out.println("Базові характеристики:");
        System.out.println("Сила (Strength):       " + strength);
        System.out.println("Спритність (Agility):  " + agility);
        System.out.println("Інтелект (Int):        " + intelligence);
        System.out.println("-----------------------------------");
        System.out.println("Похідні параметри:");
        System.out.println("Здоров'я (HP):         " + hp);
        System.out.println("Мана (MP):             " + mp);
        System.out.println("Захист (Armor):        " + armor);
        System.out.println("===================================");
        System.out.println("           СИМУЛЯЦІЯ БОЮ           ");
        System.out.println("-----------------------------------");
        System.out.println("Кидок кубиків (2d6):   " + dice1 + " + " + dice2);
        System.out.println("Атака гравця:          " + playerAttack);
        System.out.println("Атака монстра:         " + MONSTER_ATTACK);
        System.out.println("Перемога:              " + isPlayerVictory);
        System.out.println("===================================");
    }
}

