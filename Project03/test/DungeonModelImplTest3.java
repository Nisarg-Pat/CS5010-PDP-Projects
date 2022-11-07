import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import dungeon3.Direction;
import dungeon3.DungeonModel;
import dungeon3.DungeonModelImpl;
import dungeon3.Treasure;
import random3.RandomImpl;
import structureddata3.LocationDescription;
import structureddata3.Point;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Tests to check the methods of DungeonModel. Covers all the different types of
 * scenarios that could occur in a dungeon.
 */
public class DungeonModelImplTest3 {

  private DungeonModel nonWrappingDungeon;
  private DungeonModel nonWrappingDungeonFull;
  private DungeonModel nonWrappingDungeonNoTreasure;
  private DungeonModel nonWrappingDungeonNoDegree;
  private DungeonModel wrappingDungeon;
  private DungeonModel wrappingDungeonFull;
  private DungeonModel wrappingDungeonNoTreasure;
  private DungeonModel wrappingDungeonNoDegree;

  private final int rows = 6;
  private final int columns = 8;

  @Before
  public void setup() {
    RandomImpl.setSeed(0);
    nonWrappingDungeon = new DungeonModelImpl(rows, columns, false, 10, 50);
    RandomImpl.setSeed(1);
    nonWrappingDungeonFull = new DungeonModelImpl(rows, columns, false, 1000, 50);
    RandomImpl.setSeed(2);
    nonWrappingDungeonNoTreasure = new DungeonModelImpl(rows, columns, false, 10, 0);
    RandomImpl.setSeed(3);
    nonWrappingDungeonNoDegree = new DungeonModelImpl(rows, columns, false, 0, 50);

    RandomImpl.setSeed(4);
    wrappingDungeon = new DungeonModelImpl(rows, columns, true, 20, 50);
    RandomImpl.setSeed(5);
    wrappingDungeonFull = new DungeonModelImpl(rows, columns, true, 1000, 50);
    RandomImpl.setSeed(6);
    wrappingDungeonNoTreasure = new DungeonModelImpl(rows, columns, true, 20, 0);
    RandomImpl.setSeed(7);
    wrappingDungeonNoDegree = new DungeonModelImpl(rows, columns, true, 0, 50);

  }

  @Test
  public void testConstructorFail() {
    try {
      new DungeonModelImpl(-6, 8, true, 20, 50);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The arguments are not proper. Constraints are:\n"
                      + "rows >= 6, columns >= 6, degree >= 0, percentage between 0 and 100",
              e.getMessage());
    }

