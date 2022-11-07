import org.junit.Test;

import java.util.Arrays;

import gear.Belt;
import gear.BeltSize;
import gear.Footwear;
import gear.Gear;
import gear.HeadGear;
import gear.Potion;
import player.Player;
import player.PlayerImpl;
import player.Ability;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests to check the methods of Gears. Covers all the different types of
 * scenarios that could occur with a gear.
 */
public class GearTest {

  Gear headGear = new HeadGear("HeadGear", 5);
  Gear potion = new Potion("Potion", Ability.DEXTERITY, 2);
  Gear belt1 = new Belt("Small Belt", BeltSize.SMALL, Ability.STRENGTH, 1);
  Gear belt2 = new Belt("Medium Belt", BeltSize.MEDIUM, Ability.CHARISMA,
          1, Ability.CONSTITUTION, 1);
  Gear belt3 = new Belt("Large Belt", BeltSize.LARGE, Ability.CONSTITUTION,
          2, Ability.DEXTERITY, 2);
  Gear footwear = new Footwear("Footwear", 5);

  @Test
  public void testConstructorFail() {
    try {
      new HeadGear(null, 7);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The arguments are not proper.", e.getMessage());
    }
    try {
      new HeadGear("Test HeadGear", -5);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The arguments are not proper.", e.getMessage());
    }
    try {
      new Potion("Test Potion", null, 2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The arguments are not proper.", e.getMessage());
    }
    try {
      new Potion("Test Potion", Ability.CHARISMA, -2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The arguments are not proper.", e.getMessage());
    }
    try {
      new Footwear("Test Footwear", 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The arguments are not proper.", e.getMessage());
    }
    try {
      new Belt("Test Belt", null, Ability.STRENGTH, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The arguments are not proper.", e.getMessage());
    }
    try {
      new Belt("Test Belt", BeltSize.SMALL, null, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The arguments are not proper.", e.getMessage());
    }
    try {
      new Belt("Test Belt", BeltSize.SMALL, Ability.STRENGTH, -7);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The arguments are not proper.", e.getMessage());
    }
  }

  @Test
  public void testGetName() {
    assertEquals("HeadGear", headGear.getName());
    assertEquals("Potion", potion.getName());
    assertEquals("Small Belt", belt1.getName());
    assertEquals("Medium Belt", belt2.getName());
    assertEquals("Large Belt", belt3.getName());
    assertEquals("Footwear", footwear.getName());
  }

  @Test
  public void testGetAbility() {
    assertEquals(Ability.CONSTITUTION, headGear.getAbility()[0]);
    assertNull(headGear.getAbility()[1]);
    assertEquals(Ability.DEXTERITY, potion.getAbility()[0]);
    assertNull(potion.getAbility()[1]);
    assertEquals(Ability.STRENGTH, belt1.getAbility()[0]);
    assertNull(belt1.getAbility()[1]);
    assertEquals(Ability.CHARISMA, belt2.getAbility()[0]);
    assertEquals(Ability.CONSTITUTION, belt2.getAbility()[1]);
    assertEquals(Ability.CONSTITUTION, belt3.getAbility()[0]);
    assertEquals(Ability.DEXTERITY, belt3.getAbility()[1]);
    assertEquals(Ability.DEXTERITY, footwear.getAbility()[0]);
    assertNull(footwear.getAbility()[1]);
  }

  @Test
  public void testGetValue() {
    assertEquals(5, headGear.getValue()[0]);
    assertEquals(0, headGear.getValue()[1]);
    assertEquals(2, potion.getValue()[0]);
    assertEquals(0, potion.getValue()[1]);
    assertEquals(1, belt1.getValue()[0]);
    assertEquals(0, belt1.getValue()[1]);
    assertEquals(1, belt2.getValue()[0]);
    assertEquals(1, belt2.getValue()[1]);
    assertEquals(2, belt3.getValue()[0]);
    assertEquals(2, belt3.getValue()[1]);
    assertEquals(5, footwear.getValue()[0]);
    assertEquals(0, footwear.getValue()[1]);
  }

  @Test
  public void testGetSize() {
    assertEquals(1, headGear.getSize());
    assertEquals(1, potion.getSize());
    assertEquals(1, belt1.getSize());
    assertEquals(2, belt2.getSize());
    assertEquals(4, belt3.getSize());
    assertEquals(1, footwear.getSize());
  }

  @Test
  public void testCanBeEquippedByPlayer() {

    try {
      headGear.canBeEquippedByPlayer(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid argument", e.getMessage());
    }

    Player player = new PlayerImpl("Test Player");

    assertTrue(headGear.canBeEquippedByPlayer(player));
    player.equipGear(headGear);
    Gear headGear2 = new HeadGear("Test HeadGear", 5);
    assertFalse(headGear2.canBeEquippedByPlayer(player));


    assertTrue(footwear.canBeEquippedByPlayer(player));
    player.equipGear(footwear);
    Gear footwear2 = new Footwear("Test Footwear", 5);
    assertFalse(footwear2.canBeEquippedByPlayer(player));


    assertTrue(potion.canBeEquippedByPlayer(player));
    player.equipGear(potion);
    Gear[] testPotions = new Potion[1000];
    for (int i = 0; i < 100; i++) {
      testPotions[i] = new Potion(("Potion" + i), Ability.CONSTITUTION, i + 1);
      assertTrue(testPotions[i].canBeEquippedByPlayer(player));
      player.equipGear(testPotions[i]);
    }


    assertTrue(belt1.canBeEquippedByPlayer(player));
    assertTrue(belt2.canBeEquippedByPlayer(player));
    assertTrue(belt3.canBeEquippedByPlayer(player));
    player.equipGear(belt1);
    player.equipGear(belt2);
    player.equipGear(belt3);

    Gear smallBelt2 = new Belt("Test Small Belt", BeltSize.SMALL,
            Ability.STRENGTH, 1);
    Gear mediumBelt2 = new Belt("Test Medium Belt", BeltSize.MEDIUM,
            Ability.CHARISMA, 1, Ability.CONSTITUTION, 1);
    Gear largeBelt2 = new Belt("Test Large Belt", BeltSize.LARGE,
            Ability.CONSTITUTION, 2, Ability.DEXTERITY, 2);
    Gear smallBelt3 = new Belt("Test Small Belt - 3", BeltSize.SMALL,
            Ability.STRENGTH, 1);
    Gear mediumBelt3 = new Belt("Test Medium Belt -3", BeltSize.MEDIUM,
            Ability.CHARISMA, 1, Ability.CONSTITUTION, 1);

    assertTrue(smallBelt2.canBeEquippedByPlayer(player));
    assertTrue(mediumBelt2.canBeEquippedByPlayer(player));
    assertFalse(largeBelt2.canBeEquippedByPlayer(player));

    player.equipGear(mediumBelt2);
    assertTrue(smallBelt2.canBeEquippedByPlayer(player));
    assertFalse(mediumBelt3.canBeEquippedByPlayer(player));
    assertFalse(largeBelt2.canBeEquippedByPlayer(player));

    player.equipGear(smallBelt2);
    assertFalse(smallBelt3.canBeEquippedByPlayer(player));
    assertFalse(mediumBelt3.canBeEquippedByPlayer(player));
    assertFalse(largeBelt2.canBeEquippedByPlayer(player));
  }

  @Test
  public void testPoisoned() {
    headGear.setPoison(true);
    potion.setPoison(true);
    belt1.setPoison(true);
    belt2.setPoison(true);
    belt3.setPoison(true);
    footwear.setPoison(true);
    assertTrue(headGear.isPoisoned());
    assertTrue(potion.isPoisoned());
    assertTrue(belt1.isPoisoned());
    assertTrue(belt2.isPoisoned());
    assertTrue(belt3.isPoisoned());
    assertTrue(footwear.isPoisoned());

    headGear.setPoison(false);
    potion.setPoison(false);
    belt1.setPoison(false);
    belt2.setPoison(false);
    belt3.setPoison(false);
    footwear.setPoison(false);
    assertFalse(headGear.isPoisoned());
    assertFalse(potion.isPoisoned());
    assertFalse(belt1.isPoisoned());
    assertFalse(belt2.isPoisoned());
    assertFalse(belt3.isPoisoned());
    assertFalse(footwear.isPoisoned());
  }

  @Test
  public void testHeadGearCompareTo() {
    assertTrue(headGear.compareTo(potion) < 0);
    assertTrue(headGear.compareTo(belt1) < 0);
    assertTrue(headGear.compareTo(footwear) < 0);

    Gear headGear2 = new HeadGear("Sturdy HeadGear", 10);
    assertTrue(headGear.compareTo(headGear2) < 0);
    assertTrue(headGear2.compareTo(headGear) > 0);
  }

  @Test
  public void testPotionCompareTo() {
    assertTrue(potion.compareTo(headGear) > 0);
    assertTrue(potion.compareTo(belt1) < 0);
    assertTrue(potion.compareTo(footwear) < 0);

    Gear potion2 = new Potion("Hyper Potion", Ability.CONSTITUTION, 2);
    assertTrue(potion.compareTo(potion2) > 0);
    assertTrue(potion2.compareTo(potion) < 0);
  }

  @Test
  public void testBeltCompareTo() {
    assertTrue(belt1.compareTo(headGear) > 0);
    assertTrue(belt1.compareTo(potion) > 0);
    assertTrue(belt1.compareTo(footwear) < 0);


    assertTrue(belt1.compareTo(belt2) > 0);
    assertTrue(belt2.compareTo(belt1) < 0);
  }

  @Test
  public void testFootwearCompareTo() {
    assertTrue(footwear.compareTo(headGear) > 0);
    assertTrue(footwear.compareTo(potion) > 0);
    assertTrue(footwear.compareTo(belt1) > 0);

    Gear footwear2 = new Footwear("Normal Footwear", 7);
    assertTrue(footwear.compareTo(footwear2) < 0);
    assertTrue(footwear2.compareTo(footwear) > 0);
  }


  @Test
  public void testSortGears() {

    Gear headGear2 = new HeadGear("Sturdy HeadGear", 10);
    Gear potion2 = new Potion("Hyper Potion", Ability.CONSTITUTION, 2);
    Gear footwear2 = new Footwear("Normal Footwear", 7);

    Gear[] gears = new Gear[]{footwear, potion2, belt3, headGear, belt2,
        headGear2, potion, belt1, footwear2};
    Gear[] sortedGears = new Gear[]{headGear, headGear2, potion2, potion, belt3,
        belt2, belt1, footwear, footwear2};
    Arrays.sort(gears);
    for (int i = 0; i < gears.length; i++) {
      assertEquals(sortedGears[i], gears[i]);
    }
  }

}