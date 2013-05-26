/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.bridge.components;

import pl.rtshadow.bezier.components.Coordinates;
import pl.rtshadow.bezier.bridge.events.MouseActionListener;

public interface ExternalMouseDrivenComponent {
  void addMousePressedListener(MouseActionListener mouseActionListener);

  void addMouseDraggedListener(MouseActionListener mouseActionListener);

  Coordinates getCoordinates();

  void setCoordinates(Coordinates coordinates);

  void remove();
}
