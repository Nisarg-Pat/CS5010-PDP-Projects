import org.junit.Before;
import org.junit.Test;

import monkey.FavFood;
import monkey.Monkey;
import monkey.MonkeyImpl;
import monkey.Sex;

import static org.junit.Assert.assertEquals;

/**
 * Test cases for {@link MonkeyImpl}. Covers all the scenarios for a particular Monkey.
 */
public class MonkeyImplTest {

  Monkey testMonkey;

  @Before
  public void setup() {
    testMonkey = new MonkeyImpl(0, "Alfred", "Drill", Sex.MALE,
            23, 42, 5, FavFood.EGGS);
  }

  @Test
  public void testGetId() {
    assertEquals(0, testMonkey.getId());
  }

  @Test
  public void testGetName() {
    assertEquals("Alfred", testMonkey.getName());
  }

  @Test
  public void testGetSpecies() {
    assertEquals("Drill", testMonkey.getSpecies());
  }

  @Test
  public void testGetSex() {
    assertEquals(Sex.MALE, testMonkey.getSex());
  }

  @Test
  public void testGetSize() {
    assertEquals(23, testMonkey.getSize(), 0);
  }

  @Test
  public void testGetWeight() {
    assertEquals(42, testMonkey.getWeight(), 0);
  }

  @Test
  public void testGetAge() {
    assertEquals(5, testMonkey.getAge());
  }

  @Test
  public void testGetFavFood() {
    assertEquals(FavFood.EGGS, testMonkey.getFavFood());
  }

  @Test
  public void testGetFavFoodAmount() {
    assertEquals(MonkeyImpl.REQUIRED_QUANTITY_LARGE, testMonkey.getFavFoodAmount());
    assertEquals(MonkeyImpl.REQUIRED_QUANTITY_MEDIUM, new MonkeyImpl(0, "Alfred",
            "Drill", Sex.MALE, 16, 42, 5, FavFood.EGGS).getFavFoodAmount());
    assertEquals(MonkeyImpl.REQUIRED_QUANTITY_SMALL, new MonkeyImpl(0, "Alfred",
            "Drill", Sex.MALE, 5, 42, 5, FavFood.EGGS).getFavFoodAmount());
  }

  @Test
  public void testGetSizeCategory() {
    assertEquals(MonkeyImpl.MONKEY_SIZE_LIMIT_LARGE, testMonkey.getSizeCategory());
    assertEquals(MonkeyImpl.MONKEY_SIZE_LIMIT_MEDIUM, new MonkeyImpl(0, "Alfred",
            "Drill", Sex.MALE, 16, 42, 5, FavFood.EGGS).getSizeCategory());
    assertEquals(MonkeyImpl.MONKEY_SIZE_LIMIT_SMALL, new MonkeyImpl(0, "Alfred",
            "Drill", Sex.MALE, 5, 42, 5, FavFood.EGGS).getSizeCategory());
  }

  @Test
  public void testSetSize() {
    testMonkey.setSize(16);
    assertEquals(16, testMonkey.getSize(), 0);
  }

  @Test
  public void testSetWeight() {
    testMonkey.setWeight(22);
    assertEquals(22, testMonkey.getWeight(), 0);
  }

  @Test
  public void testSetAge() {
    testMonkey.setAge(6);
    assertEquals(6, testMonkey.getAge(), 0);
  }

  @Test
  public void testSetFavFood() {
    testMonkey.setFavFood(FavFood.LEAVES);
    assertEquals(FavFood.LEAVES, testMonkey.getFavFood());
  }
}