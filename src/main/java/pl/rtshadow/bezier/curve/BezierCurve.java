/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Math.abs;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import pl.rtshadow.bezier.components.Coordinates;
import pl.rtshadow.bezier.components.InteractiveComponent;
import pl.rtshadow.bezier.components.InteractiveComponentsList;
import pl.rtshadow.bezier.curve.evaluation.BezierEvaluationAlgorithm;
import pl.rtshadow.bezier.curve.transformations.BezierTransformation;
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
      surface.drawPoints(computeDrawPoints(controlPoints));
    }
  }

  public List<Coordinates> transformation(BezierTransformation transformation) {
    return transformation.apply(retrieveControlPoints());
  }

  private List<Coordinates> computeDrawPoints(List<Coordinates> controlPoints) {
    List<Coordinates> pointsToDraw = newArrayList();

    double step = 0.01f;

    Coordinates previousPoint = evaluationAlgorithm.evaluatePoint(controlPoints, 0);
    pointsToDraw.add(previousPoint);

    for (double t = 0; t <= 1 - step; t += step) {

      while (true) {
        Coordinates nextPointCandidate = evaluationAlgorithm.evaluatePoint(controlPoints, t + step);
        if (areClose(previousPoint, nextPointCandidate)) {
          pointsToDraw.add(nextPointCandidate);
          previousPoint = nextPointCandidate;
          break;
        }

        step /= 2;
      }

    }
    return pointsToDraw;
  }

  private boolean areClose(Coordinates a, Coordinates b) {
    return abs(a.getX() - b.getX()) <= 1 && abs(a.getY() - b.getY()) <= 1;
  }

  private List<Coordinates> retrieveControlPoints() {
    return Lists.transform(this.controlPoints, new Function<InteractiveComponent, Coordinates>() {
      @Override
      public Coordinates apply(InteractiveComponent input) {
        return input.getCoordinates();
      }
    });
  }
}
