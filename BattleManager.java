import java.util.Scanner;
public class BattleManager {

    public static void simulateBattle(Wanderer wanderer, Quest quest) {

        Scanner scanner = new Scanner(System.in);
        Monster monster = quest.getMonster();

        // Menyimpan HP wanderer sebelum battle dimulai
        double hpBefore = wanderer.getCurrentHp();

        // Reset state battle untuk wanderer dan monster
        monster.resetHp();
        wanderer.resetBattleState();
        monster.resetBattleState();

        // Menampilkan info kedua pihak
        System.out.println("=== Battle Dimulai ===");
        System.out.println(wanderer.getCombatInfo() + "\nvs\n" + monster.getCombatInfo());
        System.out.println(String.format("Quest: %s (%s)", quest.getName(), quest.getDifficulty()));

        

        // Instansiasi multiplier
        double atkMult = 0;
        double defMult = 0;

        // set nilai multiplier sesuai dengan difficulty quest
        if (quest.getDifficulty() == Difficulty.MUDAH){ atkMult = 1.25; defMult = 0.75;}
        else if (quest.getDifficulty() == Difficulty.MENENGAH){ atkMult = 1.0; defMult = 1.0; } 
        else if (quest.getDifficulty() == Difficulty.SULIT) { atkMult = 0.75; defMult = 1.25; }

        // Set context battle
        wanderer.setBattleContext(monster, atkMult);

        // Loop battle turn by turn

        int turn = 1;
        double totalDamageDealt = 0;
        double totalDamageTaken = 0;

        // Looping selama belum ada yang kalah
        while (!wanderer.isDefeated() && !monster.isDefeated()){
            System.out.println(String.format("--- Turn %d ---", turn));
            String note;

            // === Turn Wanderer (jalan di turn ganjil) ===
            if (turn % 2 != 0){
                wanderer.onTurnStart();

                // Setup CustomNote untuk pasif unik setiap job class Wanderer
                note = wanderer.consumeCustomDamageNote();
                if (note != null) { System.out.println(note); }

                System.out.println(String.format("%s menyerang %s!", wanderer.getName(), monster.getName()));

                // Perhitungan damage Wanderer
                double baseDamage = Math.max(1.0, wanderer.getAttackPower() - monster.getDefense()) * atkMult;
                double finalDamage = wanderer.modifyDamageDealt(baseDamage);
                
                
                note = wanderer.consumeCustomDamageNote();
                if (note != null) System.out.println(note);

                // Wanderer menyerang monster
                System.out.println(String.format("Damage ke %s: %.2f (atk x%.2f)", monster.getName(), finalDamage, atkMult));
                monster.takeDamage(finalDamage);
                totalDamageDealt += finalDamage;

                System.out.println(String.format("%s HP: %.2f/%.2f", monster.getName(), monster.getCurrentHp(), monster.getMaxHp()));

                // Turn wanderer selesai
                wanderer.onTurnEnd(finalDamage);
                note = wanderer.consumeCustomDamageNote();
                if (note != null) System.out.println(note);

            // === Turn Monster (jalan di turn genap) ===
            } else {
                monster.onTurnStart();

                note = monster.consumeCustomDamageNote();
                if (note != null) { System.out.println(note); }

                System.out.println(String.format("%s menyerang %s!", monster.getName(), wanderer.getName()));

                // Perhitungan damage monster
                double baseDamage = Math.max(1.0, monster.getAttackPower() - wanderer.getDefense()) * defMult;
                double finalDamage = wanderer.modifyDamageTaken(baseDamage);

                if (note != null) { System.out.println(note); }

                // Monster menyerang wanderer
                System.out.println(String.format("Damage ke %s: %.2f (def x%.2f)", wanderer.getName(), finalDamage, defMult));
                wanderer.takeDamage(finalDamage);
                totalDamageTaken += finalDamage;

                System.out.println(String.format("%s HP: %.2f/%.2f", wanderer.getName(), wanderer.getCurrentHp(), wanderer.getMaxHp()));
            }

            // Pause antar turn jika battle masih berlanjut
            if (!wanderer.isDefeated() && !monster.isDefeated()){
                System.out.println("Tekan Enter untuk melnajutkan...");
                scanner.nextLine().strip();
                turn += 1;
            }
            
        }

        // Handle hasil battle
        System.out.println("");
        System.out.println("=== Battle Selesai ===");

        // Jika Wanderer menang
        if (monster.isDefeated()){
            System.out.println(String.format("%s menang!", wanderer.getName()));

            quest.complete();

            int expReward = quest.getExpReward();
            int coinReward = quest.getCoinReward();

            wanderer.addExp(expReward);
            wanderer.addCoins(coinReward);

            System.out.println(String.format("%s mendapatkan %d exp dan %d koin!", wanderer.getName(), expReward, coinReward));
        
        // Jika Wanderer kalah
        } else {
            System.out.println(String.format("%s kalah!", wanderer.getName()));
            System.out.println("Hp dipulihkan ke kondisi sebelum battle");

            quest.resetToAvailable();

            // Rollback HP wanderer ke kondisi awal sebelum battle
            wanderer.setCurrentHp(hpBefore);
        }

        // Print hasil battle
        System.out.println(String.format("""
                
                === Battle Summary===
                Total Turn: %d
                Total Damage Diberikan %s: %.2f
                Total Damage Diterima %s: %.2f""",
                turn,
                wanderer.getName(), totalDamageDealt,
                wanderer.getName(), totalDamageTaken));
        
        String passiveTriggerSummary = wanderer.getPassiveTriggerSummary();
        if (!passiveTriggerSummary.isEmpty()){
            System.out.println("Passive TriggerSummary:");
            System.out.println(passiveTriggerSummary);
        }

        System.out.println("Hasil Akhir: " + (monster.isDefeated() ? "Menang" : "Kalah"));
    }
}