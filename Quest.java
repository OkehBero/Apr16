// Quest.java
public abstract class Quest implements Completable {
    private String id;
    private String name;
    private String description;
    private Difficulty difficulty;
    private Monster monster;
    private int minLevel;
    private QuestStatus status;

    public Quest(int idNumber, String name, String description,
                 Difficulty difficulty, Monster monster, int minLevel) {
        // TODO
    }

    public abstract String getQuestType();

    public int getExpReward() {
        // TODO
        return 0;
    }

    public int getCoinReward() {
        // TODO
        return 0;
    }

    protected int getBonusExp() { return 0; }
    protected int getBonusCoin() { return 0; }

    @Override
    public boolean isCompletable() { return false; }

    @Override
    public void complete() {
        // TODO
    }

    public void resetToAvailable() {
        // TODO
    }

    public String getId() { return null; }
    public String getName() { return null; }
    public Difficulty getDifficulty() { return null; }
    public Monster getMonster() { return null; }
    public int getMinLevel() { return 0; }
    public QuestStatus getStatus() { return null; }

    @Override
    public String toString() {
        // TODO
        return null;
    }
}