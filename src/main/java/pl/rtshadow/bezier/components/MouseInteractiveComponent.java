package pl.rtshadow.bezier.components;

import static pl.rtshadow.bezier.bridge.events.MouseAction.MOUSE_PRESSED;
import static pl.rtshadow.bezier.components.actions.ComponentAction.REMOVED;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import pl.rtshadow.bezier.bridge.components.ExternalMouseDrivenComponent;
import pl.rtshadow.bezier.bridge.events.MouseActionData;
import pl.rtshadow.bezier.bridge.events.MouseActionListener;
import pl.rtshadow.bezier.components.actions.ComponentAction;
import pl.rtshadow.bezier.components.listeners.ComponentActionListener;

public class MouseInteractiveComponent implements InteractiveComponent {
  protected final ExternalMouseDrivenComponent externalComponent;
  protected final Multimap<ComponentAction, ComponentActionListener> listeners = HashMultimap.create();

  public MouseInteractiveComponent(ExternalMouseDrivenComponent externalMouseDrivenComponent) {
    this.externalComponent = externalMouseDrivenComponent;

    registerMouseButtonListeners();
  }

  protected void registerMouseButtonListeners() {
    externalComponent.addMouseActionListener(MOUSE_PRESSED, new MouseActionListener() {
      @Override
      public void onMouseAction(MouseActionData action) {
        if (action.getButtonPressed() == MouseActionData.ButtonPressed.RIGHT) {
          notifyListeners(REMOVED);
          externalComponent.remove();
        }
      }
    });
  }

  protected void notifyListeners(ComponentAction componentAction) {
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

  @Override
  public void deactivate() {
    externalComponent.deactivate();
  }
}
