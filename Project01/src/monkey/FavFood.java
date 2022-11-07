package monkey;

/**
 * Enum containing all the available choices of Favourite Foods for the Monkey.
 */
public enum FavFood {
  EGGS, FRUITS, INSECTS, LEAVES, NUTS, SEEDS, TREE_SAP;

  /**
   * String representation of Favourite Food.
   *
   * @return the string representation of Favourite Food.
   */
  public String toString() {
    switch (this) {
      case EGGS:
        return "Eggs";
      case FRUITS:
        return "Fruits";
      case INSECTS:
        return "Insects";
      case LEAVES:
        return "Leaves";
      case NUTS:
        return "Nuts";
      case SEEDS:
        return "Seeds";
      case TREE_SAP:
        return "Tree Sap";
      default:
        return super.toString();
    }
  }
}
