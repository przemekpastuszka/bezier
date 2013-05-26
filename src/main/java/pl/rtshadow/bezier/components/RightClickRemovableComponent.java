/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.components;

import java.util.Collection;
import java.util.LinkedList;

import pl.rtshadow.bezier.events.MouseAction;
import pl.rtshadow.bezier.events.MouseActionListener;
import pl.rtshadow.bezier.listeners.RemovalListener;

public class RightClickRemovableComponent implements RemovableComponent {
  private final MouseOperableComponent component;
  private final Collection<RemovalListener> removalListeners = new LinkedList<>();

  public RightClickRemovableComponent(MouseOperableComponent mouseOperableComponent){
    this.component = mouseOperableComponent;

    registerListeners();
  }

  private void registerListeners() {
    component.addMousePressedListener(new MouseActionListener() {
      @Override
      public void action(MouseAction action) {
        if(action.getButtonPressed() == MouseAction.ButtonPressed.RIGHT) {
          notifyRemovalListeners();
        }
      }
    });
  }

  private void notifyRemovalListeners() {
    for(RemovalListener moveListener : removalListeners) {
       moveListener.onRemoval(this);
    }
  }

  @Override
  public void addRemovalListener(RemovalListener removalListener) {
    removalListeners.add(removalListener);
  }
}
