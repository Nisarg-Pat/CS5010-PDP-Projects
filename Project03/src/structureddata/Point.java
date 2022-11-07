package structureddata;

import java.util.Objects;

/**
 * Data structure to store a point. Contains 2 integers: row and column.
 */
public class Point {
  private final int row;
  private final int column;

  /**
   * Constructor for Point class.
   *
   * @param row    row of the point
   * @param column column of the point
   * @throws IllegalArgumentException if rows or columns are <0.
   */
  public Point(int row, int column) {
    if (row < 0 || column < 0) {
      throw new IllegalArgumentException("Invalid arguments.");
    }
    this.row = row;
    this.column = column;
  }

  /**
   * Gives the row of the point.
   *
   * @return the row of the point.
   */
  public int getRow() {
    return row;
  }

  /**
   * Gives the column of the point.
   *
   * @return the column of the point.
   */
  public int getColumn() {
    return column;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Point)) {
      return false;
    }
    Point point = (Point) o;
    return row == point.row && column == point.column;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, column);
  }

  @Override
  public String toString() {
    return String.format("(%d, %d)", row, column);
  }
}
