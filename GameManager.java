import java.util.ArrayList;

public class GameManager {
    private ArrayList<User> users;
    private ArrayList<Monster> monsters;
    private ArrayList<Quest> quests;
    private int currentDay;

    public GameManager() {
        // TODO
    }

    public User login(String username, String password) {
        // TODO
        return null;
    }

    public void addWanderer(Wanderer wanderer) {
        // TODO
    }

    public boolean isUsernameTaken(String username) {
        // TODO
        return false;
    }

    public int nextWandererId() { return 0; }

    public void addMonster(Monster monster) {
        // TODO
    }

    public int nextMonsterId() { return 0; }

    public void addQuest(Quest quest) {
        // TODO
    }

    public int nextQuestId() { return 0; }

    public Quest findQuestById(String id) {
        // TODO
        return null;
    }

    public Monster findMonsterById(String id) {
        // TODO
        return null;
    }

    public ArrayList<Quest> filterQuestByDifficulty(Difficulty difficulty) {
        // TODO
        return null;
    }

    public ArrayList<User> filterWandererByLevel(int min, int max) {
        // TODO
        return null;
    }

    public ArrayList<Quest> sortQuestByReward(boolean asc) {
        // TODO
        return null;
    }

    public ArrayList<Quest> sortQuestByDifficulty(boolean asc) {
        // TODO
        return null;
    }

    public ArrayList<User> sortWandererByName(boolean asc) {
        // TODO
        return null;
    }

    public ArrayList<User> sortWandererByLevel(boolean asc) {
        // TODO
        return null;
    }

    public void advanceDay() {
        // TODO: currentDay++
        // TODO: reset HP semua wanderer ke maxHp
    }

    public int getCurrentDay() { return 0; }
    public ArrayList<User> getWanderers() { return null; }
    public ArrayList<Monster> getMonsters() { return null; }
    public ArrayList<Quest> getQuests() { return null; }
}

