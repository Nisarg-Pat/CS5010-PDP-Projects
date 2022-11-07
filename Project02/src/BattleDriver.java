import battle.Battle;
import battle.BattleImpl;
import gear.Belt;
import gear.BeltSize;
import gear.Footwear;
import gear.Gear;
import gear.HeadGear;
import gear.Potion;
import player.Ability;
import player.Player;
import player.Weapon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


/**
 * A Driver class to create one run of program. It has all the methods that
 * user can call in Battle.
 */
public class BattleDriver {
  private static Scanner sc;

  private static Battle battle;
  private static final String[] PLAYER_NAMES = new String[]{"Player-1", "Player-2"};
  private static List<Gear> gearBag;

  /**
   * Main() method to run the driver class.
   *
   * @param args arguments for main method. None for this case.
   */
  public static void main(String[] args) {
    sc = new Scanner(System.in);
    printIntroMessage();
    gearBag = new ArrayList<>();
    generateGearBag();
    battle = new BattleImpl("Player-1", "Player-2", gearBag);
    setupPlayer(0);
    setupPlayer(1);

    String input = "Y";
    while (input.equalsIgnoreCase("Y")) {
      equipItemstoPlayer(0);
      equipItemstoPlayer(1);
      startNewBattle();
      battle.reset();
      System.out.println();
      System.out.print("Do you want to rematch with the initial generated abilities? (Y/N): ");
      input = sc.nextLine();
      while (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N")) {
        System.out.print("Could not understand the option, please enter from (Y/N): ");
        input = sc.nextLine();
      }
    }
  }

  private static void printIntroMessage() {
    System.out.println("Welcome warriers to the Jumptastic Games RPG!!!");
    System.out.println();
    System.out.println();
  }

  private static void generateGearBag() {
    addHeadGear();
    addPotions();
    addBelts();
    addFootWear();
    Collections.shuffle(gearBag);
    for (int i = 0; i < 0.25 * gearBag.size(); i++) {
      gearBag.get(i).setPoison(true);
    }
    Collections.shuffle(gearBag);
  }

  private static void setupPlayer(int playerId) {
    Player player = battle.getPlayer(playerId);
    System.out.println(PLAYER_NAMES[playerId]
            + " - This is your avatar with generated abilities: ");
    System.out.println("Name: " + player.getName());
    System.out.println(printPlayerAbilities(player));
    System.out.println();
  }

  private static void equipItemstoPlayer(int playerId) {
    System.out.println("Its time to setup " + PLAYER_NAMES[playerId]);
    System.out.println("Getting some random gears to help you in battle. "
            + "25% of the available gears are poisoned.");
    battle.equipGears(playerId);
    System.out.println("You would also require a weapon to help you in battle. "
            + "Giving you random weapon from armory.");
    battle.requestWeapon(playerId);
    System.out.println(PLAYER_NAMES[playerId] + " is fully equipped. "
            + "Look at the details of your avatar before entering the arena:");
    System.out.println();
    Player player = battle.getPlayer(playerId);
    System.out.println("Name: " + player.getName());
    System.out.println(printPlayerAbilities(player));
    System.out.println("Health: " + player.getHealth());
    System.out.println("Weapon: " + printWeapon(player.getWeapon()));
    System.out.println("List of all the Gears: ");
    System.out.println(printGears(player.getGearList()));
    System.out.println();
  }

  private static void startNewBattle() {
    battle.startBattle();
    printStartBattleMessage();
    int totalTurns = 0;
    while (battle.getWinner() == null && totalTurns < battle.getMaxTurns()) {
      totalTurns++;
      System.out.println("Turn - " + totalTurns);
      int turn = battle.getCurrentTurnPlayerId();
      System.out.println(PLAYER_NAMES[turn] + " is attacking " + PLAYER_NAMES[1 - turn] + ".");
      int damage = battle.attack();
      if (damage < 0) {
        System.out.println(PLAYER_NAMES[1 - turn] + " avoided the attack!!");
      } else if (damage == 0) {
        System.out.println(PLAYER_NAMES[1 - turn] + " blocked the attack!!");
      } else {
        System.out.println(PLAYER_NAMES[1 - turn] + " took damage of " + damage + " points.");
      }
      Player player1 = battle.getPlayer(0);
      Player player2 = battle.getPlayer(1);
      System.out.printf("%s Health: %d/%d    %s Health: %d/%d%n",
              player1.getName(), Math.max(0, player1.getRemainingHealth()),
              player1.getHealth(), player2.getName(), Math.max(0, player2.getRemainingHealth()),
              player2.getHealth());
      System.out.println();
    }

    Player winner = battle.getWinner();
    if (winner != null) {
      System.out.println("Congratulations " + winner.getName() + ". You Won!!");
    } else {
      System.out.println("After " + battle.getMaxTurns()
              + " turns both players are still standing. The game ended in draw.");
    }
  }

  private static void printStartBattleMessage() {
    System.out.println("Time to start the battle!!");
    System.out.println(PLAYER_NAMES[battle.getCurrentTurnPlayerId()]
            + " dazzled its opponent to get the first turn and start the battle!!");
    System.out.println();
  }


