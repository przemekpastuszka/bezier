/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.bridge.components;

import pl.rtshadow.bezier.bridge.events.MouseAction;
import pl.rtshadow.bezier.bridge.events.MouseActionListener;
import pl.rtshadow.bezier.components.Coordinates;

public interface ExternalMouseDrivenComponent {
  void addMouseActionListener(MouseAction action, MouseActionListener mouseActionListener);

  Coordinates getCoordinates();

  void setCoordinates(Coordinates coordinates);

  void remove();
}
