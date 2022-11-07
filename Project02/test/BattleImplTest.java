import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import battle.Battle;
import battle.BattleImpl;
import gear.Belt;
import gear.BeltSize;
import gear.Footwear;
import gear.Gear;
import gear.HeadGear;
import gear.Potion;
import player.Player;
import player.PlayerImpl;
import player.Ability;
import structureddata.RandomImpl;
import player.Weapon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;


/**
 * Tests to check the methods of BattleImpl. Covers all the different types of
 * scenarios that could occur within a battle.
 */
public class BattleImplTest {
  private List<Gear> gearBag;
  private Battle battle;
  private Gear[] headGear;
  private Gear[] potion;
  private Gear[] belt;
  private Gear[] footwear;

  @Before
  public void setup() {
    gearBag = new ArrayList<>();
    headGear = new Gear[5];
    potion = new Gear[15];
    belt = new Gear[15];
    footwear = new Gear[5];
    generateGearBag(3);
    battle = new BattleImpl("Player-1", "Player-2",
            gearBag, new RandomImpl(3), new RandomImpl(4));
  }

  @Test
  public void testConstructor() {
    try {
      new BattleImpl(null, "Player-2", gearBag);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The arguments are not proper.", e.getMessage());
    }

    try {
      new BattleImpl("Player-1", null, gearBag);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The arguments are not proper.", e.getMessage());
    }

    try {
      new BattleImpl("Player-1", "Player-2", null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The arguments are not proper.", e.getMessage());
    }

    try {
      new BattleImpl("Player-1", "Player-2", gearBag,
              null, new RandomImpl());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The arguments are not proper.", e.getMessage());
    }

    try {
      new BattleImpl("Player-1", "Player-2", null,
              new RandomImpl(), null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The arguments are not proper.", e.getMessage());
    }

    gearBag = new ArrayList<>();
    addHeadGear();
    addBelts();
    addPotions();
    addFootWear();

    try {
      new BattleImpl("Player-1", "Player-2", gearBag);
      fail();
    } catch (IllegalArgumentException e) {
      //Fail due to not many poisoned gears
    }

    for (int i = 0; i < 10; i++) {
      gearBag.get(i).setPoison(true);
    }
    new BattleImpl("Player-1", "Player-2", gearBag);

    gearBag.remove(headGear[0]);
    try {
      new BattleImpl("Player-1", "Player-2", gearBag);
      fail();
    } catch (IllegalArgumentException e) {
      //Fail due to headgear<5
    }

    gearBag.add(headGear[0]);
    gearBag.remove(potion[0]);
    try {
      new BattleImpl("Player-1", "Player-2", gearBag);
      fail();
    } catch (IllegalArgumentException e) {
      //Fail due to potion<15
    }

    gearBag.add(potion[0]);
    gearBag.remove(belt[0]);
    try {
      new BattleImpl("Player-1", "Player-2", gearBag);
      fail();
    } catch (IllegalArgumentException e) {
      //Fail due to belt<15
    }

    gearBag.add(belt[0]);
    gearBag.remove(footwear[0]);
    try {
      new BattleImpl("Player-1", "Player-2", gearBag);
      fail();
    } catch (IllegalArgumentException e) {
      //Fail due to footwear<5
    }

    gearBag.add(footwear[0]);
    new BattleImpl("Player-1", "Player-2", gearBag);
  }

  @Test
  public void testGetPlayer() {

    try {
      battle.getPlayer(-1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid argument", e.getMessage());
    }

    try {
      battle.getPlayer(2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid argument", e.getMessage());
    }

    Player player = new PlayerImpl("Player-1", new RandomImpl(3));
    assertEquals(player, battle.getPlayer(0));

    Player player2 = new PlayerImpl("Player-2", new RandomImpl(4));
    assertEquals(player2, battle.getPlayer(1));
  }

  @Test
  public void testGetTurn() {
    assertEquals(9, battle.getPlayer(0).getCharisma());
    assertEquals(12, battle.getPlayer(1).getCharisma());

    battle.startBattle();

    for (int i = 0; i < 5; i++) {
      battle.attack();
      assertEquals(0, battle.getCurrentTurnPlayerId());
      battle.attack();
      assertEquals(1, battle.getCurrentTurnPlayerId());
    }
  }

  @Test
  public void testGetWinner() {
    battle.startBattle();
    assertNull(battle.getWinner());

    for (int i = 0; i < 10; i++) {
      battle.attack();
    }
    assertNull(battle.getWinner());
    battle.attack();
    assertEquals("Player-2", battle.getWinner().getName());
  }

  @Test
  public void testEquipGears() {
    try {
      battle.equipGears(-1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid argument", e.getMessage());
    }

    try {
      battle.equipGears(2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid argument", e.getMessage());
    }

    List<Gear> gearList1 = new ArrayList<>();
    List<Gear> gearList2 = new ArrayList<>();
    for (Gear gear : gearBag) {
      System.out.println(gear.getName());
    }
    gearList1.add(headGear[1]);
    gearList1.add(potion[12]);
    gearList1.add(potion[4]);
    gearList1.add(potion[3]);
    gearList1.add(potion[14]);
    gearList1.add(potion[2]);
    gearList1.add(potion[13]);
    gearList1.add(potion[5]);
    gearList1.add(belt[12]);
    gearList1.add(belt[10]);
    gearList1.add(belt[6]);
    gearList1.add(belt[4]);
    gearList1.add(belt[0]);
    gearList1.add(belt[1]);
    gearList1.add(footwear[1]);

    gearList2.add(headGear[3]);
    gearList2.add(potion[8]);
    gearList2.add(potion[7]);
    gearList2.add(potion[11]);
    gearList2.add(potion[6]);
    gearList2.add(potion[10]);
    gearList2.add(potion[0]);
    gearList2.add(potion[9]);
    gearList2.add(potion[1]);
    gearList2.add(belt[13]);
    gearList2.add(belt[7]);
    gearList2.add(belt[11]);
    gearList2.add(belt[3]);
    gearList2.add(belt[5]);
    gearList2.add(footwear[4]);

    battle.equipGears(0);
    battle.equipGears(1);
    assertEquals(gearList1, battle.getPlayer(0).getGearList());
    assertEquals(gearList2, battle.getPlayer(1).getGearList());
  }


  @Test
  public void testRequestWeapon() {
    try {
      battle.requestWeapon(-1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid argument", e.getMessage());
    }

    try {
      battle.requestWeapon(2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid argument", e.getMessage());
    }
    battle.requestWeapon(0);
    assertEquals(Weapon.AXE, battle.getPlayer(0).getWeapon()[0]);
    assertEquals(Weapon.NOWEAPON, battle.getPlayer(0).getWeapon()[1]);

    battle.requestWeapon(1);
    assertEquals(Weapon.FLAIL, battle.getPlayer(1).getWeapon()[0]);
    assertEquals(Weapon.NOWEAPON, battle.getPlayer(1).getWeapon()[1]);

    try {
      battle.requestWeapon(0);
      fail();
    } catch (IllegalStateException e) {
      assertEquals("Player: Player-1 already has a weapon.", e.getMessage());
    }
  }

  @Test
  public void testStartBattle() {
    battle.startBattle();
    assertEquals(1, battle.getCurrentTurnPlayerId());
  }

  @Test
  public void testAttack() {
    gearBag = new ArrayList<>();
    generateGearBag(20);
    Battle battle = new BattleImpl("Player-1", "Player-2", gearBag,
            new RandomImpl(6, 5, 5, 6, 6, 5, 5, 4, 6, 5, 4, 4, 3, 4, 5, 6, 3),
            new RandomImpl(6, 5, 5, 6, 6, 5, 5, 4, 6, 5, 4, 4, 3, 4, 5, 6, 2));
    battle.equipGears(0);
    battle.equipGears(1);
    battle.requestWeapon(0);
    battle.requestWeapon(1);
    battle.startBattle();

    assertEquals(4, battle.attack());
    assertEquals(-1, battle.attack());

    for (int i = 0; i < 35; i++) {
      battle.attack();
    }

    try {
      battle.attack();
      fail();
    } catch (IllegalStateException e) {
      assertEquals("The game is already over. Start a new game.", e.getMessage());
    }

    gearBag = new ArrayList<>();
    generateGearBag(3);
    battle = new BattleImpl("Player-1", "Player-2", gearBag,
            new RandomImpl(4),
            new RandomImpl(4));
    battle.equipGears(0);
    battle.equipGears(1);
    battle.requestWeapon(0);
    battle.requestWeapon(1);
    battle.startBattle();

    for (int i = 0; i < 100; i++) {
      battle.attack();
    }

    try {
      battle.attack();
      fail();
    } catch (IllegalStateException e) {
      assertEquals("Reached maximum number of turns.", e.getMessage());
    }
  }

  @Test
  public void testReset() {
    battle = new BattleImpl("Player-1", "Player-2", gearBag,
            new RandomImpl(4),
            new RandomImpl(4));
    battle.equipGears(0);
    battle.equipGears(1);
    battle.requestWeapon(0);
    battle.requestWeapon(1);
    battle.startBattle();

    for (int i = 0; i < 50; i++) {
      battle.attack();
    }

    assertEquals(50, battle.getTurnCounter());

    battle.reset();
    assertEquals(0, battle.getTurnCounter());
  }

  @Test
  public void testGetMaxTurns() {
    assertEquals(100, battle.getMaxTurns());
  }

  @Test
  public void testGetTurnCounter() {
    assertEquals(0, battle.getTurnCounter());
    for (int i = 0; i < 10; i++) {
      battle.attack();
      assertEquals(i + 1, battle.getTurnCounter());
    }
  }

  private void generateGearBag(int seed) {
    addHeadGear();
    addPotions();
    addBelts();
    addFootWear();
    Collections.shuffle(gearBag, new Random(seed));
    for (int i = 0; i < 0.25 * gearBag.size(); i++) {
      gearBag.get(i).setPoison(true);
    }
    Collections.shuffle(gearBag, new Random(seed));
  }

  private void addHeadGear() {
    headGear[0] = new HeadGear("HeadGear", 6);
    headGear[1] = new HeadGear("HeadGear - II", 7);
    headGear[2] = new HeadGear("HeadGear - III", 8);
    headGear[3] = new HeadGear("HeadGear - IV", 9);
    headGear[4] = new HeadGear("HeadGear - V", 10);
    gearBag.addAll(List.of(headGear));
  }

  private void addPotions() {
    potion[0] = new Potion("Strength Potion", Ability.STRENGTH, 1);
    potion[1] = new Potion("Super Strength Potion", Ability.STRENGTH, 2);
    potion[2] = new Potion("Hyper Strength Potion", Ability.STRENGTH, 3);
    potion[3] = new Potion("Extreme Strength Potion", Ability.STRENGTH, 4);
    potion[4] = new Potion("Constitution Potion", Ability.CONSTITUTION, 1);
    potion[5] = new Potion("Super Constitution Potion", Ability.CONSTITUTION, 2);
    potion[6] = new Potion("Hyper Constitution Potion", Ability.CONSTITUTION, 3);
    potion[7] = new Potion("Extreme Constitution Potion", Ability.CONSTITUTION, 4);
    potion[8] = new Potion("Dexterity Potion", Ability.DEXTERITY, 1);
    potion[9] = new Potion("Super Dexterity Potion", Ability.DEXTERITY, 2);
    potion[10] = new Potion("Hyper Dexterity Potion", Ability.DEXTERITY, 3);
    potion[11] = new Potion("Extreme Dexterity Potion", Ability.DEXTERITY, 4);
    potion[12] = new Potion("Charisma Potion", Ability.CHARISMA, 1);
    potion[13] = new Potion("Super Charisma Potion", Ability.CHARISMA, 2);
    potion[14] = new Potion("Hyper Charisma Potion", Ability.CHARISMA, 3);
    gearBag.addAll(List.of(potion));
  }

  private void addBelts() {
    belt[0] = new Belt("Small S Belt", BeltSize.SMALL, Ability.STRENGTH, 1);
    belt[1] = new Belt("Small S Mega Belt", BeltSize.SMALL, Ability.STRENGTH, 2);
    belt[2] = new Belt("Small Ch Belt", BeltSize.SMALL, Ability.CHARISMA, 1);
    belt[3] = new Belt("Small Ch Mega Belt", BeltSize.SMALL, Ability.CHARISMA, 2);
    belt[4] = new Belt("Small D Belt", BeltSize.SMALL, Ability.DEXTERITY, 1);
    belt[5] = new Belt("Small D Mega Belt", BeltSize.SMALL, Ability.DEXTERITY, 2);
    belt[6] = new Belt("Small Co Belt", BeltSize.SMALL, Ability.CONSTITUTION, 1);
    belt[7] = new Belt("Medium S Belt", BeltSize.MEDIUM, Ability.STRENGTH, 2);
    belt[8] = new Belt("Medium Ch Belt", BeltSize.MEDIUM, Ability.CHARISMA, 2);
    belt[9] = new Belt("Medium CoD Belt", BeltSize.MEDIUM, Ability.CONSTITUTION,
            1, Ability.DEXTERITY, 1);
    belt[10] = new Belt("Medium ChS Belt", BeltSize.MEDIUM, Ability.STRENGTH,
            1, Ability.CHARISMA, 1);
    belt[11] = new Belt("Medium SD Mega Belt", BeltSize.MEDIUM, Ability.STRENGTH,
            2, Ability.DEXTERITY, 2);
    belt[12] = new Belt("Large ChD Belt", BeltSize.LARGE, Ability.CHARISMA,
            3, Ability.DEXTERITY, 1);
    belt[13] = new Belt("Large CoS Belt", BeltSize.LARGE, Ability.STRENGTH,
            2, Ability.CONSTITUTION, 2);
    belt[14] = new Belt("Large ChCo Mega Belt", BeltSize.LARGE, Ability.CHARISMA,
            3, Ability.CONSTITUTION, 3);
    gearBag.addAll(List.of(belt));
  }

  private void addFootWear() {
    footwear[0] = new Footwear("Footwear - I", 6);
    footwear[1] = new Footwear("Footwear - II", 7);
    footwear[2] = new Footwear("Footwear - III", 8);
    footwear[3] = new Footwear("Footwear - IV", 9);
    footwear[4] = new Footwear("Footwear - V", 10);
    gearBag.addAll(List.of(footwear));
  }
}