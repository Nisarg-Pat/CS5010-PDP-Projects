import monkey.FavFood;
import monkey.Sex;
import sanctuary.Sanctuary;
import sanctuary.SanctuaryImpl;
import structureddata.MonkeyData;
import structureddata.MonkeySign;
import structureddata.ShoppingList;
import structureddata.SpeciesData;

import java.util.List;

/**
 * A Driver class to create one run of program. It has all the methods that
 * user can call in sanctuary.
 */
public class SanctuaryDriver {

  private static final int n = 10;
  private static final int m = 5;

  /**
   * Main() method to run the driver class.
   * @param args arguments for main method. Non for this case.
   * @throws Exception Can throw exception sometimes
   */
  public static void main(String... args) throws Exception {
    Sanctuary sanctuary = new SanctuaryImpl();

    //Initializing n isolations and m enclosures
    for (int i = 0; i < n; i++) {
      sanctuary.addIsolation(i, "I_" + i);
    }
    for (int i = 0; i < m; i++) {
      sanctuary.addEnclosure(i + n, "E_" + i, i * 10 + 5);
    }

    //Adding Primate Species:
    sanctuary.addSpecies("Drill");
    sanctuary.addSpecies("Guereza");
    sanctuary.addSpecies("Howler");
    sanctuary.addSpecies("Mangabey");
    sanctuary.addSpecies("Saki");
    sanctuary.addSpecies("Spider");
    sanctuary.addSpecies("Squirrel");

    //Adding 9 monkeys to empty isolations:
    sanctuary.addNewMonkey(0, "Alfred", "Drill", Sex.MALE, 23, 42, 5, FavFood.EGGS);
    sanctuary.addNewMonkey(1, "Bella", "Drill", Sex.MALE, 5, 44, 3, FavFood.FRUITS);
    sanctuary.addNewMonkey(2, "Carlie", "Drill", Sex.MALE, 16, 66, 7, FavFood.FRUITS);
    sanctuary.addNewMonkey(3, "Duke", "Drill", Sex.MALE, 30, 21, 9, FavFood.INSECTS);
    sanctuary.addNewMonkey(4, "Estelle", "Guereza", Sex.FEMALE, 20, 26, 2, FavFood.INSECTS);
    sanctuary.addNewMonkey(5, "Frank", "Guereza", Sex.MALE, 7, 26, 2, FavFood.LEAVES);
    sanctuary.addNewMonkey(6, "Gina", "Howler", Sex.FEMALE, 34, 26, 2, FavFood.LEAVES);
    sanctuary.addNewMonkey(7, "Helly", "Mangabey", Sex.FEMALE, 16, 26, 2, FavFood.LEAVES);
    sanctuary.addNewMonkey(8, "Isabell", "Saki", Sex.FEMALE, 18, 26, 2, FavFood.LEAVES);

    //Trying to add a species that is already present:
    System.out.println("Adding species already present: ");
    sanctuary.addSpecies("Drill");

    System.out.println();
    //Adding a new Monkey:
    System.out.println("Adding a different Species' Monkey : ");
    sanctuary.addNewMonkey(9, "Alfred", "Peacock", Sex.MALE, 23, 31, 4, FavFood.EGGS);

    System.out.println();
    System.out.println("Adding a monkey with same id:");
    sanctuary.addNewMonkey(0, "Jack", "Saki", Sex.MALE, 23, 31, 4, FavFood.EGGS);

    System.out.println();
    System.out.println("Adding a monkey with invalid arguments");
    sanctuary.addNewMonkey(9, "Jack", "Saki", Sex.MALE, -23, 31, 4, FavFood.EGGS);
    sanctuary.addNewMonkey(9, "Jack", "Saki", Sex.MALE, 23, 0, 4, FavFood.EGGS);
    sanctuary.addNewMonkey(9, "Jack", "Saki", Sex.MALE, 23, 31, -4, FavFood.EGGS);

    System.out.println();
    System.out.println("Moving an invalid id Monkey:");
    sanctuary.moveMonkey(15, 11);

    System.out.println();
    System.out.println("Moving monkey Having same initial and final housing:");
    sanctuary.moveMonkey(0, 0);

    System.out.println();
    System.out.println("Moving monkey Having invalid finalHousing id:");
    sanctuary.moveMonkey(0, 20);

    System.out.println();
    System.out.println("Moving monkey to a filled Isolation:");
    sanctuary.moveMonkey(0, 5);

    sanctuary.moveMonkey(0, 11);
    sanctuary.moveMonkey(1, 11);
    sanctuary.moveMonkey(2, 12);
    sanctuary.moveMonkey(3, 12);
    sanctuary.moveMonkey(4, 13);

    System.out.println();
    System.out.println("Getting the current List of Monkeys:");
    print(sanctuary.getListOfMonkeys());

    System.out.println();
    System.out.println("Moving different Species Monkeys into same enclosure:");
    sanctuary.moveMonkey(0, 13);

    System.out.println();
    System.out.println("Moving species into enclosure that does not have enough room:");
    sanctuary.moveMonkey(2, 11);

    System.out.println();
    System.out.println("Get Housing Id of a Monkey: ");
    System.out.println("Housing Id for Monkey 3 is: " + sanctuary.getHousingIdFromMonkeyId(3));

    System.out.println();
    System.out.println("Get the list of Species in the Sanctuary:");
    printSpeciesList(sanctuary.getSpeciesList());

    System.out.println();
    System.out.println("Lookup for a particular species in Sanctuary:");
    printLookUp(sanctuary.lookUpSpecies("Guereza"));

    System.out.println();
    System.out.println("Lookup for a species not in the Sanctuary");
    printLookUp(sanctuary.lookUpSpecies("Spider"));

    System.out.println();
    System.out.println("Sign for Enclosure E_3:");
    printEnclosureSign(sanctuary.enclosureSign(12));

    System.out.println();
    System.out.println("Sign for Empty Enclosure:");
    printEnclosureSign(sanctuary.enclosureSign(10));

    System.out.println();
    System.out.println("Get the Shopping List: ");
    printShoppingList(sanctuary.getShoppingList());
  }

