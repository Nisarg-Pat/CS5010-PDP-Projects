package dungeon3;

import structureddata3.LocationDescription;
import structureddata3.PlayerDescription;
import structureddata3.Point;

import java.util.List;

/**
 * Representation of the Dungeon specific to the adventure game.
 * The dungeon is represented as a 2-D grid.
 * There is a path from every cave in the dungeon to every other cave in the dungeon.
 * Each dungeon can be constructed with a degree of interconnectivity.
 * interconnectivity is 0 when there is exactly one path from every cave in the dungeon
 * to every other cave in the dungeon.
 * Increasing the degree of interconnectivity increases the number of paths between caves.
 * Dungeons can wrap from one side to the other
 * One cave is randomly selected as the start and one cave is randomly selected to be the end.
 * The path between the start and the end locations should be at least of length 5.
 */
public class DungeonModelImpl implements DungeonModel {
  private final LocationGraph locationGraph;
  private final Location startLocation;
  private final Location endLocation;
  private final Player player;

  private boolean gameOver;

  /**
   * Creates a new dungeon with properties mentioned by the user.
   *
   * @param rows                        Number of rows in the dungeon. Should be at least 6.
   * @param columns                     Number of Columns in the dungeon. Should be at least 6.
   * @param isWrapped                   Whether the dungeon is wrapped around its end
   * @param degreeOfInterconnectivity   The degree of interconnectivity for the dungeon.
   * @param percentageCavesWithTreasure The percentage of caves having treasure in it.
   * @throws IllegalArgumentException if rows, columns, degree of Interconnectivity
   *                                  or percentage of caves with treasure is invalid.
   */
  public DungeonModelImpl(int rows, int columns, boolean isWrapped,
                          int degreeOfInterconnectivity, int percentageCavesWithTreasure) {
    locationGraph = new LocationGraphImpl(rows, columns, isWrapped,
            degreeOfInterconnectivity, percentageCavesWithTreasure);

    List<Location> startEndPoints = getStartEndPoints();

    startLocation = startEndPoints.get(0);
    endLocation = startEndPoints.get(1);

    player = new PlayerImpl(startLocation);
    gameOver = false;
  }

  private List<Location> getStartEndPoints() {
    return locationGraph.getStartEndPoints();
  }

  @Override
  public LocationDescription getCurrentLocationDescription() {
    return getLocationDescription(player.getLocation().getPoint());
  }

  @Override
  public LocationDescription getLocationDescription(Point point) {
    if (point == null) {
      throw new IllegalArgumentException("Point cannot be null.");
    }
    Location location = locationGraph.getLocation(point);
    return new LocationDescription(location.getConnections().keySet(),
            location.getTreasureMap(), location.getPoint());
  }

  @Override
  public PlayerDescription getPlayerDescription() {
    return new PlayerDescription(player.getCollectedTreasures(), player.getLocation().getPoint());
  }

  @Override
  public LocationDescription getStartLocation() {
    return new LocationDescription(startLocation.getConnections().keySet(),
            startLocation.getTreasureMap(), startLocation.getPoint());
  }

  @Override
  public LocationDescription getEndLocation() {
    return new LocationDescription(endLocation.getConnections().keySet(),
            endLocation.getTreasureMap(), endLocation.getPoint());
  }

  @Override
  public void movePlayer(Direction direction) {
    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null");
    }
    if (gameOver) {
      throw new IllegalStateException("Player has reached the end point. Game over");
    }
    Location newLocation = player.movePlayer(direction);
    if (newLocation == endLocation) {
      gameOver = true;
    }
  }

  @Override
  public void pickAllTreasures() {
    player.pickAllTreasures();
  }

  @Override
  public boolean isGameOver() {
    return gameOver;
  }

  @Override
  public String printDungeon() {
    StringBuilder sb = new StringBuilder();
    for (int j = 0; j < locationGraph.getColumns(); j++) {
      if (locationGraph.hasEdge(new Point(locationGraph.getRows() - 1, j), new Point(0, j))) {
        sb.append("     | ");
      } else {
        sb.append("       ");
      }
    }
    sb.append("    \n");
    for (int i = 0; i < locationGraph.getRows(); i++) {
      if (locationGraph.hasEdge(new Point(i, locationGraph.getColumns() - 1), new Point(i, 0))) {
        sb.append(" —— ");
      } else {
        sb.append("    ");
      }
      for (int j = 0; j < locationGraph.getColumns(); j++) {
        sb.append("(").append(checkSpecial(i, j)).append(")");
        if (locationGraph.hasEdge(new Point(i, j),
                new Point(i, (j + 1) % locationGraph.getColumns()))) {
          sb.append(" —— ");
        } else {
          sb.append("    ");
        }
      }
      sb.append("\n");
      for (int j = 0; j < locationGraph.getColumns(); j++) {
        if (locationGraph.hasEdge(new Point(i, j),
                new Point((i + 1) % locationGraph.getRows(), j))) {
          sb.append("     | ");
        } else {
          sb.append("       ");
        }
      }
      sb.append("    \n");
    }
    return sb.toString();
  }

  @Override
  public int getDistance(Point first, Point second) {
    if (first == null || second == null) {
      throw new IllegalArgumentException("Invalid points.");
    }
    return locationGraph.getDistance(first, second);
  }

  @Override
  public List<Direction> getFullTraversalPath() {
    return locationGraph.getFullTraversalPath(player.getLocation().getPoint(),
            endLocation.getPoint());
  }

  @Override
  public List<Direction> getShortestPathToEnd() {
    return locationGraph.getShortestPath(player.getLocation().getPoint(),
            endLocation.getPoint());
  }

  private char checkSpecial(int row, int column) {
    Location playerLocation = player.getLocation();
    if (playerLocation.getPoint().getRow() == row
            && playerLocation.getPoint().getColumn() == column) {
      return 'P';
    } else if (startLocation.getPoint().getRow() == row
            && startLocation.getPoint().getColumn() == column) {
      return '#';
    } else if (endLocation.getPoint().getRow() == row
            && endLocation.getPoint().getColumn() == column) {
      return '&';
    }
    return ' ';
  }
}

