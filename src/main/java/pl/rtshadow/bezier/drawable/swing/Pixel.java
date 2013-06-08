/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.drawable.swing;

import java.awt.*;

import org.apache.commons.lang3.tuple.MutablePair;

import pl.rtshadow.bezier.components.Coordinates;

public class Pixel extends MutablePair<Coordinates, Color> {
  public Pixel(Coordinates left, Color right) {
    super(left, right);
  }

  public Coordinates getPosition() {
    return getLeft();
  }

  public Color getColor() {
    return getRight();
  }
}
