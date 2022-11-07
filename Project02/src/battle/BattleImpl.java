package battle;

import gear.Gear;
import player.Player;
import player.PlayerImpl;
import structureddata.RandomImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Representation of a battle specific to the Jumptastic Games Two-Player RPG.
 * Players enter arena with their basic abilities.
 * Players gets equipped with random gears and a random weapon.
 * When players enter the arena, the player with the higher charisma
 * dazzles their opponent and gets in the first strike.
 * After completion of a battle, players gets opportunity to rematch with their basic abilities.
 * The battle is said to be draw if both players are still standing after 100 turns.
 */
public class BattleImpl implements Battle {
  private final Player[] player;
  private Player winner;
  private int turnCounter;
  private int currentTurn;
  private final List<Gear> gearBag;

  private static final int MAX_TURNS = 100;

  /**
   * Constructor for BattleImpl.
   *
   * @param player1Name Name of first player
   * @param player2Name Name of second player
   * @param gearBag     The bag of available gears.
   */
  public BattleImpl(String player1Name, String player2Name, List<Gear> gearBag) {
    this(player1Name, player2Name, gearBag, new RandomImpl(), new RandomImpl());
  }

  /**
   * Constructor for BattleImpl.
   *
   * @param player1Name Name of first player
   * @param player2Name Name of second player
   * @param gearBag     The bag of available gears.
   * @param random1     Random object to perform random operations
   * @param random2     Random object to perform random operations
   * @throws IllegalArgumentException if arguments are not proper.
   * @throws IllegalArgumentException if the gearBag does not match the required constraints:
   *                                  Headgears >=5, Potions >=15, Belts >=15,
   *                                  Footwears >=15, Min 25% gears Poisoned
   */
  public BattleImpl(String player1Name, String player2Name, List<Gear> gearBag,
                    RandomImpl random1, RandomImpl random2) {
    if (player1Name == null || player2Name == null || gearBag == null
            || random1 == null || random2 == null) {
      throw new IllegalArgumentException("The arguments are not proper.");
    }
    if (!isValidGearBag(gearBag)) {
      throw new IllegalArgumentException("The gearBag does not match the required constraints:"
              + "Headgears >=5, Potions >=15, Belts >=15, Footwears >=15, Min 25% gears Poisoned");
    }
    player = new Player[2];
    player[0] = new PlayerImpl(player1Name, random1);
    player[1] = new PlayerImpl(player2Name, random2);

    winner = null;
    turnCounter = 0;
    currentTurn = 0;

    this.gearBag = new ArrayList<>();
    this.gearBag.addAll(gearBag);
  }

  private boolean isValidGearBag(List<Gear> gearBag) {
    int[] requiredCount = new int[]{5, 15, 15, 5};
    int[] countGear = new int[4];
    int countPoisoned = 0;
    for (Gear gear : gearBag) {
      countGear[gear.getIndex()]++;
      if (gear.isPoisoned()) {
        countPoisoned++;
      }
    }
    int minPoisoned = (int) (gearBag.size() * 0.25);
    for (int i = 0; i < 4; i++) {
      if (countGear[i] < requiredCount[i]) {
        return false;
      }
    }
    return countPoisoned >= minPoisoned;
  }

  @Override
  public Player getPlayer(int playerId) {
    if (playerId < 0 || playerId >= 2) {
      throw new IllegalArgumentException("Invalid argument");
    }
    return new PlayerImpl(player[playerId]);
  }

  @Override
  public int getCurrentTurnPlayerId() {
    if (winner != null) {
      throw new IllegalStateException("The game is already over. Start a new game.");
    }
    if (turnCounter >= MAX_TURNS) {
      throw new IllegalStateException("Reached maximum number of turns.");
    }
    return currentTurn;
  }

  @Override
  public Player getWinner() {
    return winner;
  }

  @Override
  public void equipGears(int playerId) {
    if (playerId < 0 || playerId >= 2) {
      throw new IllegalArgumentException("Invalid argument");
    }
    for (int i = 0; i < 20; i++) {
      try {
        player[playerId].equipGear(gearBag.get(playerId * 20 + i));
      } catch (IllegalStateException e) {
        //Empty catch to reject the gear
      }
    }
  }

  @Override
  public void requestWeapon(int playerId) {
    if (playerId < 0 || playerId >= 2) {
      throw new IllegalArgumentException("Invalid argument");
    }
    if (player[playerId].hasWeapon()) {
      throw new IllegalStateException("Player: " + player[playerId].getName()
              + " already has a weapon.");
    } else {
      player[playerId].selectRandomWeapon();
    }
  }

  @Override
  public void startBattle() {
    if (player[0].getCharisma() >= player[1].getCharisma()) {
      currentTurn = 0;
    } else {
      currentTurn = 1;
    }
  }

  @Override
  public int attack() {
    if (winner != null) {
      throw new IllegalStateException("The game is already over. Start a new game.");
    }
    if (turnCounter >= MAX_TURNS) {
      throw new IllegalStateException("Reached maximum number of turns.");
    }
    int nextTurn = 1 - currentTurn;
    final int damage = player[currentTurn].attack(player[nextTurn]);
    if (player[nextTurn].isDefeated()) {
      this.winner = new PlayerImpl(player[currentTurn]);
    }
    currentTurn = nextTurn;
    turnCounter++;
    return damage;
  }

  @Override
  public void reset() {
    player[0].reset();
    player[1].reset();
    Collections.shuffle(gearBag);
    winner = null;
    currentTurn = 0;
    turnCounter = 0;
  }

  @Override
  public int getTurnCounter() {
    return turnCounter;
  }

  @Override
  public int getMaxTurns() {
    return MAX_TURNS;
  }
}
