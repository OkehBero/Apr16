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
            // Memanggil metode getWelcomeMessage() secara polimorfik
            System.out.println(loggedInUser.getWelcomeMessage());

            // Penggunaan instanceof DIWAJIBKAN khusus routing
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
            System.out.println("=== Menu Admin (Hari ke-\" + gm.getCurrentDay() + \") ===");
            System.out.println("""
                    1. Lihat daftar quest
                    2. Lihat daftar pengembara
                    3. Tambah Quest
                    4. Tambah pengembara
                    5. Tambah monster
                    6. Lihat daftar monster
                    7. Filter daftar quest
                    8. Filter daftar pengembara
                    9. Tampilkan daftar quest terurut
                    10. Tampilkan daftar pengembara terurut
                    11. Lanjut ke hari berikutnya
                    0. Keluar""");
            System.out.print("Masukkan pilihan");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    
                    break;
            
                case "2":
                    System.out.println("Pengembara yang terdaftar:");
                    for (User wanderer : gm.getWanderers()){
                        System.out.println(wanderer);
                        System.out.println("");
                    }
                    break;
            
                case "3":
                    
                    break;
            
                case "4":
                    System.out.print("Masukkan nama pengembara: ");
                    String name = scanner.strip().nextLine();

                    System.out.print("Masukkan username pengembara: ");
                    String username = scanner.strip().nextLine();

                    if (gm.isUsernameTaken(username)){
                        System.out.println("Username sudah digunakan.");
                    }

                    System.out.print("Masukkan password pengembara: ");
                    String password = scanner.nextLine();
                    System.out.print("Masukkan HP maksimal: ");
                    double maxHp = Double.parseDouble(scanner.nextLine());
                    System.out.print("Masukkan attack power: ");
                    double attack = Double.parseDouble(scanner.nextLine());
                    System.out.print("Masukkan defense: ");
                    double defense = Double.parseDouble(scanner.nextLine());

                    Wanderer newWanderer = new Wanderer(gm.nextWandererId(), name, username, password, maxHp, attack, defense);
                    gm.addWanderer(newWanderer);
                    System.out.println("Pengembara berhasil ditambahkan!");

                    break;
            
                case "5":
                    
                    break;
            
                case "6":
                    
                    break;
            
                case "7":
                    
                    break;
            
                case "8":
                    
                    break;
            
                case "9":
                    
                    break;
            
                case "10":
                    
                    break;
            
                case "11":
                    
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
            System.out.println("=== Menu Admin (Hari ke-\" + gm.getCurrentDay() + \") ===");
            System.out.print("""
                    1. Lihat data diri
                    2. Lihat daftar quest
                    3. Filter daftar quest
                    4. Tampilkan daftar quest terurut
                    5. Ambil quest
                    0. Keluar
                    Masukkan pilihan: """;);
            
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    System.out.println("\n=== Data Diri ===");
                    System.out.println(wanderer.toString());
                    break;
                
                case "2":
                    
                    break;
            
                case "3":
                    
                    break;
            
                case "4":
                    
                    break;
            
                case "5":
                    
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