package player;

/**
 * Represents the abilities that a player can have.
 * Strength affects how effective the player is at striking their opponent.
 * Constitution affects how much damage a player can take when they are hit in battle.
 * Dexterity affects how effective the player is at avoiding a strike from their opponent.
 * Charisma affects how their opponent views them.
 * Each ability can range between 6 and 18 without any gears.
 */
public enum Ability {
  STRENGTH("Strength", 0), CONSTITUTION("Constitution", 1),
  DEXTERITY("Dexterity", 2), CHARISMA("Charisma", 3);

  private final String name;
  private final int index;

  Ability(String name, int index) {
    this.name = name;
    this.index = index;
  }

  /**
   * Gives the index of the ability.
   * @return index of the ability
   */
  public int getIndex() {
    return index;
  }

  /**
   * String representation of the ability.
   * @return string representation of the ability.
   */
  @Override
  public String toString() {
    return name;
  }


}
