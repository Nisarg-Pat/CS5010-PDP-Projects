package monkey;

/**
 * Enum containing the Sex.
 */
public enum Sex {
  MALE, FEMALE;

  /**
   * String representation of Sex.
   *
   * @return String representation of Sex
   */
  @Override
  public String toString() {
    if (this.equals(MALE)) {
      return "Male";
    } else {
      return "Female";
    }
  }
}
