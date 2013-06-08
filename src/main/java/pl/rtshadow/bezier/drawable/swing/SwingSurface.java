/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.drawable.swing;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import pl.rtshadow.bezier.components.Coordinates;
import pl.rtshadow.bezier.drawable.Surface;

public class SwingSurface extends JPanel implements Surface {
  private final Collection<Pair<Coordinates, Color>> points = new ArrayList<>();

  public SwingSurface(int sizeX, int sizeY) {
    setSize(sizeX, sizeY);
  }

  @Override
  public void drawPoints(Collection<Coordinates> coordinates, final Color color) {
    points.addAll(Collections2.transform(coordinates, new Function<Coordinates, Pair<Coordinates, Color>>() {
      @Override
      public Pair<Coordinates, Color> apply(Coordinates input) {
        return Pair.of(input, color);
      }
    }));

    repaint();
  }

  @Override
  public void clear() {
    points.clear();

    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    for(Pair<Coordinates, Color> point : points) {
      g.setColor(point.getRight());
      g.drawRect(point.getLeft().getXAsInt(), point.getLeft().getYAsInt(), 1, 1);
    }
  }
}
