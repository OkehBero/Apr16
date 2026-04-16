public class Assassin extends Wanderer {
    public Assassin(int idNumber, String name, String username,
                    String password, double  maxHp, double  attack, double  defense) {
        super(idNumber, name, username, password, maxHp, attack, defense);
    }

    @Override
    public double modifyDamageDealt(double baseDamage) {
        // TODO
        return 0;
    }

}
