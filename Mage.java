public class Mage extends Wanderer {
    private boolean overcharged;
    private boolean usedBurstThisTurn;

    public Mage(int idNumber, String name, String username,
                String password, double  maxHp, double  attack, double  defense) {
        super(idNumber, name, username, password, maxHp, attack, defense);
        // TODO
    }

    @Override
    public void onTurnStart() {
        // TODO
    }

    @Override
    public double modifyDamageDealt(double baseDamage) {
        // TODO
        return 0;
    }

    @Override
    public void onTurnEnd(double result) {
        // TODO
    }

    @Override
    public void resetBattleState() {
        // TODO
    }
}
