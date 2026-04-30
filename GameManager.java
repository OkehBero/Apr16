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

    public void addWanderer(int idNumber, String name, String username,
                    String password, double maxHp, double attack, double defense, String jobClass)
                    throws DuplicateWandererException, IllegalArgumentException {
        
        if (isUsernameTaken(username)){
            throw new DuplicateWandererException("Username sudah digunakan.");
        }
        
        if (maxHp <= 0){
            throw new IllegalArgumentException("HP maksimal harus bilangan positif");
        }

        if (attack <= 0){
            throw new IllegalArgumentException("Attack harus bilangan positif.");
        }

        if (defense <= 0) {
            throw new IllegalArgumentException("Defense harus bilangan positif.");
        }


        Wanderer wanderer = switch (jobClass) {
            case "1" -> new Wanderer(nextWandererId(), name, username, password, maxHp, attack, defense);
            case "2" -> new Tank(nextWandererId(), name, username, password, maxHp, attack, defense);
            case "3" -> new Mage(nextWandererId(), name, username, password, maxHp, attack, defense);
            case "4" -> new Assassin(nextWandererId(), name, username, password, maxHp, attack, defense);
            case "5" -> new Fighter(nextWandererId(), name, username, password, maxHp, attack, defense);
            case "6" -> new Support(nextWandererId(), name, username, password, maxHp, attack, defense);

        
            default -> throw new IllegalArgumentException("Input job class tidak valid.");
        };
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

    public boolean isMosterNameTaken(String name){
        for (Monster monster : monsters){
            if (monster.getName().equals(name)){
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

    public void addMonster(Monster monster) throws IllegalArgumentException{
        if (isMosterNameTaken(monster.getName())){
            throw new IllegalArgumentException("Nama monster sudah digunakan.");
        }

        if (monster.getMaxHp() <= 0){
            throw new IllegalArgumentException("HP maksimal harus bilangan positif.");
        }

        if (monster.getAttackPower() <= 0){
            throw new IllegalArgumentException("Attak harus bilangan positif.");
        }

        if (monster.getDefense() <= 0){
            throw new IllegalArgumentException("Defense harus bilangan positif.");
        }

        this.monsters.add(monster);
    }

    public int nextMonsterId() {
        return this.monsters.size()+1;
    }

    public void addQuest(Quest quest) {
        this.quests.add(quest);
    }

    public void addQuest(int questType, String name, String description, Difficulty difficulty, Monster monster, int minLevel, int bonusExp, int bonusCoin) throws IllegalArgumentException{
        int questId = nextQuestId();

        if (difficulty == null){
            throw new IllegalArgumentException("Input difficulty tidak valid");
        }

        switch (questType) {
            case 1 -> addQuest(new RegularQuest(questId, name, description, difficulty, monster, minLevel));
            case 2 -> addQuest(new DailyQuest(questId, name, description, difficulty, monster, minLevel));
            case 3 -> addQuest(new BountyQuest(questId, name, description, difficulty, monster, minLevel, bonusExp, bonusCoin));

            default -> throw new IllegalArgumentException("Input tipe quest tidak valid.");
        };
    }

    public int nextQuestId() {
        return this.quests.size()+1;
    }

    public Quest findQuestById(String id) throws IllegalArgumentException{
        for (Quest quest : this.quests){
            if (quest.getId().equalsIgnoreCase(id.strip())){
                return quest;
            }
        }
        throw new IllegalArgumentException("Quest tidak ditemukan di sistem.");
    }

    public Quest takeQuestValidation(Wanderer wanderer, String idQuest) throws IllegalArgumentException, IllegalStateException, InsufficientLevelException {
        Quest quest = findQuestById(idQuest);
        
        if (quest.getStatus() != QuestStatus.TERSEDIA){
            throw new IllegalStateException("Quest tidak tersedia atau sudah selesai.");
        }

        if (wanderer.getLevel() < quest.getDifficulty().getMinWandererLevel()){
            throw new InsufficientLevelException("Level pengembara tidak cukup.");
        }

        return quest;
    }

    public Monster findMonsterById(String id) throws IllegalArgumentException{
        for (Monster monster : this.monsters){
            if (String.valueOf(monster.getMonsterId()).equalsIgnoreCase(id.strip())){
                return monster;
            }
        }

        throw new IllegalArgumentException("Nomor monster tidak valid." );
    }

    public ArrayList<Quest> filterQuestByDifficulty(Difficulty difficulty) throws IllegalArgumentException{
        if (difficulty == null){
            throw new IllegalArgumentException("Input difficulty tidak valid.");
        }
        
        ArrayList<Quest> filtered = new ArrayList<>();
        for (Quest q : this.quests) {
            if (q.getDifficulty() == difficulty) {
                filtered.add(q);
            }
        }
        return filtered;
    }

    public ArrayList<User> filterWandererByLevel(int min, int max) throws IllegalArgumentException {
        if (min < 0 || max < min){
            throw new IllegalArgumentException("Range level tidak valid.");
        }
        
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
        ArrayList<Quest> sorted = new ArrayList<>(this.quests);
        sorted.sort((quest1, quest2) -> {
            int compare = Integer.compare(quest1.getExpReward(), quest2.getExpReward());
            if (compare == 0) {
                // Jika reward sama, urutkan berdasarkan nama
                int nameCompare = quest1.getName().compareToIgnoreCase(quest2.getName());
                return asc ? nameCompare : -nameCompare;
            }
            return asc ? compare : -compare;
        });
        return sorted;
    }

    public ArrayList<Quest> sortQuestByDifficulty(boolean asc) {
        ArrayList<Quest> sorted = new ArrayList<>(this.quests);
        sorted.sort((quest1, quest2) -> {
            // Urutkan berdasarkan level minimum tingkat kesulitan
            int compare = Integer.compare(quest1.getDifficulty().getMinWandererLevel(), quest2.getDifficulty().getMinWandererLevel());
            if (compare == 0) {
                int nameCompare = quest1.getName().compareToIgnoreCase(quest2.getName());
                return asc ? nameCompare : -nameCompare;
            }
            return asc ? compare : -compare;
        });
        return sorted;
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
        this.currentDay++;

        for (User user : getWanderers()){
            Wanderer wanderer = (Wanderer) user;
            wanderer.setCurrentHp(wanderer.getMaxHp());
        }

        for (Quest quest: quests){
            if (quest instanceof Repeatable){
                ((Repeatable) quest).reset();
            }
        }
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

    public ArrayList<Monster> getMonsters() {
        return this.monsters;
    }

    public ArrayList<Quest> getQuests() {
        return this.quests;
    }
}

