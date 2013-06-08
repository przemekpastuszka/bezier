/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.util;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public final class Coordinates {
  private Coordinates() {}

  public static Coordinate multiply(double scalar, Coordinate coordinate) {
    return new Coordinate(scalar * coordinate.getX(), scalar * coordinate.getY());
  }

  public static Coordinate add(Coordinate a, Coordinate b) {
    return new Coordinate(a.getX() + b.getX(), a.getY() + b.getY());
  }

  public static boolean areNeighbours(Coordinate a, Coordinate b) {
    return abs(a.getX() - b.getX()) <= 1 && abs(a.getY() - b.getY()) <= 1;
  }

  public static double getDistance(Coordinate a, Coordinate b) {
    return sqrt(pow(a.getX() - b.getX(), 2) + pow(a.getY() - b.getY(), 2));
  }
}
