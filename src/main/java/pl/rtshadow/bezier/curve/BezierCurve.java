/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve;

import static com.google.common.collect.Lists.transform;

import java.util.List;

import com.google.common.base.Function;

import pl.rtshadow.bezier.components.Coordinates;
import pl.rtshadow.bezier.components.InteractiveComponent;
import pl.rtshadow.bezier.components.InteractiveComponentsList;
import pl.rtshadow.bezier.drawable.Surface;

public class BezierCurve {
  public final InteractiveComponentsList controlPoints;

  public BezierCurve(InteractiveComponentsList controlPoints) {
    this.controlPoints = controlPoints;
  }

  public void draw(Surface surface) {
    List<Coordinates> pointsToDraw = transform(controlPoints, new Function<InteractiveComponent, Coordinates>() {
      @Override
      public Coordinates apply(InteractiveComponent input) {
        Coordinates position = input.getCoordinates();
        return new Coordinates(position.getX() + 50, position.getY() + 50);
      }
    });

    surface.drawPoints(pointsToDraw);
  }
}
