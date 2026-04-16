public class Fighter extends Wanderer {
    private int furyStacks;

    public Fighter(int idNumber, String name, String username,
                   String password, double  maxHp, double attack, double defense) {
        super(idNumber, name, username, password, maxHp, attack, defense);
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
