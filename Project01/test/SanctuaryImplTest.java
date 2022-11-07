import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import monkey.FavFood;
import monkey.Sex;
import sanctuary.Sanctuary;
import sanctuary.SanctuaryImpl;
import structureddata.MonkeyData;
import structureddata.MonkeySign;
import structureddata.ShoppingList;
import structureddata.SpeciesData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Test Cases for {@link SanctuaryImpl}. Covers all the different types of scenarios that
 * could occurs in Jungle Friends Primate Sanctuary.
 */
public class SanctuaryImplTest {
  private Sanctuary sanctuary;
  private static final int n = 10;
  private static final int m = 5;

  @Before
  public void setup() throws Exception {
    sanctuary = new SanctuaryImpl();
    for (int i = 0; i < n; i++) {
      sanctuary.addIsolation(i, "I_" + i);
    }
    for (int i = 0; i < m; i++) {
      sanctuary.addEnclosure(i + n, "E_" + i, i * 10 + 5);
    }
    sanctuary.addSpecies("Drill");
    sanctuary.addSpecies("Guereza");
    sanctuary.addSpecies("Howler");
    sanctuary.addSpecies("Mangabey");
    sanctuary.addSpecies("Saki");
    sanctuary.addSpecies("Spider");
    sanctuary.addSpecies("Squirrel");

    sanctuary.addNewMonkey(0, "Alfred", "Drill", Sex.MALE, 23, 42, 5, FavFood.EGGS);
    sanctuary.addNewMonkey(1, "Bella", "Drill", Sex.MALE, 5, 44, 3, FavFood.FRUITS);
    sanctuary.addNewMonkey(2, "Charlie", "Drill", Sex.MALE, 16, 66, 7, FavFood.FRUITS);
    sanctuary.addNewMonkey(3, "Duke", "Drill", Sex.MALE, 30, 21, 9, FavFood.INSECTS);
    sanctuary.addNewMonkey(4, "Estelle", "Guereza", Sex.FEMALE, 20, 26, 2, FavFood.INSECTS);
    sanctuary.addNewMonkey(5, "Frank", "Guereza", Sex.MALE, 7, 26, 2, FavFood.LEAVES);
    sanctuary.addNewMonkey(6, "Gina", "Howler", Sex.FEMALE, 34, 26, 2, FavFood.LEAVES);
    sanctuary.addNewMonkey(7, "Helly", "Mangabey", Sex.FEMALE, 16, 26, 2, FavFood.LEAVES);
    sanctuary.addNewMonkey(8, "Isabell", "Saki", Sex.FEMALE, 18, 26, 2, FavFood.LEAVES);
  }

  @Test
  public void testAddSpecies() {
    assertTrue(sanctuary.addSpecies("Tamarin"));
    assertFalse(sanctuary.addSpecies("Drill"));
  }

  @Test
  public void testAddNewMonkey() {
    assertTrue(sanctuary.addNewMonkey(9, "Jack", "Saki", Sex.MALE, 23, 31, 4, FavFood.EGGS));
  }

  @Test
  public void testAddNewMonkeyFailSpeciesNotExist() {
    assertFalse(sanctuary.addNewMonkey(9, "Alfred", "Peacock", Sex.MALE, 23, 31, 4, FavFood.EGGS));
  }

  @Test
  public void testAddNewMonkeyFailIdAlreadyPresent() {
    assertFalse(sanctuary.addNewMonkey(0, "Jack", "Saki", Sex.MALE, 23, 31, 4, FavFood.EGGS));
  }

  @Test
  public void testAddNewMonkeyFailIsolationFull() {
    sanctuary.addNewMonkey(9, "Jack", "Saki", Sex.MALE, 23, 31, 4, FavFood.EGGS);
    //Isolations are full at this point.
    assertFalse(sanctuary.addNewMonkey(10, "Kevin", "Howler", Sex.MALE, 23, 14, 12, FavFood.EGGS));
  }

  @Test
  public void testAddNewMonkeyFailInvalidSize() {
    assertFalse(sanctuary.addNewMonkey(9, "Jack", "Saki", Sex.MALE, -23, 31, 4, FavFood.EGGS));
  }

