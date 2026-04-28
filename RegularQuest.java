public class RegularQuest extends Quest {

    public RegularQuest(int idNumber, String name, String description,
                        Difficulty difficulty, Monster monster, int minLevel) {
        super(idNumber, name, description, difficulty, monster, minLevel);
    }

    @Override
    public String getQuestType() {
        return "Regular";
    }

    @Override
    public String toString() {
        return super.toString() + "\nStatus: " + getStatus().getDisplayName();
    }
}