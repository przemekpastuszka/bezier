/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.components;

import pl.rtshadow.bezier.components.listeners.MoveListener;
import pl.rtshadow.bezier.components.listeners.RemovalListener;

public interface InteractiveComponent {
  void addMoveListener(MoveListener moveListener);
  void addRemovalListener(RemovalListener removalListener);
  Coordinates getCoordinates();
}
