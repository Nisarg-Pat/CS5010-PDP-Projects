import org.junit.Before;
import org.junit.Test;

import housing.Isolation;
import monkey.FavFood;
import monkey.MonkeyImpl;
import monkey.Sex;

/**
 * Test Cases specific to {@link Isolation} Housing.
 */
public class IsolationTest {

  private Isolation testIsolation;

  @Before
  public void setup() throws Exception {
    testIsolation = new Isolation(0, "testIsolation");
    testIsolation.addMonkey(new MonkeyImpl(0, "Alfred", "Drill", Sex.MALE,
            23, 42, 5, FavFood.EGGS));
  }

  @Test(expected = Exception.class)
  public void testGetMonkeyFromIdFailIsolation() throws Exception {
    testIsolation.getMonkeyFromId(-1);
  }

  @Test(expected = Exception.class)
  public void testRemoveMonkeyFailIsolation() throws Exception {
    testIsolation.removeMonkey(-1);

  }

  @Test(expected = Exception.class)
  public void testAddMonkeyFailIsolation() throws Exception {
    testIsolation.addMonkey(new MonkeyImpl(4, "Estelle", "Guereza", Sex.FEMALE,
            20, 26, 2, FavFood.INSECTS));
  }
}