import java.util.ArrayList;

public class GameManager {
    // === Attribute ===
    private ArrayList<User> users;
    private ArrayList<Monster> monsters;
    private ArrayList<Quest> quests;
    private int currentDay;

    // === Constructor ===
    public GameManager() {
        this.users = new ArrayList<>();
        this.monsters = new ArrayList<>();
        this.quests = new ArrayList<>();
        this.currentDay = 1;

        this.users.add(new Admin("Burhan", "burhan", "burunghantu123")); // hardcode admin
    }

    // Method Login
    public User login(String username, String password) {
        for (User user : users) {
            if (user.authenticate(username, password)) {
                return user;
            }
        }
        return null;
    }

    // Method menambahkan wanderer
    public void addWanderer(int idNumber, String name, String username,
                    String password, double maxHp, double attack, double defense, String jobClass)
                    throws DuplicateWandererException, IllegalArgumentException { // throw exception
        
        if (isUsernameTaken(username)){ // Cek apakah username sudah dipakai
            throw new DuplicateWandererException(String.format("Username '%s' sudah digunakan.", username));
        }

        if (name == null || name.isEmpty()){
            throw new IllegalArgumentException("Input nama tidak boleh kosong.");
        }
        
        if (maxHp <= 0){ // validasi input maxHp
            throw new IllegalArgumentException("HP maksimal harus bilangan positif.");
        }

        if (attack <= 0){  // validasi input attack
            throw new IllegalArgumentException("Attack harus bilangan positif.");
        }

        if (defense <= 0) { // validasi input defense
            throw new IllegalArgumentException("Defense harus bilangan positif.");
        }

        // Memilih job class dan validasi job class
        Wanderer wanderer = switch (jobClass) {
            case "1" -> new Wanderer(nextWandererId(), name, username, password, maxHp, attack, defense);
            case "2" -> new Tank(nextWandererId(), name, username, password, maxHp, attack, defense);
            case "3" -> new Mage(nextWandererId(), name, username, password, maxHp, attack, defense);
            case "4" -> new Assassin(nextWandererId(), name, username, password, maxHp, attack, defense);
            case "5" -> new Fighter(nextWandererId(), name, username, password, maxHp, attack, defense);
            case "6" -> new Support(nextWandererId(), name, username, password, maxHp, attack, defense);

        
            default -> throw new IllegalArgumentException("Input job class tidak valid."); // throw ini jika input user tidak valid
        };
        this.users.add(wanderer); // tambahkan wanderer jika tidak ada error
    }

