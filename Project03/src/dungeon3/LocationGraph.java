package dungeon3;

import structureddata3.Point;

import java.util.List;

/**
 * Representation of a Graph of locations in the dungeon.
 * Each location in the graph can have 4 connections.
 * Visibility: Package - private
 */
interface LocationGraph {
  int getRows();

  int getColumns();

  Location getLocation(Point point);

  boolean hasEdge(Point first, Point second);

  List<Location> getStartEndPoints();

  int getDistance(Point first, Point second);

  List<Direction> getFullTraversalPath(Point start, Point end);

  List<Direction> getShortestPath(Point start, Point end);
}
