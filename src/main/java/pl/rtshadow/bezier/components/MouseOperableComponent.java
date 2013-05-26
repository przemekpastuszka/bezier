/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.components;

import pl.rtshadow.bezier.events.MouseActionListener;

public interface MouseOperableComponent extends PositionableComponent {
  void addMousePressedListener(MouseActionListener mouseActionListener);
  void addMouseDraggedListener(MouseActionListener mouseActionListener);
}
