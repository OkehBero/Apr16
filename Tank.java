public class Tank extends Wanderer {
    private int shieldCount = 0;
    public Tank(int idNumber, String name, String username,
                String password, double  maxHp, double  attack, double  defense) {
        super(idNumber, name, username, password, maxHp, attack, defense);
    }

    @Override
    public double modifyDamageTaken(double incomingDamage) {
        if (getCurrentHp() <= 0.3 * getMaxHp()){
            this.shieldCount++;
            setCustomDamageNote("[TANK] Shield menyala! Damage terpotong 50%");
            return incomingDamage * 0.5;
        }
        return incomingDamage;
    }

    @Override
    public String getPassiveTriggerSummary(){
        return String.format("- Shield aktif: %d kali", shieldCount);
    }
}
