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
        // TODO
    }

    public String getMonsterId() { return null; }
    public int getExpReward() { return 0; }
    public int getCoinReward() { return 0; }

    public void resetHp() {
        // TODO
    }

    void applyBleed(String sourceName, int bleedDamage) {
        // TODO
    }

    public void onTurnStart() {
        // TODO
    }

    public void resetBattleState() {
        // TODO
    }

    // Implementasi Combatant
    @Override
    public String getName() { return null; }

    @Override
    public double getAttackPower() { return 0; }

    @Override
    public double getDefense() { return 0; }

    @Override
    public void takeDamage(double damage) {
        // TODO
    }

    @Override
    public boolean isDefeated() { return false; }

    @Override
    public String getCombatInfo() { return null; }

    @Override
    public String toString() {
        // TODO
        return null;
    }
}