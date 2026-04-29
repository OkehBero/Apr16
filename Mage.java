public class Mage extends Wanderer {
    private boolean overcharged;
    private boolean usedBurstThisTurn;
    private int overchargedCount = 0;
    private int burstCount = 0;

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
        if (this.overcharged) {
            this.burstCount++;
            System.out.println("[MAGE] Arcane Burst Aktif!");
            this.overcharged = false;
            this.usedBurstThisTurn = true;
            return getAttackPower() * 2.0;
        }
        this.usedBurstThisTurn = false;
        return baseDamage;
    }

    @Override
    public void onTurnEnd(double result) {
        if (!this.overcharged && !this.usedBurstThisTurn){
            this.overcharged = true;
            this.overchargedCount++;
            System.out.println("[MAGE] Mana terkumpul! Overcharged untuk serangna berikutnya.");
        }
    }

    @Override
    public void resetBattleState() {
        super.resetBattleState();;
        this.overcharged = false;
        this.usedBurstThisTurn = false;
        this.overchargedCount = 0;
        this.burstCount = 0;
    }

    @Override
    public String getPassiveTriggerSummary(){
        return String.format("""
                - Overcharged terkumpul: %d kali
                - Arcane Burst aktif: %d kali""", overchargedCount, burstCount);
    }
}
