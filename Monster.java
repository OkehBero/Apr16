public class Monster implements Combatant {
    private String monsterId;
    private String name;
    private double  maxHp;
    private double  currentHp;
    private double  attackPower;
    private double  defense;
    private int expReward;
    private int coinReward;
    private boolean bleeding;
    private int bleedDamage;
    private String bleedSourceName;

    public Monster(int idNumber, String name, double  maxHp,
                   double  attackPower, double  defense,
                   int expReward, int coinReward) {

        this.monsterId = "M" + idNumber;
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.expReward = expReward;
        this.coinReward = coinReward;
    }

    public String getMonsterId() { return this.monsterId; }
    public int getExpReward() { return this.expReward; }
    public int getCoinReward() { return this.coinReward; }
    public double getMaxHp() { return this.maxHp; }
    public double getCurrentHp() {return this.currentHp;}

    public void resetHp() {
        this.currentHp = this.maxHp;
    }

    void applyBleed(String sourceName, int bleedDamage) {
        this.bleeding = true;
        this.bleedSourceName = sourceName;
        this.bleedDamage = bleedDamage;
    }

    public void onTurnStart() {
        if (this.bleeding){
            takeDamage(bleedDamage);
            System.out.println(String.format("%s terkena efek bleed dari %s sebesar %d damage!", this.name, this.bleedSourceName, this.bleedDamage));
        }
    }

    public void resetBattleState() {
        this.bleeding = false;
        this.bleedDamage = 0;
        this.bleedSourceName = null;
    }

    // Implementasi Combatant
    @Override
    public String getName() { return this.name; }

    @Override
    public double getAttackPower() { return this.attackPower; }

    @Override
    public double getDefense() { return this.defense; }

    @Override
    public void takeDamage(double damage) {
        this.currentHp -= damage;
        if (this.currentHp < 0) {
            this.currentHp = 0; 
        }
    }

    @Override
    public boolean isDefeated() { return this.currentHp <= 0; }

    @Override
    public String getCombatInfo() { return String.format("%s (HP: %.2f/%.2f)", this.name, this.currentHp, this.maxHp); }

    @Override
    public String toString() {
        return String.format("""
                ID Monster: %s
                Nama Monster: %s
                HP: %.2f
                ATK: %.2f | DEF: %.2f
                Reward: %d EXP | %d Koin
                """,
                getMonsterId(),
                getName(),
                getMaxHp(),
                getAttackPower(),
                getDefense(),
                getExpReward(),
                getCoinReward());
    }
}