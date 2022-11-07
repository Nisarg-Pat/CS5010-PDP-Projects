package dungeon;

import structureddata.LocationDescription;
import structureddata.PlayerDescription;
import structureddata.Point;

import java.util.List;

/**
 * The Dungeon Model for the adventure game.
 * The dungeon contains a network of tunnels and caves that are interconnected so that
 * player can explore the entire world by traveling
 * from cave to cave through the tunnels that connect them.
 * The dungeon is generated randomly.
 * Player is spawned in a random Cave.
 * Player can visit the locations connected by his current location.
 * A location can be connected with maximum 4 other locations,
 * one in each direction(North, South, East, West).
 * Player can collect treasures from the caves. The goal of the player is to reach an endpoint cave.
 */
public interface DungeonModel {
  /**
   * Gets the description of starting Cave of the dungeon.
   *
   * @return the description of starting cave
   */
  LocationDescription getStartLocation();

  /**
   * Gets the description of ending Cave of the dungeon.
   *
   * @return the description of ending cave
   */
  LocationDescription getEndLocation();

  /**
   * Gets the description of the player in the dungeon.
   * Contains information about its current location, and the treasures collected by the player.
   *
   * @return the description of the player
   */
  PlayerDescription getPlayerDescription();

  /**
   * Gets the description of the current location of the player.
   * Contains information about possible directions,
   * player can move from this location and the available treasures in it.
   *
   * @return the description of current location of the person.
   */
  LocationDescription getCurrentLocationDescription();

  /**
   * Gets the description of a specific location in the dungeon.
   *
   * @param point the point of the location whose description is required.
   * @return the description of the location.
   * @throws IllegalArgumentException if p is null
   */
  LocationDescription getLocationDescription(Point point);

  /**
   * Moves the player to the location present at the specified direction from his current cave.
   *
   * @param direction The direction in which to move.
   * @throws IllegalArgumentException if direction is null or particular direction
   *                                  is not possible from the current location.
   * @throws IllegalStateException    if game is already over.
   */
  void movePlayer(Direction direction);

  /**
   * Player picks all the treasure available in the current location.
   * If the current location does not have any treasure
   * or if the location is a tunnel, no change happens to player's treasures.
   */
  void pickAllTreasures();

  /**
   * Gives the status of the game as over or not. Game is over when player reached the endPoint.
   *
   * @return true if game is over else false.
   */
  boolean isGameOver();

  /**
   * Prints the current state of the dungeon.
   *
   * @return the String representation of the state of the dungeon.
   */
  String printDungeon();

  /**
   * Gives the distance between two points in the dungeon.
   *
   * @param first  the first point
   * @param second the second point
   * @return the distance between the two points in the dungeon
   */
  int getDistance(Point first, Point second);

  /**
   * Gives a list of directions that player must follow from his current location to visit every
   * location in the dungeon and to reach the endPoint at last.
   *
   * @return the list of directions
   */
  List<Direction> getFullTraversalPath();

  /**
   * Gives a list of directions that player must follow from his current location
   * to reach endPoint with the shortest path.
   *
   * @return the list of directions for shortest path.
   */
  List<Direction> getShortestPathToEnd();
}
