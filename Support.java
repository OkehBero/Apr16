public class Support extends Wanderer {

    // Attribute
    private int healCount = 0;
    private double totalHeal = 0.0;
    
    // Constructor
    public Support(int idNumber, String name, String username,
                   String password, double  maxHp, double  attack, double  defense) {
        // Inisiasi attribute dari parrent class Wanderer
        super(idNumber, name, username, password, maxHp, attack, defense);
    }

    @Override
    public void onTurnStart() {
        super.onTurnStart();

        // Inisiasi dan mendapatkan nilai heal
        double heal = getMaxHp() * 0.10;
        double maxHeal = getMaxHp() - getCurrentHp();

        // Heal tidak akan membuat hp > maxHp
        if (heal > maxHeal){ heal = maxHeal; }

        // Mekanisme pasif Support akan aktif jika heal > 0
        if (heal > 0){
            setCurrentHp(getCurrentHp() + heal);
            this.healCount++;
            this.totalHeal += heal;
            setCustomDamageNote(String.format("[SUPPORT]: %s memulihkan %.2f HP!", getName(), heal));
        }
        
    }

    @Override
    public void resetBattleState(){
        super.resetBattleState(); // Reset battle context

        // Reset attribute pasif unik support
        this.healCount = 0;
        this.totalHeal = 0.0;
    }

    // Summary
    @Override
    public String getPassiveTriggerSummary(){
        return String.format("""
                - Heal aktif: %d kali
                - Total HP dipulikan: %.2f""",
                this.healCount,
                this.totalHeal);
    }

}
