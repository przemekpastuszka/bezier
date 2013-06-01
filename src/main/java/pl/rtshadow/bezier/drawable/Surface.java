package pl.rtshadow.bezier.drawable;

import java.util.Collection;

import pl.rtshadow.bezier.components.Coordinates;

public interface Surface {
  void drawPoints(Collection<Coordinates> coordinates);

  void clear();
}
