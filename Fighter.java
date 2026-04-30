public class Fighter extends Wanderer {

    // === Attribute ===
    private int furyStacks; // Mekanisme unik fighter
    private int stackICount = 0;
    private int stackIICount = 0;
    private int stackIIICount = 0;
    private int resetStackCount = 0;

    // === Constructor
    public Fighter(int idNumber, String name, String username,
                   String password, double  maxHp, double attack, double defense) {
        super(idNumber, name, username, password, maxHp, attack, defense);
        this.furyStacks = 0;
    }

    @Override
    public double modifyDamageDealt(double baseDamage) {
        if (this.furyStacks > 0){

            // Penentuan stack dan bonus damage
            String stacks;
            if (this.furyStacks == 1){ this.stackICount++; stacks = "I"; }
            else if (this.furyStacks == 2){ this.stackIICount++; stacks = "II"; }
            else { this.stackIIICount++; stacks = "III"; }

            // Perhitungan damage akhir berdasarkan stack
            int persenan = this.furyStacks * 10;
            setCustomDamageNote("[FIGHTER] Fury Stacks (" + stacks + ") aktif, Damage bertambah sebesar " + persenan + "% ATK!");
            return baseDamage + (getAttackPower() * persenan / 100.0);
        }
        return baseDamage;
    }

    @Override
    public void onTurnEnd(double result) {
        // Menambahkan stack di akhir turn
        if (this.furyStacks == 3){
            this.furyStacks = 0;
            this.resetStackCount++;
            setCustomDamageNote("[FIGHTER] Stacks full, reset ke 0!");
        } else {
            this.furyStacks++;
            setCustomDamageNote("[FIGHTER] Stacks bertambah!");
        }
    }

    @Override
    public void resetBattleState() {
        super.resetBattleState(); // Reset konteks

        // Reset atrbut pasif unik fighter
        this.furyStacks = 0;
        this.stackICount = 0;
        this.stackIICount = 0;
        this.stackIIICount = 0;
        this.resetStackCount = 0;
    }

    // Battle Summary
    @Override
    public String getPassiveTriggerSummary(){
        return String.format("""
                - Fury Stacks I aktif: %d kali
                - Fury Stacks II aktif: %d kali
                - Fury Stacks III aktif: %d kali
                - Reset Fury Stack: %d kali""",
                stackICount,
                stackIICount,
                stackIIICount,
                resetStackCount);
    }
}
