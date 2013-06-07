package pl.rtshadow.bezier.drawable;

import java.awt.*;
import java.util.Collection;

import pl.rtshadow.bezier.components.Coordinates;

public interface Surface {
  void drawPoints(Collection<Coordinates> coordinates, Color color);

  void clear();
}
