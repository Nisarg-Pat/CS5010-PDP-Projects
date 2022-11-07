package dungeon3;

import random.RandomImpl;
import structureddata3.DistanceData;
import structureddata3.Edge;
import structureddata3.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * The representation of LocationGraph specific to the requirements of the dungeon.
 * Random edges are selected from the possible edges such that all locations are connected
 * and the graph has the mentioned degree of interconnectivity.
 * Random caves are chosen to contain treasures by mentioned percentage of caves.
 * Visibility: Package - private
 */
class LocationGraphImpl implements LocationGraph {

  private final Location[][] location;
  private final int rows;
  private final int columns;
  private final boolean isWrapped;
  private final int degreeOfInterconnectivity;
  private final int percentageCavesWithTreasure;

  protected LocationGraphImpl(int rows, int columns, boolean isWrapped,
                    int degreeOfInterconnectivity, int percentageCavesWithTreasure) {
    if (rows < 6 || columns < 6 || degreeOfInterconnectivity < 0
            || percentageCavesWithTreasure < 0 || percentageCavesWithTreasure > 100) {
      throw new IllegalArgumentException("The arguments are not proper. "
              + "Constraints are:\nrows >= 6, columns >= 6, degree >= 0, "
              + "percentage between 0 and 100");
    }
    this.rows = rows;
    this.columns = columns;
    this.isWrapped = isWrapped;
    this.degreeOfInterconnectivity = degreeOfInterconnectivity;
    this.percentageCavesWithTreasure = percentageCavesWithTreasure;
    location = addLocations();
    List<Edge> allEdges = addEdges();
    allEdges = shuffle(allEdges);
    selectEdges(allEdges);
    addTreasureToCaves();
  }