    try {
      new DungeonModelImpl(6, -8, true, 20, 50);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The arguments are not proper. Constraints are:\n"
                      + "rows >= 6, columns >= 6, degree >= 0, percentage between 0 and 100",
              e.getMessage());
    }

    try {
      new DungeonModelImpl(6, 8, true, -20, 50);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The arguments are not proper. Constraints are:\n"
                      + "rows >= 6, columns >= 6, degree >= 0, percentage between 0 and 100",
              e.getMessage());
    }

    try {
      new DungeonModelImpl(6, 8, true, 20, -50);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The arguments are not proper. Constraints are:\n"
                      + "rows >= 6, columns >= 6, degree >= 0, percentage between 0 and 100",
              e.getMessage());
    }

    try {
      new DungeonModelImpl(6, 8, true, 20, 150);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The arguments are not proper. Constraints are:\n"
                      + "rows >= 6, columns >= 6, degree >= 0, percentage between 0 and 100",
              e.getMessage());
    }

    try {
      new DungeonModelImpl(3, 3, false, 20, 50);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The arguments are not proper. Constraints are:\n"
                      + "rows >= 6, columns >= 6, degree >= 0, percentage between 0 and 100",
              e.getMessage());
    }
  }

  @Test
  public void testStartEndNotTunnel() {
    //Test to check if start and end points are not tunnel
    assertTrue(nonWrappingDungeon.getStartLocation().getPossibleDirections().size() != 2);
    assertTrue(nonWrappingDungeon.getEndLocation().getPossibleDirections().size() != 2);

    assertTrue(nonWrappingDungeonFull.getStartLocation().getPossibleDirections().size() != 2);
    assertTrue(nonWrappingDungeonFull.getEndLocation().getPossibleDirections().size() != 2);

    assertTrue(nonWrappingDungeonNoDegree.getStartLocation().getPossibleDirections().size() != 2);
    assertTrue(nonWrappingDungeonNoDegree.getEndLocation().getPossibleDirections().size() != 2);

    assertTrue(nonWrappingDungeonNoTreasure.getStartLocation().getPossibleDirections().size() != 2);
    assertTrue(nonWrappingDungeonNoTreasure.getEndLocation().getPossibleDirections().size() != 2);

    assertTrue(wrappingDungeon.getStartLocation().getPossibleDirections().size() != 2);
    assertTrue(wrappingDungeon.getEndLocation().getPossibleDirections().size() != 2);

    assertTrue(wrappingDungeonFull.getStartLocation().getPossibleDirections().size() != 2);
    assertTrue(wrappingDungeonFull.getEndLocation().getPossibleDirections().size() != 2);

    assertTrue(wrappingDungeonNoDegree.getStartLocation().getPossibleDirections().size() != 2);
    assertTrue(wrappingDungeonNoDegree.getEndLocation().getPossibleDirections().size() != 2);

    assertTrue(wrappingDungeonNoTreasure.getStartLocation().getPossibleDirections().size() != 2);
    assertTrue(wrappingDungeonNoTreasure.getEndLocation().getPossibleDirections().size() != 2);
  }

  @Test
  public void testStartEndDistance() {
    //Test to check if start and end point has a distance greater than 5.
    Point expectedStartPoint = new Point(1, 1);
    Point expectedEndPoint = new Point(0, 7);
    assertEquals(expectedStartPoint, nonWrappingDungeon.getStartLocation().getPoint());
    assertEquals(expectedEndPoint, nonWrappingDungeon.getEndLocation().getPoint());
    assertEquals(13, nonWrappingDungeon.getDistance(
            nonWrappingDungeon.getStartLocation().getPoint(),
            nonWrappingDungeon.getEndLocation().getPoint()));

    assertEquals(6, wrappingDungeon.getDistance(
            wrappingDungeon.getStartLocation().getPoint(),
            wrappingDungeon.getEndLocation().getPoint()));


    assertTrue(nonWrappingDungeonFull.getDistance(
            nonWrappingDungeonFull.getStartLocation().getPoint(),
            nonWrappingDungeonFull.getEndLocation().getPoint()) >= 5);
    assertTrue(nonWrappingDungeonNoDegree.getDistance(
            nonWrappingDungeonNoDegree.getStartLocation().getPoint(),
            nonWrappingDungeonNoDegree.getEndLocation().getPoint()) >= 5);
    assertTrue(nonWrappingDungeonNoTreasure.getDistance(
            nonWrappingDungeonNoTreasure.getStartLocation().getPoint(),
            nonWrappingDungeonNoTreasure.getEndLocation().getPoint()) >= 5);
    assertTrue(wrappingDungeonFull.getDistance(
            wrappingDungeonFull.getStartLocation().getPoint(),
            wrappingDungeonFull.getEndLocation().getPoint()) >= 5);
    assertTrue(wrappingDungeonNoDegree.getDistance(
            wrappingDungeonNoDegree.getStartLocation().getPoint(),
            wrappingDungeonNoDegree.getEndLocation().getPoint()) >= 5);
    assertTrue(wrappingDungeonNoTreasure.getDistance(
            wrappingDungeonNoTreasure.getStartLocation().getPoint(),
            wrappingDungeonNoTreasure.getEndLocation().getPoint()) >= 5);

  }

  @Test
  public void testNoWrapping() {
    //For isWrapped = false, there should be no edge between the end locations.
    for (int j = 0; j < columns; j++) {
      assertFalse(nonWrappingDungeonFull.getLocationDescription(
              new Point(0, j)).getPossibleDirections().contains(Direction.NORTH));
      assertFalse(nonWrappingDungeonFull.getLocationDescription(
              new Point(rows - 1, j)).getPossibleDirections().contains(Direction.SOUTH));
    }
    for (int i = 0; i < rows; i++) {
      assertFalse(nonWrappingDungeonFull.getLocationDescription(
              new Point(i, 0)).getPossibleDirections().contains(Direction.WEST));
      assertFalse(nonWrappingDungeonFull.getLocationDescription(
              new Point(i, columns - 1)).getPossibleDirections().contains(Direction.EAST));
    }
  }

  @Test
  public void testWrapping() {
    //For isWrapped = true, there can be an edge between the end locations.
    // For full wrapping dungeon, all edges must be present.
    for (int j = 0; j < columns; j++) {
      assertTrue(wrappingDungeonFull.getLocationDescription(
              new Point(0, j)).getPossibleDirections().contains(Direction.NORTH));
      assertTrue(wrappingDungeonFull.getLocationDescription(
              new Point(rows - 1, j)).getPossibleDirections().contains(Direction.SOUTH));
    }
    for (int i = 0; i < rows; i++) {
      assertTrue(wrappingDungeonFull.getLocationDescription(
              new Point(i, 0)).getPossibleDirections().contains(Direction.WEST));
      assertTrue(wrappingDungeonFull.getLocationDescription(
              new Point(i, columns - 1)).getPossibleDirections().contains(Direction.EAST));
    }
  }


  @Test
  public void degreeOfInterConnectivity() {
    int totalEdgesNonWrapping = 0;
    int totalEdgesNonWrappingFull = 0;
    int totalEdgesNonWrappingNoDegree = 0;
    int totalEdgesWrapping = 0;
    int totalEdgesWrappingFull = 0;
    int totalEdgesWrappingNoDegree = 0;

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        totalEdgesNonWrapping += nonWrappingDungeon.getLocationDescription(
                new Point(i, j)).getPossibleDirections().size();
        totalEdgesNonWrappingFull += nonWrappingDungeonFull.getLocationDescription(
                new Point(i, j)).getPossibleDirections().size();
        totalEdgesNonWrappingNoDegree += nonWrappingDungeonNoDegree.getLocationDescription(
                new Point(i, j)).getPossibleDirections().size();
        totalEdgesWrapping += wrappingDungeon.getLocationDescription(
                new Point(i, j)).getPossibleDirections().size();
        totalEdgesWrappingFull += wrappingDungeonFull.getLocationDescription(
                new Point(i, j)).getPossibleDirections().size();
        totalEdgesWrappingNoDegree += wrappingDungeonNoDegree.getLocationDescription(
                new Point(i, j)).getPossibleDirections().size();

      }
    }
    totalEdgesNonWrapping /= 2;
    totalEdgesNonWrappingFull /= 2;
    totalEdgesNonWrappingNoDegree /= 2;
    totalEdgesWrapping /= 2;
    totalEdgesWrappingFull /= 2;
    totalEdgesWrappingNoDegree /= 2;

    //An MST will have rows*columns-1 edges.
    // Thus, degree of interconnectivity should be total edges - edges in MST.
    assertEquals(10, totalEdgesNonWrapping - (rows * columns - 1));
    assertEquals(35, totalEdgesNonWrappingFull - (rows * columns - 1));
    assertEquals(0, totalEdgesNonWrappingNoDegree - (rows * columns - 1));
    assertEquals(20, totalEdgesWrapping - (rows * columns - 1));
    assertEquals(49, totalEdgesWrappingFull - (rows * columns - 1));
    assertEquals(0, totalEdgesWrappingNoDegree - (rows * columns - 1));
  }

  @Test
  public void testTunnelNoTreasure() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        LocationDescription location1 = nonWrappingDungeon.getLocationDescription(new Point(i, j));
        if (location1.getPossibleDirections().size() == 2) {
          //For Tunnel we need to check it does not contain any treasure.
          assertTrue(location1.getTreasureMap().isEmpty());
        }

        LocationDescription location2 = wrappingDungeon.getLocationDescription(new Point(i, j));
        if (location2.getPossibleDirections().size() == 2) {
          //For Tunnel we need to check it does not contain any treasure.
          assertTrue(location2.getTreasureMap().isEmpty());
        }
      }
    }
  }

  @Test
  public void testPercentageOfCavesWithTreasure() {
    int totalCavesNonWrapping = 0;
    int totalCavesWithTreasureNonWrapping = 0;

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        LocationDescription location = nonWrappingDungeon.getLocationDescription(new Point(i, j));
        if (location.getPossibleDirections().size() != 2) {
          totalCavesNonWrapping++;
          if (!location.getTreasureMap().isEmpty()) {
            totalCavesWithTreasureNonWrapping++;
          }
        }
      }
    }
    //To check if number of caves having treasure satisfy the constraint mentioned by the user.
    assertEquals(50, (totalCavesWithTreasureNonWrapping * 100L) / totalCavesNonWrapping);

    totalCavesNonWrapping = 0;
    totalCavesWithTreasureNonWrapping = 0;

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        LocationDescription location =
                nonWrappingDungeonNoTreasure.getLocationDescription(new Point(i, j));
        if (location.getPossibleDirections().size() != 2) {
          totalCavesNonWrapping++;
          if (!location.getTreasureMap().isEmpty()) {
            totalCavesWithTreasureNonWrapping++;
          }
        }
      }
    }
    //To check if number of caves having treasure satisfy the constraint mentioned by the user.
    assertEquals(0, (totalCavesWithTreasureNonWrapping * 100L) / totalCavesNonWrapping);
  }

  @Test
  public void testConnectivityOfLocations() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        //To check if all locations are connected to atleast one other location
        assertTrue(nonWrappingDungeonNoDegree.getCurrentLocationDescription()
                .getPossibleDirections().size() != 0);
        assertTrue(wrappingDungeonNoDegree.getCurrentLocationDescription()
                .getPossibleDirections().size() != 0);
      }
    }
  }

  @Test
  public void testPlayerStartingPosition() {
    //To check if player is initially at the start point.
    assertEquals(nonWrappingDungeon.getStartLocation().getPoint(),
            nonWrappingDungeon.getPlayerDescription().getPoint());
    assertEquals(wrappingDungeon.getStartLocation().getPoint(),
            wrappingDungeon.getPlayerDescription().getPoint());
  }

  @Test
  public void testPlayerDescription() {
    //Test initial player description.
    String expectedDescription = "Player Description:\n"
            + "Point: (1, 1)\n"
            + "Treasures collected: {}";
    assertEquals(expectedDescription, nonWrappingDungeon.getPlayerDescription().toString());

    nonWrappingDungeon.movePlayer(Direction.WEST);
    nonWrappingDungeon.movePlayer(Direction.EAST);
    assertEquals(expectedDescription, nonWrappingDungeon.getPlayerDescription().toString());

    nonWrappingDungeon.pickAllTreasures();
    nonWrappingDungeon.movePlayer(Direction.NORTH);

    expectedDescription = "Player Description:\n"
            + "Point: (0, 1)\n"
            + "Treasures collected: {DIAMOND=4, SAPPHIRE=3}";
    assertEquals(expectedDescription, nonWrappingDungeon.getPlayerDescription().toString());
  }

  @Test
  public void testLocationDescription() {
    //Test initial location description.
    String expectedDescription = "Location Description:\n"
            + "Possible directions: [NORTH, EAST, SOUTH, WEST]\n"
            + "Treasures: {DIAMOND=4, SAPPHIRE=3}";
    assertEquals(expectedDescription,
            nonWrappingDungeon.getCurrentLocationDescription().toString());


    nonWrappingDungeon.movePlayer(Direction.NORTH);
    expectedDescription = "Location Description:\n"
            + "Possible directions: [SOUTH]\n"
            + "Treasures: {DIAMOND=5, RUBY=4}";
    assertEquals(expectedDescription,
            nonWrappingDungeon.getCurrentLocationDescription().toString());
  }

  @Test
  public void testPickAllTreasure() {
    Map<Treasure, Integer> emptyMap = new TreeMap<>();
    Map<Treasure, Integer> expectedMap = new TreeMap<>();
    expectedMap.put(Treasure.DIAMOND, 4);
    expectedMap.put(Treasure.SAPPHIRE, 3);

    //Initial Player and Location Treasures
    assertEquals(emptyMap, nonWrappingDungeon.getPlayerDescription().getCollectedTreasures());
    assertEquals(expectedMap, nonWrappingDungeon.getCurrentLocationDescription().getTreasureMap());
    nonWrappingDungeon.pickAllTreasures();

    //Player collecting the treasure from Location. Location treasure becomes empty.
    assertEquals(expectedMap, nonWrappingDungeon.getPlayerDescription().getCollectedTreasures());
    assertEquals(emptyMap, nonWrappingDungeon.getCurrentLocationDescription().getTreasureMap());

    nonWrappingDungeon.movePlayer(Direction.NORTH);
    expectedMap = new TreeMap<>();
    expectedMap.put(Treasure.DIAMOND, 5);
    expectedMap.put(Treasure.RUBY, 4);
    assertEquals(expectedMap, nonWrappingDungeon.getCurrentLocationDescription().getTreasureMap());

    //Player picks up treasure from another location
    nonWrappingDungeon.pickAllTreasures();
    expectedMap = new TreeMap<>();
    expectedMap.put(Treasure.DIAMOND, 9);
    expectedMap.put(Treasure.SAPPHIRE, 3);
    expectedMap.put(Treasure.RUBY, 4);
    assertEquals(expectedMap, nonWrappingDungeon.getPlayerDescription().getCollectedTreasures());

    //Trying to pick up again from the same location
    nonWrappingDungeon.pickAllTreasures();
    assertEquals(expectedMap, nonWrappingDungeon.getPlayerDescription().getCollectedTreasures());

    assertEquals(emptyMap, nonWrappingDungeon.getCurrentLocationDescription().getTreasureMap());
    nonWrappingDungeon.movePlayer(Direction.SOUTH);
    assertEquals(emptyMap, nonWrappingDungeon.getCurrentLocationDescription().getTreasureMap());

    nonWrappingDungeon.movePlayer(Direction.WEST);
    assertEquals(2,
            nonWrappingDungeon.getCurrentLocationDescription().getPossibleDirections().size());

    //Picking from a tunnel, gives no change in player's collected treasure
    nonWrappingDungeon.pickAllTreasures();
    assertEquals(expectedMap, nonWrappingDungeon.getPlayerDescription().getCollectedTreasures());
  }

  @Test
  public void testMovePlayerFail() {
    nonWrappingDungeon.movePlayer(Direction.WEST);
    try {
      nonWrappingDungeon.movePlayer(Direction.WEST);
    } catch (IllegalArgumentException e) {
      assertEquals("Direction WEST is not possible from here.", e.getMessage());
    }
  }

  @Test
  public void testMovePlayer() {
    assertEquals(new Point(1, 1),
            nonWrappingDungeon.getPlayerDescription().getPoint());
    nonWrappingDungeon.movePlayer(Direction.NORTH);
    assertEquals(new Point(0, 1),
            nonWrappingDungeon.getPlayerDescription().getPoint());
    nonWrappingDungeon.movePlayer(Direction.SOUTH);
    assertEquals(new Point(1, 1),
            nonWrappingDungeon.getPlayerDescription().getPoint());
    nonWrappingDungeon.movePlayer(Direction.EAST);
    assertEquals(new Point(1, 2),
            nonWrappingDungeon.getPlayerDescription().getPoint());
    nonWrappingDungeon.movePlayer(Direction.WEST);
    assertEquals(new Point(1, 1),
            nonWrappingDungeon.getPlayerDescription().getPoint());

    assertEquals(new Point(4, 2), wrappingDungeon.getPlayerDescription().getPoint());
    wrappingDungeon.movePlayer(Direction.SOUTH);
    assertEquals(new Point(5, 2), wrappingDungeon.getPlayerDescription().getPoint());
    wrappingDungeon.movePlayer(Direction.WEST);
    assertEquals(new Point(5, 1), wrappingDungeon.getPlayerDescription().getPoint());
    wrappingDungeon.movePlayer(Direction.SOUTH);
    assertEquals(new Point(0, 1), wrappingDungeon.getPlayerDescription().getPoint());
    wrappingDungeon.movePlayer(Direction.WEST);
    assertEquals(new Point(0, 0), wrappingDungeon.getPlayerDescription().getPoint());
    wrappingDungeon.movePlayer(Direction.NORTH);
    assertEquals(new Point(5, 0), wrappingDungeon.getPlayerDescription().getPoint());
    wrappingDungeon.movePlayer(Direction.WEST);
    assertEquals(new Point(5, 7), wrappingDungeon.getPlayerDescription().getPoint());
    wrappingDungeon.movePlayer(Direction.EAST);
    assertEquals(new Point(5, 0), wrappingDungeon.getPlayerDescription().getPoint());
  }

  @Test
  public void testGetFullTraversalPath() {
    Map<Treasure, Integer> totalTreasure = new TreeMap<>();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        LocationDescription location = nonWrappingDungeon.getLocationDescription(new Point(i, j));
        Map<Treasure, Integer> locationTreasure = location.getTreasureMap();
        for (Treasure treasure : locationTreasure.keySet()) {
          totalTreasure.put(treasure, totalTreasure.getOrDefault(
                  treasure, 0) + locationTreasure.get(treasure));
        }
      }
    }
    List<Direction> coverAllLocation = nonWrappingDungeon.getFullTraversalPath();
    //Test for player visiting all locations
    boolean[][] visited = new boolean[rows][columns];
    Point currentPoint = nonWrappingDungeon.getPlayerDescription().getPoint();
    visited[currentPoint.getRow()][currentPoint.getColumn()] = true;
    for (Direction direction : coverAllLocation) {
      nonWrappingDungeon.movePlayer(direction);
      nonWrappingDungeon.pickAllTreasures();
      currentPoint = nonWrappingDungeon.getPlayerDescription().getPoint();
      visited[currentPoint.getRow()][currentPoint.getColumn()] = true;
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        assertTrue(visited[i][j]);
      }
    }

    assertEquals(nonWrappingDungeon.getEndLocation().getPoint(),
            nonWrappingDungeon.getPlayerDescription().getPoint());
    assertEquals(totalTreasure, nonWrappingDungeon.getPlayerDescription().getCollectedTreasures());


    //FOR WRAPPING DUNGEON

    totalTreasure = new TreeMap<>();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        LocationDescription location = wrappingDungeon.getLocationDescription(new Point(i, j));
        Map<Treasure, Integer> locationTreasure = location.getTreasureMap();
        for (Treasure treasure : locationTreasure.keySet()) {
          totalTreasure.put(treasure, totalTreasure.getOrDefault(
                  treasure, 0) + locationTreasure.get(treasure));
        }
      }
    }
    coverAllLocation = wrappingDungeon.getFullTraversalPath();
    //Test for player visiting all locations
    visited = new boolean[rows][columns];
    currentPoint = wrappingDungeon.getPlayerDescription().getPoint();
    visited[currentPoint.getRow()][currentPoint.getColumn()] = true;
    for (Direction direction : coverAllLocation) {
      wrappingDungeon.movePlayer(direction);
      wrappingDungeon.pickAllTreasures();
      currentPoint = wrappingDungeon.getPlayerDescription().getPoint();
      visited[currentPoint.getRow()][currentPoint.getColumn()] = true;
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        assertTrue(visited[i][j]);
      }
    }

    assertEquals(wrappingDungeon.getEndLocation().getPoint(),
            wrappingDungeon.getPlayerDescription().getPoint());
    assertEquals(totalTreasure, wrappingDungeon.getPlayerDescription().getCollectedTreasures());
  }

  @Test
  public void testGetShortestPath() {
    //For non wrapping
    List<Direction> shortestPath = nonWrappingDungeon.getShortestPathToEnd();
    assertEquals(13, shortestPath.size());
    for (Direction direction : shortestPath) {
      nonWrappingDungeon.movePlayer(direction);
    }
    assertEquals(nonWrappingDungeon.getEndLocation().getPoint(),
            nonWrappingDungeon.getPlayerDescription().getPoint());

    //For wrapping
    shortestPath = wrappingDungeon.getShortestPathToEnd();
    assertEquals(6, shortestPath.size());
    for (Direction direction : shortestPath) {
      wrappingDungeon.movePlayer(direction);
    }
    assertEquals(wrappingDungeon.getEndLocation().getPoint(),
            wrappingDungeon.getPlayerDescription().getPoint());
  }

  @Test
  public void gameOver() {
    //For non wrapping
    assertFalse(nonWrappingDungeon.isGameOver());
    List<Direction> shortestPath = nonWrappingDungeon.getShortestPathToEnd();
    for (Direction direction : shortestPath) {
      assertFalse(nonWrappingDungeon.isGameOver());
      nonWrappingDungeon.movePlayer(direction);
    }
    assertEquals(nonWrappingDungeon.getEndLocation().getPoint(),
            nonWrappingDungeon.getPlayerDescription().getPoint());
    assertTrue(nonWrappingDungeon.isGameOver());

    try {
      nonWrappingDungeon.movePlayer(Direction.WEST);
      fail();
    } catch (IllegalStateException e) {
      assertEquals(e.getMessage(), "Player has reached the end point. Game over");
    }

    //For wrapping
    assertFalse(wrappingDungeon.isGameOver());
    shortestPath = wrappingDungeon.getShortestPathToEnd();
    for (Direction direction : shortestPath) {
      assertFalse(wrappingDungeon.isGameOver());
      wrappingDungeon.movePlayer(direction);
    }
    assertEquals(wrappingDungeon.getEndLocation().getPoint(),
            wrappingDungeon.getPlayerDescription().getPoint());
    assertTrue(wrappingDungeon.isGameOver());

    try {
      wrappingDungeon.movePlayer(Direction.WEST);
      fail();
    } catch (IllegalStateException e) {
      assertEquals(e.getMessage(), "Player has reached the end point. Game over");
    }
  }

  @Test
  public void testPrintDungeon() {
    String output = "                                                            \n"
            + "    ( )    ( )    ( ) —— ( ) —— ( ) —— ( )    ( ) —— (&)    \n"
            + "     |      |      |      |                    |            \n"
            + "    ( ) —— (P) —— ( )    ( )    ( ) —— ( ) —— ( ) —— ( )    \n"
            + "            |             |      |      |                   \n"
            + "    ( )    ( ) —— ( ) —— ( )    ( ) —— ( ) —— ( ) —— ( )    \n"
            + "     |             |                    |             |     \n"
            + "    ( )    ( ) —— ( ) —— ( )    ( ) —— ( ) —— ( ) —— ( )    \n"
            + "     |      |             |      |      |      |      |     \n"
            + "    ( ) —— ( ) —— ( ) —— ( ) —— ( )    ( ) —— ( ) —— ( )    \n"
            + "     |      |      |      |      |      |      |            \n"
            + "    ( ) —— ( ) —— ( )    ( ) —— ( )    ( ) —— ( ) —— ( )    \n"
            + "                                                            \n";
    assertEquals(output, nonWrappingDungeon.printDungeon());

    output = "                                                            \n"
            + "    ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( )    \n"
            + "     |      |      |      |      |      |      |      |     \n"
            + "    ( ) —— ( ) —— (P) —— ( ) —— ( ) —— ( ) —— ( ) —— ( )    \n"
            + "     |      |      |      |      |      |      |      |     \n"
            + "    ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( )    \n"
            + "     |      |      |      |      |      |      |      |     \n"
            + "    ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( )    \n"
            + "     |      |      |      |      |      |      |      |     \n"
            + "    ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( )    \n"
            + "     |      |      |      |      |      |      |      |     \n"
            + "    ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— (&) —— ( )    \n"
            + "                                                            \n";
    assertEquals(output, nonWrappingDungeonFull.printDungeon());

    output = "                                                            \n"
            + "    ( ) —— ( )    ( )    ( ) —— ( ) —— ( ) —— ( ) —— ( )    \n"
            + "     |      |      |      |             |                   \n"
            + "    ( ) —— (P) —— ( ) —— ( ) —— ( ) —— ( ) —— ( )    (&)    \n"
            + "     |      |      |                                  |     \n"
            + "    ( ) —— ( )    ( )    ( ) —— ( )    ( ) —— ( ) —— ( )    \n"
            + "     |      |             |      |      |      |      |     \n"
            + "    ( ) —— ( ) —— ( )    ( ) —— ( )    ( ) —— ( ) —— ( )    \n"
            + "     |             |      |                    |      |     \n"
            + "    ( )    ( ) —— ( ) —— ( )    ( ) —— ( ) —— ( ) —— ( )    \n"
            + "                          |             |      |      |     \n"
            + "    ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( )    \n"
            + "                                                            \n";
    assertEquals(output, nonWrappingDungeonNoTreasure.printDungeon());

    output = "                                                            \n"
            + "    ( )    (&) —— ( )    ( )    (P) —— ( )    ( )    ( )    \n"
            + "     |             |      |             |      |      |     \n"
            + "    ( )    ( )    ( ) —— ( ) —— ( )    ( ) —— ( )    ( )    \n"
            + "     |      |             |                    |      |     \n"
            + "    ( ) —— ( ) —— ( ) —— ( )    ( )    ( )    ( )    ( )    \n"
            + "     |             |             |      |      |      |     \n"
            + "    ( ) —— ( )    ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( )    \n"
            + "            |             |                    |            \n"
            + "    ( )    ( )    ( ) —— ( ) —— ( ) —— ( )    ( ) —— ( )    \n"
            + "     |      |                           |             |     \n"
            + "    ( ) —— ( ) —— ( )    ( ) —— ( ) —— ( ) —— ( )    ( )    \n"
            + "                                                            \n";
    assertEquals(output, nonWrappingDungeonNoDegree.printDungeon());

    output = "     |      |             |      |                    |     \n"
            + " —— ( ) —— ( ) —— ( )    ( ) —— ( ) —— ( ) —— (&) —— ( ) —— \n"
            + "     |                           |      |      |      |     \n"
            + " —— ( ) —— ( ) —— ( )    ( ) —— ( )    ( ) —— ( ) —— ( ) —— \n"
            + "     |      |      |             |      |      |      |     \n"
            + " —— ( )    ( )    ( ) —— ( ) —— ( ) —— ( )    ( ) —— ( ) —— \n"
            + "     |      |      |      |      |                    |     \n"
            + "    ( ) —— ( )    ( )    ( )    ( ) —— ( ) —— ( )    ( )    \n"
            + "     |             |      |      |             |      |     \n"
            + "    ( )    ( ) —— (P) —— ( )    ( ) —— ( ) —— ( )    ( )    \n"
            + "     |      |      |      |             |      |      |     \n"
            + " —— ( )    ( ) —— ( )    ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— \n"
            + "     |      |             |      |                    |     \n";
    assertEquals(output, wrappingDungeon.printDungeon());

    output = "     |      |      |      |      |      |      |      |     \n"
            + " —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— \n"
            + "     |      |      |      |      |      |      |      |     \n"
            + " —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— \n"
            + "     |      |      |      |      |      |      |      |     \n"
            + " —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— \n"
            + "     |      |      |      |      |      |      |      |     \n"
            + " —— ( ) —— (&) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— \n"
            + "     |      |      |      |      |      |      |      |     \n"
            + " —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— \n"
            + "     |      |      |      |      |      |      |      |     \n"
            + " —— ( ) —— ( ) —— ( ) —— ( ) —— ( ) —— (P) —— ( ) —— ( ) —— \n"
            + "     |      |      |      |      |      |      |      |     \n";
    assertEquals(output, wrappingDungeonFull.printDungeon());
    output = "     |      |      |      |                    |            \n"
            + " —— ( ) —— ( ) —— ( )    ( ) —— ( ) —— ( )    ( )    ( ) —— \n"
            + "            |                           |      |      |     \n"
            + " —— ( ) —— ( ) —— ( ) —— ( )    ( ) —— ( ) —— ( ) —— (&) —— \n"
            + "                   |      |      |      |      |      |     \n"
            + " —— ( ) —— ( )    ( ) —— ( ) —— ( ) —— ( )    ( ) —— ( ) —— \n"
            + "            |                    |      |      |      |     \n"
            + "    ( )    ( ) —— ( ) —— ( ) —— ( ) —— ( )    ( ) —— ( )    \n"
            + "     |      |      |                    |      |      |     \n"
            + " —— ( ) —— (P)    ( ) —— ( ) —— ( ) —— ( ) —— ( )    ( ) —— \n"
            + "     |      |      |      |             |      |      |     \n"
            + " —— ( )    ( ) —— ( ) —— ( )    ( ) —— ( )    ( ) —— ( ) —— \n"
            + "     |      |      |      |                    |            \n";
    assertEquals(output, wrappingDungeonNoTreasure.printDungeon());
    output = "     |      |      |             |      |      |      |     \n"
            + " —— ( )    ( )    ( ) —— ( )    ( ) —— ( ) —— ( )    ( ) —— \n"
            + "     |      |             |      |             |            \n"
            + " —— ( )    ( )    (&) —— ( )    ( )    ( ) —— ( )    ( ) —— \n"
            + "     |                    |             |      |            \n"
            + " —— ( )    ( ) —— ( ) —— ( ) —— ( )    ( )    ( )    ( ) —— \n"
            + "     |                                         |            \n"
            + "    ( )    ( ) —— ( ) —— ( ) —— ( )    ( )    ( ) —— ( )    \n"
            + "                   |      |             |      |            \n"
            + " —— ( ) —— ( ) —— ( )    ( )    ( ) —— ( ) —— ( ) —— ( ) —— \n"
            + "            |      |                                        \n"
            + "    ( ) —— ( )    ( ) —— ( )    ( )    (P)    ( )    ( )    \n"
            + "     |      |      |             |      |      |      |     \n";
    assertEquals(output, wrappingDungeonNoDegree.printDungeon());
  }
}