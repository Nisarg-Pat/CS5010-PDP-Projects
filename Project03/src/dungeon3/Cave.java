package dungeon3;

import random.RandomImpl;
import structureddata3.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Cave representation of the location.
 * A cave can have 1, 3 or 4 connections.
 * Cave can contain treasures.
 * Visibility: Package - private
 */
class Cave implements Location {
  protected final Map<Direction, Location> connectedMap;
  protected final Map<Treasure, Integer> treasureMap;
  protected final Point point;

  protected Cave(int row, int column) {
    if (row < 0 || column < 0) {
      throw new IllegalArgumentException("Invalid argument.");
    }
    this.point = new Point(row, column);
    this.connectedMap = new TreeMap<>();
    treasureMap = new TreeMap<>();
  }

  protected Cave(int row, int column, Map<Direction, Location> connectedMap) {
    this(row, column);
    this.connectedMap.putAll(connectedMap);
  }

  @Override
  public Point getPoint() {
    return point;
  }

  @Override
  public Map<Direction, Location> getConnections() {
    return new TreeMap<>(connectedMap);
  }

  @Override
  public Map<Treasure, Integer> getTreasureMap() {
    return new TreeMap<>(treasureMap);
  }

  @Override
  public boolean isCave() {
    return true;
  }

  @Override
  public boolean hasEdge(Location location) {
    if (location == null) {
      throw new IllegalArgumentException("Invalid location.");
    }
    return connectedMap.containsValue(location);
  }

  @Override
  public Location addPath(Location other) {
    if (other == null) {
      throw new IllegalArgumentException("Invalid location.");
    }
    Direction direction = getDirection(other.getPoint());
    connectedMap.put(direction, other);
    if (connectedMap.size() == 2) {
      Tunnel newLocation = new Tunnel(point.getRow(), point.getColumn(), connectedMap);
      for (Location location : connectedMap.values()) {
        location.updatePath(newLocation);
      }
      return newLocation;
    }
    return this;
  }

  @Override
  public void updatePath(Location other) {
    if (other == null) {
      throw new IllegalArgumentException("Invalid location.");
    }
    Direction direction = getDirection(other.getPoint());
    connectedMap.put(direction, other);
  }

  @Override
  public void setTreasure() {
    List<Treasure> treasures = new ArrayList<>(List.of(Treasure.values()));
    int index = RandomImpl.getIntInRange(0, treasures.size() - 1);
    treasureMap.put(treasures.get(index), RandomImpl.getIntInRange(1, 10));
    treasures.remove(index);
    int randomNumber = RandomImpl.getIntInRange(1, 100);
    if (randomNumber <= 50) {
      index = RandomImpl.getIntInRange(0, treasures.size() - 1);
      treasureMap.put(treasures.get(index), RandomImpl.getIntInRange(1, 10));
      treasures.remove(index);
      randomNumber = RandomImpl.getIntInRange(1, 100);
      if (randomNumber <= 40) {
        index = RandomImpl.getIntInRange(0, treasures.size() - 1);
        treasureMap.put(treasures.get(index), RandomImpl.getIntInRange(1, 10));
        treasures.remove(index);
      }
    }
    for (Treasure treasure : treasures) {
      int number = RandomImpl.getIntInRange(0, 5);
      if (number == 0) {
        treasureMap.put(treasure, RandomImpl.getIntInRange(1, 10));
      }
    }
  }

  @Override
  public void removeTreasure(Treasure treasure) {
    if (treasure == null) {
      throw new IllegalArgumentException("Invalid treasure.");
    }
    treasureMap.remove(treasure);
  }

  @Override
  public Direction getDirection(Point otherPoint) {
    if (otherPoint == null) {
      throw new IllegalArgumentException("Invalid Point");
    }
    int rowChange = point.getRow() - otherPoint.getRow();
    int columnChange = point.getColumn() - otherPoint.getColumn();
    if (columnChange == 0) {
      if (rowChange == 1) {
        return Direction.NORTH;
      } else if (rowChange == -1) {
        return Direction.SOUTH;
      } else if (point.getRow() == 0) {
        return Direction.NORTH;
      } else if (otherPoint.getRow() == 0) {
        return Direction.SOUTH;
      }
    } else if (rowChange == 0) {
      if (columnChange == 1) {
        return Direction.WEST;
      } else if (columnChange == -1) {
        return Direction.EAST;
      } else if (point.getColumn() == 0) {
        return Direction.WEST;
      } else if (otherPoint.getColumn() == 0) {
        return Direction.EAST;
      }
    }
    throw new IllegalArgumentException("The Locations cannot be connected");
  }
}
