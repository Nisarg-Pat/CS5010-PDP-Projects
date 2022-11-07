package structureddata;

import java.util.Random;

/**
 * RandomImpl encapsulation of Random class.
 * Returns a predefined set of integers mentioned in constructor.
 */
public class RandomImpl {

  private final Random random;
  private int[] randomValues;
  private int index;

  /**
   * Constructor without predefined set of integers.
   */
  public RandomImpl() {
    random = new Random();
  }

  /**
   * Constructor with predefined set of integers.
   *
   * @param values values to be returned when called getRandomInt in sequence
   */
  public RandomImpl(int... values) {
    random = null;
    this.randomValues = values;
    this.index = 0;
  }

  /**
   * Gives a random number between the given limits.
   *
   * @param lower lower limit
   * @param upper upper limit
   * @return a random number between the given limits
   */
  public int getRandomInt(int lower, int upper) {
    if (random != null) {
      return (random.nextInt(upper - lower + 1) + lower);
    } else {
      int value = randomValues[index];
      index = (index + 1) % randomValues.length;
      return value;
    }
  }
}
