/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.components.factory;

import pl.rtshadow.bezier.components.InteractiveComponent;

public interface ActionBasedComponentFactory {
  void addComponentCreationListener(ComponentCreationListener listener);

  public static interface ComponentCreationListener {
    void onCreation(InteractiveComponent component);
  }
}
