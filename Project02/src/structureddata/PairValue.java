package structureddata;

import java.util.Objects;

/**
 * Data structure to store constraint pair values. Contains constraint value and the current value.
 */
public class PairValue {
  private final int maxValue;
  private int currentValue;

  /**
   * Contructor for PairValue class.
   *
   * @param maxValue     maximum possible value
   * @param currentValue current value
   */
  public PairValue(int maxValue, int currentValue) {
    this.maxValue = maxValue;
    this.currentValue = currentValue;
  }

  /**
   * Returns the maximum possible value.
   *
   * @return the maximum possible value
   */
  public int getMaxValue() {
    return maxValue;
  }

  /**
   * Returns the current value.
   *
   * @return the current value
   */
  public int getCurrentValue() {
    return currentValue;
  }

  /**
   * Adds a value to the current value.
   *
   * @param currentValue the value to be added
   */
  public void addCurrentValue(int currentValue) {
    this.currentValue += currentValue;
  }

  /**
   * Checks if two PairValues are equal.
   *
   * @param o other Object
   * @return true if both PairValues have same maximum value and current value
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PairValue)) {
      return false;
    }
    PairValue pairValue = (PairValue) o;
    return maxValue == pairValue.maxValue && currentValue == pairValue.currentValue;
  }

  /**
   * Hashcode for PairValue.
   *
   * @return hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(maxValue, currentValue);
  }
}
