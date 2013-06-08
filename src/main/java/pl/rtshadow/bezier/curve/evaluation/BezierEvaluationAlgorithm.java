/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve.evaluation;

import pl.rtshadow.bezier.util.Coordinate;

public interface BezierEvaluationAlgorithm {

  /**
   * Evaluates Bezier curve, given by control points, in given argument
   * 
   * @param controlPoints
   *          defines Bezier curve
   * @param argument
   *          for bezier curve from [0, 1]
   * @return evaluated point on Bezier curve
   */
  Coordinate evaluatePoint(Iterable<Coordinate> controlPoints, double argument);
}