  private static void printShoppingList(ShoppingList shoppingList) {
    System.out.println("Shopping List:");
    System.out.println(shoppingList);
  }

  private static void printEnclosureSign(List<MonkeySign> monkeysigns) {
    if (monkeysigns == null) {
      return;
    }
    System.out.println("Enclosure sign: ");
    for (MonkeySign monkeySign : monkeysigns) {
      System.out.println(monkeySign);
    }
  }

  private static void printLookUp(SpeciesData data) {
    if (data == null) {
      return;
    }
    System.out.println("Housings of " + data.getSpecies() + ":");
    if (!data.getIsolation().isEmpty()) {
      System.out.print("\tIsolations: ");
      for (String isolation : data.getIsolation()) {
        System.out.print(isolation + " ");
      }
      System.out.println();
    }
    if (!data.getEnclosure().isEmpty()) {
      System.out.print("\tEnclosures: ");
      for (String enclosure : data.getEnclosure()) {
        System.out.print(enclosure + " ");
      }
      System.out.println();
    }
  }

  private static void print(List<MonkeyData> listOfMonkeys) {
    System.out.println("List of Monkeys in Sanctuary:");
    for (MonkeyData monkeyData : listOfMonkeys) {
      System.out.println(monkeyData);
    }
  }

  private static void printSpeciesList(List<SpeciesData> speciesList) {
    System.out.println("Species in the Sanctuary with current Housing:");
    for (SpeciesData speciesData : speciesList) {
      System.out.println(speciesData.getSpecies() + ":");
      if (!speciesData.getIsolation().isEmpty()) {
        System.out.print("\tIsolations: ");
        for (String isolation : speciesData.getIsolation()) {
          System.out.print(isolation + " ");
        }
        System.out.println();
      }
      if (!speciesData.getEnclosure().isEmpty()) {
        System.out.print("\tEnclosures: ");
        for (String enclosure : speciesData.getEnclosure()) {
          System.out.print(enclosure + " ");
        }
        System.out.println();
      }
    }
  }
}
