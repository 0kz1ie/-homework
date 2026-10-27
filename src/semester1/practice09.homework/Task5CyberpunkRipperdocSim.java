package semester1.practice09.homework;

class BioMonitor {
    private int hp;
    private final int maxHp;
    private int humanity;
    private boolean cyberpsychosis;

    public BioMonitor(int maxHp) {
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.humanity = 100;
        this.cyberpsychosis = false;
    }

    public void reduceHumanity(int cost) {
        this.humanity = Math.max(0, this.humanity - cost);
        if (this.humanity < 20) {
            this.cyberpsychosis = true;
            System.out.println("⚠️ [КРИТИЧНЕ ПОПЕРЕДЖЕННЯ BIO-MONITOR]: Людяність впала до "
                    + this.humanity + "/100! Стан: КІБЕРПСИХОЗ (Атака x1.5, саморуйнування!)");
        }
    }

    public void injectImmunoblockers(int restoreAmount) {
        this.humanity = Math.min(100, this.humanity + restoreAmount);
        if (this.humanity >= 20) {
            this.cyberpsychosis = false;
        }
        System.out.println("💉 Введено Імуноблокатор (+" + restoreAmount + " Людяності -> "
                + this.humanity + "/100). Нейро-стабільність відновлено!");
    }

    public int applyDamage(int rawDamage, int totalArmor) {
        int actualDamage = Math.max(5, rawDamage - totalArmor);
        this.hp = Math.max(0, this.hp - actualDamage);
        return actualDamage;
    }

