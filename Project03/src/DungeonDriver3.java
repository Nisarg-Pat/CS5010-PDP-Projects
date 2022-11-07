import dungeon3.Direction;
import dungeon3.DungeonModelImpl;
import structureddata3.LocationDescription;
import structureddata3.PlayerDescription;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * A Driver class to create one run of program. It has all the methods that
 * user can call in a Dungeon.
 */
public class DungeonDriver3 {

  /**
   * Main() method to run the driver class.
   *
   * @param args arguments for main method. Should be 5 arguments.
   *             rows: Number of rows in the dungeon. Should be at least 6.
   *             columns: Number of Columns in the dungeon. Should be at least 6.
   *             isWrapped: Whether the dungeon is wrapped around its end
   *             degreeOfInterconnectivity: The degree of interconnectivity for the dungeon.
   *             percentageCavesWithTreasure: The percentage of caves having treasure in it.
   */
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Welcome to the Dungeon Game");
    System.out.println("Commands: 'move', 'pick', 'quit', 'visitAll', 'shortestPath'");
    int rows;
    int columns;
    int degreeOfConnectivity;
    int percentageCavesWithTreasure;
    boolean isWrapped;
    try {
      rows = Integer.parseInt(args[0]);
      columns = Integer.parseInt(args[1]);
      isWrapped = Boolean.parseBoolean(args[2]);
      degreeOfConnectivity = Integer.parseInt(args[3]);
      percentageCavesWithTreasure = Integer.parseInt(args[4]);
    } catch (Exception e) {
      System.out.println("The command line arguments are not valid.");
      return;
    }
    DungeonModelImpl dungeon = new DungeonModelImpl(rows, columns,
            isWrapped, degreeOfConnectivity, percentageCavesWithTreasure);
    System.out.println(dungeon.printDungeon());
    describePlayer(dungeon.getPlayerDescription());
    System.out.println();
    describeLocation(dungeon.getCurrentLocationDescription());
    System.out.println();
    Map<String, Direction> directionMap = new HashMap<>();
    directionMap.put("north", Direction.NORTH);
    directionMap.put("west", Direction.WEST);
    directionMap.put("south", Direction.SOUTH);
    directionMap.put("east", Direction.EAST);


    String command = "";

    while (!command.equalsIgnoreCase("quit") && !dungeon.isGameOver()) {
      System.out.print("Enter a command('move', 'pick', 'quit') : ");
      command = sc.next();
      switch (command) {
        case ("move"):
          System.out.print("Enter a direction ('north','east','south','west') : ");
          String direction = sc.next();
          System.out.println();
          if (directionMap.containsKey(direction.toLowerCase())) {
            try {
              dungeon.movePlayer(directionMap.get(direction));
              System.out.println(dungeon.printDungeon());
              describePlayer(dungeon.getPlayerDescription());
              System.out.println();
              describeLocation(dungeon.getCurrentLocationDescription());
              System.out.println();
            } catch (IllegalArgumentException e) {
              System.out.println(e.getMessage());
            }
          } else {
            System.out.println("Invalid input.");
          }
          break;
        case ("quit"):
          break;
        case ("pick"):
          System.out.println();
          dungeon.pickAllTreasures();
          describePlayer(dungeon.getPlayerDescription());
          System.out.println();
          break;
        case ("visitAll"):
          System.out.println();
          dungeon.pickAllTreasures();
          List<Direction> directions = dungeon.getFullTraversalPath();
          for (Direction direction1 : directions) {
            dungeon.movePlayer(direction1);
            dungeon.pickAllTreasures();
            System.out.println(dungeon.printDungeon());
            describePlayer(dungeon.getPlayerDescription());
            System.out.println();
            describeLocation(dungeon.getCurrentLocationDescription());
            System.out.println();
          }
          break;
        case ("shortestPath"):
          System.out.println();
          dungeon.pickAllTreasures();
          directions = dungeon.getShortestPathToEnd();
          for (Direction direction1 : directions) {
            dungeon.movePlayer(direction1);
            dungeon.pickAllTreasures();
            System.out.println(dungeon.printDungeon());
            describePlayer(dungeon.getPlayerDescription());
            System.out.println();
            describeLocation(dungeon.getCurrentLocationDescription());
            System.out.println();
          }
          break;
        default:
          System.out.println("Invalid command");
          break;
      }
    }

    System.out.println();
    System.out.println("Game Over! Player collected the following treasures: ");
    System.out.println(dungeon.getPlayerDescription().getCollectedTreasures());
  }

  private static void describeLocation(LocationDescription location) {
    System.out.println(location);
  }

  private static void describePlayer(PlayerDescription player) {
    System.out.println(player);
  }

}
