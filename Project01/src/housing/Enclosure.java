package housing;

import monkey.Monkey;
import monkey.MonkeyImpl;
import structureddata.MonkeySign;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The Enclosure representation of the {@link Housing}. It is an extension of
 * {@link AbstractHousing}. An enclosure can contain troops of monkeys having same species.
 * Empty enclosures can host new species.
 * The capacity of an enclosure is dependent upon the size of the enclosure
 * and the size of the monkeys in the enclosure.
 */
public class Enclosure extends AbstractHousing {
  private final int maxSpace;
  private int filledSpace;

  public static final int REQUIRED_SPACE_SMALL = 1;
  public static final int REQUIRED_SPACE_MEDIUM = 5;
  public static final int REQUIRED_SPACE_LARGE = 10;

  /**
   * Constructor for Enclosure class.
   *
   * @param id   the id of the enclosure
   * @param name the name of the enclosure
   * @param size the maximum size of the enclosure
   */
  public Enclosure(int id, String name, int size) {
    super(id, name);
    this.maxSpace = size;
    this.filledSpace = 0;
  }

  /**
   * Adds the monkey to the enclosure.
   *
   * @param monkey the Monkey to be added to the enclosure
   * @throws Exception If the monkey's species and current species in enclosure is different,
   *                   or if there is not enough space in the enclosure for the monkey.
   */
  @Override
  public void addMonkey(Monkey monkey) throws Exception {
    if (!isEmpty() && !getSpecies().equals(monkey.getSpecies())) {
      throw new Exception("The enclosure " + id + " contains different species than Monkey: "
              + monkey.getId() + ".");
    } else if (!isSpaceAvailable(monkey.getSizeCategory())) {
      throw new Exception("The enclosure " + id + " does not contain enough space to house Monkey: "
              + monkey.getId() + ". Required space: " + getRequiredSpace(monkey.getId())
              + " square meter. Available space: " + getAvailableSpace() + " square meter.");
    }
    mapOfMonkeys.put(monkey.getId(), monkey);
    filledSpace += getRequiredSpace(monkey.getSizeCategory());
  }

  private boolean isSpaceAvailable(int sizeCategory) {
    return getRequiredSpace(sizeCategory) <= getAvailableSpace();
  }

  private int getRequiredSpace(int sizeCategory) {
    if (sizeCategory == MonkeyImpl.MONKEY_SIZE_LIMIT_SMALL) {
      return REQUIRED_SPACE_SMALL;
    } else if (sizeCategory == MonkeyImpl.MONKEY_SIZE_LIMIT_MEDIUM) {
      return REQUIRED_SPACE_MEDIUM;
    } else {
      return REQUIRED_SPACE_LARGE;
    }
  }

  @Override
  public Monkey removeMonkey(int monkeyId) throws Exception {
    Monkey removedMonkey = super.removeMonkey(monkeyId);
    filledSpace -= getRequiredSpace(removedMonkey.getSizeCategory());
    return removedMonkey;
  }

  /**
   * Generates a sign for the enclosure containing the name, age and favourite food
   * of all the monkeys currently in the enclosure.
   *
   * @return the specific format of the enclosure sign
   * @throws Exception if the enclosure is empty
   */
  public ArrayList<MonkeySign> enclosureSign() throws Exception {
    if (isEmpty()) {
      throw new Exception("The enclosure is empty.");
    }
    ArrayList<MonkeySign> enclosureSign = new ArrayList<>();
    for (Monkey monkey : mapOfMonkeys.values()) {
      enclosureSign.add(new MonkeySign(monkey.getName(), monkey.getSex(), monkey.getFavFood()));
    }
    Collections.sort(enclosureSign);
    return enclosureSign;
  }

  /**
   * Gives the maximum space of the enclosure.
   *
   * @return the maximum space of the enclosure
   */
  public int getMaxSpace() {
    return maxSpace;
  }

  /**
   * Gives the filled space of the enclosure.
   *
   * @return the filled space of the enclosure
   */
  public int getFilledSpace() {
    return filledSpace;
  }

  /**
   * Gives the available space in the enclosure.
   *
   * @return the available space in the enclosure
   */
  public int getAvailableSpace() {
    return maxSpace - filledSpace;
  }
}
