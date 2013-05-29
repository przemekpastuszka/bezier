/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.components;

public interface ComponentFactory {
  void addComponentCreationListener(ComponentCreationListener listener);

  public static interface ComponentCreationListener {
    void onCreation(InteractiveComponent component);
  }
}
