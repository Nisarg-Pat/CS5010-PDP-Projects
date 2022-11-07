package housing;

import monkey.Monkey;

/**
 * The Isolation representation of the {@link Housing}. It is an extension of
 * {@link AbstractHousing}. A monkey in the sanctuary should be kept in Isolation if it
 * is new to the sanctuary or to provide medical attention whenever necessary.
 * An isolation can house only a single Monkey of any size.
 */
public class Isolation extends AbstractHousing {

  /**
   * Constructor for Isolation Class.
   *
   * @param id   the id of the isolation
   * @param name the name of the isolation.
   */
  public Isolation(int id, String name) {
    super(id, name);
  }

  /**
   * Adds the monkey to the isolation.
   *
   * @param monkey the monkey to be added in the isolation
   * @throws Exception if the isolation already contains a monkey.
   */
  public void addMonkey(Monkey monkey) throws Exception {
    if (!isEmpty()) {
      throw new Exception("The Isolation " + id + " is not empty! "
              + "Please add to another isolation.");
    }
    mapOfMonkeys.put(monkey.getId(), monkey);
  }
}
