package gear;

import player.Ability;
import player.Player;
import structureddata.PairValue;

abstract class AbstractGear implements Gear {
  protected final String name;
  protected final Ability[] ability;
  protected int[] value;
  protected boolean poisoned;

  AbstractGear(String name, Ability ability, int value) {
    if (value <= 0 || ability == null || name == null) {
      throw new IllegalArgumentException("The arguments are not proper.");
    }
    this.name = name;
    this.ability = new Ability[2];
    this.ability[0] = ability;
    this.ability[1] = null;

    this.value = new int[2];
    this.value[0] = value;
    this.value[1] = 0;
    this.poisoned = false;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Ability[] getAbility() {
    return new Ability[]{ability[0], ability[1]};
  }

  @Override
  public int[] getValue() {
    if (poisoned) {
      return new int[]{-1 * value[0], -1 * value[1]};
    } else {
      return new int[]{value[0], value[1]};
    }
  }

  @Override
  public int getSize() {
    return 1;
  }

  @Override
  public boolean canBeEquippedByPlayer(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Invalid argument");
    }
    PairValue headGearPair = player.getGearValues().get(getIndex());
    return headGearPair.getCurrentValue() + getSize() <= headGearPair.getMaxValue();
  }

  @Override
  public void setPoison(boolean poisoned) {
    this.poisoned = poisoned;
  }

  @Override
  public boolean isPoisoned() {
    return poisoned;
  }

  protected int compareToBelt(Belt belt) {
    return getIndex() - belt.getIndex();
  }

  protected int compareToHeadGear(HeadGear headGear) {
    return getIndex() - headGear.getIndex();
  }

  protected int compareToFootwear(Footwear footwear) {
    return getIndex() - footwear.getIndex();
  }

  protected int compareToPotion(Potion potion) {
    return getIndex() - potion.getIndex();
  }

  /**
   * Compares the gears in order of worn top to bottom, then alphabetically:
   * thus any headgear comes before potions which comes before any belts which
   * comes before any footwear.
   *
   * @param o The Gear with which to compare
   * @return the compare value between the two gears.
   */
  @Override
  public abstract int compareTo(Gear o);
}
