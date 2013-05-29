/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.components;

import static java.util.Collections.unmodifiableList;
import static pl.rtshadow.bezier.components.actions.ComponentAction.REMOVED;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ForwardingList;

import pl.rtshadow.bezier.components.actions.ComponentAction;
import pl.rtshadow.bezier.components.listeners.ComponentActionListener;

public class InteractiveComponentsList extends ForwardingList<InteractiveComponent> {
  private final List<InteractiveComponent> components = new ArrayList<>();
  private final List<InteractiveComponent> unmodifiableViewOfComponents = unmodifiableList(components);

  public InteractiveComponentsList(ComponentFactory factory) {
     factory.addComponentCreationListener(new ComponentFactory.ComponentCreationListener() {
       @Override
       public void onCreation(final InteractiveComponent component) {
         components.add(component);
         removeFromListOnRemoval(component);
       }
     });
  }

  private void removeFromListOnRemoval(final InteractiveComponent component) {
    component.addListener(REMOVED, new ComponentActionListener() {
      @Override
      public void onComponentAction(ComponentAction action) {
        components.remove(component);
      }
    });
  }

  @Override
  protected List<InteractiveComponent> delegate() {
    return unmodifiableViewOfComponents;
  }
}
