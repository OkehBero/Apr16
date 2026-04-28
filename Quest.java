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

        this.id = "Q" + idNumber;
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.monster = monster;
        this.minLevel = minLevel;
        this.status = QuestStatus.TERSEDIA;
    }

    public abstract String getQuestType();

    public int getExpReward() {
        return this.monster.getExpReward() + getBonusExp();
    }

    public int getCoinReward() {
        return this.monster.getCoinReward() + getBonusCoin();
    }

    protected int getBonusExp() { return 0; }
    protected int getBonusCoin() { return 0; }

    @Override
    public boolean isCompletable() {
        return this.status == QuestStatus.TERSEDIA;
    }

    @Override
    public void complete() {
        this.status = QuestStatus.SELESAI;
    }

    public void resetToAvailable() {
        this.status = QuestStatus.TERSEDIA;
    }

    public String getId() { return this.id; }
    public String getName() { return this.name; }
    public Difficulty getDifficulty() { return this.difficulty; }
    public Monster getMonster() { return this.monster; }
    public int getMinLevel() { return this.minLevel; }
    public QuestStatus getStatus() { return this.status; }

    @Override
    public String toString() {
        return String.format("""
                ID Quest: %s
                Nama Quest: %s
                Tipe Quest: %s
                Deskripsi Quest: %s
                Tingkat Kesulitan: %s
                Monster: %s
                Level Minimum: %d
                Reward Koin: %d
                Reward Exp: %d
                """,
                this.getId(),
                this.getName(),
                this.getQuestType(),
                this.description,
                this.getDifficulty().getDisplayName(),
                this.getMonster().getName(),
                this.getMinLevel(),
                this.getMonster().getCoinReward(),
                this.getMonster().getExpReward());
    }
}