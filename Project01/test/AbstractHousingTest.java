import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import housing.Enclosure;
import housing.Housing;
import housing.Isolation;
import monkey.FavFood;
import monkey.Monkey;
import monkey.MonkeyImpl;
import monkey.Sex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Test Cases for Abstract Housing. Covers all the different types of
 * scenarios that could occurs in a housing.
 */
public class AbstractHousingTest {

  private Housing testIsolation;
  private Housing testEnclosure;

  @Before
  public void setup() throws Exception {
    testIsolation = new Isolation(0, "testIsolation");
    testEnclosure = new Enclosure(1, "testEnclosure", 15);
    testIsolation.addMonkey(new MonkeyImpl(0, "Alfred", "Drill", Sex.MALE,
            23, 42, 5, FavFood.EGGS));
    testEnclosure.addMonkey(new MonkeyImpl(1, "Bella", "Spider", Sex.FEMALE,
            5, 44, 3, FavFood.FRUITS));
    testEnclosure.addMonkey(new MonkeyImpl(2, "Carlie", "Spider", Sex.MALE,
            16, 66, 7, FavFood.FRUITS));
  }

  @Test
  public void testGetId() {
    assertEquals(0, testIsolation.getId());
    assertEquals(1, testEnclosure.getId());
  }

  @Test
  public void testGetName() {
    assertEquals("testIsolation", testIsolation.getName());
    assertEquals("testEnclosure", testEnclosure.getName());
  }

  @Test
  public void testGetSpecies() {
    assertNull(new Isolation(2, "nullIsolation").getSpecies());
    assertNull(new Enclosure(3, "nullEnclosure", 20).getSpecies());
    assertEquals("Drill", testIsolation.getSpecies());
    assertEquals("Spider", testEnclosure.getSpecies());
  }

  @Test
  public void testGetMapOfMonkeys() {
    HashMap<Integer, Monkey> expectedMap = new HashMap<>();
    expectedMap.put(0, new MonkeyImpl(0, "Alfred", "Drill", Sex.MALE, 23, 42, 5, FavFood.EGGS));
    assertEquals(expectedMap.toString(), testIsolation.getMapOfMonkeys().toString());

    expectedMap = new HashMap<>();
    expectedMap.put(1, new MonkeyImpl(1, "Bella", "Spider", Sex.FEMALE, 5, 44, 3, FavFood.FRUITS));
    expectedMap.put(2, new MonkeyImpl(2, "Carlie", "Spider", Sex.MALE, 16, 66, 7, FavFood.FRUITS));
    assertEquals(expectedMap.toString(), testEnclosure.getMapOfMonkeys().toString());
  }

  @Test
  public void testContainsMonkeyId() {
    assertTrue(testIsolation.containsMonkeyId(0));
    assertTrue(testEnclosure.containsMonkeyId(1));
    assertTrue(testEnclosure.containsMonkeyId(2));
    assertFalse(testIsolation.containsMonkeyId(1));
    assertFalse(testEnclosure.containsMonkeyId(0));
    assertFalse(testIsolation.containsMonkeyId(10));
    assertFalse(testEnclosure.containsMonkeyId(-10));
  }

  @Test
  public void testGetMonkeyFromId() throws Exception {
    Monkey testMonkey = new MonkeyImpl(0, "Alfred", "Drill", Sex.MALE, 23, 42, 5, FavFood.EGGS);
    assertEquals(testMonkey.toString(), testIsolation.getMonkeyFromId(0).toString());

    testMonkey = new MonkeyImpl(1, "Bella", "Spider", Sex.FEMALE, 5, 44, 3, FavFood.FRUITS);
    assertEquals(testMonkey.toString(), testEnclosure.getMonkeyFromId(1).toString());

  }

  @Test
  public void testRemoveMonkey() throws Exception {
    Monkey testMonkey = new MonkeyImpl(0, "Alfred", "Drill", Sex.MALE, 23, 42, 5, FavFood.EGGS);
    assertEquals(testMonkey.toString(), testIsolation.removeMonkey(0).toString());

    testMonkey = new MonkeyImpl(1, "Bella", "Spider", Sex.FEMALE, 5, 44, 3, FavFood.FRUITS);
    assertEquals(testMonkey.toString(), testEnclosure.removeMonkey(1).toString());
  }

  @Test
  public void testIsEmpty() {
    assertFalse(testIsolation.isEmpty());
    assertFalse(testEnclosure.isEmpty());
    assertTrue(new Isolation(2, "nullIsolation").isEmpty());
    assertTrue(new Enclosure(3, "nullEnclosure", 20).isEmpty());
  }
}