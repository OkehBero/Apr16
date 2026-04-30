public class Mage extends Wanderer {

    // === Attribute
    private boolean overcharged;
    private boolean usedBurstThisTurn;
    private int overchargedCount = 0;
    private int burstCount = 0;

    // === Constructor ===
    public Mage(int idNumber, String name, String username,
                String password, double  maxHp, double  attack, double  defense) {
        super(idNumber, name, username, password, maxHp, attack, defense);
        this.overcharged = false;
        this.usedBurstThisTurn = false;
    }

    @Override
    public void onTurnStart() {
        // TODO
    }

    @Override
    public double modifyDamageDealt(double baseDamage) {
        // Damage 2x lipat jika Overcharged aktif
        if (this.overcharged) {
            this.burstCount++;
            setCustomDamageNote("[MAGE] Arcane Burst Aktif!");
            
            // Ubah state setelah burst dipakai
            this.overcharged = false;
            this.usedBurstThisTurn = true;
            return getAttackPower() * 2.0;
        }

        // Kembalikan baseDamage jika Overcharged tidak aktif
        this.usedBurstThisTurn = false;
        return baseDamage;
    }

    @Override
    public void onTurnEnd(double result) {
        if (!this.overcharged && !this.usedBurstThisTurn){
            this.overcharged = true;
            this.overchargedCount++;
            setCustomDamageNote("[MAGE] Mana terkumpul! Overcharged untuk serangna berikutnya.");
        }
    }

    @Override
    public void resetBattleState() {
        super.resetBattleState(); // Reset konteks
        
        // Reset attribute unik pasif Mage
        this.overcharged = false;
        this.usedBurstThisTurn = false;
        this.overchargedCount = 0;
        this.burstCount = 0;
    }

    // Summary
    @Override
    public String getPassiveTriggerSummary(){
        return String.format("""
                - Overcharged terkumpul: %d kali
                - Arcane Burst aktif: %d kali""", overchargedCount, burstCount);
    }
}
