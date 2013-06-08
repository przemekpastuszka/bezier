/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.components;

import pl.rtshadow.bezier.components.actions.ComponentAction;
import pl.rtshadow.bezier.components.listeners.ComponentActionListener;
import pl.rtshadow.bezier.util.Coordinate;

public interface InteractiveComponent {
  void addListener(ComponentAction componentAction, ComponentActionListener componentActionListener);

  Coordinate getCoordinates();

  void deactivate();
}
