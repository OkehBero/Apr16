public class Support extends Wanderer {

    private int healCount = 0;
    private double totalHeal = 0.0;
    public Support(int idNumber, String name, String username,
                   String password, double  maxHp, double  attack, double  defense) {
        super(idNumber, name, username, password, maxHp, attack, defense);
    }

    @Override
    public void onTurnStart() {
        super.onTurnStart();
        double heal = getMaxHp() * 0.10;
        double maxHeal = getMaxHp() - getCurrentHp();

        if (heal > maxHeal){
            heal = maxHeal;
        }

        if (heal > 0){
            setCurrentHp(getCurrentHp() + heal);
            this.healCount++;
            this.totalHeal += heal;
            setCustomDamageNote(String.format("[SUPPORT]: %s memulihkan %.2f HP!", getName(), heal));
        }
        
    }

    @Override
    public void resetBattleState(){
        super.resetBattleState();
        this.healCount = 0;
        this.totalHeal = 0.0;
    }

    @Override
    public String getPassiveTriggerSummary(){
        return String.format("""
                - Heal aktif: %d kali
                - Total HP dipulikan: %.2f""",
                this.healCount,
                this.totalHeal);
    }

}
