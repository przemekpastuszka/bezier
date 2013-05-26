/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.components.listeners;

import pl.rtshadow.bezier.components.Coordinates;

public interface MoveListener {
  void onMove(Coordinates newPosition);
}
