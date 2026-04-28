public class BountyQuest extends Quest implements Rewardable {
    private int bonusExp;
    private int bonusCoin;

    public BountyQuest(int idNumber, String name, String description,
                       Difficulty difficulty, Monster monster,
                       int minLevel, int bonusExp, int bonusCoin) {
        super(idNumber, name, description, difficulty, monster, minLevel);
        
        this.bonusExp = bonusExp;
        this.bonusCoin = bonusCoin;
    }

    @Override
    public String getQuestType() {
        return "Bounty";
    }

    @Override
    public int getBonusExp() { return this.bonusExp; }

    @Override
    public int getBonusCoin() { return this.bonusCoin; }

@Override
    public String toString() {
        return super.toString() + "\n" + String.format("""
                Bonus Koin: %d
                Bonus Exp: %d
                Status: %s""",
                this.getBonusCoin(),
                this.getBonusExp(),
                getStatus().getDisplayName());
    }
}