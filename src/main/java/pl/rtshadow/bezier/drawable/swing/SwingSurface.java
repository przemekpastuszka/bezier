/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.drawable.swing;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import pl.rtshadow.bezier.components.Coordinates;
import pl.rtshadow.bezier.drawable.Surface;

public class SwingSurface extends JPanel implements Surface {
  private final Collection<Pixel> pixels = new ArrayList<>();

  public SwingSurface(int sizeX, int sizeY) {
    setSize(sizeX, sizeY);
  }

  @Override
  public void drawPoints(Collection<Coordinates> coordinates, final Color color) {
    pixels.addAll(Collections2.transform(coordinates, new Function<Coordinates, Pixel>() {
      @Override
      public Pixel apply(Coordinates input) {
        return new Pixel(input, color);
      }
    }));

    repaint();
  }

  @Override
  public void clear() {
    pixels.clear();

    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    for(Pixel pixel : pixels) {
      g.setColor(pixel.getColor());
      g.drawRect(pixel.getPosition().getXAsInt(), pixel.getPosition().getYAsInt(), 1, 1);
    }
  }
}
