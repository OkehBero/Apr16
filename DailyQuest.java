public class DailyQuest extends Quest implements Repeatable {
    private int timesCompleted;

    public DailyQuest(int idNumber, String name, String description,
                      Difficulty difficulty, Monster monster, int minLevel) {
        super(idNumber, name, description, difficulty, monster, minLevel);
        
        this.timesCompleted = 0;
    }

    @Override
    public String getQuestType() {
        return "Daily";
    }

    @Override
    public void complete() {
        this.timesCompleted++;
    }

    @Override
    public void reset() {
        this.timesCompleted = 0;
    }

    @Override
    public int getTimesCompleted() { return this.timesCompleted; }

    @Override
    protected int getBonusExp() {
        return 0;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("""
                Status: %s
                Sudah Diselesaikan: %d kali
                """, 
                getStatus().getDisplayName(),
                this.timesCompleted);
    }
}