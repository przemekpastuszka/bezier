/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.components;

import static pl.rtshadow.bezier.bridge.events.MouseAction.MOUSE_DRAGGED;
import static pl.rtshadow.bezier.bridge.events.MouseAction.MOUSE_PRESSED;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import pl.rtshadow.bezier.bridge.components.ExternalMouseDrivenComponent;
import pl.rtshadow.bezier.bridge.events.MouseActionData;
import pl.rtshadow.bezier.bridge.events.MouseActionListener;
import pl.rtshadow.bezier.components.actions.ComponentAction;
import pl.rtshadow.bezier.components.listeners.ComponentActionListener;

public class MouseInteractiveComponent implements InteractiveComponent {
  private final ExternalMouseDrivenComponent externalComponent;
  private int draggedAtX, draggedAtY;

  private final Multimap<ComponentAction, ComponentActionListener> listeners = HashMultimap.create();

  public MouseInteractiveComponent(ExternalMouseDrivenComponent externalMouseDrivenComponent) {
    this.externalComponent = externalMouseDrivenComponent;

    registerMouseActionListeners();
  }

  private void registerMouseActionListeners() {
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
        externalComponent.setCoordinates(new Coordinates(
            action.getMousePosition().getX() - draggedAtX + externalComponent.getCoordinates().getX(),
            action.getMousePosition().getY() - draggedAtY + externalComponent.getCoordinates().getY()
            ));

        notifyListeners(ComponentAction.MOVED);
      }
    });

    externalComponent.addMouseActionListener(MOUSE_PRESSED, new MouseActionListener() {
      @Override
      public void onMouseAction(MouseActionData action) {
        if (action.getButtonPressed() == MouseActionData.ButtonPressed.RIGHT) {
          notifyListeners(ComponentAction.REMOVED);
          externalComponent.remove();
        }
      }
    });
  }

  private void notifyListeners(ComponentAction componentAction) {
    for (ComponentActionListener listener : listeners.get(componentAction)) {
      listener.onComponentAction(componentAction);
    }
  }

  @Override
  public void addListener(ComponentAction componentAction, ComponentActionListener componentActionListener) {
    listeners.put(componentAction, componentActionListener);
  }

  @Override
  public Coordinates getCoordinates() {
    return externalComponent.getCoordinates();
  }
}
