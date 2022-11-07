package gear;

import player.Ability;
import player.Player;

/**
 * Represents the gears that a player can equip. Can affect positively or
 * negatively one or more {@link Ability} of a player.
 */
public interface Gear extends Comparable<Gear> {

  /**
   * Gives the name of the Gear.
   *
   * @return name of the gear.
   */
  String getName();

  /**
   * Gives the abilities that can be affected by the gear.
   *
   * @return abilities that can be affected by the gear
   */
  Ability[] getAbility();

  /**
   * Gives the value of how much each ability is affected.
   *
   * @return the value of how much each ability is affected
   */
  int[] getValue();

  /**
   * Gives the size of the gear.
   *
   * @return the size of the gear.
   */
  int getSize();

  /**
   * Gives the index of the gear.
   *
   * @return the index of the gear
   */
  int getIndex();

  /**
   * Tells whether the player can equip this gear based on player constraints of different gear.
   *
   * @param player The player whose contraints need to be checked
   * @return whether the gear can be equipped by player.
   * @throws IllegalArgumentException if player is not valid.
   */
  boolean canBeEquippedByPlayer(Player player);

  /**
   * Sets the gear to be poisoned.
   * Poisoned gears hinder the ability of the player rather than improve it.
   *
   * @param b whether to set gear to poisoned
   */
  void setPoison(boolean b);

  /**
   * Tells whether the gear is poisoned or not.
   *
   * @return whether the gear is poisoned or not
   */
  boolean isPoisoned();
}
