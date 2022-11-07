package sanctuary;

import housing.Enclosure;
import housing.Housing;
import housing.Isolation;
import monkey.FavFood;
import monkey.Monkey;
import monkey.MonkeyImpl;
import monkey.Sex;
import structureddata.MonkeyData;
import structureddata.MonkeySign;
import structureddata.ShoppingList;
import structureddata.SpeciesData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


/**
 * The {@link Sanctuary} implementation for <a href="https://www.junglefriends.org/monkeys/">
 * Jungle Friends Primate Sanctuary</a>.
 * This Sanctuary contains the Housing in the form of
 * {@link housing.Isolation} and {@link housing.Enclosure}.
 * It assumes that the incoming Monkey should be transferred to
 * the Isolation first and given medical attention.
 * If there are no more space available in the isolation,
 * the monkey needs to be transferred to another Sanctuary.
 */
public class SanctuaryImpl implements Sanctuary {
  private final HashMap<Integer, Housing> isolationMap;
  private final HashMap<Integer, Housing> enclosureMap;
  private final HashSet<String> speciesSet;

  /**
   * A constructor for SanctuaryImpl.
   */
  public SanctuaryImpl() {
    isolationMap = new HashMap<>();
    enclosureMap = new HashMap<>();
    speciesSet = new HashSet<>();
  }

  /**
   * Adds a new Monkey in the Sanctuary. To add a monkey in the Sanctuary:
   * The species needs to be added first to the Species List.
   * The Monkey Id has to be Unique.
   * There should be atleast one free Isolation.
   * Monkey's details should be valid.
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
  @Override
  public boolean addNewMonkey(int id, String name, String species, Sex sex,
                              double size, double weight, int age, FavFood favFood) {
    if (!speciesSet.contains(species)) {
      System.out.println("The species is not found in the Sanctuary. "
              + "Please add the Species first.");
      return false;
    }
    try {
      checkId(id);
      int freeIsolationId = getFreeIsolation();
      Housing freeIsolation = isolationMap.get(freeIsolationId);
      Monkey newMonkey = new MonkeyImpl(id, name, species, sex, size, weight, age, favFood);
      freeIsolation.addMonkey(newMonkey);
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  private int getFreeIsolation() throws Exception {
    for (Housing isolation : isolationMap.values()) {
      if (isolation.isEmpty()) {
        return isolation.getId();
      }
    }
    throw new Exception("No space in this sanctuary. Send the monkey to another sanctuary.");
  }

  @Override
  public boolean addSpecies(String species) {
    if (speciesSet.contains(species)) {
      System.out.println("Species: " + species + " is already added");
      return false;
    } else {
      speciesSet.add(species);
      return true;
    }
  }

  @Override
  public boolean moveMonkey(int monkeyId, int finalHousingId) {
    try {
      int initialHousingId = getHousingIdFromMonkeyId(monkeyId);
      moveMonkey(monkeyId, initialHousingId, finalHousingId);
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  private void moveMonkey(int monkeyId, int initialHousingId, int finalHousingId) throws Exception {
    if (initialHousingId == finalHousingId) {
      throw new Exception("The entered initial and final housing for Monkey: "
              + monkeyId + " cannot be same");
    }
    Housing initialHousing = getHousingFromId(initialHousingId);
    Monkey monkey = initialHousing.getMonkeyFromId(monkeyId);
    Housing finalHousing = getHousingFromId(finalHousingId);
    finalHousing.addMonkey(monkey);
    initialHousing.removeMonkey(monkeyId);
  }

  @Override
  public int getHousingIdFromMonkeyId(int monkeyId) throws Exception {
    for (Housing isolation : isolationMap.values()) {
      if (isolation.containsMonkeyId(monkeyId)) {
        return isolation.getId();
      }
    }
    for (Housing enclosure : enclosureMap.values()) {
      if (enclosure.containsMonkeyId(monkeyId)) {
        return enclosure.getId();
      }
    }
    throw new Exception("No housing for monkeyId = " + monkeyId + " found");
  }

  private Housing getHousingFromId(int initialHousing) throws Exception {
    Housing housing = isolationMap.get(initialHousing);
    if (housing == null) {
      housing = enclosureMap.get(initialHousing);
    }
    if (housing == null) {
      throw new Exception("No such housing for id = " + initialHousing + " present.");
    }
    return housing;
  }

  /**
   * Gives the list of the species in a specific format.
   *
   * @return the list of the species with the names of the isolations and enclosures
   *         where the species is being currently hosted.
   */
  @Override
  public List<SpeciesData> getSpeciesList() {
    HashMap<String, SpeciesData> speciesMap = new HashMap<>();
    for (Housing isolation : isolationMap.values()) {
      String species = isolation.getSpecies();
      if (species == null) {
        continue;
      }
      SpeciesData speciesData;
      if (!speciesMap.containsKey(species)) {
        speciesData = new SpeciesData(species);
      } else {
        speciesData = speciesMap.get(species);
      }
      speciesData.addIsolationName(isolation.getName());
      speciesMap.put(species, speciesData);
    }
    for (Housing enclosure : enclosureMap.values()) {
      String species = enclosure.getSpecies();
      if (species == null) {
        continue;
      }
      SpeciesData speciesData;
      if (!speciesMap.containsKey(species)) {
        speciesData = new SpeciesData(species);
        speciesMap.put(species, speciesData);
      } else {
        speciesData = speciesMap.get(species);
      }
      speciesData.addEnclosureName(enclosure.getName());
    }
    for (SpeciesData speciesData : speciesMap.values()) {
      Collections.sort(speciesData.getIsolation());
      Collections.sort(speciesData.getEnclosure());
    }
    List<SpeciesData> speciesDataList = new ArrayList<>(speciesMap.values());
    if (speciesDataList.isEmpty()) {
      System.out.println("The Sanctuary is empty!!!");
    }
    Collections.sort(speciesDataList);
    return speciesDataList;
  }

