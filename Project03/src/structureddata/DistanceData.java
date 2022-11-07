package structureddata;

import java.util.Objects;

/**
 * Structured Data to store the distance between 2 points.
 */
public class DistanceData {
  Point startPoint;
  Point endPoint;
  private final int distance;

  /**
   * Constructor for Distance data class.
   *
   * @param startPoint starting point
   * @param endPoint   ending point
   * @param distance   distance between the two point/
   */
  public DistanceData(Point startPoint, Point endPoint, int distance) {
    if (startPoint == null || endPoint == null) {
      throw new IllegalArgumentException("Invalid arguments");
    }
    this.startPoint = startPoint;
    this.endPoint = endPoint;
    this.distance = distance;
  }

  /**
   * Gives the startpoint.
   *
   * @return the startpoint.
   */
  public Point getStartPoint() {
    return startPoint;
  }

  /**
   * Gives the endpoint.
   *
   * @return the endpoint.
   */
  public Point getEndPoint() {
    return endPoint;
  }

  /**
   * Gives the distance between the two points.
   *
   * @return the distance between the two points.
   */
  public int getDistance() {
    return distance;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DistanceData)) {
      return false;
    }
    DistanceData that = (DistanceData) o;
    return distance == that.distance && Objects.equals(startPoint, that.startPoint)
            && Objects.equals(endPoint, that.endPoint);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startPoint, endPoint, distance);
  }
}
