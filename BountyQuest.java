public class BountyQuest extends Quest implements Rewardable {
    private int bonusExp;
    private int bonusCoin;

    public BountyQuest(int idNumber, String name, String description,
                       Difficulty difficulty, Monster monster,
                       int minLevel, int bonusExp, int bonusCoin) {
        super(idNumber, name, description, difficulty, monster, minLevel);
        // TODO
    }

    @Override
    public String getQuestType() {
        return "Bounty";
    }

    @Override
    public int getBonusExp() { return 0; }

    @Override
    public int getBonusCoin() { return 0; }

    @Override
    public String toString() {
        // TODO
        return null;
    }
}