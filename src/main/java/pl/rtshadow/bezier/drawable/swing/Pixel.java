/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.drawable.swing;

import java.awt.*;

import org.apache.commons.lang3.tuple.MutablePair;

import pl.rtshadow.bezier.util.Coordinate;

public class Pixel extends MutablePair<Coordinate, Color> {
  public Pixel(Coordinate left, Color right) {
    super(left, right);
  }

  public Coordinate getPosition() {
    return getLeft();
  }

  public Color getColor() {
    return getRight();
  }
}
