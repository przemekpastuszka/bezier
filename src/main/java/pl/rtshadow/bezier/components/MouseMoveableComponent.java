package pl.rtshadow.bezier.components;

import java.util.Collection;
import java.util.LinkedList;

import pl.rtshadow.bezier.events.MouseAction;
import pl.rtshadow.bezier.events.MouseActionListener;
import pl.rtshadow.bezier.listeners.MoveListener;

public class MouseMoveableComponent implements MoveableComponent {
  private final MouseOperableComponent component;
  private int draggedAtX, draggedAtY;
  private final Collection<MoveListener> moveListeners = new LinkedList<>();

  public MouseMoveableComponent(MouseOperableComponent mouseOperableComponent){
    this.component = mouseOperableComponent;

    registerListeners();
  }

  private void registerListeners() {
    component.addMousePressedListener(new MouseActionListener() {
      @Override
      public void action(MouseAction action) {
        draggedAtX = action.getMousePosition().getX();
        draggedAtY = action.getMousePosition().getY();
      }
    });

    component.addMouseDraggedListener(new MouseActionListener() {
      @Override
      public void action(MouseAction action) {
        component.setCoordinates(new Coordinates(
            action.getMousePosition().getX() - draggedAtX + component.getCoordinates().getX(),
            action.getMousePosition().getY() - draggedAtY + component.getCoordinates().getY()
        ));

        notifyMoveListeners();
      }
    });
  }

  private void notifyMoveListeners() {
    Coordinates coordinates = component.getCoordinates();
    for(MoveListener moveListener : moveListeners) {
       moveListener.onMove(coordinates);
    }
  }

  @Override
  public void addMoveListener(MoveListener moveListener) {
    moveListeners.add(moveListener);
  }
}
