/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.components;

import static java.util.Collections.unmodifiableList;
import static pl.rtshadow.bezier.components.actions.ComponentAction.ADDED;
import static pl.rtshadow.bezier.components.actions.ComponentAction.MOVED;
import static pl.rtshadow.bezier.components.actions.ComponentAction.REMOVED;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ForwardingList;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import pl.rtshadow.bezier.components.actions.ComponentAction;
import pl.rtshadow.bezier.components.factory.ActionBasedComponentFactory;
import pl.rtshadow.bezier.components.listeners.ComponentActionListener;

public class InteractiveComponentsList extends ForwardingList<InteractiveComponent> {
  private final List<InteractiveComponent> components = new ArrayList<>();
  private final List<InteractiveComponent> unmodifiableViewOfComponents = unmodifiableList(components);
  private final Multimap<ComponentAction, ComponentActionListener> listeners = HashMultimap.create();
  private final ActionBasedComponentFactory factory;

  public InteractiveComponentsList(ActionBasedComponentFactory factory) {
     factory.addComponentCreationListener(new ActionBasedComponentFactory.ComponentCreationListener() {
       @Override
       public void onCreation(final InteractiveComponent component) {
          add(component);
       }
     });
    this.factory = factory;
  }

  private void registerMoveListener(InteractiveComponent component) {
    component.addListener(MOVED, new ComponentActionListener() {
      @Override
      public void onComponentAction(ComponentAction action) {
        notifyListeners(MOVED);
      }
    });
  }

  private void removeFromListOnRemoval(final InteractiveComponent component) {
    component.addListener(REMOVED, new ComponentActionListener() {
      @Override
      public void onComponentAction(ComponentAction action) {
        components.remove(component);

        notifyListeners(REMOVED);
      }
    });
  }

  private void notifyListeners(ComponentAction action) {
    for(ComponentActionListener listener : listeners.get(action)) {
      listener.onComponentAction(action);
    }
  }

  @Override
  protected List<InteractiveComponent> delegate() {
    return unmodifiableViewOfComponents;
  }

  public void addListenerOnEachComponent(ComponentAction componentAction, ComponentActionListener componentActionListener) {
    listeners.put(componentAction, componentActionListener);
  }

  public void add(Coordinates coordinates) {
    add(factory.createFromPosition(coordinates));
  }

  @Override
  public boolean add(InteractiveComponent component) {
    components.add(component);
    removeFromListOnRemoval(component);
    registerMoveListener(component);

    notifyListeners(ADDED);

    return true;
  }

  public void deactivate() {
     factory.deactivate();
     for(InteractiveComponent interactiveComponent : components) {
         interactiveComponent.deactivate();
     }
  }
}
