package structureddata;

import java.util.Objects;

/**
 * Structured Data containing a Monkey's name and housing.
 */
public class MonkeyData implements Comparable<MonkeyData> {
  private final String name;
  private final String housing;

  /**
   * Constructor for MonkeyData.
   *
   * @param name    the name of the monkey
   * @param housing the name of the housing
   */
  public MonkeyData(String name, String housing) {
    this.name = name;
    this.housing = housing;
  }

  /**
   * The name of the monkey.
   *
   * @return the name of the monkey
   */
  public String getName() {
    return name;
  }

  /**
   * The name of the housing.
   *
   * @return the name of the housing
   */
  public String getHousing() {
    return housing;
  }

  /**
   * String representation of MonkeyData.
   *
   * @return String representation of MonkeyData
   */
  @Override
  public String toString() {
    return String.format("Name: %s, Housing: %s", this.name, this.housing);
  }

  @Override
  public int compareTo(MonkeyData o) {
    return this.name.compareTo(o.name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o instanceof MonkeyData) {
      MonkeyData other = (MonkeyData) o;
      return name.equals(other.getName()) && housing.equals(other.getHousing());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, housing);
  }
}
