package player;

import gear.Gear;
import structureddata.PairValue;

import java.util.List;
import java.util.Map;



/**
 * Representation of a player who will enter the Arena to battle.
 * Player can have different abilities. A player can equip many gears based on the contraints.
 * A player can have weapon to inflict more damage.
 */
public interface Player {
  /**
   * Gives the name of the player.
   *
   * @return the name of the player
   */
  String getName();

  /**
   * Gives the total STRENGTH ability of the player.
   *
   * @return the STRENGTH ability of the player.
   */
  int getStrength();

  /**
   * Gives the total CONSTITUTION ability of the player.
   *
   * @return the CONSTITUTION ability of the player.
   */
  int getConstitution();

  /**
   * Gives the total DEXTERITY ability of the player.
   *
   * @return the DEXTERITY ability of the player.
   */
  int getDexterity();

  /**
   * Gives the total CHARISMA ability of the player.
   *
   * @return the CHARISMA ability of the player.
   */
  int getCharisma();

  /**
   * Randomly select a weapon for the player.
   */
  void selectRandomWeapon();

  /**
   * Gives the weapon equipped by the player.
   *
   * @return the weapons equipped by the player
   */
  Weapon[] getWeapon();

  /**
   * Equips a gear to the player.
   *
   * @param gear the gear to be equipped
   * @throws IllegalArgumentException if gear is invalid or already equipped by the player
   */
  void equipGear(Gear gear);

  /**
   * Gives the current gear values and its constraints of the player.
   *
   * @return the urrent gear values and its constraints of the player
   */
  Map<Integer, PairValue> getGearValues();

  /**
   * Gives a list of gears equipped by the player.
   *
   * @return a list of gears
   */
  List<Gear> getGearList();

  /**
   * Gives the total health of the player. It is the sum of all the four abilities.
   *
   * @return the current health of the player.
   */
  int getHealth();

  /**
   * Gives the striking power of the player.
   * Striking power is the sum of the strength of the player,
   * any of the gear that adds (or substracts) from strength,
   * and a random number between 1 and 10 (inclusive).
   *
   * @return Striking Power
   */
  int getStrikingPower();

  /**
   * Gives the avoidance ability of the player.
   * Avoidance ability is the sum of the dexterity of the player,
   * any of the gear that adds (or subtracts) from dexterity,
   * and a random number between 1 and 6 (inclusive).
   *
   * @return Avoidance ability
   */
  int getAvoidanceAbility();

  /**
   * Gives the potential striking damage.
   * The potential striking damage is calculated by adding the strength of the attacking player
   * to a random value in the range of the damage that their weapon can inflict
   * (if they have a weapon).
   *
   * @return the potential striking damage
   */
  int getPotentialStrikingDamage();

  /**
   * Gives the total damage that is done to the player till now.
   *
   * @return total damage
   */
  int getTotalDamage();

  /**
   * Attacks the other player to deal some damage.
   *
   * @param player the player to whom to attack
   * @return the actual damage dealt to the player. The actual damage is
   *         the potential striking damage minus the constitution of their opponent.
   */
  int attack(Player player);

  /**
   * Tells whether the player is defeated in the battle.
   *
   * @return whether the player is defeated in the battle.
   */
  boolean isDefeated();

  /**
   * Tells whether the player has a weapon equipped.
   *
   * @return whether the player has a weapon equipped.
   */
  boolean hasWeapon();

  /**
   * Deals the damage to the player.
   *
   * @param actualDamage the actual damage dealt to the player.
   * @throws IllegalArgumentException if actualDamage is negative or 0.
   * @throws IllegalStateException    if battle is over.
   */
  void doDamage(int actualDamage);

  /**
   * Gives the remaining health of the player.
   *
   * @return the remaining health of the player.
   */
  int getRemainingHealth();

  /**
   * Resets the player to its base form. Removes all Gears and Weapons to start a new battle.
   */
  void reset();
}
