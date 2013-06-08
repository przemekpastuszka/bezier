/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.components;

import static pl.rtshadow.bezier.components.actions.ComponentAction.ADDED;
import static pl.rtshadow.bezier.components.actions.ComponentAction.MOVED;
import static pl.rtshadow.bezier.components.actions.ComponentAction.REMOVED;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Multimap;

import pl.rtshadow.bezier.components.actions.ComponentAction;
import pl.rtshadow.bezier.components.factory.ActionBasedComponentFactory;
import pl.rtshadow.bezier.components.listeners.ComponentActionListener;
import pl.rtshadow.bezier.util.BoundedIterable;
import pl.rtshadow.bezier.util.Coordinate;

public class InteractiveComponentsList implements BoundedIterable<Coordinate> {
  private final List<InteractiveComponent> components = new ArrayList<>();
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

  public void addListenerOnEachComponent(ComponentAction componentAction, ComponentActionListener componentActionListener) {
    listeners.put(componentAction, componentActionListener);
  }

  public void add(Coordinate coordinate) {
    add(factory.createFromPosition(coordinate));
  }

  private boolean add(InteractiveComponent component) {
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

  @Override
  public int getSize() {
    return components.size();
  }

  @Override
  public Iterator<Coordinate> iterator() {
    return Iterators.transform(components.iterator(), new Function<InteractiveComponent, Coordinate>() {
      @Override
      public Coordinate apply(InteractiveComponent input) {
        return input.getCoordinates();
      }
    });
  }
}
