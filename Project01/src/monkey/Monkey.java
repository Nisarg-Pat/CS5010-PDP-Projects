package monkey;

/**
 * Monkey interface to represent functionalities of all the monkeys.
 * Each Monkey has several characteristics that can be implemented using this interface.
 */
public interface Monkey {
  /**
   * Gives the id of the Monkey.
   *
   * @return the id of the Monkey
   */
  int getId();

  /**
   * Gives the name of the monkey.
   *
   * @return the name of the monkey
   */
  String getName();

  /**
   * Gives the species of the monkey.
   *
   * @return the species of the monkey
   */
  String getSpecies();

  /**
   * Gives the sex of the monkey.
   *
   * @return the sex of the monkey
   */
  Sex getSex();

  /**
   * Gives the size of the monkey.
   *
   * @return the size of the monkey
   */
  double getSize();

  /**
   * Gives the weight of the monkey.
   *
   * @return the weight of the monkey
   */
  double getWeight();

  /**
   * Gives the age of the monkey.
   *
   * @return the age of the monkey
   */
  int getAge();

  /**
   * Gives the Favourite Food of the monkey.
   *
   * @return the Favourite Food of the monkey
   */
  FavFood getFavFood();

  /**
   * Gives the required amount of Favourite Food for the monkey.
   *
   * @return he required amount of Favourite Food for the monkey
   */
  int getFavFoodAmount();

  /**
   * Gives the size category of the monkey.
   *
   * @return the size category of the monkey
   */
  int getSizeCategory();

  /**
   * Sets the size of the Monkey.
   *
   * @param size the new size of the Monkey
   */
  void setSize(double size);

  /**
   * Sets the weight of the Monkey.
   *
   * @param weight the new weight of the Monkey
   */
  void setWeight(double weight);

  /**
   * Sets the age of the Monkey.
   *
   * @param age the new age of the Monkey
   */
  void setAge(int age);

  /**
   * Sets the Favourite Food of the Monkey.
   *
   * @param favFood the new Favourite Food of the Monkey
   */
  void setFavFood(FavFood favFood);


}