  @Test
  public void testAddNewMonkeyFailInvalidWeight() {
    assertFalse(sanctuary.addNewMonkey(9, "Jack", "Saki", Sex.MALE, 23, 0, 4, FavFood.EGGS));
  }

  @Test
  public void testAddNewMonkeyFailInvalidAge() {
    assertFalse(sanctuary.addNewMonkey(9, "Jack", "Saki", Sex.MALE, 23, 31, -4, FavFood.EGGS));
  }


  @Test
  public void testMoveMonkeyToEnclosure() {
    assertTrue(sanctuary.moveMonkey(0, 11));
    assertTrue(sanctuary.moveMonkey(1, 11));
    assertTrue(sanctuary.moveMonkey(2, 12));
    assertTrue(sanctuary.moveMonkey(3, 12));
    assertTrue(sanctuary.moveMonkey(4, 13));

    assertTrue(sanctuary.moveMonkey(1, 12));
  }

  @Test
  public void testMoveMonkeyFailInvalidMonkeyId() {
    assertFalse(sanctuary.moveMonkey(15, 11));
  }

  @Test
  public void testMoveMonkeyFailInvalidMoveToSameHousing() {
    assertFalse(sanctuary.moveMonkey(0, 0));
  }

  @Test
  public void testMoveMonkeyFailInvalidFinalHousingId() {
    assertFalse(sanctuary.moveMonkey(0, 20));
  }

  @Test
  public void testMoveMonkeyToIsolation() {
    sanctuary.moveMonkey(0, 11);
    sanctuary.moveMonkey(1, 11);
    sanctuary.moveMonkey(2, 12);
    sanctuary.moveMonkey(3, 12);
    sanctuary.moveMonkey(4, 13);

    assertTrue(sanctuary.moveMonkey(5, 9));
    assertTrue(sanctuary.moveMonkey(6, 0));
    assertTrue(sanctuary.moveMonkey(1, 1));
  }

  @Test
  public void testMoveMonkeyFailEnclosureDifferentSpecies() {
    sanctuary.moveMonkey(2, 12);
    sanctuary.moveMonkey(3, 12);
    assertFalse(sanctuary.moveMonkey(4, 12));
  }

  @Test
  public void testMoveMonkeyFailEnclosureSpaceFilled() {
    sanctuary.moveMonkey(0, 12);
    sanctuary.moveMonkey(1, 12);
    sanctuary.moveMonkey(2, 12);
    assertFalse(sanctuary.moveMonkey(3, 12));
  }

  @Test
  public void testGetHousingIdFromMonkeyId() throws Exception {
    assertEquals(0, sanctuary.getHousingIdFromMonkeyId(0));
    assertEquals(7, sanctuary.getHousingIdFromMonkeyId(7));
    sanctuary.moveMonkey(0, 12);
    assertEquals(12, sanctuary.getHousingIdFromMonkeyId(0));
  }

  @Test(expected = Exception.class)
  public void testGetHousingIdFromMonkeyIdFail() throws Exception {
    assertEquals(0, sanctuary.getHousingIdFromMonkeyId(-5));
  }

  @Test
  public void testGetSpeciesList() {
    sanctuary.moveMonkey(0, 11);
    sanctuary.moveMonkey(1, 11);
    sanctuary.moveMonkey(2, 12);
    sanctuary.moveMonkey(3, 12);
    sanctuary.moveMonkey(4, 13);
    List<SpeciesData> expectedList = new ArrayList<>();
    expectedList.add(new SpeciesData("Drill"));
    expectedList.add(new SpeciesData("Guereza"));
    expectedList.add(new SpeciesData("Howler"));
    expectedList.add(new SpeciesData("Mangabey"));
    expectedList.add(new SpeciesData("Saki"));
    expectedList.get(0).addEnclosureName("E_1");
    expectedList.get(0).addEnclosureName("E_2");
    expectedList.get(1).addIsolationName("I_5");
    expectedList.get(1).addEnclosureName("E_3");
    expectedList.get(2).addIsolationName("I_6");
    expectedList.get(3).addIsolationName("I_7");
    expectedList.get(4).addIsolationName("I_8");
    assertEquals(expectedList, sanctuary.getSpeciesList());
  }

