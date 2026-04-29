import java.util.Scanner;
public class BattleManager {

    public static void simulateBattle(Wanderer wanderer, Quest quest) {

        Scanner scanner = new Scanner(System.in);
        Monster monster = quest.getMonster();

        // TODO: simpan currentHp wanderer sebelum battle
        double hpBefore = wanderer.getCurrentHp();

        // TODO: reset HP monster
        monster.resetHp();
        wanderer.resetBattleState();
        monster.resetBattleState();

        

        // TODO: tampilkan info kedua pihak
        System.out.println("=== Battle Dimulai ===");
        System.out.println(wanderer.getCombatInfo() + " vs " + monster.getCombatInfo());
        System.out.println(String.format("Quest: %s (%s)", quest.getName(), quest.getDifficulty()));

        double atkMult = 0;
        double defMult = 0;

        wanderer.setBattleContext(monster, atkMult);

        if (quest.getDifficulty() == Difficulty.MUDAH){
            atkMult = 1.25;
            defMult = 0.75;
        } else if (quest.getDifficulty() == Difficulty.MENENGAH){
            atkMult = 1.0;
            defMult = 1.0;
        } else if (quest.getDifficulty() == Difficulty.SULIT) {
            atkMult = 0.75;
            defMult = 1.25;
        }

        // TODO: loop battle turn by turn

        int turn = 1;
        double totalDamageDealt = 0;
        double totalDamageTaken = 0;

        while (!wanderer.isDefeated() && !monster.isDefeated()){
            System.out.println(String.format("--- Turn %d ---", turn));

            if (turn % 2 != 0){
                wanderer.onTurnStart();

                System.out.println(String.format("%s menyerang %s!", wanderer.getName(), monster.getName()));

                double baseDamage = Math.max(1.0, wanderer.getAttackPower() - monster.getDefense()) * atkMult;

                double finalDamage = wanderer.modifyDamageDealt(baseDamage);

                System.out.println(String.format("Damage ke %s: %.2f", monster.getName(), finalDamage));
                monster.takeDamage(finalDamage);
                totalDamageDealt += finalDamage;

                System.out.println(String.format("%s HP: %.2f/%.2f", monster.getName(), monster.getCurrentHp(), monster.getMaxHp()));

                wanderer.onTurnEnd(finalDamage);

            } else {
                monster.onTurnStart();

                System.out.println(String.format("%s menyerang %s!", monster.getName(), wanderer.getName()));

                double baseDamage = Math.max(1.0, monster.getAttackPower() - wanderer.getDefense()) * defMult;

                double finalDamage = wanderer.modifyDamageTaken(baseDamage);

                System.out.println(String.format("Damage ke %s: %.2f", wanderer.getName(), finalDamage));
                wanderer.takeDamage(finalDamage);
                totalDamageTaken += finalDamage;

                System.out.println(String.format("%s HP: %.2f/%.2f", wanderer.getName(), wanderer.getCurrentHp(), wanderer.getMaxHp()));
            }


            if (!wanderer.isDefeated() && !monster.isDefeated()){
                System.out.println("Tekan Enter untuk melnajutkan...");
                scanner.nextLine().strip();
                turn += 1;
            }
            
        }

        // TODO: handle hasil battle
        System.out.println("");
        System.out.println("=== Battle Selesai ===");

        if (monster.isDefeated()){
            System.out.println(String.format("%s menang!", wanderer.getName()));

            quest.complete();

            int expReward = quest.getExpReward();
            int coinReward = quest.getCoinReward();

            wanderer.addExp(expReward);
            wanderer.addCoins(coinReward);

            System.out.println(String.format("%s mendapatkan %d exp dan %d koin!", wanderer.getName(), expReward, coinReward));
        } else {
            System.out.println(String.format("%s kalah!", wanderer.getName()));
            System.out.println("Hp dipulihkan ke kondisi sebelum battle");

            quest.resetToAvailable();

            wanderer.setCurrentHp(hpBefore);
        }

        System.out.println(String.format("""
                
                === Battle Summary===
                Total Turn: %d
                Total Damage Diberikan %s: %.2f
                Total Damage Diterima %s: %.2f
                """,
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