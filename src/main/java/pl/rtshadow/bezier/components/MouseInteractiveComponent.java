/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.components;

import java.util.Collection;
import java.util.LinkedList;

import pl.rtshadow.bezier.bridge.components.ExternalMouseDrivenComponent;
import pl.rtshadow.bezier.bridge.events.MouseAction;
import pl.rtshadow.bezier.bridge.events.MouseActionListener;
import pl.rtshadow.bezier.components.listeners.MoveListener;
import pl.rtshadow.bezier.components.listeners.RemovalListener;

public class MouseInteractiveComponent implements InteractiveComponent {
  private final ExternalMouseDrivenComponent externalComponent;
  private int draggedAtX, draggedAtY;

  private final Collection<MoveListener> moveListeners = new LinkedList<>();
  private final Collection<RemovalListener> removalListeners = new LinkedList<>();

  public MouseInteractiveComponent(ExternalMouseDrivenComponent externalMouseDrivenComponent){
    this.externalComponent = externalMouseDrivenComponent;

    registerMouseActionListeners();
    registerRemovalListener();
  }

  private void registerRemovalListener() {
    addRemovalListener(new RemovalListener() {
      @Override
      public void onRemoval() {
        externalComponent.remove();
      }
    });
  }

  private void registerMouseActionListeners() {
    externalComponent.addMousePressedListener(new MouseActionListener() {
      @Override
      public void onMouseAction(MouseAction action) {
        draggedAtX = action.getMousePosition().getX();
        draggedAtY = action.getMousePosition().getY();
      }
    });

    externalComponent.addMouseDraggedListener(new MouseActionListener() {
      @Override
      public void onMouseAction(MouseAction action) {
        externalComponent.setCoordinates(new Coordinates(
            action.getMousePosition().getX() - draggedAtX + externalComponent.getCoordinates().getX(),
            action.getMousePosition().getY() - draggedAtY + externalComponent.getCoordinates().getY()
        ));

        notifyMoveListeners();
      }
    });

    externalComponent.addMousePressedListener(new MouseActionListener() {
      @Override
      public void onMouseAction(MouseAction action) {
        if (action.getButtonPressed() == MouseAction.ButtonPressed.RIGHT) {
          notifyRemovalListeners();
        }
      }
    });
  }

  private void notifyRemovalListeners() {
    for(RemovalListener moveListener : removalListeners) {
      moveListener.onRemoval();
    }
  }

  private void notifyMoveListeners() {
    Coordinates coordinates = externalComponent.getCoordinates();
    for(MoveListener moveListener : moveListeners) {
       moveListener.onMove(coordinates);
    }
  }

  @Override
  public void addMoveListener(MoveListener moveListener) {
    moveListeners.add(moveListener);
  }

  @Override
  public void addRemovalListener(RemovalListener removalListener) {
    removalListeners.add(removalListener);
  }

  @Override
  public Coordinates getCoordinates() {
    return externalComponent.getCoordinates();
  }
}
