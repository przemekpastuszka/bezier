/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.components;

import pl.rtshadow.bezier.listeners.RemovalListener;

public interface RemovableComponent {
  void addRemovalListener(RemovalListener moveListener);
}
