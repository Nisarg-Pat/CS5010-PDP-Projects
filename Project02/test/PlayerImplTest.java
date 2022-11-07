import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import gear.Belt;
import gear.BeltSize;
import gear.Footwear;
import gear.Gear;
import gear.HeadGear;
import gear.Potion;
import player.Player;
import player.PlayerImpl;
import player.Ability;
import structureddata.PairValue;
import structureddata.RandomImpl;
import player.Weapon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Tests to check the methods of PlayerImpl. Covers all the different types of
 * scenarios that could occur with a player.
 */
public class PlayerImplTest {

  Player player;
  Gear headGear;
  Gear potion;
  Gear potion2;
  Gear belt1;
  Gear belt2;
  Gear belt3;
  Gear footwear;

  @Before
  public void setup() {
    player = new PlayerImpl("Test Player",
            new RandomImpl(6, 5, 5, 6, 6, 5, 5, 4, 6, 5, 4, 4, 3, 4, 5, 6));

    headGear = new HeadGear("HeadGear", 5);
    potion = new Potion("Potion", Ability.DEXTERITY, 2);
    potion2 = new Potion("Potion 2", Ability.CHARISMA, 2);
    belt1 = new Belt("Small Belt", BeltSize.SMALL, Ability.STRENGTH, 1);
    belt2 = new Belt("Medium Belt", BeltSize.MEDIUM, Ability.CHARISMA,
            1, Ability.STRENGTH, 1);
    belt3 = new Belt("Large Belt", BeltSize.LARGE, Ability.CONSTITUTION,
            2, Ability.DEXTERITY, 2);
    footwear = new Footwear("Footwear", 5);
  }

