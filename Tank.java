public class Tank extends Wanderer {

    // === Attribute ===
    private int shieldCount = 0;

    // === Constructor ====
    public Tank(int idNumber, String name, String username,
                String password, double  maxHp, double  attack, double  defense) {
        // Inisiasi attribute dari parrent class Wanderer
        super(idNumber, name, username, password, maxHp, attack, defense);
    }

    @Override
    public double modifyDamageTaken(double incomingDamage) {
        // Syarat aktifasi pasif unik tank
        if (getCurrentHp() <= 0.3 * getMaxHp()){
            this.shieldCount++;
            setCustomDamageNote("[TANK] Shield menyala! Damage terpotong 50%");
            return incomingDamage * 0.5;
        }
        // Return damage normal jika pasif unik tank tidak aktif
        return incomingDamage;
    }

    // === Summary ====
    @Override
    public String getPassiveTriggerSummary(){
        return String.format("- Shield aktif: %d kali", shieldCount);
    }
}