  @Test
  public void testLookUpSpecies() {
    sanctuary.moveMonkey(0, 11);
    sanctuary.moveMonkey(1, 11);
    sanctuary.moveMonkey(2, 12);
    SpeciesData expectedData = new SpeciesData("Drill");
    expectedData.addIsolationName("I_3");
    expectedData.addEnclosureName("E_1");
    expectedData.addEnclosureName("E_2");
    assertEquals(expectedData, sanctuary.lookUpSpecies("Drill"));
    expectedData = new SpeciesData("Saki");
    expectedData.addIsolationName("I_8");
    assertEquals(expectedData, sanctuary.lookUpSpecies("Saki"));
  }

  @Test
  public void testLookUpSpeciesFail() {
    assertNull(sanctuary.lookUpSpecies("Spider"));
  }

  @Test
  public void testListOfMonkeys() {
    sanctuary.moveMonkey(0, 11);
    sanctuary.moveMonkey(1, 11);
    sanctuary.moveMonkey(2, 12);
    sanctuary.moveMonkey(3, 12);
    sanctuary.moveMonkey(4, 13);
    List<MonkeyData> expectedList = new ArrayList<>();
    expectedList.add(new MonkeyData("Alfred", "E_1"));
    expectedList.add(new MonkeyData("Bella", "E_1"));
    expectedList.add(new MonkeyData("Charlie", "E_2"));
    expectedList.add(new MonkeyData("Duke", "E_2"));
    expectedList.add(new MonkeyData("Estelle", "E_3"));
    expectedList.add(new MonkeyData("Frank", "I_5"));
    expectedList.add(new MonkeyData("Gina", "I_6"));
    expectedList.add(new MonkeyData("Helly", "I_7"));
    expectedList.add(new MonkeyData("Isabell", "I_8"));
    assertEquals(expectedList, sanctuary.getListOfMonkeys());
  }

  @Test
  public void testGetShoppingList() {
    sanctuary.moveMonkey(0, 11);
    sanctuary.moveMonkey(1, 11);
    sanctuary.moveMonkey(2, 12);
    sanctuary.moveMonkey(3, 12);
    sanctuary.moveMonkey(4, 13);
    ShoppingList expectedList = new ShoppingList();
    expectedList.add(FavFood.LEAVES, 1100);
    expectedList.add(FavFood.EGGS, 500);
    expectedList.add(FavFood.FRUITS, 350);
    expectedList.add(FavFood.INSECTS, 1000);
    assertEquals(expectedList, sanctuary.getShoppingList());
  }

  @Test(expected = Exception.class)
  public void testGetIsolationFromIdFail() throws Exception {
    sanctuary.getIsolationFromId(100);
  }

  @Test(expected = Exception.class)
  public void testGetEnclosureFromIdFail() throws Exception {
    sanctuary.getEnclosureFromId(100);
  }

  @Test(expected = Exception.class)
  public void testAddIsolationFailDupicateId() throws Exception {
    sanctuary.addIsolation(0, "I_1");
  }

  @Test(expected = Exception.class)
  public void testAddIsolationFailInvalidId() throws Exception {
    sanctuary.addIsolation(-1, "I_1");
  }

  @Test(expected = Exception.class)
  public void testAddEnclosureFailDupicateId() throws Exception {
    sanctuary.addEnclosure(10, "E_1", 100);
  }

  @Test(expected = Exception.class)
  public void testAddEnclosureFailInvalidId() throws Exception {
    sanctuary.addEnclosure(-30, "E_1", 100);
  }

  @Test(expected = Exception.class)
  public void testAddEnclosureFailInvalidSize() throws Exception {
    sanctuary.addEnclosure(17, "E_1", 0);
  }

  @Test
  public void testEnclosureSign() throws Exception {
    sanctuary.moveMonkey(1, 12);
    sanctuary.moveMonkey(2, 12);
    List<MonkeySign> expectedSign = new ArrayList<MonkeySign>();
    expectedSign.add(new MonkeySign("Bella", Sex.MALE, FavFood.FRUITS));
    expectedSign.add(new MonkeySign("Charlie", Sex.MALE, FavFood.FRUITS));
    assertEquals(expectedSign, sanctuary.enclosureSign(12));
  }
}