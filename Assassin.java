public class Assassin extends Wanderer {
    
    // === Attribute
    private int bleedDiTerapkanCount = 0;

    // Constructor
    public Assassin(int idNumber, String name, String username,
                    String password, double  maxHp, double  attack, double  defense) {
        // Inisialisasi attribute dari parent class Wanderer
        super(idNumber, name, username, password, maxHp, attack, defense);
    }

    @Override
    public double modifyDamageDealt(double baseDamage) {
        if (Math.random() < 0.5){ // 50% chance pasif aktif
            Monster targetMonster = getCurrentTarget();
            if (targetMonster != null){
                this.bleedDiTerapkanCount++;
                setCustomDamageNote("[ASSASISIN] " + targetMonster.getName() + " terkena Bleed!");
                targetMonster.applyBleed(this.getName(), getAttackPower() * 0.20);
                
            }
        }
        return baseDamage;
    }

    // === Summary ===
    @Override
    public String getPassiveTriggerSummary(){
        int count = 0;
        if (getCurrentTarget() != null){
            count = getCurrentTarget().getBleedCount();
        }

        return String.format("""
                - Bleed berhasil diterapkan: %d kali
                - Bleed terpicu: %d kali""",
                this.bleedDiTerapkanCount,
                count);
    }

}
