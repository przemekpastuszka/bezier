/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.util.List;

import com.google.common.base.Function;

import pl.rtshadow.bezier.components.Coordinates;
import pl.rtshadow.bezier.components.InteractiveComponent;
import pl.rtshadow.bezier.components.InteractiveComponentsList;
import pl.rtshadow.bezier.drawable.Surface;

public class BezierCurve {
  public final InteractiveComponentsList controlPoints;
  public final BezierEvaluationAlgorithm evaluationAlgorithm;

  public BezierCurve(InteractiveComponentsList controlPoints, BezierEvaluationAlgorithm evaluationAlgorithm) {
    this.controlPoints = controlPoints;
    this.evaluationAlgorithm = evaluationAlgorithm;
  }

  public void draw(Surface surface) {
    List<Coordinates> controlPoints = retrieveControlPoints();

    if (controlPoints.size() >= 2) {
      List<Coordinates> pointsToDraw = newArrayList();
      float step = 0.01f;
      for (float t = 0; t <= 1; t += step) {
        pointsToDraw.add(evaluationAlgorithm.evaluatePoint(controlPoints, t));
      }
      surface.drawPoints(pointsToDraw);
    }
  }

  private List<Coordinates> retrieveControlPoints() {
    return transform(this.controlPoints, new Function<InteractiveComponent, Coordinates>() {
      @Override
      public Coordinates apply(InteractiveComponent input) {
        return input.getCoordinates();
      }
    });
  }
}
