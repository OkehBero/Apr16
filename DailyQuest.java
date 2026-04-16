public class DailyQuest extends Quest implements Repeatable {
    private int timesCompleted;

    public DailyQuest(int idNumber, String name, String description,
                      Difficulty difficulty, Monster monster, int minLevel) {
        super(idNumber, name, description, difficulty, monster, minLevel);
        // TODO
    }

    @Override
    public String getQuestType() {
        return "Daily";
    }

    @Override
    public void complete() {
        // TODO: timesCompleted++ lalu reset()
    }

    @Override
    public void reset() {
        // TODO
    }

    @Override
    public int getTimesCompleted() { return 0; }

    @Override
    protected int getBonusExp() {
        // TODO
        return 0;
    }

    @Override
    public String toString() {
        // TODO
        return null;
    }
}