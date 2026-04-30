import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Wanderer extends User implements Combatant {
    
    // === Attribute dasar ===
    private String id;
    private int level;
    private int exp;
    private int coins;
    private double currentHp;
    private double maxHp;
    private double attack;
    private double defense;

    // === Attribute battle ===
    private Monster currentTarget;
    private double currentMultiplier;
    private String customDamageNote;

    // === Constructor ===
    public Wanderer(int idNumber, String name, String username,
                    String password, double maxHp, double attack, double defense) {
        // inisiasi attribute dari parrent class User
        super(name, username, password);

        this.id = "P" + idNumber;
        this.maxHp = maxHp;
        this.attack = attack;
        this.defense = defense;

        // inisiasi status awal
        this.level = 1;
        this.exp = 0;
        this.coins = 0;
        this.currentHp = maxHp;
    }

    // === Setter dan getter methods ===

    public String getId() { return this.id; }
    public int getLevel() { return this.level; }
    public String getNameForSorting() { return getName(); }
    public double getCurrentHp() { return this.currentHp; }
    public double getMaxHp() { return this.maxHp; }

    public void setCurrentHp(double hp) {
        this.currentHp = hp;
    
        if (this.currentHp > this.maxHp) { this.currentHp = this.maxHp; }
        else if (this.currentHp < 0) { this.currentHp = 0; }
    }

    // Sistem

    public void addExp(int amount) {
        this.exp += amount;
        // Menetapkan maksimal EXP yang bisa didapatkan pengembara
        if (this.exp > 1310720000) { this.exp = 1310720000; }

        // Level up looping
        while (this.level < 20 && this.exp >= getNextLevelExp(this.level)) {
            this.level++;
            
            this.maxHp += 10.0;
            this.attack += 2.0;
            this.defense += 2.0;
            
            // Memulihkan HP hingga penuh saat level up
            this.currentHp = this.maxHp;
        }

    }

    public void addCoins(int amount) { this.coins += amount; }

    public int getNextLevelExp(int currentLevel) {
        if (currentLevel == 1) { return 5000; }
        return 2 * getNextLevelExp(currentLevel -1);
    }

    // === Taking Quest ===
    public boolean canTakeQuest(Difficulty difficulty) {
        if (difficulty == Difficulty.MENENGAH) return this.level >= 6;
        if (difficulty == Difficulty.SULIT) return this.level >= 16;
        return true;
    }

    // === State dan Lifecycle Battle ===

    public void resetBattleState() {
        this.currentTarget = null;
        this.currentMultiplier = 0;
        this.customDamageNote = null;
    }

    void setBattleContext(Monster target, double multiplier) {
        this.currentTarget = target;
        this.currentMultiplier = multiplier;
    }

    Monster getCurrentTarget() { return this.currentTarget; }
    double getCurrentMultiplier() { return this.currentMultiplier; }

    void setCustomDamageNote(String customDamageNote) { this.customDamageNote = customDamageNote; }

    String consumeCustomDamageNote() {
        String x = this.customDamageNote;
        this.customDamageNote = null;
        return x;  
    }

    // === METHOD YANG DI OVERRIDE SUBCLASS JOB

    public void onTurnStart() { }

    public double modifyDamageDealt(double baseDamage) { return baseDamage; }

    public double modifyDamageTaken(double incomingDamage) { return incomingDamage; }

    public void onTurnEnd(double result) { }

    public String getPassiveTriggerSummary(){ return ""; }


    // === Implementasi Interface (Combatant) ===

    // Implementasi Combatant
    @Override
    public String getName() { return super.getName(); }

    @Override
    public double getAttackPower() { return this.attack; }

    @Override
    public double getDefense() { return this.defense; }

    @Override
    public void takeDamage(double damage) {
        this.currentHp -= damage;
        if (this.currentHp < 0){
            this.currentHp = 0;
        }
    }

    @Override
    public boolean isDefeated() { return this.currentHp <= 0; }

    @Override
    public String getCombatInfo() {
        return String.format("%s | HP: %.2f/%.2f | ATK: %.2f | DEF: %.2f", this.getName(), this.currentHp, this.maxHp, this.getAttackPower(), this.getDefense());
    }

    // =============================================
    @Override
    public String getWelcomeMessage() {
        return "Login berhasil! Selamat datang, " + getName() + ".";
    }

    @Override
    public String toString() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df = new DecimalFormat("#.##", symbols);
        String output = """
                    ID Pengembara: %s
                    Nama Pengembara: %s
                    Username: %s
                    Level Pengembara: %d
                    Exp Pengembara: %d poin exp
                    Koin Didapatkan: %d koin
                    HP: %s/%s
                    Attack: %s | Defense: %s"""
                    .formatted(
                        this.id, 
                        getName(), 
                        getUsername(), 
                        this.level, 
                        this.exp, 
                        this.coins, 
                        df.format(this.currentHp), 
                        df.format(this.maxHp), 
                        df.format(this.attack), 
                        df.format(this.defense)
                    );
        return output;
    }

}