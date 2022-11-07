package gear;

/**
 * Represents the sizes possible for the Belt Gear - small, medium, and large.
 * small has 1 unit, medium has 2 and large has 4 units of size.
 */
public enum BeltSize {
  SMALL(1), MEDIUM(2), LARGE(4);

  private final int units;

  BeltSize(int units) {
    this.units = units;
  }


  /**
   * Gives the units of the belt.
   * @return the units of the belt.
   */
  public int getUnits() {
    return units;
  }
}
