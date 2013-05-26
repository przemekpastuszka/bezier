/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.components;

import pl.rtshadow.bezier.listeners.MoveListener;

public interface MoveableComponent {
  void addMoveListener(MoveListener moveListener);
}
