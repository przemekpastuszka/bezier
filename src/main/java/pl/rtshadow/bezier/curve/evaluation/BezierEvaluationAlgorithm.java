/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve.evaluation;

import pl.rtshadow.bezier.util.Coordinate;

public interface BezierEvaluationAlgorithm {
  Coordinate evaluatePoint(Iterable<Coordinate> controlPoints, double argument);
}
