/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.bridge.components;

import pl.rtshadow.bezier.bridge.events.MouseAction;
import pl.rtshadow.bezier.bridge.events.MouseActionListener;
import pl.rtshadow.bezier.util.Coordinate;

public interface ExternalMouseDrivenComponent {
  void addMouseActionListener(MouseAction action, MouseActionListener mouseActionListener);

  Coordinate getCoordinates();

  void setCoordinates(Coordinate coordinate);

  void remove();

  void deactivate();

  void activate();
}
