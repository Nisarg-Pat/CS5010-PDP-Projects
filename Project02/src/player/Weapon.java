package player;

import structureddata.RandomImpl;

/**
 * Represents weapons that can be equipped by the player.
 * Katanas are lightweight curved swords that come in pairs.
 * They can do a base of 4-6 points of damage when they hit.
 * They are so light that a player can carry two of them (which attack separately).
 * Broadswords are a good medium weapon that can do 6-10 points of damage when they hit.
 * Two-handed swords are a heavy sword that can only be effectively wielded by players with
 * strength greater than 14, but they can do 8-12 points of damage when they hit.
 * If the player does not have the strength to wield a two-handed sword, t
 * he sword only does half damage.
 * Axes are great general weapons doing 6-10 points of damage when they hit.
 * Flails are also great general weapons but they can only be effectively wielded by players
 * with a dexterity greater than 14. They do 8-12 points of damage when they hit. If the player
 * does not have the dexterity to wield a flail, the flail only does half damage.
 */
public enum Weapon {
  KATANA("Katana", 4, 6), BROADSWORD("Broadsword", 6, 10), TWOHANDED("Two-Handed Sword", 8, 12),
  AXE("Axe", 6, 10), FLAIL("Flail", 8, 12), NOWEAPON("No Weapon", 0, 0);

  private final String name;
  private final int minDamage;
  private final int maxDamage;


  Weapon(String name, int minDamage, int maxDamage) {
    this.name = name;
    this.minDamage = minDamage;
    this.maxDamage = maxDamage;
  }

  /**
   * Gives damage that can be dealth with the weapon.
   *
   * @param random random object to perform random operations
   * @return valid damage that can be dealt by the weapon.
   */
  public int getDamage(RandomImpl random) {
    if (random == null) {
      throw new IllegalArgumentException("Invalid argument");
    }
    return random.getRandomInt(minDamage, maxDamage);
  }

  /**
   * String representation of the weapon.
   *
   * @return String representation of the weapon.
   */
  @Override
  public String toString() {
    return name;
  }
}
