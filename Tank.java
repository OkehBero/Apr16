public class Tank extends Wanderer {
    public Tank(int idNumber, String name, String username,
                String password, double  maxHp, double  attack, double  defense) {
        super(idNumber, name, username, password, maxHp, attack, defense);
    }

    @Override
    public double modifyDamageTaken(double incomingDamage) {
        // TODO
        return 0;
    }
}
