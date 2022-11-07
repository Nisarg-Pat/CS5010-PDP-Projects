package structureddata;

import monkey.FavFood;
import monkey.Sex;

import java.util.Objects;

/**
 * Structured Data containing a Monkey's name, sex and favourite food.
 */
public class MonkeySign implements Comparable<MonkeySign> {
  private final String name;
  private final String sex;
  private final String favFood;

  /**
   * Constructor for MonkeySign.
   *
   * @param name    name of the Monkey
   * @param sex     sex of the Monkey
   * @param favFood Favourite food of the Monkey
   */
  public MonkeySign(String name, Sex sex, FavFood favFood) {
    this.name = name;
    this.sex = sex.toString();
    this.favFood = favFood.toString();
  }

  /**
   * String representation of MonkeySign.
   *
   * @return String representation of MonkeySign
   */
  @Override
  public String toString() {
    return String.format("Name: %s, Sex: %s, Favourite Food: %s", name, sex, favFood);
  }

  @Override
  public int compareTo(MonkeySign o) {
    return this.name.compareTo(o.name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o instanceof MonkeySign) {
      MonkeySign that = (MonkeySign) o;
      return name.equals(that.name) && sex.equals(that.sex) && favFood.equals(that.favFood);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, sex, favFood);
  }
}
