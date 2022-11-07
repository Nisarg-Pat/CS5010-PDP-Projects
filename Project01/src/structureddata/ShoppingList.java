package structureddata;

import monkey.FavFood;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * Structured Data containing the Shopping List of items.
 */
public class ShoppingList {
  private final HashMap<FavFood, Integer> shoppingList;

  /**
   * Constructor to create a new Shopping List.
   */
  public ShoppingList() {
    shoppingList = new HashMap<>();
    for (FavFood favFood : FavFood.values()) {
      shoppingList.put(favFood, 0);
    }
  }

  /**
   * Adds a particular amount of a favourite food in the list.
   *
   * @param favFood the favourite food
   * @param value   teh amount of the food to be added in list
   */
  public void add(FavFood favFood, int value) {
    shoppingList.put(favFood, shoppingList.get(favFood) + value);
  }

  /**
   * String representation of ShoppingList.
   *
   * @return String representation of ShoppingList
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<FavFood, Integer> entry : shoppingList.entrySet()) {
      FavFood favFood = entry.getKey();
      int quantity = entry.getValue();
      if (quantity != 0) {
        sb.append(favFood).append(": ").append(quantity).append(" gms\n");
      }
    }
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o instanceof ShoppingList) {
      ShoppingList that = (ShoppingList) o;
      return Objects.equals(shoppingList, that.shoppingList);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(shoppingList);
  }


}
