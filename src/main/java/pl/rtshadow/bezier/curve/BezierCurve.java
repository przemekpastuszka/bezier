/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve;

import java.awt.*;
import java.util.Collection;
import java.util.List;

import pl.rtshadow.bezier.components.InteractiveComponentsList;
import pl.rtshadow.bezier.curve.draw.DensePixelEvaluator;
import pl.rtshadow.bezier.curve.evaluation.BezierEvaluationAlgorithm;
import pl.rtshadow.bezier.curve.transformations.BezierTransformation;
import pl.rtshadow.bezier.drawable.Surface;
import pl.rtshadow.bezier.util.BoundedIterable;
import pl.rtshadow.bezier.util.Coordinate;

public class BezierCurve {
  private final InteractiveComponentsList controlPoints;
  private final BezierEvaluationAlgorithm evaluationAlgorithm;
  private final Color color;

  public BezierCurve(InteractiveComponentsList controlPoints, BezierEvaluationAlgorithm evaluationAlgorithm, Color color) {
    this.controlPoints = controlPoints;
    this.evaluationAlgorithm = evaluationAlgorithm;
    this.color = color;
  }

  public void draw(Surface surface) {
    if (controlPoints.getSize() >= 2) {
      surface.drawPoints(computeDrawPoints(controlPoints), color);
    }
  }

  public List<Coordinate> transformation(BezierTransformation transformation) {
    return transformation.apply(controlPoints);
  }

  public void deactivate() {
    controlPoints.deactivate();
  }

  private Collection<Coordinate> computeDrawPoints(BoundedIterable<Coordinate> controlPoints) {
    return new DensePixelEvaluator().computePixelPositionsFrom(controlPoints, evaluationAlgorithm);
  }
}
