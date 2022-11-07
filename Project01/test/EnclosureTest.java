import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import housing.Enclosure;
import monkey.FavFood;
import monkey.MonkeyImpl;
import monkey.Sex;
import structureddata.MonkeySign;

import static org.junit.Assert.assertEquals;

/**
 * Test Cases specific to {@link Enclosure} Housing.
 */
public class EnclosureTest {

  private Enclosure testEnclosure;

  @Before
  public void setup() throws Exception {
    testEnclosure = new Enclosure(1, "testEnclosure", 15);
    testEnclosure.addMonkey(new MonkeyImpl(1, "Bella", "Spider", Sex.FEMALE,
            5, 44, 3, FavFood.FRUITS));
    testEnclosure.addMonkey(new MonkeyImpl(2, "Charlie", "Spider", Sex.MALE,
            16, 66, 7, FavFood.FRUITS));
  }

  @Test(expected = Exception.class)
  public void testGetMonkeyFromIdFailEnclosure() throws Exception {
    testEnclosure.getMonkeyFromId(0);
  }

  @Test(expected = Exception.class)
  public void testRemoveMonkeyFailEnclosure() throws Exception {
    testEnclosure.getMonkeyFromId(0);
  }

  @Test(expected = Exception.class)
  public void testAddMonkeyFailEnclosureNoSpace() throws Exception {
    testEnclosure.addMonkey(new MonkeyImpl(4, "Estelle", "Spider", Sex.FEMALE,
            20, 26, 2, FavFood.INSECTS));
  }

  @Test(expected = Exception.class)
  public void testAddMonkeyFailEnclosureDifferentSpecies() throws Exception {
    testEnclosure.addMonkey(new MonkeyImpl(4, "Estelle", "Drill", Sex.FEMALE,
            2, 26, 2, FavFood.INSECTS));
  }

  @Test
  public void testGetMaxSpace() {
    assertEquals(15, testEnclosure.getMaxSpace());
  }

  @Test
  public void testGetFilledSpace() {
    assertEquals(6, testEnclosure.getFilledSpace());
  }

  @Test
  public void testGetAvailableSpace() {
    assertEquals(9, testEnclosure.getAvailableSpace());
  }

  @Test
  public void testEnclosureSign() throws Exception {
    List<MonkeySign> expectedSign = new ArrayList<MonkeySign>();
    expectedSign.add(new MonkeySign("Bella", Sex.FEMALE, FavFood.FRUITS));
    expectedSign.add(new MonkeySign("Charlie", Sex.MALE, FavFood.FRUITS));
    assertEquals(expectedSign, testEnclosure.enclosureSign());
  }

  @Test(expected = Exception.class)
  public void testEnclosureSignFail() throws Exception {
    new Enclosure(5, "E_5", 20).enclosureSign();
  }
}