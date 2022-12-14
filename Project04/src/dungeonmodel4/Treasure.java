package dungeonmodel4;

/**
 * Enum representation of types of treasures: DIAMOND, RUBY, SAPPHIRE.
 * Treasures are {@link Item}s that can be picked by player.
 */
public enum Treasure implements Item {
  DIAMOND,
  RUBY,
  SAPPHIRE;

  @Override
  public String getSingular() {
    switch (this) {
      case DIAMOND:
        return "Diamond";
      case RUBY:
        return "Ruby";
      case SAPPHIRE:
        return "Sapphire";
      default:
        return super.toString();
    }
  }

  @Override
  public String getPlural() {
    switch (this) {
      case DIAMOND:
        return "Diamonds";
      case RUBY:
        return "Rubies";
      case SAPPHIRE:
        return "Sapphires";
      default:
        return super.toString();
    }
  }

  @Override
  public String toString() {
    return this.getSingular();
  }
}
