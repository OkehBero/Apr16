import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Wanderer extends User implements Combatant {
    private String id;
    private int level;
    private int exp;
    private int coins;
    private double currentHp;
    private double maxHp;
    private double attack;
    private double defense;
    private Monster currentTarget;
    private double currentMultiplier;
    private String customDamageNote;

    public Wanderer(int idNumber, String name, String username,
                    String password, double maxHp, double attack, double defense) {
        super(name, username, password);

        this.id = "P" + idNumber;
        this.maxHp = maxHp;
        this.attack = attack;
        this.defense = defense;

        this.level = 1;
        this.exp = 0;
        this.coins = 0;
        this.currentHp = maxHp;
    }

    public boolean canTakeQuest(Difficulty difficulty) {
        if (difficulty == Difficulty.MENENGAH) return this.level >= 6;
        if (difficulty == Difficulty.SULIT) return this.level >= 16;
        return true;
    }

    public void addExp(int amount) {
        this.exp += amount;
        if (this.exp > 1310720000){
            this.exp = 1310720000;
        }
    }

    public void addCoins(int amount) {
        this.coins += amount;
    }

    public int getNextLevelExp(int currentLevel) {
        if (currentLevel == 1){
            return 5000;
        }
        return 2 * getNextLevelExp(currentLevel -1);
    }

    public void setCurrentHp(double hp) {
        this.currentHp = hp;
        
        if (this.currentHp > this.maxHp) {
            this.currentHp = this.maxHp;
        } else if (this.currentHp < 0) {
            this.currentHp = 0;
        }
    }

    public double getCurrentHp() {
        return this.currentHp;

    }
    
    void setBattleContext(Monster target, double multiplier) {
        // TODO
    }

    Monster getCurrentTarget() {
        // TODO
        return null;
    }

    double getCurrentMultiplier() {
        // TODO
        return 0;
    }

    void setCustomDamageNote(String customDamageNote) {
        // TODO
    }

    String consumeCustomDamageNote() {
        // TODO
        return null;
    }

    @Override
    public String getWelcomeMessage() {
        return "Login berhasil! Selamat datang, " + getName() + ".";
    }

    // Implementasi Combatant
    @Override
    public String getName() { return super.getName(); }

    @Override
    public double getAttackPower() { return this.attack; }

    @Override
    public double getDefense() { return this.defense; }

    @Override
    public void takeDamage(double damage) {
        // TODO
    }

    // Implementasi mekanisme pertempuran
    public void onTurnStart() {
    }

    public double modifyDamageDealt(double baseDamage) {
        // TODO
        return 0;
    }

    public double modifyDamageTaken(double incomingDamage) {
        // TODO
        return 0;
    }

    public void onTurnEnd(double result) {
    }

    public void resetBattleState() {
        // TODO
    }


    @Override
    public boolean isDefeated() { return false; }

    @Override
    public String getCombatInfo() { return null; }

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

    public String getId() {
        return this.id;
    }

    public int getLevel() {
        return this.level;
    }

    public String getNameForSorting() {
        return getName();
    }
}