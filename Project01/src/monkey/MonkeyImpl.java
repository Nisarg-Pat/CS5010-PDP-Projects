package monkey;

/**
 * The Implementation of the Monkey class pertinent to the Jungle Friends Primate Sanctuary.
 * Each monkey belong to one of the three categories: Small, Medium and Large.
 * The space required in housing and the quantity of favourite food depends on the size
 * of the monkey.
 */
public class MonkeyImpl implements Monkey {
  private final int id;
  private final String name;
  private final String species;
  private final Sex sex;
  private double size;
  private double weight;
  private int age;
  private FavFood favFood;

  public static final int MONKEY_SIZE_LIMIT_SMALL = 10;
  public static final int MONKEY_SIZE_LIMIT_MEDIUM = 20;
  public static final int MONKEY_SIZE_LIMIT_LARGE = Integer.MAX_VALUE;

  public static final int REQUIRED_QUANTITY_SMALL = 100;
  public static final int REQUIRED_QUANTITY_LARGE = 500;
  public static final int REQUIRED_QUANTITY_MEDIUM = 250;

  /**
   * Contructor for MonkeyImpl class. Contructs a new Monkey with the following details.
   *
   * @param id      the id of the Monkey
   * @param name    the name of the monkey
   * @param species the species of the monkey
   * @param sex     the sex of the monkey
   * @param size    the size of the monkey
   * @param weight  the weight of the monkey
   * @param age     the age of the monkey
   * @param favFood the Favourite Food of the monkey
   */
  public MonkeyImpl(int id, String name, String species, Sex sex, double size, double weight,
                    int age, FavFood favFood) {
    if (size <= 0 || weight <= 0 || age <= 0) {
      throw new IllegalArgumentException("The details of monkey are not correct. "
              + "Please check again");
    }
    this.id = id;
    this.name = name;
    this.species = species;
    this.sex = sex;
    this.size = size;
    this.weight = weight;
    this.age = age;
    this.favFood = favFood;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getSpecies() {
    return species;
  }

  @Override
  public Sex getSex() {
    return sex;
  }

  @Override
  public double getSize() {
    return size;
  }

  @Override
  public double getWeight() {
    return weight;
  }

  @Override
  public int getAge() {
    return age;
  }

  @Override
  public FavFood getFavFood() {
    return favFood;
  }

  @Override
  public int getFavFoodAmount() {
    if (size < MonkeyImpl.MONKEY_SIZE_LIMIT_SMALL) {
      return REQUIRED_QUANTITY_SMALL;
    } else if (size < MonkeyImpl.MONKEY_SIZE_LIMIT_MEDIUM) {
      return REQUIRED_QUANTITY_MEDIUM;
    } else {
      return REQUIRED_QUANTITY_LARGE;
    }
  }

  @Override
  public int getSizeCategory() {
    if (size < MonkeyImpl.MONKEY_SIZE_LIMIT_SMALL) {
      return MONKEY_SIZE_LIMIT_SMALL;
    } else if (size < MonkeyImpl.MONKEY_SIZE_LIMIT_MEDIUM) {
      return MONKEY_SIZE_LIMIT_MEDIUM;
    } else {
      return MONKEY_SIZE_LIMIT_LARGE;
    }
  }

  @Override
  public void setSize(double size) {
    this.size = size;
  }

  @Override
  public void setWeight(double weight) {
    this.weight = weight;
  }

  @Override
  public void setAge(int age) {
    this.age = age;
  }

  @Override
  public void setFavFood(FavFood favFood) {
    this.favFood = favFood;
  }

  @Override
  public String toString() {
    return "MonkeyImpl{"
            + "id=" + id
            + ", name='" + name + '\''
            + ", species='" + species + '\''
            + ", sex=" + sex
            + ", size=" + size
            + ", weight=" + weight
            + ", age=" + age
            + ", favFood=" + favFood
            + '}';
  }
}