    // Method untuk mnengecek apakah username sudah diapakai
    public boolean isUsernameTaken(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    // Method untuk mengecek apakah nama monster sudah terpakai
    public boolean isMosterNameTaken(String name){
        for (Monster monster : monsters){
            if (monster.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    // Method untuk mendapatkan Id wanderer berikutnya
    public int nextWandererId() {
        int count = 1;
        for (User user : users) {
            if (user instanceof Wanderer) {
                count++;
            }
        }
        return count;
    }

    // Method untuk menambahkan Monster
    public void addMonster(Monster monster) throws IllegalArgumentException{ 

        if (isMosterNameTaken(monster.getName())){ // validasi apakah nama monster sudah digunakan
            throw new IllegalArgumentException(String.format("Nama monster '%s' sudah digunakan.", monster.getName()));
        }

        if (monster.getName() == null || monster.getName().isBlank()){
            throw new IllegalArgumentException("Nama monster tidak boleh kosong");
        }

        if (monster.getMaxHp() <= 0){ // validasi input maxHp monster
            throw new IllegalArgumentException("HP maksimal harus bilangan positif.");
        }

        if (monster.getAttackPower() <= 0){ // validasi input attack monster
            throw new IllegalArgumentException("Attak harus bilangan positif.");
        }

        if (monster.getDefense() <= 0){ // validasi input defense monster
            throw new IllegalArgumentException("Defense harus bilangan positif.");
        }

        if (monster.getCoinReward() < 0){
            throw new IllegalArgumentException("Reward koin harus bilangan positif");
        }

        if (monster.getExpReward() < 0){
            throw new IllegalArgumentException("Reward exp harus bilangan positif");
        }

        this.monsters.add(monster); // tambahkan monster jika tidak ada error
    }

    // Method untuk mendapatkan Id monster berikutnya
    public int nextMonsterId() {
        return this.monsters.size()+1;
    }

    // Method untuk menambahkan quest
    public void addQuest(Quest quest) {
        this.quests.add(quest);
    }

    // Method untuk menambahkan quest
    public void addQuest(int questType, String name, String description, Difficulty difficulty, Monster monster, int minLevel, int bonusExp, int bonusCoin) throws IllegalArgumentException{
        int questId = nextQuestId(); // Untuk mendapatkan Id quest untuk quest saat ini

        if (difficulty == null){ // validasi input difficulty
            throw new IllegalArgumentException("Input difficulty tidak valid");
        }

        if (name == null || name.isEmpty()){
            throw new IllegalArgumentException("Input nama tidak boleh kosong");
        }

        if (bonusExp < 0) {throw new IllegalArgumentException("Input bonus exp tidak boleh negatif.");}
        if (bonusCoin < 0) {throw new IllegalArgumentException("Input bonus koin tidak boleh negatif.");}

        // validasi input tipe quest sekaligus menambahkan quest
        switch (questType) {
            case 1 -> addQuest(new DailyQuest(questId, name, description, difficulty, monster, minLevel));
            case 2 -> addQuest(new RegularQuest(questId, name, description, difficulty, monster, minLevel));
            case 3 -> addQuest(new BountyQuest(questId, name, description, difficulty, monster, minLevel, bonusExp, bonusCoin));

            default -> throw new IllegalArgumentException("Input tipe quest tidak valid.");
        };
    }

    // Method untuk mendapatkan Id Quest berikutnya
    public int nextQuestId() {
        return this.quests.size()+1;
    }

    // Method untuk mendapatkan quest berdasarkan Id
    public Quest findQuestById(String id) throws IllegalArgumentException{
        for (Quest quest : this.quests){
            if (quest.getId().equalsIgnoreCase(id.strip())){
                return quest; // return quest jika quest ditemukan
            }
        }
        throw new IllegalArgumentException("Quest tidak ditemukan di sistem."); // validasi jika quest tidak ditemukan
    }

    // Method untuk take Quest
    public Quest takeQuestValidation(Wanderer wanderer, String idQuest) throws IllegalArgumentException, IllegalStateException, InsufficientLevelException {
        Quest quest = findQuestById(idQuest);
        
        if (quest.getStatus() != QuestStatus.TERSEDIA){ // validasi apakah quest tersedia un
            throw new IllegalStateException("Quest tidak tersedia atau sudah selesai.");
        }

        if (wanderer.getLevel() < quest.getDifficulty().getMinWandererLevel()){ // validasi apakah level pengembara memenuhi syarat
            throw new InsufficientLevelException("Level pengembara tidak cukup.");
        }

        return quest;
    }

    //  Method untuk mendapatkan objek monster dari Id
    public Monster findMonsterById(String id) throws IllegalArgumentException{
        for (Monster monster : this.monsters){
            if (String.valueOf(monster.getMonsterId()).equalsIgnoreCase("M"+id.strip())){
                return monster; // return objek monster jika dapat
            }
        }

        throw new IllegalArgumentException("Nomor monster tidak valid." ); // validasi input Id monster
    }

    // Method untuk memfilter quest berdasarkan difficulty
    public ArrayList<Quest> filterQuestByDifficulty(Difficulty difficulty) throws IllegalArgumentException{
        if (difficulty == null){ // validasi input difficulty
            throw new IllegalArgumentException("Input difficulty tidak valid.");
        }
        
        ArrayList<Quest> filtered = new ArrayList<>(); 
        for (Quest q : this.quests) {
            if (q.getDifficulty() == difficulty) {
                filtered.add(q); // Tambahkan jika sesuai kriteria
            }
        }
        return filtered; // return quest yang sudah ter-sort
    }

    // Method untuk filter wanderer berdasarkan leevel
    public ArrayList<User> filterWandererByLevel(int min, int max) throws IllegalArgumentException {
        if (min < 0 || max < min){ // Validasi input rentang level
            throw new IllegalArgumentException("Range level tidak valid.");
        }
        
        ArrayList<User> filtered = new ArrayList<>();
        for (User user : getWanderers()) {
            Wanderer w = (Wanderer) user;
            if (w.getLevel() >= min && w.getLevel() <= max) {
                filtered.add(w); // Tampung ke suatu variabel jika memenuhi kriteria
            }
        }
        return filtered; // return wanderer yang sudah di filter
    }

    // Method untuk men-sort quest berdasarkan reward
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

    // Method untuk men-sort Quest berdasrkan difficulty
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

    // Mehtod untuk men-sort Wanderer berdasarkan nama
    public ArrayList<User> sortWandererByName(boolean asc) {
        ArrayList<User> sorted = getWanderers();
        sorted.sort((u1, u2) -> {
            // Urutkan berdaarkan nama
            int cmp = u1.getName().compareToIgnoreCase(u2.getName());
            return asc ? cmp : -cmp;
        });
        return sorted;
    }

    // Method untuk mengurutkan Wanderer berdasarkan level
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

    // Logika next day
    public void advanceDay() {
        this.currentDay++; // menambahkan currentDay dengan 1

        // Men-set hp wanderer ke maxHp masing-masing
        for (User user : getWanderers()){
            Wanderer wanderer = (Wanderer) user;
            wanderer.setCurrentHp(wanderer.getMaxHp());
        }

        // Me-reset daily quest
        for (Quest quest: quests){
            if (quest instanceof Repeatable){
                ((Repeatable) quest).reset();
            }
        }
    }

    // Getter attribute currentDay
    public int getCurrentDay() { return this.currentDay; }

    // Method untuk menampilkan wanderer
    public ArrayList<User> getWanderers() {
        ArrayList<User> wanderers = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Wanderer) {
                wanderers.add(user);
            }
        }
        return wanderers; 
    }

    // method untuk menampilkan monster
    public ArrayList<Monster> getMonsters() {
        return this.monsters;
    }

    // method untuk nenampilkan quest
    public ArrayList<Quest> getQuests() {
        return this.quests;
    }
}

