package pl.rtshadow.bezier.listeners;

import pl.rtshadow.bezier.components.Coordinates;

public interface MoveListener {
  void onMove(Coordinates newPosition);
}