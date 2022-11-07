package gear;

import player.Ability;

/**
 * Represents a Potion Gear that can be equipped by the player.
 * Potions are consumed by the player before entering the field of battle.
 * They can temporarily affect any of the player's abilities.
 * There is no limit to the number of these that the player can drink.
 */
public class Potion extends AbstractGear {

  private static final int index = 1;

  /**
   * Constructor for potion class.
   *
   * @param name    name of the potion
   * @param ability single ability affected by belt
   * @param value   affected value of the ability
   */
  public Potion(String name, Ability ability, int value) {
    super(name, ability, value);
  }

  @Override
  public int getIndex() {
    return index;
  }

  @Override
  protected int compareToPotion(Potion potion) {
    return this.name.compareTo(potion.name);
  }

  @Override
  public int compareTo(Gear o) {
    AbstractGear other = (AbstractGear) o;
    return -1 * other.compareToPotion(this);
  }
}
