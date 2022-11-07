package structureddata3;

import dungeon3.Direction;
import dungeon3.Treasure;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Structured Data to store the description of the location
 * which can be used to print the details of the player.
 */
public class LocationDescription {
  private final Set<Direction> possibleDirections;
  private final Map<Treasure, Integer> treasureMap;
  private final Point point;

  /**
   * Constructor for LocationDescription class.
   *
   * @param possibleDirections Set of valid directions from this location
   * @param treasureMap        Treasures currently present in the location
   * @param point              The point of the location.
   * @throws IllegalArgumentException if any of the argument is null
   */
  public LocationDescription(Set<Direction> possibleDirections,
                             Map<Treasure, Integer> treasureMap, Point point) {
    if (possibleDirections == null || treasureMap == null || point == null) {
      throw new IllegalArgumentException("Invalid arguments.");
    }
    this.possibleDirections = possibleDirections;
    this.treasureMap = treasureMap;
    this.point = point;
  }

  /**
   * Gives the set of directions possible from this location.
   *
   * @return the set of directions possible from this location.
   */
  public Set<Direction> getPossibleDirections() {
    return new TreeSet<>(possibleDirections);
  }

  /**
   * Gives a map of treasures present in the location.
   *
   * @return map of treasures present in the location.
   */
  public Map<Treasure, Integer> getTreasureMap() {
    return new TreeMap<>(treasureMap);
  }

  /**
   * Gives the point of the location.
   *
   * @return the point of the location.
   */
  public Point getPoint() {
    return point;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof LocationDescription)) {
      return false;
    }
    LocationDescription that = (LocationDescription) o;
    return Objects.equals(possibleDirections, that.possibleDirections)
            && Objects.equals(treasureMap, that.treasureMap) && Objects.equals(point, that.point);
  }

  @Override
  public int hashCode() {
    return Objects.hash(possibleDirections, treasureMap, point);
  }

  @Override
  public String toString() {
    return String.format("Location Description:\n"
            + "Possible directions: %s\nTreasures: %s", possibleDirections, treasureMap);
  }
}
