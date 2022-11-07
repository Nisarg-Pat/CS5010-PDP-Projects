package housing;

import monkey.Monkey;

import java.util.HashMap;

/**
 * Housing interface to represent different types of housing available for a Sanctuary
 * to keep Monkeys. It assumes that each housing can have Monkeys of same species only.
 */
public interface Housing {
  /**
   * Gives the id of the housing.
   *
   * @return the id of the housing
   */
  int getId();

  /**
   * Gives the name of the housing.
   *
   * @return the name of the housing
   */
  String getName();

  /**
   * Gives the species kept in the housing.
   *
   * @return the species kept in the housing
   */
  String getSpecies();

  /**
   * Gives a map of monkeys that are currently present in the housing.
   *
   * @return the map of monkeys with their ids currently present in the housing
   */
  HashMap<Integer, Monkey> getMapOfMonkeys();

  /**
   * Checks if the housing contains a specific monkey.
   *
   * @param monkeyId the id of the monkey to be checked
   * @return true if the monkey is present in the housing
   */
  boolean containsMonkeyId(int monkeyId);

  /**
   * Gives the monkey from its corresponding id.
   *
   * @param monkeyId the id of the monkey to be found
   * @return the Monkey corresponding to the id.
   * @throws Exception if the monkey is not present in the housing.
   */
  Monkey getMonkeyFromId(int monkeyId) throws Exception;

  /**
   * Removes a particular monkey from the housing.
   *
   * @param monkeyId id of the monkey to be removed
   * @return the Monkey that has been removed.
   * @throws Exception if the Monkey is not present in the housing.
   */
  Monkey removeMonkey(int monkeyId) throws Exception;

  /**
   * Adds a monkey to the housing.
   *
   * @param monkey The monkey that has to be added in the housing
   * @throws Exception If the monkey cannot be added to the housing
   */
  void addMonkey(Monkey monkey) throws Exception;

  /**
   * Checks if the housing is currently empty.
   *
   * @return true id the housing is currently empty
   */
  boolean isEmpty();
}
