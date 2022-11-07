package gear;

import player.Ability;

/**
 * Represents a Belt Gear that can be equipped by the player.
 * Belts come in three sizes -- small, medium, and large --
 * and are worn around the player's torso affecting up to two of the player's abilities.
 * Players have the ability to wear 10 "units" of belts
 * where small belts count as 1 unit, medium as 2 units, and large as 4 units.
 */
public class Belt extends AbstractGear {
  private final BeltSize beltSize;

  private static final int index = 2;

  /**
   * Constructor for Belt class.
   *
   * @param name     name for the belt
   * @param beltSize size of the belt
   * @param ability  single ability affected by belt
   * @param value    affected value of the ability
   */
  public Belt(String name, BeltSize beltSize, Ability ability, int value) {
    super(name, ability, value);
    if (beltSize == null) {
      throw new IllegalArgumentException("The arguments are not proper.");
    }
    this.beltSize = beltSize;
  }

  /**
   * Constructor for Belt class.
   *
   * @param name     name for the belt
   * @param beltSize size of the belt
   * @param ability  single ability affected by belt
   * @param value    affect value of the ability
   * @param ability2 another ability affected by belt
   * @param value2   affected value of the second ability
   */
  public Belt(String name, BeltSize beltSize, Ability ability,
              int value, Ability ability2, int value2) {
    super(name, ability, value);
    this.beltSize = beltSize;
    this.ability[1] = ability2;
    this.value[1] = value2;
  }

  @Override
  public int getSize() {
    return this.beltSize.getUnits();
  }

  @Override
  public int getIndex() {
    return index;
  }

  @Override
  protected int compareToBelt(Belt belt) {
    return this.name.compareTo(belt.name);
  }

  @Override
  public int compareTo(Gear o) {
    AbstractGear other = (AbstractGear) o;
    return -1 * other.compareToBelt(this);
  }
}
