package dungeon3;

import java.util.Map;

/**
 * Tunnel representation of the location.
 * A tunnel will have only 2 connections.
 * A Tunnel cannot contain treasures.
 * Visibility: Package - private
 */
class Tunnel extends Cave {

  protected Tunnel(int row, int column, Map<Direction, Location> connectedMap) {
    super(row, column, connectedMap);
  }

  @Override
  public Location addPath(Location other) {
    if (other == null) {
      throw new IllegalArgumentException("Invalid argument.");
    }
    Direction direction = getDirection(other.getPoint());
    connectedMap.put(direction, other);
    Cave newLocation = new Cave(point.getRow(), point.getColumn(), connectedMap);
    for (Location location : connectedMap.values()) {
      location.updatePath(newLocation);
    }
    return newLocation;
  }

  @Override
  public void setTreasure() {
    throw new IllegalStateException("Cannot set/remove treasure in a tunnel");
  }

  @Override
  public void removeTreasure(Treasure treasure) {
    throw new IllegalStateException("Cannot set/remove treasure in a tunnel");
  }

  @Override
  public boolean isCave() {
    return false;
  }
}