    public int applyOverloadDamage() {
        int overload = (int) (this.maxHp * 0.10);
        this.hp = Math.max(0, this.hp - overload);
        return overload;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getHumanity() {
        return humanity;
    }

    public boolean isCyberpsychosis() {
        return cyberpsychosis;
    }

    private String getHpBar() {
        int totalBars = 10;
        int filledBars = (int) Math.round(((double) hp / maxHp) * totalBars);
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < totalBars; i++) {
            sb.append(i < filledBars ? "█" : "░");
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public String toString() {
        String status = cyberpsychosis ? "[🔥 CYBERPSYCHOSIS]" : "(Стабільний)";
        return "BioMonitor: HP " + getHpBar() + " " + hp + "/" + maxHp
                + " | Humanity: " + humanity + "/100 " + status;
    }
}

abstract class AbstractCyberImplant {
    private final String name;
    private final String slot;
    private final int humanityCost;

    public AbstractCyberImplant(String name, String slot, int humanityCost) {
        this.name = name;
        this.slot = slot;
        this.humanityCost = humanityCost;
    }

    public String getName() {
        return name;
    }

    public String getSlot() {
        return slot;
    }

    public int getHumanityCost() {
        return humanityCost;
    }

    public abstract int getAttackBonus();
    public abstract int getArmorBonus();
    public abstract String activateSpecialAbility();

    @Override
    public String toString() {
        return "[" + slot + "] " + name + " (+" + getAttackBonus() + " ATK, +"
                + getArmorBonus() + " DEF, -" + humanityCost + " Hum)";
    }
}

class SandevistanOs extends AbstractCyberImplant {
    public SandevistanOs() {
        super("Militech Sandevistan Apogee", "Нейро-ОС", 35);
    }

    @Override
    public int getAttackBonus() {
        return 90;
    }

    @Override
    public int getArmorBonus() {
        return 20;
    }

    @Override
    public String activateSpecialAbility() {
        return "⚡ САНДЕВІСТАН АКТИВОВАНО: Час сповільнено на 85%! Вороги завмерли на місці!";
    }
}

class ProjectileLaunchSystem extends AbstractCyberImplant {
    public ProjectileLaunchSystem() {
        super("Система запуску снарядів", "Руки", 20);
    }

    @Override
    public int getAttackBonus() {
        return 115;
    }

    @Override
    public int getArmorBonus() {
        return 10;
    }

    @Override
    public String activateSpecialAbility() {
        return "🚀 СИСТЕМА ЗАПУСКУ СНАРЯДІВ: Випущено розривну термальну ракету прямо в ціль!";
    }
}

class Kerenzikov extends AbstractCyberImplant {
    public Kerenzikov() {
        super("Керезніков", "Нервова система", 15);
    }

    @Override
    public int getAttackBonus() {
        return 45;
    }

    @Override
    public int getArmorBonus() {
        return 30;
    }

    @Override
    public String activateSpecialAbility() {
        return "🌀 КЕРЕЗНІКОВ: Нейро-рефлекси прискорено під час ривка та прицілювання!";
    }
}

class MechatronicCore extends AbstractCyberImplant {
    public MechatronicCore() {
        super("Мехатронне ядро", "Лобна частка", 10);
    }

    @Override
    public int getAttackBonus() {
        return 40;
    }

    @Override
    public int getArmorBonus() {
        return 25;
    }

    @Override
    public String activateSpecialAbility() {
        return "🤖 МЕХАТРОННЕ ЯДРО: Проаналізовано вразливі вузли меха-конструкції ворога!";
    }
}

class JenkinsTendons extends AbstractCyberImplant {
    public JenkinsTendons() {
        super("Сухожилля Дженкінса", "Ноги", 10);
    }

    @Override
    public int getAttackBonus() {
        return 25;
    }

    @Override
    public int getArmorBonus() {
        return 35;
    }

    @Override
    public String activateSpecialAbility() {
        return "🏃 СУХОЖИЛЛЯ ДЖЕНКІНСА: Швидкість спринту виведена на максимум!";
    }
}

class CyberMercenary {
    private final String nickname;
    private final int baseAttack;
    private final BioMonitor bioMonitor;
    private final AbstractCyberImplant[] implants;
    private int implantCount;

    public CyberMercenary(String nickname, int baseAttack, int maxHp) {
        this.nickname = nickname;
        this.baseAttack = baseAttack;
        this.bioMonitor = new BioMonitor(maxHp);
        this.implants = new AbstractCyberImplant[5];
        this.implantCount = 0;
    }

    public boolean installImplant(AbstractCyberImplant implant) {
        for (int i = 0; i < implantCount; i++) {
            if (implants[i].getSlot().equals(implant.getSlot())) {
                System.out.println("❌ Помилка слота: Слот [" + implant.getSlot()
                        + "] вже зайнятий імплантом '" + implants[i].getName() + "'!");
                return false;
            }
        }
        if (implantCount >= implants.length) {
            System.out.println("❌ Помилка: Всі слоти для імплантів зайняті!");
            return false;
        }
        implants[implantCount++] = implant;
        bioMonitor.reduceHumanity(implant.getHumanityCost());
        System.out.println("🔧 Встановлено [" + implant.getSlot() + "]: " + implant.getName()
                + " (-" + implant.getHumanityCost() + " Людяності -> залишок: "
                + bioMonitor.getHumanity() + ")");
        return true;
    }

    public int getImplantsAttackBonus() {
        int bonus = 0;
        for (int i = 0; i < implantCount; i++) {
            bonus += implants[i].getAttackBonus();
        }
        return bonus;
    }

    public int getTotalAttack() {
        int rawTotal = baseAttack + getImplantsAttackBonus();
        if (bioMonitor.isCyberpsychosis()) {
            return (int) (rawTotal * 1.5);
        }
        return rawTotal;
    }

    public int getTotalArmor() {
        int totalArmor = 0;
        for (int i = 0; i < implantCount; i++) {
            totalArmor += implants[i].getArmorBonus();
        }
        return totalArmor;
    }

    public void triggerAllImplants() {
        for (int i = 0; i < implantCount; i++) {
            System.out.println(implants[i].activateSpecialAbility());
        }
    }

    public BioMonitor getBioMonitor() {
        return bioMonitor;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String rageInfo = bioMonitor.isCyberpsychosis() ? ", лють x1.5" : "";
        sb.append("CyberMercenary{nick='").append(nickname)
                .append("', TotalATK=").append(getTotalAttack())
                .append(" (База: ").append(baseAttack)
                .append(" + Імпланти: ").append(getImplantsAttackBonus())
                .append(rageInfo).append("), TotalDEF=").append(getTotalArmor()).append("}\n");
        sb.append("  ├─ ").append(bioMonitor.toString()).append("\n");
        sb.append("  └─ Встановлений Хром (").append(implantCount).append("/").append(implants.length).append("):\n");
        for (int i = 0; i < implantCount; i++) {
            sb.append("      ").append(i + 1).append(". ").append(implants[i].toString());
            if (i < implantCount - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}

public class Task5CyberpunkRipperdocSim {
    public static void main(String[] args) {
        System.out.println("================ 🦾 CYBERPUNK 2077: КЛІНІКА РІППЕРДОКА 🦾 ================");
        CyberMercenary v = new CyberMercenary("Ві", 120, 1000);
        System.out.println("Пацієнт: " + v.getNickname() + " | " + v.getBioMonitor());
        System.out.println();

        System.out.println("[ХІРУРГІЧНЕ ВСТАНОВЛЕННЯ ІМПЛАНТІВ]:");
        v.installImplant(new SandevistanOs());
        v.installImplant(new ProjectileLaunchSystem());

        // Демонстрація блокування дублюючого слота
        v.installImplant(new SandevistanOs());

        v.installImplant(new Kerenzikov());
        v.installImplant(new MechatronicCore());
        v.installImplant(new JenkinsTendons());
        System.out.println();

        System.out.println("[ДОСЬЄ НАЙМАНЦЯ ПЕРЕД БОЄМ]:");
        System.out.println(v);
        System.out.println();

        System.out.println("--- ⚔️ БІЙ НА ВЕРШИНІ ARASAKA TOWER ПРОТИ АДАМА СМЕШЕРА ⚔️ ---");
        int smasherHp = 1800;
        int smasherArmor = 80;
        int smasherAttack = 250;

        for (int round = 1; round <= 4 && smasherHp > 0 && v.getBioMonitor().getHp() > 0; round++) {
            System.out.println("\n[РАУНД " + round + "]:");
            v.triggerAllImplants();

            int vAttack = v.getTotalAttack();
            int damageToSmasher = Math.max(5, vAttack - smasherArmor);
            smasherHp = Math.max(0, smasherHp - damageToSmasher);
            System.out.println("💥 " + v.getNickname() + " завдає " + vAttack
                    + " шкоди! Адам Смешер втрачає " + damageToSmasher
                    + " HP (залишок HP Смешера: " + smasherHp + ")!");

            if (smasherHp > 0) {
                int damageToV = v.getBioMonitor().applyDamage(smasherAttack, v.getTotalArmor());
                System.out.println("💢 Адам Смешер атакує у відповідь! " + v.getNickname()
                        + " отримує " + damageToV + " шкоди (HP: " + v.getBioMonitor().getHp() + "/" + v.getBioMonitor().getMaxHp() + ").");
            }

            if (v.getBioMonitor().isCyberpsychosis()) {
                int overload = v.getBioMonitor().applyOverloadDamage();
                System.out.println("⚡ [ПЕРЕВАНТАЖЕННЯ ЦНС]: Через кіберпсихоз " + v.getNickname()
                        + " втрачає " + overload + " власного HP (HP: " + v.getBioMonitor().getHp() + "/" + v.getBioMonitor().getMaxHp() + ")!");

                if (round == 2) {
                    v.getBioMonitor().injectImmunoblockers(30);
                }
            }
        }

        System.out.println("\n[ФІНАЛЬНИЙ СТАН НАЙМАНЦЯ]:");
        System.out.println(v);
        System.out.println("==========================================================================");
    }
}