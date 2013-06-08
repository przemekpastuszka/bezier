/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.components;

import static pl.rtshadow.bezier.bridge.events.MouseAction.MOUSE_DRAGGED;
import static pl.rtshadow.bezier.bridge.events.MouseAction.MOUSE_PRESSED;
import static pl.rtshadow.bezier.components.actions.ComponentAction.MOVED;

import pl.rtshadow.bezier.bridge.components.ExternalMouseDrivenComponent;
import pl.rtshadow.bezier.bridge.events.MouseActionData;
import pl.rtshadow.bezier.bridge.events.MouseActionListener;
import pl.rtshadow.bezier.util.Coordinate;

public class MouseInteractiveMovableComponent extends MouseInteractiveComponent {
  private double draggedAtX, draggedAtY;

  public MouseInteractiveMovableComponent(ExternalMouseDrivenComponent externalMouseDrivenComponent) {
    super(externalMouseDrivenComponent);

    registerMouseMovementListeners();
  }

  private void registerMouseMovementListeners() {
    externalComponent.addMouseActionListener(MOUSE_PRESSED, new MouseActionListener() {
      @Override
      public void onMouseAction(MouseActionData action) {
        draggedAtX = action.getMousePosition().getX();
        draggedAtY = action.getMousePosition().getY();
      }
    });

    externalComponent.addMouseActionListener(MOUSE_DRAGGED, new MouseActionListener() {
      @Override
      public void onMouseAction(MouseActionData action) {
        externalComponent.setCoordinates(new Coordinate(
            action.getMousePosition().getX() - draggedAtX + externalComponent.getCoordinates().getX(),
            action.getMousePosition().getY() - draggedAtY + externalComponent.getCoordinates().getY()
            ));

        notifyListeners(MOVED);
      }
    });
  }
}
