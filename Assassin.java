public class Assassin extends Wanderer {
    private int bleedDiTerapkanCount = 0;

    public Assassin(int idNumber, String name, String username,
                    String password, double  maxHp, double  attack, double  defense) {
        super(idNumber, name, username, password, maxHp, attack, defense);
    }

    @Override
    public double modifyDamageDealt(double baseDamage) {
        if (Math.random() < 0.5){
            Monster targetMonster = getCurrentTarget();
            if (targetMonster != null){
                this.bleedDiTerapkanCount++;
                System.out.println("[ASSASISIN] " + targetMonster.getName() + " terkena Bleed!");
                targetMonster.applyBleed(this.getName(), getAttackPower() * 0.20);
                
            }
        }
        return baseDamage;
    }

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