  private Location[][] addLocations() {
    Location[][] location = new Location[rows][columns];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        location[i][j] = new Cave(i, j);
      }
    }
    return location;
  }

  private List<Edge> addEdges() {
    List<Edge> edges = new ArrayList<>();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        addEdge(edges, i, j, i, j + 1);
        addEdge(edges, i, j, i + 1, j);
      }
    }
    return edges;
  }

  private void addEdge(List<Edge> edges, int row1, int column1, int row2, int column2) {
    if (isWrapped) {
      edges.add(new Edge(row1, column1, (row2 + rows) % rows, (column2 + columns) % columns));
    } else if (row2 >= 0 && row2 < rows && column2 >= 0 && column2 < columns) {
      edges.add(new Edge(row1, column1, row2, column2));
    }
  }

  private List<Edge> shuffle(List<Edge> allEdges) {
    int totalEdges = allEdges.size();
    List<Edge> shuffledEdges = new ArrayList<>();
    while (!allEdges.isEmpty()) {
      int index = RandomImpl.getIntInRange(0, totalEdges - 1);
      totalEdges--;
      shuffledEdges.add(allEdges.remove(index));
    }
    return shuffledEdges;
  }

  private void selectEdges(List<Edge> allEdges) {
    List<List<Point>> parent = new ArrayList<>();
    for (int i = 0; i < rows; i++) {
      parent.add(new ArrayList<>());
      for (int j = 0; j < columns; j++) {
        parent.get(i).add(new Point(i, j));
      }
    }
    List<Edge> remainingEdges = new ArrayList<>();
    for (Edge edge : allEdges) {
      Point x = find(parent, edge.getFirst());
      Point y = find(parent, edge.getSecond());
      if (x.equals(y)) {
        remainingEdges.add(edge);
      } else {
        connectLocations(edge.getFirst(), edge.getSecond());
        union(parent, x, y);
      }
    }
    int max_degree = Math.min(degreeOfInterconnectivity, remainingEdges.size());
    for (int i = 0; i < max_degree; i++) {
      Edge edge = remainingEdges.get(i);
      connectLocations(edge.getFirst(), edge.getSecond());
    }
  }

  private void union(List<List<Point>> parent, Point x, Point y) {
    Point parent_a = find(parent, x);
    Point parent_b = find(parent, y);
    if (!parent_a.equals(parent_b)) {
      parent.get(parent_a.getRow()).set(parent_a.getColumn(), parent_b);
    }
  }

  private Point find(List<List<Point>> parent, Point a) {
    if (!parent.get(a.getRow()).get(a.getColumn()).equals(a)) {
      parent.get(a.getRow()).set(a.getColumn(),
              find(parent, parent.get(a.getRow()).get(a.getColumn())));
    }
    return parent.get(a.getRow()).get(a.getColumn());
  }

  private void connectLocations(Point a, Point b) {
    location[a.getRow()][a.getColumn()] =
            location[a.getRow()][a.getColumn()].addPath(location[b.getRow()][b.getColumn()]);
    location[b.getRow()][b.getColumn()] =
            location[b.getRow()][b.getColumn()].addPath(location[a.getRow()][a.getColumn()]);
  }

  @Override
  public int getRows() {
    return rows;
  }

  @Override
  public int getColumns() {
    return columns;
  }

  @Override
  public Location getLocation(Point point) {
    if (point == null) {
      throw new IllegalArgumentException("Invalid Point.");
    }
    return location[point.getRow()][point.getColumn()];
  }

  @Override
  public boolean hasEdge(Point first, Point second) {
    if (first == null || second == null) {
      throw new IllegalArgumentException("Invalid location.");
    }
    return location[first.getRow()][first.getColumn()].hasEdge(
            location[second.getRow()][second.getColumn()]);
  }

  @Override
  public List<Location> getStartEndPoints() {
    List<Location> caves = getListOfCaves();
    Point startPoint = caves.get(RandomImpl.getIntInRange(0, caves.size() - 1)).getPoint();
    List<Location> possibleEndPoints = possibleEndPoints(startPoint);
    if (possibleEndPoints.isEmpty()) {
      throw new IllegalStateException("Cannot find a distance of 5 between two points.");
    }
    Location endPoint = possibleEndPoints.get(
            RandomImpl.getIntInRange(0, possibleEndPoints.size() - 1));
    List<Location> startEndPoints = new ArrayList<>();
    startEndPoints.add(location[startPoint.getRow()][startPoint.getColumn()]);
    startEndPoints.add(location[endPoint.getPoint().getRow()][endPoint.getPoint().getColumn()]);
    return startEndPoints;
  }

  private List<Location> possibleEndPoints(Point startPoint) {
    if (startPoint == null) {
      throw new IllegalArgumentException("Invalid Point");
    }
    List<Location> locationsWithDistanceGreaterThanEqualTo5 = new ArrayList<>();
    boolean[][] visited = new boolean[rows][columns];
    Queue<DistanceData> q = new LinkedList<>();
    q.add(new DistanceData(startPoint, startPoint, 0));
    visited[startPoint.getRow()][startPoint.getColumn()] = true;
    while (!q.isEmpty()) {
      DistanceData distanceData = q.poll();
      Point endPoint = distanceData.getEndPoint();
      if (distanceData.getDistance() >= 5
              && location[endPoint.getRow()][endPoint.getColumn()].isCave()) {
        locationsWithDistanceGreaterThanEqualTo5.add(
                location[endPoint.getRow()][endPoint.getColumn()]);
      }
      visitConnections(startPoint, visited, q, distanceData, endPoint);
    }
    return locationsWithDistanceGreaterThanEqualTo5;
  }


  private void visitConnections(Point startPoint, boolean[][] visited,
                                Queue<DistanceData> q, DistanceData distanceData, Point endPoint) {
    for (Location location :
            location[endPoint.getRow()][endPoint.getColumn()].getConnections().values()) {
      if (!visited[location.getPoint().getRow()][location.getPoint().getColumn()]) {
        visited[location.getPoint().getRow()][location.getPoint().getColumn()] = true;
        q.add(new DistanceData(startPoint, location.getPoint(), distanceData.getDistance() + 1));
      }
    }
  }

  private void addTreasureToCaves() {
    List<Location> caves = getListOfCaves();
    int totalCaves = caves.size();
    int totalCavesWithTreasure =
            (int) Math.ceil((percentageCavesWithTreasure * totalCaves) / 100.0);
    for (int i = 0; i < totalCavesWithTreasure; i++) {
      int index = RandomImpl.getIntInRange(0, totalCaves - 1);
      totalCaves--;
      caves.get(index).setTreasure();
      caves.remove(index);
    }
  }

  private List<Location> getListOfCaves() {
    List<Location> caves = new ArrayList<>();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        if (location[i][j].isCave()) {
          caves.add(location[i][j]);
        }
      }
    }
    return caves;
  }

  @Override
  public int getDistance(Point first, Point second) {
    boolean[][] visited = new boolean[rows][columns];
    Queue<DistanceData> q = new LinkedList<>();
    q.add(new DistanceData(first, first, 0));
    visited[first.getRow()][first.getColumn()] = true;
    while (!q.isEmpty()) {
      DistanceData distanceData = q.poll();
      Point endPoint = distanceData.getEndPoint();
      if (second.equals(endPoint)) {
        return distanceData.getDistance();
      }
      visitConnections(first, visited, q, distanceData, endPoint);
    }
    return -1;
  }

  @Override
  public List<Direction> getFullTraversalPath(Point start, Point end) {
    boolean[][] visited = new boolean[rows][columns];
    List<Direction> list = new ArrayList<>();
    dfsUtil(visited, start, end, list);
    list.addAll(getShortestPath(start, end));
    return list;
  }

  private void dfsUtil(boolean[][] visited, Point current, Point end, List<Direction> list) {
    visited[current.getRow()][current.getColumn()] = true;
    Map<Direction, Location> locationMap =
            location[current.getRow()][current.getColumn()].getConnections();
    for (Direction direction : locationMap.keySet()) {
      Point point = locationMap.get(direction).getPoint();
      if (!visited[point.getRow()][point.getColumn()] && !point.equals(end)) {
        list.add(direction);
        dfsUtil(visited, point, end, list);
        list.add(locationMap.get(direction).getDirection(current));
      }
    }
  }

  @Override
  public List<Direction> getShortestPath(Point first, Point second) {
    boolean[][] visited = new boolean[rows][columns];
    Point[][] parent = new Point[rows][columns];
    List<Direction> directionList = new ArrayList<>();
    Queue<DistanceData> q = new LinkedList<>();
    q.add(new DistanceData(first, first, 0));
    visited[first.getRow()][first.getColumn()] = true;
    parent[first.getRow()][first.getColumn()] = null;
    while (!q.isEmpty()) {
      DistanceData distanceData = q.poll();
      Point point = distanceData.getEndPoint();
      if (second.equals(point)) {
        while (parent[point.getRow()][point.getColumn()] != null) {
          Point parentPoint = parent[point.getRow()][point.getColumn()];
          directionList.add(
                  location[parentPoint.getRow()][parentPoint.getColumn()].getDirection(point));
          point = parentPoint;
        }
        Collections.reverse(directionList);
        return directionList;
      }
      for (Location location :
              location[point.getRow()][point.getColumn()].getConnections().values()) {
        if (!visited[location.getPoint().getRow()][location.getPoint().getColumn()]) {
          visited[location.getPoint().getRow()][location.getPoint().getColumn()] = true;
          parent[location.getPoint().getRow()][location.getPoint().getColumn()] = point;
          q.add(new DistanceData(first, location.getPoint(), distanceData.getDistance() + 1));
        }
      }
    }
    return directionList;
  }
}
