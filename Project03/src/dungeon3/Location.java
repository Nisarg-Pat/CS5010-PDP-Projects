package dungeon3;

import structureddata3.Point;

import java.util.Map;

/**
 * Represents a location in the dungeon.
 * Each location can be connected to 4 other locations.
 * One in each direction.
 * Visibility: Package - private
 */
interface Location {
  Point getPoint();

  Map<Direction, Location> getConnections();

  Map<Treasure, Integer> getTreasureMap();

  boolean isCave();

  boolean hasEdge(Location location);

  Location addPath(Location other);

  void updatePath(Location other);

  void setTreasure();

  void removeTreasure(Treasure treasure);

  Direction getDirection(Point otherPoint);
}
