/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.components;

import static java.lang.Math.round;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

import java.awt.*;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Coordinates {
  private final double x;
  private final double y;

  public Coordinates(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Coordinates(Point point) {
    this(point.x, point.y);
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public int getXAsInt() {
    return (int) round(x);
  }

  public int getYAsInt() {
    return (int) round(y);
  }

  public static Coordinates multiply(double scalar, Coordinates coordinates) {
    return new Coordinates(scalar * coordinates.getX(), scalar * coordinates.getY());
  }

  public static Coordinates add(Coordinates a, Coordinates b) {
    return new Coordinates(a.getX() + b.getX(), a.getY() + b.getY());
  }

  @Override
  public boolean equals(Object o) {
    return reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return reflectionHashCode(this);
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this);
  }
}
