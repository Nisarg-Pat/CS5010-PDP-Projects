package housing;

import monkey.Monkey;

import java.util.HashMap;


/**
 * Abstract representation of the {@link Housing}. This class
 * contains all the method definitions that are common to the concrete
 * implementations of the {@link Housing} interface. A new implementation of
 * the interface has the option of extending this class, or directly
 * implementing all the methods.
 */
abstract class AbstractHousing implements Housing {
  protected final int id;
  protected final String name;
  protected HashMap<Integer, Monkey> mapOfMonkeys;

  /**
   * Constructor for AbstractHousing class.
   *
   * @param id   The id of the housing
   * @param name The name of the housing
   */
  protected AbstractHousing(int id, String name) {
    this.id = id;
    this.name = name;
    this.mapOfMonkeys = new HashMap<>();
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getSpecies() {
    if (mapOfMonkeys.isEmpty()) {
      return null;
    }
    return mapOfMonkeys.entrySet().iterator().next().getValue().getSpecies();
  }

  @Override
  public HashMap<Integer, Monkey> getMapOfMonkeys() {
    return new HashMap<>(mapOfMonkeys);
  }

  public boolean containsMonkeyId(int monkeyId) {
    return mapOfMonkeys.containsKey(monkeyId);
  }

  @Override
  public Monkey getMonkeyFromId(int monkeyId) throws Exception {
    if (!mapOfMonkeys.containsKey(monkeyId)) {
      throw new Exception("Monkey: " + monkeyId + " not present in Housing: " + getId());
    }
    return mapOfMonkeys.get(monkeyId);
  }

  @Override
  public Monkey removeMonkey(int monkeyId) throws Exception {
    if (!mapOfMonkeys.containsKey(monkeyId)) {
      throw new Exception("Monkey: " + monkeyId + " not present in Housing: " + getId());
    }
    return mapOfMonkeys.remove(monkeyId);
  }

  @Override
  public abstract void addMonkey(Monkey monkey) throws Exception;

  public boolean isEmpty() {
    return mapOfMonkeys.isEmpty();
  }
}
