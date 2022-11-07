package gear;

import player.Ability;

/**
 * Represents a Footwear Gear that can be equipped by the player.
 * Footwear is worn on the player's feet and affects the player's dexterity.
 * Footwear always comes in pairs and a player can only wear one piece of footwear at a time.
 */
public class Footwear extends AbstractGear {

  private static final int index = 3;

  /**
   * Constructor for Footwear.
   *
   * @param name  the name of the footwear
   * @param value affected value of the ability
   */
  public Footwear(String name, int value) {
    super(name, Ability.DEXTERITY, value);
  }

  @Override
  protected int compareToFootwear(Footwear footwear) {
    return this.name.compareTo(footwear.name);
  }

  @Override
  public int getIndex() {
    return index;
  }

  @Override
  public int compareTo(Gear o) {
    AbstractGear other = (AbstractGear) o;
    return -1 * other.compareToFootwear(this);
  }
}