  @Test
  public void testConstructor() {
    try {
      player = new PlayerImpl((String) null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The arguments are not proper.", e.getMessage());
    }

    try {
      player = new PlayerImpl("Player1", null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The arguments are not proper.", e.getMessage());
    }

    try {
      player = new PlayerImpl((Player) null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The arguments are not proper.", e.getMessage());
    }

    player = new PlayerImpl("New Player");
    assertEquals("New Player", player.getName());
  }

  private void equipGearHelper() {
    player.equipGear(headGear);
    player.equipGear(footwear);
    player.equipGear(potion);
    player.equipGear(potion2);
    player.equipGear(belt1);
    player.equipGear(belt2);
    player.equipGear(belt3);
  }

  private void poisonGearHelper() {
    footwear.setPoison(true);
    belt2.setPoison(true);
  }

  @Test
  public void testGetName() {
    assertEquals("Test Player", player.getName());
  }

  @Test
  public void testGetStrength() {
    assertEquals(17, player.getStrength());
    belt1.setPoison(true);
    equipGearHelper();
    assertEquals(17, player.getStrength());
  }

  @Test
  public void testGetConstitution() {
    assertEquals(16, player.getConstitution());
    belt3.setPoison(true);
    equipGearHelper();
    assertEquals(19, player.getConstitution());
  }

  @Test
  public void testGetDexterity() {
    assertEquals(15, player.getDexterity());
    belt3.setPoison(true);
    equipGearHelper();
    assertEquals(20, player.getDexterity());
  }

  @Test
  public void testGetCharisma() {
    assertEquals(15, player.getCharisma());
    belt2.setPoison(true);
    equipGearHelper();
    assertEquals(16, player.getCharisma());
  }

  @Test
  public void testEquipWeapon() {
    Player player2 = new PlayerImpl("Test Player", new RandomImpl(0));
    player2.selectRandomWeapon();
    assertEquals(Weapon.KATANA, player2.getWeapon()[0]);
    assertEquals(Weapon.KATANA, player2.getWeapon()[1]);

    player2 = new PlayerImpl("Test Player", new RandomImpl(0, 1));
    player2.selectRandomWeapon();
    assertEquals(Weapon.KATANA, player2.getWeapon()[0]);
    assertEquals(Weapon.NOWEAPON, player2.getWeapon()[1]);

    player2 = new PlayerImpl("Test Player", new RandomImpl(1));
    player2.selectRandomWeapon();
    assertEquals(Weapon.BROADSWORD, player2.getWeapon()[0]);
    assertEquals(Weapon.NOWEAPON, player2.getWeapon()[1]);

    player2 = new PlayerImpl("Test Player", new RandomImpl(2));
    player2.selectRandomWeapon();
    assertEquals(Weapon.TWOHANDED, player2.getWeapon()[0]);
    assertEquals(Weapon.NOWEAPON, player2.getWeapon()[1]);

    player2 = new PlayerImpl("Test Player", new RandomImpl(3));
    player2.selectRandomWeapon();
    assertEquals(Weapon.AXE, player2.getWeapon()[0]);
    assertEquals(Weapon.NOWEAPON, player2.getWeapon()[1]);

    player2 = new PlayerImpl("Test Player", new RandomImpl(4));
    player2.selectRandomWeapon();
    assertEquals(Weapon.FLAIL, player2.getWeapon()[0]);
    assertEquals(Weapon.NOWEAPON, player2.getWeapon()[1]);

  }

  @Test
  public void testEquipGear() {
    try {
      player.equipGear(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Gear is null.", e.getMessage());
    }

    player.equipGear(potion);
    try {
      player.equipGear(potion);
    } catch (IllegalArgumentException e) {
      assertEquals("Gear is already equipped by Player.", e.getMessage());
    }

    Gear headGear2 = new HeadGear("Test HeadGear", 5);
    Gear footwear2 = new Footwear("Test Footwear", 5);
    Gear[] testPotions = new Potion[1000];

    player.equipGear(headGear);
    try {
      player.equipGear(headGear2);
      fail();
    } catch (IllegalStateException e) {
      assertEquals("Gear : Test HeadGear cannot be equipped by Test Player.",
              e.getMessage());
    }

    player.equipGear(footwear);
    try {
      player.equipGear(footwear2);
      fail();
    } catch (IllegalStateException e) {
      assertEquals("Gear : Test Footwear cannot be equipped by Test Player.",
              e.getMessage());
    }


    for (int i = 0; i < 100; i++) {
      testPotions[i] = new Potion(String.format("Potion - %d", i), Ability.DEXTERITY, 5);
      player.equipGear(testPotions[i]);
    }

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
    player.equipGear(belt1);
    player.equipGear(belt2);
    player.equipGear(belt3);
    player.equipGear(smallBelt2);
    player.equipGear(mediumBelt2);
    try {
      player.equipGear(smallBelt3);
      fail();
    } catch (IllegalStateException e) {
      assertEquals("Gear : Test Small Belt - 3 cannot be equipped by Test Player.",
              e.getMessage());
    }
    try {
      player.equipGear(mediumBelt3);
      fail();
    } catch (IllegalStateException e) {
      assertEquals("Gear : Test Medium Belt -3 cannot be equipped by Test Player.",
              e.getMessage());
    }
    try {
      player.equipGear(largeBelt2);
      fail();
    } catch (IllegalStateException e) {
      assertEquals("Gear : Test Large Belt cannot be equipped by Test Player.",
              e.getMessage());
    }

    List<Gear> expectedItems = new ArrayList<>();
    expectedItems.add(headGear);
    expectedItems.add(potion);
    for (int i = 0; i < 100; i++) {
      expectedItems.add(testPotions[i]);
    }
    expectedItems.add(belt1);
    expectedItems.add(belt2);
    expectedItems.add(belt3);
    expectedItems.add(smallBelt2);
    expectedItems.add(mediumBelt2);
    expectedItems.add(footwear);
    Collections.sort(expectedItems);
    assertEquals(expectedItems, player.getGearList());
  }

  @Test
  public void testGetGearValues() {
    equipGearHelper();

    HashMap<Integer, PairValue> expectedValues = new HashMap<>();
    expectedValues.put(0, new PairValue(1, 1));
    expectedValues.put(1, new PairValue(Integer.MAX_VALUE, 2));
    expectedValues.put(2, new PairValue(10, 7));
    expectedValues.put(3, new PairValue(1, 1));
    assertEquals(expectedValues, player.getGearValues());
  }

  @Test
  public void testGetHealth() {
    poisonGearHelper();
    equipGearHelper();
    assertEquals(70, player.getHealth());
  }

  @Test
  public void testGetStrikingPower() {
    player = new PlayerImpl("Test Player",
            new RandomImpl(6, 5, 5, 6, 6, 5, 5, 4, 6, 5, 4, 4, 3, 4, 5, 6,
            10, 9, 8, 7, 6, 5, 4, 3, 2, 1));
    poisonGearHelper();
    equipGearHelper();
    assertEquals(27, player.getStrikingPower());
    assertEquals(26, player.getStrikingPower());
    assertEquals(25, player.getStrikingPower());
    assertEquals(24, player.getStrikingPower());
    assertEquals(23, player.getStrikingPower());
    assertEquals(22, player.getStrikingPower());
    assertEquals(21, player.getStrikingPower());
    assertEquals(20, player.getStrikingPower());
    assertEquals(19, player.getStrikingPower());
    assertEquals(18, player.getStrikingPower());
  }

  @Test
  public void testGetAvoidanceAbility() {
    player = new PlayerImpl("Test Player",
            new RandomImpl(6, 5, 5, 6, 6, 5, 5, 4, 6, 5, 4, 4, 3, 4, 5, 6,
            6, 5, 4, 3, 2, 1));
    poisonGearHelper();
    equipGearHelper();
    assertEquals(20, player.getAvoidanceAbility());
    assertEquals(19, player.getAvoidanceAbility());
    assertEquals(18, player.getAvoidanceAbility());
    assertEquals(17, player.getAvoidanceAbility());
    assertEquals(16, player.getAvoidanceAbility());
    assertEquals(15, player.getAvoidanceAbility());
  }

  @Test
  public void testGetPotentialStrikingDamage() {
    player = new PlayerImpl("Test Player",
            new RandomImpl(6, 5, 5, 6, 6, 5, 5, 4, 6, 5, 4, 4, 3, 4, 5, 6,
            0, 0, 5, 6,       //Double Katana
            0, 3, 4,       // Single Katana
            1, 7,
            2, 10,
            3, 9,
            4, 12
    ));

    Player player2 = new PlayerImpl("Temp Player",
            new RandomImpl(6, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
            2, 10,
            4, 12
    ));

    assertEquals(17, player.getStrength());

    // Double Katana
    player.selectRandomWeapon();
    assertEquals(Weapon.KATANA, player.getWeapon()[0]);
    assertEquals(Weapon.KATANA, player.getWeapon()[1]);
    assertEquals(28, player.getPotentialStrikingDamage());

    //Single Katana
    player.selectRandomWeapon();
    assertEquals(Weapon.KATANA, player.getWeapon()[0]);
    assertEquals(Weapon.NOWEAPON, player.getWeapon()[1]);
    assertEquals(21, player.getPotentialStrikingDamage());

    //BroadSword
    player.selectRandomWeapon();
    assertEquals(Weapon.BROADSWORD, player.getWeapon()[0]);
    assertEquals(Weapon.NOWEAPON, player.getWeapon()[1]);
    assertEquals(24, player.getPotentialStrikingDamage());

    //Two Handed with strength > 14
    player.selectRandomWeapon();
    assertEquals(17, player.getStrength());
    assertEquals(Weapon.TWOHANDED, player.getWeapon()[0]);
    assertEquals(Weapon.NOWEAPON, player.getWeapon()[1]);
    assertEquals(27, player.getPotentialStrikingDamage());

    //Two Handed with strength <= 14
    player2.selectRandomWeapon();
    assertEquals(14, player2.getStrength());
    assertEquals(Weapon.TWOHANDED, player2.getWeapon()[0]);
    assertEquals(Weapon.NOWEAPON, player2.getWeapon()[1]);
    assertEquals(19, player2.getPotentialStrikingDamage());

    //Axe
    player.selectRandomWeapon();
    assertEquals(Weapon.AXE, player.getWeapon()[0]);
    assertEquals(Weapon.NOWEAPON, player.getWeapon()[1]);
    assertEquals(26, player.getPotentialStrikingDamage());

    //Flail with Dexterity > 14
    player.selectRandomWeapon();
    assertEquals(15, player.getDexterity());
    assertEquals(Weapon.FLAIL, player.getWeapon()[0]);
    assertEquals(Weapon.NOWEAPON, player.getWeapon()[1]);
    assertEquals(29, player.getPotentialStrikingDamage());

    //Flail with Dexterity <= 14
    player2.selectRandomWeapon();
    assertEquals(12, player2.getDexterity());
    assertEquals(Weapon.FLAIL, player2.getWeapon()[0]);
    assertEquals(Weapon.NOWEAPON, player2.getWeapon()[1]);
    assertEquals(20, player2.getPotentialStrikingDamage());
  }

  @Test
  public void testGetTotalDamage() {
    assertEquals(0, player.getTotalDamage());
    player.doDamage(8);
    assertEquals(8, player.getTotalDamage());
    player.doDamage(12);
    assertEquals(20, player.getTotalDamage());
  }

  @Test
  public void testAttack() {

    try {
      player.attack(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid argument", e.getMessage());
    }

    Player player1 = new PlayerImpl("Test Player",
            new RandomImpl(6, 5, 5, 6, 6, 5, 5, 4, 6, 5, 4, 4, 3, 4, 5, 6,
            0, 5,
            1,
            5, 6,
            5, 4));
    player1.selectRandomWeapon();
    assertEquals(Weapon.KATANA, player1.getWeapon()[0]);
    assertEquals(Weapon.NOWEAPON, player1.getWeapon()[1]);

    Player player2 = new PlayerImpl("Temp Player",
            new RandomImpl(6, 4, 4, 4, 6, 6, 6, 6, 4, 4, 4, 4, 4, 4, 4, 4,
            3,
            6,
            5,
            5));


    player2.selectRandomWeapon();
    assertEquals(Weapon.AXE, player2.getWeapon()[0]);
    assertEquals(Weapon.NOWEAPON, player2.getWeapon()[1]);

    assertEquals(-1, player1.attack(player2));
    assertEquals(5, player1.attack(player2));

    player2.equipGear(new Potion("Boost Constitution", Ability.CONSTITUTION, 3));
    assertEquals(21, player2.getConstitution());
    assertEquals(0, player1.attack(player2));
  }

  @Test
  public void testIsDefeated() {
    poisonGearHelper();
    equipGearHelper();
    assertEquals(70, player.getHealth());
    assertFalse(player.isDefeated());
    player.doDamage(10);
    assertFalse(player.isDefeated());
    player.doDamage(59);
    assertFalse(player.isDefeated());
    player.doDamage(1);
    assertTrue(player.isDefeated());
  }

  @Test
  public void testHasWeapon() {
    player = new PlayerImpl("Temp Player",
            new RandomImpl(6, 4, 4, 4, 6, 6, 6, 6, 4, 4, 4, 4, 4, 4, 4, 4,
            3));
    assertFalse(player.hasWeapon());
    player.selectRandomWeapon();
    assertTrue(player.hasWeapon());
  }

  @Test
  public void testDoDamage() {
    poisonGearHelper();
    equipGearHelper();

    assertEquals(70, player.getHealth());
    try {
      player.doDamage(-5);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Damage dealt should be positive.", e.getMessage());
    }

    try {
      player.doDamage(0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Damage dealt should be positive.", e.getMessage());
    }

    player.doDamage(12);
    assertEquals(12, player.getTotalDamage());
    player.doDamage(7);
    assertEquals(19, player.getTotalDamage());

    try {
      player.doDamage(70);
      player.doDamage(5);
      fail();
    } catch (IllegalStateException e) {
      assertEquals("Player is already defeated.", e.getMessage());
    }
  }

  @Test
  public void testGetRemainingHealth() {
    poisonGearHelper();
    equipGearHelper();
    assertEquals(70, player.getHealth());
    assertEquals(70, player.getRemainingHealth());
    player.doDamage(10);
    assertEquals(60, player.getRemainingHealth());
    player.doDamage(59);
    assertEquals(1, player.getRemainingHealth());
    player.doDamage(1);
    assertEquals(0, player.getRemainingHealth());
  }

  @Test
  public void testReset() {
    player = new PlayerImpl("Temp Player",
            new RandomImpl(6, 4, 4, 4, 6, 6, 6, 6, 4, 4, 4, 4, 4, 4, 4, 4,
            3));
    Player playerCopy = new PlayerImpl(player);
    player.selectRandomWeapon();
    poisonGearHelper();
    equipGearHelper();
    player.reset();
    assertEquals(playerCopy, player);
  }

  @Test
  public void testGetGearList() {
    poisonGearHelper();
    equipGearHelper();
    List<Gear> expectedGears = new ArrayList<>();
    expectedGears.add(headGear);
    expectedGears.add(potion);
    expectedGears.add(potion2);
    expectedGears.add(belt3);
    expectedGears.add(belt2);
    expectedGears.add(belt1);
    expectedGears.add(footwear);
    assertEquals(expectedGears, player.getGearList());
  }
}