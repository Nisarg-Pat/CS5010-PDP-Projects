package battle;

import player.Player;

/**
 * Battle interface for turn based role playing game.
 * Turn-based battles generally start by pitting two players against one another.
 * The battle begins by determining which player will go first and then proceeds as follows:
 *
 * <br>Player 1 attacks using the weapon that they have in-hand by taking a swing at
 * player 2 who tries to avoid the attack.
 * If player 1 hits player 2, then player 2 potentially takes damage.
 * Player 2 attacks using the weapon that they have in-hand by taking a swing at
 * player 1 who tries to avoid the attack. If player 2 hits player 1,
 * then player 1 potentially takes damage.
 * Turns continue back and forth until one of the players has taken a total damage that
 * is greater than or equal to their health.
 */
public interface Battle {

  /**
   * Gives the player with respect to the id.
   *
   * @param playerId id of the player. Can be either 0 or 1.
   * @return the respective player
   */
  Player getPlayer(int playerId);

  /**
   * Gives the id of the player who has the current turn.
   *
   * @return id of the player who has the turn. Can be either 0 or 1
   * @throws IllegalArgumentException if match is completed.
   */
  int getCurrentTurnPlayerId();

  /**
   * Gives the player who won the game.
   *
   * @return the winner of the game
   *         null if the match is still going on or ended in draw
   */
  Player getWinner();

  /**
   * Gives random gears to player from a gearBag.
   *
   * @param playerId id of the player. Can be 0 or 1
   * @throws IllegalArgumentException if invalid player id
   */
  void equipGears(int playerId);

  /**
   * Gives random weapon to player from a gearBag.
   *
   * @param playerId id of the player. Can be 0 or 1
   * @throws IllegalArgumentException if invalid player id
   */
  void requestWeapon(int playerId);

  /**
   * Starts a battle and computes who will attack first.
   */
  void startBattle();

  /**
   * Player with current turn attacks another player.
   */
  int attack();

  /**
   * Resets the game to let the players rematch.
   * All the Gears and Weapons are removed from both players.
   */
  void reset();

  /**
   * Gives the total turns passed in the battle.
   *
   * @return the total turns passed in battle.
   */
  int getTurnCounter();

  /**
   * Gives the maximum turns alloted for a battle.
   *
   * @return the maximum turns alloted for a battle (100).
   */
  int getMaxTurns();
}