  /**
   * Looks up where a particular species is housed in this sanctuary.
   *
   * @param species The species whose housing needs to be found in the sanctuary
   * @return the species with the names of the isolations and enclosures where the species is
   *         being currently hosted. Returns null if the species is not being hosted
   *         in the Sanctuary.
   */
  @Override
  public SpeciesData lookUpSpecies(String species) {
    SpeciesData speciesData = new SpeciesData(species);
    for (Housing isolation : isolationMap.values()) {
      if (species.equals(isolation.getSpecies())) {
        speciesData.addIsolationName(isolation.getName());
      }
    }
    for (Housing enclosure : enclosureMap.values()) {
      if (species.equals(enclosure.getSpecies())) {
        speciesData.addEnclosureName(enclosure.getName());
      }
    }
    if (speciesData.getEnclosure().isEmpty() && speciesData.getIsolation().isEmpty()) {
      System.out.println("The species: " + species
              + " is not housed currently anywhere at the Sanctuary.");
      return null;
    }
    Collections.sort(speciesData.getIsolation());
    Collections.sort(speciesData.getEnclosure());
    return speciesData;
  }

  @Override
  public List<MonkeyData> getListOfMonkeys() {
    List<MonkeyData> monkeyData = new ArrayList<>();
    for (Housing isolation : isolationMap.values()) {
      for (Monkey monkey : isolation.getMapOfMonkeys().values()) {
        monkeyData.add(new MonkeyData(monkey.getName(), isolation.getName()));
      }
    }
    for (Housing enclosure : enclosureMap.values()) {
      for (Monkey monkey : enclosure.getMapOfMonkeys().values()) {
        monkeyData.add(new MonkeyData(monkey.getName(), enclosure.getName()));
      }
    }
    if (monkeyData.isEmpty()) {
      System.out.println("The Sanctuary is empty!!!");
    }
    Collections.sort(monkeyData);
    return monkeyData;
  }

  @Override
  public ShoppingList getShoppingList() {
    ShoppingList shoppingList = new ShoppingList();
    for (Housing isolation : isolationMap.values()) {
      for (Monkey monkey : isolation.getMapOfMonkeys().values()) {
        shoppingList.add(monkey.getFavFood(), monkey.getFavFoodAmount());
      }
    }
    for (Housing enclosure : enclosureMap.values()) {
      for (Monkey monkey : enclosure.getMapOfMonkeys().values()) {
        shoppingList.add(monkey.getFavFood(), monkey.getFavFoodAmount());
      }
    }
    return shoppingList;
  }

  @Override
  public Housing getIsolationFromId(int id) throws Exception {
    if (!isolationMap.containsKey(id)) {
      throw new Exception("The isolation with id: " + id + " is not present.");
    }
    return isolationMap.get(id);
  }

  @Override
  public Housing getEnclosureFromId(int id) throws Exception {
    if (!enclosureMap.containsKey(id)) {
      throw new Exception("The enclosure with id: " + id + " is not present.");
    }
    return enclosureMap.get(id);
  }

  @Override
  public void addIsolation(int id, String name) throws Exception {
    if (isolationMap.containsKey(id) || enclosureMap.containsKey(id)) {
      throw new Exception("There is already a housing with this key");
    }
    if (id < 0) {
      throw new Exception("The isolation details is invalid. Please check again");
    }
    Isolation newIsolation = new Isolation(id, name);
    isolationMap.put(newIsolation.getId(), newIsolation);
  }

  @Override
  public void addEnclosure(int id, String name, int size) throws Exception {
    if (isolationMap.containsKey(id) || enclosureMap.containsKey(id)) {
      throw new Exception("There is already a housing with this key");
    }
    if (id < 0 || size <= 0) {
      throw new Exception("The isolation details is invalid. Please check again");
    }
    Enclosure newEnclosure = new Enclosure(id, name, size);
    enclosureMap.put(newEnclosure.getId(), newEnclosure);
  }

  @Override
  public ArrayList<MonkeySign> enclosureSign(int enclosureId) {
    try {
      getEnclosureFromId(enclosureId);
      Enclosure enclosure = (Enclosure) enclosureMap.get(enclosureId);
      return enclosure.enclosureSign();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  private void checkId(int id) throws Exception {
    for (Housing isolation : isolationMap.values()) {
      if (isolation.containsMonkeyId(id)) {
        throw new Exception("The specified id is already present, Please select a new id.");
      }
    }
    for (Housing enclosure : enclosureMap.values()) {
      if (enclosure.containsMonkeyId(id)) {
        throw new Exception("The specified id is already present, Please select a new id.");
      }
    }
  }
}
