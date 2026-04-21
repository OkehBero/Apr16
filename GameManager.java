import java.util.ArrayList;

public class GameManager {
    private ArrayList<User> users;
    private ArrayList<Monster> monsters;
    private ArrayList<Quest> quests;
    private int currentDay;

    public GameManager() {
        this.users = new ArrayList<>();
        this.monsters = new ArrayList<>();
        this.quests = new ArrayList<>();
        this.currentDay = 1;

        this.users.add(new Admin("Burhan", "burhan", "burunghantu123")); // hardcode admin
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.authenticate(username, password)) {
                return user;
            }
        }
        return null;
    }

    public void addWanderer(Wanderer wanderer) {
        this.users.add(wanderer);
    }

    public boolean isUsernameTaken(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public int nextWandererId() {
        int count = 1;
        for (User user : users) {
            if (user instanceof Wanderer) {
                count++;
            }
        }
        return count;
    }

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
        ArrayList<User> filtered = new ArrayList<>();
        for (User user : getWanderers()) {
            Wanderer w = (Wanderer) user;
            if (w.getLevel() >= min && w.getLevel() <= max) {
                filtered.add(w);
            }
        }
        return filtered;
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
        ArrayList<User> sorted = getWanderers();
        sorted.sort((u1, u2) -> {
            int cmp = u1.getName().compareToIgnoreCase(u2.getName());
            return asc ? cmp : -cmp;
        });
        return sorted;
    }

    public ArrayList<User> sortWandererByLevel(boolean asc) {
        ArrayList<User> sorted = getWanderers();
        sorted.sort((u1, u2) -> {
            Wanderer w1 = (Wanderer) u1;
            Wanderer w2 = (Wanderer) u2;
            int cmp = Integer.compare(w1.getLevel(), w2.getLevel());
            
            // Jika levelnya sama, urutkan berdasarkan nama
            if (cmp == 0) {
                int nameCmp = w1.getName().compareToIgnoreCase(w2.getName());
                return asc ? nameCmp : -nameCmp;
            }
            return asc ? cmp : -cmp;
        });
        return sorted;
    }

    public void advanceDay() {
        // TODO: currentDay++
        // TODO: reset HP semua wanderer ke maxHp
    }

    public int getCurrentDay() { return this.currentDay; }

    public ArrayList<User> getWanderers() {
        ArrayList<User> wanderers = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Wanderer) {
                wanderers.add(user);
            }
        }
        return wanderers; 
    }

    public ArrayList<Monster> getMonsters() { return null; }

    public ArrayList<Quest> getQuests() { return null; }
}

