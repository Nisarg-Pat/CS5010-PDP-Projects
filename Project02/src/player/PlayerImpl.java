package player;

import gear.Gear;
import structureddata.PairValue;
import structureddata.RandomImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;



/**
 * Representation of a Player specific to the requirements of Jumptastic Games.
 * Each player can have 4 abilities:
 * Strength, Constitution, Dexterity, and Charisma.
 * Each player can equip gears from HeadGears, Potions, Belts and Footwear.
 * Each player can have a weapon from Katana, Double Katana,
 * BroadSword, Two-handed Sword, Axe or Flail.
 */
public class PlayerImpl implements Player {

  private final String name;
  private final Map<Ability, Integer> abilityValues;
  private final Map<Ability, Integer> tempAbilityValues;
  private final Weapon[] weapon;


  private final Set<Gear> gearSet;
  private final Map<Integer, PairValue> gearValues;

  private static final int MAX_HEADGEAR = 1;
  private static final int MAX_FOOTWEAR = 1;
  private static final int MAX_POTION = Integer.MAX_VALUE;
  private static final int MAX_BELT = 10;

  private int totalDamage;
  private final RandomImpl random;

  /**
   * Constructor for playerImpl.
   *
   * @param name Name of the player.
   */
  public PlayerImpl(String name) {
    this(name, new RandomImpl());
  }

  /**
   * Constructor for playerImpl.
   *
   * @param name   Name of the player.
   * @param random Object of random assigned to perform random opertations
   */
  public PlayerImpl(String name, RandomImpl random) {
    if (name == null || name.equals("") || random == null) {
      throw new IllegalArgumentException("The arguments are not proper.");
    }
    this.name = name;
    this.random = random;

    abilityValues = new HashMap<>();
    abilityValues.put(Ability.STRENGTH, randomizeVal());
    abilityValues.put(Ability.CONSTITUTION, randomizeVal());
    abilityValues.put(Ability.DEXTERITY, randomizeVal());
    abilityValues.put(Ability.CHARISMA, randomizeVal());

    tempAbilityValues = new HashMap<>();
    tempAbilityValues.put(Ability.STRENGTH, 0);
    tempAbilityValues.put(Ability.CONSTITUTION, 0);
    tempAbilityValues.put(Ability.DEXTERITY, 0);
    tempAbilityValues.put(Ability.CHARISMA, 0);

    this.weapon = new Weapon[2];
    weapon[0] = Weapon.NOWEAPON;
    weapon[1] = Weapon.NOWEAPON;

    this.totalDamage = 0;

    this.gearValues = new HashMap<>();
    setGearValues();

    this.gearSet = new HashSet<>();
  }

  /**
   * Constructor for playerImpl. Copy values from other player.
   *
   * @param player player whose values need to be copied.
   */
  public PlayerImpl(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("The arguments are not proper.");
    }
    this.name = player.getName();
    abilityValues = new HashMap<>();
    abilityValues.put(Ability.STRENGTH, player.getStrength());
    abilityValues.put(Ability.CONSTITUTION, player.getConstitution());
    abilityValues.put(Ability.DEXTERITY, player.getDexterity());
    abilityValues.put(Ability.CHARISMA, player.getCharisma());

    tempAbilityValues = new HashMap<>();
    tempAbilityValues.put(Ability.STRENGTH, 0);
    tempAbilityValues.put(Ability.CONSTITUTION, 0);
    tempAbilityValues.put(Ability.DEXTERITY, 0);
    tempAbilityValues.put(Ability.CHARISMA, 0);

    this.weapon = new Weapon[2];
    weapon[0] = player.getWeapon()[0];
    weapon[1] = player.getWeapon()[1];

    this.totalDamage = player.getTotalDamage();

    this.gearValues = new HashMap<>();
    setGearValues();

    this.gearSet = new HashSet<>();
    this.gearSet.addAll(player.getGearList());

