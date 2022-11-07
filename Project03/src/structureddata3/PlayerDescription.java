package structureddata3;

import dungeon3.Treasure;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Structured Data to store the description of the player which can be used
 * to print the details of the player.
 */
public class PlayerDescription {
  private final Map<Treasure, Integer> collectedTreasures;
  private final Point point;

  /**
   * Constructor for PlayerDescription class.
   *
   * @param collectedTreasures the collected treasures of the player
   * @param location           The current point of the player in the dungeon
   * @throws IllegalArgumentException if collectedTreasure or location is null
   */
  public PlayerDescription(Map<Treasure, Integer> collectedTreasures, Point location) {
    if (collectedTreasures == null || location == null) {
      throw new IllegalArgumentException("Invalid arguments.");
    }
    this.collectedTreasures = collectedTreasures;
    this.point = location;
  }

  /**
   * Gives a map of treasure and its amount collected by the player.
   *
   * @return the map of treasure and its amount collected by the player.
   */
  public Map<Treasure, Integer> getCollectedTreasures() {
    return new TreeMap<>(collectedTreasures);
  }

  /**
   * Gives the point of the player.
   *
   * @return the point of the player.
   */
  public Point getPoint() {
    return new Point(point.getRow(), point.getColumn());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PlayerDescription)) {
      return false;
    }
    PlayerDescription that = (PlayerDescription) o;
    return Objects.equals(collectedTreasures, that.collectedTreasures)
            && Objects.equals(point, that.point);
  }

  @Override
  public int hashCode() {
    return Objects.hash(collectedTreasures, point);
  }

  @Override
  public String toString() {
    return String.format("Player Description:\nPoint: %s\nTreasures collected: %s",
            point, collectedTreasures);
  }
}
