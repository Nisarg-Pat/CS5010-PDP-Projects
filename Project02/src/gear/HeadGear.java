package gear;

import player.Ability;

/**
 * Represents a HeadGear Gear that can be equipped by the player.
 * Headgear is worn on the player's head and affects the player's constitution.
 * Since a player has one head, they can only wear one piece of headgear.
 */
public class HeadGear extends AbstractGear {

  private static final int index = 0;

  /**
   * Contructor for Headgear class.
   *
   * @param name  the name of the headgear
   * @param value affected value of the ability
   */
  public HeadGear(String name, int value) {
    super(name, Ability.CONSTITUTION, value);
  }

  @Override
  public int getIndex() {
    return index;
  }

  @Override
  protected int compareToHeadGear(HeadGear headGear) {
    return this.name.compareTo(headGear.name);
  }

  @Override
  public int compareTo(Gear o) {
    AbstractGear other = (AbstractGear) o;
    return -1 * other.compareToHeadGear(this);
  }
}
