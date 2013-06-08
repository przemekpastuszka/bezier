/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve.evaluation;

import pl.rtshadow.bezier.components.Coordinates;
import pl.rtshadow.bezier.util.BoundedIterable;

public interface BezierEvaluationAlgorithm {
  Coordinates evaluatePoint(BoundedIterable<Coordinates> controlPoints, double argument);
}
