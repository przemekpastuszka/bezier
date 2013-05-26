/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.components;

public interface PositionableComponent {
  Coordinates getCoordinates();

  void setCoordinates(Coordinates coordinates);
}
