/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.components.factory;

import static pl.rtshadow.bezier.bridge.events.MouseAction.MOUSE_CLICKED;

import java.util.ArrayList;
import java.util.Collection;

import pl.rtshadow.bezier.bridge.components.ExternalMouseDrivenComponent;
import pl.rtshadow.bezier.bridge.events.MouseActionData;
import pl.rtshadow.bezier.bridge.events.MouseActionListener;
import pl.rtshadow.bezier.util.Coordinate;
import pl.rtshadow.bezier.components.InteractiveComponent;

public class OnClickComponentFactory implements ActionBasedComponentFactory {
  private final Collection<ComponentCreationListener> listeners = new ArrayList<>();
  private final ComponentFactory factory;

  private boolean isActive = true;

  public OnClickComponentFactory(ExternalMouseDrivenComponent component, final ComponentFactory factory) {
    component.addMouseActionListener(MOUSE_CLICKED, new MouseActionListener() {

      @Override
      public void onMouseAction(MouseActionData action) {
           if(isActive && action.getButtonPressed() == MouseActionData.ButtonPressed.LEFT) {
             InteractiveComponent newComponent = factory.createFromPosition(action.getMousePosition());
             notifyAllListeners(newComponent);
           }
      }
    });
    this.factory = factory;
  }

  private void notifyAllListeners(InteractiveComponent newComponent) {
    for(ComponentCreationListener listener : listeners) {
      listener.onCreation(newComponent);
    }
  }

  @Override
  public void addComponentCreationListener(ComponentCreationListener listener) {
    listeners.add(listener);
  }

  @Override
  public void deactivate() {
    isActive = false;
  }

  @Override
  public InteractiveComponent createFromPosition(Coordinate coordinate) {
    return factory.createFromPosition(coordinate);
  }
}