  private static String printGears(List<Gear> gearList) {
    StringBuilder sb = new StringBuilder();
    for (Gear gear : gearList) {
      sb.append(gear.getName());
      if (gear.isPoisoned()) {
        sb.append(" (Poisoned)");
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  private static String printWeapon(Weapon[] weapon) {
    if (weapon[1] == Weapon.KATANA) {
      return "Double Katana";
    } else {
      return weapon[0].toString();
    }
  }

  private static String printPlayerAbilities(Player player) {
    StringBuilder sb = new StringBuilder();
    sb.append("Abilities: ");
    sb.append(Ability.STRENGTH).append(":").append(player.getStrength()).append(" ");
    sb.append(Ability.CONSTITUTION).append(":").append(player.getConstitution()).append(" ");
    sb.append(Ability.DEXTERITY).append(":").append(player.getDexterity()).append(" ");
    sb.append(Ability.CHARISMA).append(":").append(player.getCharisma());
    return sb.toString();
  }

  private static void addHeadGear() {
    gearBag.add(new HeadGear("HeadGear", 6));
    gearBag.add(new HeadGear("HeadGear - II", 7));
    gearBag.add(new HeadGear("HeadGear - III", 8));
    gearBag.add(new HeadGear("HeadGear - IV", 9));
    gearBag.add(new HeadGear("HeadGear - V", 10));
  }

  private static void addPotions() {
    gearBag.add(new Potion("Strength Potion", Ability.STRENGTH, 1));
    gearBag.add(new Potion("Super Strength Potion", Ability.STRENGTH, 2));
    gearBag.add(new Potion("Hyper Strength Potion", Ability.STRENGTH, 3));
    gearBag.add(new Potion("Extreme Strength Potion", Ability.STRENGTH, 4));
    gearBag.add(new Potion("Constitution Potion", Ability.CONSTITUTION, 1));
    gearBag.add(new Potion("Super Constitution Potion", Ability.CONSTITUTION, 2));
    gearBag.add(new Potion("Hyper Constitution Potion", Ability.CONSTITUTION, 3));
    gearBag.add(new Potion("Extreme Constitution Potion", Ability.CONSTITUTION, 4));
    gearBag.add(new Potion("Dexterity Potion", Ability.DEXTERITY, 1));
    gearBag.add(new Potion("Super Dexterity Potion", Ability.DEXTERITY, 2));
    gearBag.add(new Potion("Hyper Dexterity Potion", Ability.DEXTERITY, 3));
    gearBag.add(new Potion("Extreme Dexterity Potion", Ability.DEXTERITY, 4));
    gearBag.add(new Potion("Charisma Potion", Ability.CHARISMA, 1));
    gearBag.add(new Potion("Super Charisma Potion", Ability.CHARISMA, 2));
    gearBag.add(new Potion("Hyper Charisma Potion", Ability.CHARISMA, 3));
  }

  private static void addBelts() {
    gearBag.add(new Belt("Small S Belt", BeltSize.SMALL, Ability.STRENGTH, 1));
    gearBag.add(new Belt("Small S Mega Belt", BeltSize.SMALL, Ability.STRENGTH, 2));
    gearBag.add(new Belt("Small Ch Belt", BeltSize.SMALL, Ability.CHARISMA, 1));
    gearBag.add(new Belt("Small Ch Mega Belt", BeltSize.SMALL, Ability.CHARISMA, 2));
    gearBag.add(new Belt("Small D Belt", BeltSize.SMALL, Ability.DEXTERITY, 1));
    gearBag.add(new Belt("Small D Mega Belt", BeltSize.SMALL, Ability.DEXTERITY, 2));
    gearBag.add(new Belt("Small Co Belt", BeltSize.SMALL, Ability.CONSTITUTION, 1));
    gearBag.add(new Belt("Medium S Belt", BeltSize.MEDIUM, Ability.STRENGTH, 2));
    gearBag.add(new Belt("Medium Ch Belt", BeltSize.MEDIUM, Ability.CHARISMA, 2));
    gearBag.add(new Belt("Medium CoD Belt", BeltSize.MEDIUM, Ability.CONSTITUTION, 1,
            Ability.DEXTERITY, 1));
    gearBag.add(new Belt("Medium ChS Belt", BeltSize.MEDIUM, Ability.STRENGTH, 1,
            Ability.CHARISMA, 1));
    gearBag.add(new Belt("Medium SD Mega Belt", BeltSize.MEDIUM, Ability.STRENGTH, 2,
            Ability.DEXTERITY, 2));
    gearBag.add(new Belt("Large ChD Belt", BeltSize.LARGE, Ability.CHARISMA, 3,
            Ability.DEXTERITY, 1));
    gearBag.add(new Belt("Large CoS Belt", BeltSize.LARGE, Ability.STRENGTH, 2,
            Ability.CONSTITUTION, 2));
    gearBag.add(new Belt("Large ChCo Mega Belt", BeltSize.LARGE, Ability.CHARISMA, 3,
            Ability.CONSTITUTION, 3));
  }

  private static void addFootWear() {
    gearBag.add(new Footwear("Footwear - I", 6));
    gearBag.add(new Footwear("Footwear - II", 7));
    gearBag.add(new Footwear("Footwear - III", 8));
    gearBag.add(new Footwear("Footwear - IV", 9));
    gearBag.add(new Footwear("Footwear - V", 10));
  }

}
