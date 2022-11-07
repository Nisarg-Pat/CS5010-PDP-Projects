package sanctuary;

import housing.Housing;
import monkey.FavFood;
import monkey.Sex;
import structureddata.MonkeyData;
import structureddata.MonkeySign;
import structureddata.ShoppingList;
import structureddata.SpeciesData;

import java.util.List;

/**
 * A interface to represent a Sanctuary specific to Primates.
 * Sanctuary depends on different types of {@link housing.Housing} to keep monkeys.
 */
public interface Sanctuary {

  /**
   * Adds a new Monkey in the Sanctuary.
   *
   * @param id      The unique id of the new Monkey
   * @param name    The name of the new Monkey
   * @param species The species of the new Monkey
   * @param sex     The sex of the new Monkey
   * @param size    The size of the new Monkey in cms
   * @param weight  The weight of the new Monkey in kgs
   * @param age     The age of the new Monkey
   * @param favFood The Favourite Food of the new Monkey
   * @return whether the Monkey is successfully added to the Sanctuary or not
   */
  boolean addNewMonkey(int id, String name, String species, Sex sex, double size,
                       double weight, int age, FavFood favFood);

  /**
   * Adds a new species for the sanctuary to host.
   *
   * @param species The unique name of the species
   * @return whether the species name got added to the sanctuary or not
   */
  boolean addSpecies(String species);

  /**
   * Moves a {@link monkey.Monkey} to a different {@link housing.Housing} from its original one.
   *
   * @param monkeyId       the id of the monkey that has to be moved.
   * @param finalHousingId the housing id to which the monkey needs to be moved.
   * @return true if the monkey is successfully moved to new Housing
   */
  boolean moveMonkey(int monkeyId, int finalHousingId);

  /**
   * Gives the housing id of the monkey.
   *
   * @param monkeyId the id of the monkey of which the housing id is required
   * @return the id of the current housing of the monkey.
   * @throws Exception if the monkey is not present in the sanctuary
   */
  int getHousingIdFromMonkeyId(int monkeyId) throws Exception;

  /**
   * Gives the list of the species in a specific format.
   *
   * @return the list of the species in a specific format
   */
  List<SpeciesData> getSpeciesList();

  /**
   * Looks up where a particular species is housed in this sanctuary.
   *
   * @param species The species whose housing needs to be found in the sanctuary
   * @return the species data in a specific format
   */
  SpeciesData lookUpSpecies(String species);

  /**
   * Gives the list of all the monkeys currently housed in the Sanctuary.
   *
   * @return the list of monkeys with its housing
   */
  List<MonkeyData> getListOfMonkeys();

  /**
   * Gives the shopping list required for the Monkeys of the Sanctuary.
   *
   * @return the shopping list
   */
  ShoppingList getShoppingList();

  /**
   * Gives the {@link housing.Isolation} from the id.
   *
   * @param id the id of the Isolation
   * @return the isolation corresponding to that id
   * @throws Exception if isolation for that id could not be found.
   */
  Housing getIsolationFromId(int id) throws Exception;

  /**
   * Gives the {@link housing.Enclosure} from the id.
   *
   * @param id the id of the Enclosure
   * @return the enclosure corresponding to that id
   * @throws Exception if enclosure for that id could not be found.
   */
  Housing getEnclosureFromId(int id) throws Exception;

  /**
   * Adds a new isolation to the sanctuary.
   *
   * @param id   id of the isolation
   * @param name the name of the isolation
   * @throws Exception if isolation could not be added
   */
  void addIsolation(int id, String name) throws Exception;

  /**
   * Adds a new enclosure to the sanctuary.
   *
   * @param id   id of the enclosure
   * @param name the name of the enclosure
   * @param size the maximum size of the enclosure
   * @throws Exception if enclosure could not be added
   */
  void addEnclosure(int id, String name, int size) throws Exception;

  /**
   * Provide the enclosure sign for the enclosure id in a specific format.
   *
   * @param enclosureId the id of the enclosure
   * @return the specific format of the enclosure sign
   * @throws Exception if enclosure id is invalid or no monkeys are present in the enclosure
   */
  List<MonkeySign> enclosureSign(int enclosureId) throws Exception;
}