    this.random = new RandomImpl();
  }

  private void setGearValues() {
    this.gearValues.put(0, new PairValue(MAX_HEADGEAR, 0));
    this.gearValues.put(1, new PairValue(MAX_POTION, 0));
    this.gearValues.put(2, new PairValue(MAX_BELT, 0));
    this.gearValues.put(3, new PairValue(MAX_FOOTWEAR, 0));
  }


  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getStrength() {
    return abilityValues.get(Ability.STRENGTH)
            + tempAbilityValues.get(Ability.STRENGTH);
  }

  @Override
  public int getConstitution() {
    return abilityValues.get(Ability.CONSTITUTION)
            + tempAbilityValues.get(Ability.CONSTITUTION);
  }

  @Override
  public int getDexterity() {
    return abilityValues.get(Ability.DEXTERITY)
            + tempAbilityValues.get(Ability.DEXTERITY);
  }

  @Override
  public int getCharisma() {
    return abilityValues.get(Ability.CHARISMA)
            + tempAbilityValues.get(Ability.CHARISMA);
  }

  @Override
  public void selectRandomWeapon() {
    Weapon[] weaponList = Weapon.values();
    int index = random.getRandomInt(0, weaponList.length - 2);
    weapon[0] = weaponList[index];
    weapon[1] = Weapon.NOWEAPON;
    if (weapon[0] == Weapon.KATANA) {
      index = random.getRandomInt(0, weaponList.length - 2);
      if (index == Weapon.KATANA.ordinal()) {
        weapon[1] = Weapon.KATANA;
      }
    }
  }

  @Override
  public Weapon[] getWeapon() {
    return new Weapon[]{weapon[0], weapon[1]};
  }

  @Override
  public void equipGear(Gear gear) {
    if (gear == null) {
      throw new IllegalArgumentException("Gear is null.");
    } else if (gearSet.contains(gear)) {
      throw new IllegalArgumentException("Gear is already equipped by Player.");
    }
    if (gear.canBeEquippedByPlayer(this)) {
      addGear(gear);
      addGearValue(gear.getIndex(), gear.getSize());
    } else {
      throw new IllegalStateException(
              String.format("Gear : %s cannot be equipped by %s.", gear.getName(), this.getName()));
    }
  }

  private void addGear(Gear gear) {
    gearSet.add(gear);
    Ability[] abilities = gear.getAbility();
    int value = tempAbilityValues.get(abilities[0]);
    tempAbilityValues.put(abilities[0], value + gear.getValue()[0]);
    if (abilities[1] != null) {
      value = tempAbilityValues.get(abilities[1]);
      tempAbilityValues.put(abilities[1], value + gear.getValue()[1]);
    }
  }

  private void addGearValue(int key, int value) {
    if (!gearValues.containsKey(key)) {
      throw new IllegalArgumentException("No key found");
    } else {
      gearValues.get(key).addCurrentValue(value);
    }
  }

  @Override
  public Map<Integer, PairValue> getGearValues() {
    Map<Integer, PairValue> gearValuesClone = new HashMap<>();
    for (int key : gearValues.keySet()) {
      PairValue pairValue = gearValues.get(key);
      gearValuesClone.put(key, new PairValue(pairValue.getMaxValue(), pairValue.getCurrentValue()));
    }
    return gearValuesClone;
  }


  @Override
  public int getHealth() {
    int health = 0;
    for (int value : abilityValues.values()) {
      health += value;
    }
    for (int value : tempAbilityValues.values()) {
      health += value;
    }
    return health;
  }

  @Override
  public int getStrikingPower() {
    return this.getStrength() + random.getRandomInt(1, 10);
  }

  @Override
  public int getAvoidanceAbility() {
    return this.getDexterity() + random.getRandomInt(1, 6);
  }

  @Override
  public int getPotentialStrikingDamage() {
    int weaponDamage = weapon[0].getDamage(random);
    if (weapon[1] == Weapon.KATANA) {
      weaponDamage += weapon[1].getDamage(random);
    }
    if ((weapon[0] == Weapon.TWOHANDED && getStrength() <= 14)
            || (weapon[0] == Weapon.FLAIL && getDexterity() <= 14)) {
      weaponDamage = weaponDamage / 2;
    }
    return getStrength() + weaponDamage;
  }

  @Override
  public int getTotalDamage() {
    return totalDamage;
  }

  @Override
  public int attack(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Invalid argument");
    }
    int strikingPower = this.getStrikingPower();
    int avoidanceAbility = player.getAvoidanceAbility();
    if (strikingPower <= avoidanceAbility) {
      return -1;
    }
    int potentialStrikingDamage = this.getPotentialStrikingDamage();
    int actualDamage = potentialStrikingDamage - player.getConstitution();
    if (actualDamage > 0) {
      player.doDamage(actualDamage);
      return actualDamage;
    }
    return 0;
  }

  @Override
  public boolean isDefeated() {
    return getRemainingHealth() <= 0;
  }

  @Override
  public boolean hasWeapon() {
    return weapon[0] != Weapon.NOWEAPON;
  }

  @Override
  public void doDamage(int damage) {
    if (damage <= 0) {
      throw new IllegalArgumentException("Damage dealt should be positive.");
    } else if (isDefeated()) {
      throw new IllegalStateException("Player is already defeated.");
    }
    this.totalDamage += damage;
  }

  @Override
  public int getRemainingHealth() {
    return getHealth() - getTotalDamage();
  }

  @Override
  public void reset() {
    weapon[0] = Weapon.NOWEAPON;
    weapon[1] = Weapon.NOWEAPON;

    for(Ability ability : tempAbilityValues.keySet()) {
      tempAbilityValues.put(ability, 0);
    }

    setGearValues();

    gearSet.clear();

    totalDamage = 0;
  }

  @Override
  public List<Gear> getGearList() {
    List<Gear> gearListClone = new ArrayList<>(gearSet);
    Collections.sort(gearListClone);
    return gearListClone;
  }

  private int randomizeVal() {
    int minimalvalue = 7;
    int value = 0;
    for (int i = 0; i < 4; i++) {
      int randomValue = random.getRandomInt(2, 6);
      if (randomValue < minimalvalue) {
        minimalvalue = randomValue;
      }
      value += randomValue;
    }
    value -= minimalvalue;
    return value;
  }

  /**
   * Checks if the two objects are equal.
   *
   * @param o Other object
   * @return true if both player has same values.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PlayerImpl)) {
      return false;
    }
    PlayerImpl player = (PlayerImpl) o;
    return totalDamage == player.totalDamage
            && Objects.equals(name, player.name)
            && getStrength() == player.getStrength()
            && getConstitution() == player.getConstitution()
            && getDexterity() == player.getDexterity()
            && getCharisma() == player.getCharisma()
            && Arrays.equals(weapon, player.weapon)
            && Objects.equals(gearSet, player.gearSet)
            && Objects.equals(gearValues, player.gearValues);
  }

  /**
   * Hashcode for the player.
   *
   * @return Hashcode for the player
   */
  @Override
  public int hashCode() {
    int result = Objects.hash(name, gearSet, gearValues, totalDamage, random);
    result = 31 * result + Arrays.hashCode(new Map[]{abilityValues});
    result = 31 * result + Arrays.hashCode(new Map[]{tempAbilityValues});
    result = 31 * result + Arrays.hashCode(weapon);
    return result;
  }
}
