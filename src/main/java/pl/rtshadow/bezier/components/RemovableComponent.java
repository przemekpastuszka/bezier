package pl.rtshadow.bezier.components;

import pl.rtshadow.bezier.listeners.RemovalListener;

public interface RemovableComponent {
  void addRemovalListener(RemovalListener moveListener);
}
