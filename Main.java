import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static GameManager gm = new GameManager();

    public static void main(String[] args) {
        // TODO: tampilkan banner
        System.out.println(" ____             _                    ___                  _   \n" + //
                        "| __ ) _   _ _ __| |__   __ _ _ __    / _ \\ _   _  ___  ___| |_ \n" + //
                        "|  _ \\| | | | '__| '_ \\ / _` | '_ \\  | | | | | | |/ _ \\/ __| __|\n" + //
                        "| |_) | |_| | |  | | | | (_| | | | | | |_| | |_| |  __/\\__ \\ |_ \n" + //
                        "|____/ \\__,_|_|  |_| |_|\\__,_|_| |_|  \\__\\_\\\\__,_|\\___||___/\\__|");
        // TODO: loop menu login
        System.out.println("\nSelamat datang di BurhanQuest!");

        while (true) {
            System.out.println("\n=== Hari ke-" + gm.getCurrentDay() + " ===");
            System.out.println("1. Login");
            System.out.println("0. Keluar dari program");
            System.out.print("Masukkan pilihan: ");
            String input = scanner.nextLine();

            if (input.strip().equals("1")) {
                showLoginMenu();
            } else if (input.strip().equals("0")) {
                break;
            }
        }
    }

    static void showLoginMenu() {
        System.out.print("Masukkan username: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan password: ");
        String password = scanner.nextLine();

        User loggedInUser = gm.login(username, password);

        if (loggedInUser != null) {
            System.out.println(loggedInUser.getWelcomeMessage());

            if (loggedInUser instanceof Admin) {
                showAdminMenu((Admin) loggedInUser);
            } else if (loggedInUser instanceof Wanderer) {
                showWandererMenu((Wanderer) loggedInUser);
            }
        } else {
            System.out.println("Username atau password salah.");
        }
    }

    static void showAdminMenu(Admin admin) {
        boolean login = true;

        while (login){
            System.out.println("\n=== Menu Admin (Hari ke-" + gm.getCurrentDay() + ") ===");
            System.out.println("""
                    1. Lihat daftar quest
                    2. Lihat daftar pengembara
                    3. Tambah quest
                    4. Tambah pengembara
                    5. Tambah monster
                    6. Lihat daftar monster
                    7. Filter daftar quest
                    8. Filter daftar pengembara
                    9. Tampilkan daftar quest terurut
                    10. Tampilkan daftar pengembara terurut
                    11. Lanjut ke hari berikutnya
                    0. Keluar""");
            System.out.print("Masukkan pilihan: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    System.out.println("\n=== Daftar Quest ===");
                    if (gm.getQuests().isEmpty()) {
                        System.out.println("Belum ada quest yang terdaftar.");
                    } else {
                        for (Quest q : gm.getQuests()) {
                            System.out.println(q.toString() + "\n");
                        }
                    }
                    break;
            
                case "2":
                    System.out.println("Pengembara yang terdaftar:");
                    for (User wanderer : gm.getWanderers()){
                        System.out.println(wanderer);
                        System.out.println("");
                    }
                    break;
            
                case "3":
                    if (gm.getMonsters().isEmpty()){
                        System.out.println("Monster belum ada. Tambahkan monster terlebih dahulu.");
                        break;
                    }

                    System.out.println();
                    System.out.println("=== Tanbah Quest ===");
                    System.out.print("Masukkan nama quest: ");
                    String questName = scanner.nextLine().strip();

                    System.out.print("Masukkan deskripsi quest: ");
                    String questDesc = scanner.nextLine().strip();

                    System.out.print("Masukkan tingkat kesulitan (mudah/menengah/sulit); ");
                    String questDiff = scanner.nextLine().strip();
                    Difficulty difficulty = Difficulty.fromString(questDiff);


                    System.out.println("Pilih monster:");
                    ArrayList<Monster> monsterList = gm.getMonsters();
                    for (int i = 0; i < monsterList.size(); i++){
                        System.out.println((i + 1) + ". " + monsterList.get(i).getName());
                    }
                    
                    System.out.print("Masukkan nomor monster: ");
                    int nomorMonster = Integer.parseInt(scanner.nextLine().strip());

                    if (nomorMonster < 1 || nomorMonster > monsterList.size()){
                        System.out.println("Pilihan monster tidak valid.");
                    }
                    Monster targetMoster = monsterList.get(nomorMonster-1);

                    System.out.print("""
                            Pilih tipe quest
                            1. Daily
                            2. Regular
                            3. Bounty
                            Masukkan pilihan tipe quest: """);
                    int tipeQuest = Integer.parseInt(scanner.nextLine().strip());
                    
                    int bonusExp = 0;
                    int bonusCoin = 0;

                    if (tipeQuest == 3){
                        System.out.println("Masukkan bonus EXP: ");
                        bonusExp = Integer.parseInt(scanner.nextLine().strip());

                        System.out.println("Masukkan bonus Koin: ");
                        bonusCoin = Integer.parseInt(scanner.nextLine().strip());
                    }

                    int minLevel = difficulty.getMinWandererLevel();

                    gm.addQuest(tipeQuest, questName, questDesc, difficulty, targetMoster, minLevel, bonusExp, bonusCoin);



                    System.out.println("Quest berhasil ditambahkan!");
                    break;
            
                case "4":
                    System.out.print("Masukkan nama pengembara: ");
                    String name = scanner.nextLine().strip();

                    System.out.print("Masukkan username pengembara: ");
                    String username = scanner.nextLine().strip();

                    if (gm.isUsernameTaken(username)){
                        System.out.println("Username sudah digunakan.");
                    } else{
                        System.out.print("Masukkan password pengembara: ");
                        String password = scanner.nextLine().strip();
                        System.out.print("Masukkan HP maksimal: ");
                        double maxHp = Double.parseDouble(scanner.nextLine().strip());
                        System.out.print("Masukkan attack power: ");
                        double attack = Double.parseDouble(scanner.nextLine().strip());
                        System.out.print("Masukkan defense: ");
                        double defense = Double.parseDouble(scanner.nextLine().strip());

                        Wanderer newWanderer = new Wanderer(gm.nextWandererId(), name, username, password, maxHp, attack, defense);
                        gm.addWanderer(newWanderer);
                        System.out.println("Pengembara berhasil ditambahkan!");
                    }
                    break;
            
                case "5":
                    System.out.print("Mauskkan nama monster: ");
                    String monsterName = scanner.nextLine().strip();

                    if (gm.isMosterNameTaken(monsterName)){
                        System.out.println("Nama sudah digunakan.");
                    } else {
                        System.out.print("Masukkan Hp maksimal monster: ");
                        double maxHp = Double.parseDouble(scanner.nextLine().strip());

                        System.out.print("Masukkan attack power moster: ");
                        double attackPower = Double.parseDouble(scanner.nextLine().strip());

                        System.out.print("Masukkan defense monster: ");
                        double defense = Double.parseDouble(scanner.nextLine().strip());

                        System.out.print("Masukkan exp reward monster: ");
                        int expReward = Integer.parseInt(scanner.nextLine().strip());

                        System.out.print("Masukkan coin reward monster: ");
                        int coinReward = Integer.parseInt(scanner.nextLine().strip());

                        Monster newMonster = new Monster(gm.nextMonsterId(), monsterName, maxHp, attackPower, defense, expReward, coinReward);
                        gm.addMonster(newMonster);
                        
                    }
                    break;
            
                case "6":
                    System.out.println();
                    System.out.println("=== Daftar Monster ===");

                    ArrayList<Monster> monsterList6 = gm.getMonsters();

                    if (monsterList6.isEmpty()){
                        System.out.println("Belum ada monster yang terdaftar.");
                    } else {
                        for (Monster monster : monsterList6){
                            System.out.println(monster.toString());
                        }
                    }
                    break;
            
                case "7":
                    System.out.print("Masukkan tingkat kesulitan (mudah/menengah/sulit): ");
                    String diffInputFilter = scanner.nextLine().strip();
                    Difficulty diffFilter = Difficulty.fromString(diffInputFilter);

                    if (diffFilter == null){
                        System.out.println("Tingkat kesuilitan tidak valid.");
                    } else {
                        ArrayList<Quest> filteredQuests = gm.filterQuestByDifficulty(diffFilter);
                        if (filteredQuests.isEmpty()){
                            System.out.println("Tidak ada quest dengan difficulty tersebut.");
                        } else {
                            System.out.println("Hasil filter:");
                            System.out.println();
                            for (Quest quest : filteredQuests){
                                System.out.println(quest.toString());
                                System.out.println();
                            }
                        }
                    }
                    break;
            
                case "8":
                    System.out.print("Masukkan level minimum: ");
                    int min = Integer.parseInt(scanner.nextLine());

                    System.out.print("Masukkan level maksimum: ");
                    int max = Integer.parseInt(scanner.nextLine());
                    
                    ArrayList<User> filtered = gm.filterWandererByLevel(min, max);
                    if(filtered.isEmpty()) {
                        System.out.println("Tidak ada pengembara pada rentang level tersebut.");
                    } else {
                        System.out.println("Hasil pencarian:");
                        for (User user : filtered) {
                            System.out.println(user.toString());
                            System.out.println();
                        }
                    }
                    break;
            
                case "9":
                    System.out.print("""
                            Urutkan daftar quest
                            1. Berdasarkan tingkat kesulitan
                            2. Berdasarkan reward
                            X. Kembali ke menu utama
                            Masukkan input: """);
                    String sortInput = scanner.nextLine().strip();

                    System.out.print("Masukkan order urutan (asc/desc), masukkan x untuk kembali ke menu utama: ");
                    String ascDesc = scanner.nextLine().strip();

                    if (sortInput.equalsIgnoreCase("x")){
                        break;
                    }

                    boolean asc = ascDesc.equalsIgnoreCase("asc");

                    ArrayList<Quest> sortedQuests = new ArrayList<>();
                    if (sortInput.equals("1")){
                        sortedQuests = gm.sortQuestByDifficulty(asc);
                    } else if (sortInput.equalsIgnoreCase("2")){
                        sortedQuests = gm.sortQuestByReward(asc);
                    }

                    if (sortedQuests.isEmpty()){
                        System.out.println("Belum ada quest yang terdaftar.");
                    } else {
                        System.out.println("Daftar quest terurut:");
                        for (Quest quest : sortedQuests){
                            System.out.println(quest.toString());
                        }
                    }
                    break;
            
                case "10":
                    System.out.print("""
                            Urutkan daftar pengembara
                            1. Berdasarkan nama
                            2. Berdasarkan level
                            Masukkan pilihan: """);
                    String sort10 = scanner.nextLine();
                    
                    System.out.print("Masukkan order urutan (asc/desc), masukkan x untuk kembali ke menu utama: ");
                    String ascDesc10 = scanner.nextLine().strip();
                    boolean isAsc10 = ascDesc10.equals("asc");

                    ArrayList<User> sorted10 = new ArrayList<>();
                    if (sort10.equals("1")) {
                        sorted10 = gm.sortWandererByName(isAsc10);
                    } else if (sort10.equals("2")) {
                        sorted10 = gm.sortWandererByLevel(isAsc10);
                    }
                    
                    if (sorted10.isEmpty()) {
                        System.out.println("Belum ada pengembara yang terdaftar.");
                    } else {
                        System.out.println("Daftar pengembara terurut:");
                        for (User user : sorted10) {
                            System.out.println(user.toString());
                            System.out.println();
                        }
                    }
                    break;
            
                case "11":
                    gm.advanceDay();
                    System.out.println(String.format("Hari berganti menjadi hari ke-%d", gm.getCurrentDay()));
                    break;
            
                case "0":
                    System.out.println("Logout berhasil.");
                    login = false;
                    break;
            
                default:
                    break;
            }
        }
    }

    static void showWandererMenu(Wanderer wanderer) {
        boolean login = true;

        while (login){
            System.out.println("\n=== Menu Pengembara: " + wanderer.getName() + " (Hari ke-" + gm.getCurrentDay() + ") ===");
            System.out.print("""
                    1. Lihat data diri
                    2. Lihat daftar quests
                    3. Filter daftar quest
                    4. Tampilkan daftar quest terurut
                    5. Ambil quest
                    0. Keluar
                    Masukkan pilihan:\s""");
            
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    System.out.println("\n=== Data Diri ===");
                    System.out.println(wanderer.toString());
                    break;
                
                case "2":
                    System.out.println("\n=== Daftar Quest ===");
                    if (gm.getQuests().isEmpty()) {
                        System.out.println("Belum ada quest yang terdaftar.");
                    } else {
                        for (Quest q : gm.getQuests()) {
                            System.out.println(q.toString() + "\n");
                        }
                    }
                    break;
            
                case "3":
                    System.out.print("Masukkan tingkat kesulitan (mudah/menengah/sulit): ");
                    String diffInputFilter = scanner.nextLine().strip();
                    Difficulty diffFilter = Difficulty.fromString(diffInputFilter);

                    if (diffFilter == null){
                        System.out.println("Tingkat kesuilitan tidak valid.");
                    } else {
                        ArrayList<Quest> filteredQuests = gm.filterQuestByDifficulty(diffFilter);
                        if (filteredQuests.isEmpty()){
                            System.out.println("Tidak ada quest dengan difficulty tersebut.");
                        } else {
                            System.out.println("Hasil filter:");
                            System.out.println();
                            for (Quest quest : filteredQuests){
                                System.out.println(quest.toString());
                                System.out.println();
                            }
                        }
                    }
                    break;
            
                case "4":
                    System.out.print("""
                            Urutkan daftar quest
                            1. Berdasarkan tingkat kesulitan
                            2. Berdasarkan reward
                            X. Kembali ke menu utama
                            Masukkan input: """);
                    String sortInput = scanner.nextLine().strip();

                    System.out.print("Masukkan order urutan (asc/desc), masukkan x untuk kembali ke menu utama: ");
                    String ascDesc = scanner.nextLine().strip();

                    if (sortInput.equalsIgnoreCase("x")){
                        break;
                    }

                    boolean asc = ascDesc.equalsIgnoreCase("asc");

                    ArrayList<Quest> sortedQuests = new ArrayList<>();
                    if (sortInput.equals("1")){
                        sortedQuests = gm.sortQuestByReward(asc);
                    } else if (sortInput.equalsIgnoreCase("2")){
                        sortedQuests = gm.sortQuestByDifficulty(asc);
                    }

                    if (sortedQuests.isEmpty()){
                        System.out.println("Belum ada quest yang terdaftar.");
                    } else {
                        System.out.println("Daftar quest terurut:");
                        for (Quest quest : sortedQuests){
                            System.out.println(quest.toString());
                        }
                    }
                    break;
            
                case "5":
                    ArrayList<Quest> questList = gm.getQuests();

                    System.out.println("Masukkan ID Quest yang ingin diambil (atau 'X'/'x' untuk kembali: ");
                    String idQuest = scanner.nextLine().strip();

                    if (questList.isEmpty()){
                        System.out.println("Belum ada quest yang terdaftar di sistem.");
                    }

                    Quest questTerpilih = gm.findQuestById(idQuest);
                    // TODO validasi quest ada
                    // TODO validasi quest tersedia
                    // TODO validasi quest yang diambil sudah selesai atau belom
                    // TODO validasi level minimum pengembara

                    BattleManager.simulateBattle(wanderer, questTerpilih);
                    break;
            
                case "0":
                    System.out.println("Logout berhasil.");
                    login = false;
                    break;
            
                default:
                    break;
            }
        }
    }
}