/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve.draw;

import java.util.Collection;

import pl.rtshadow.bezier.curve.evaluation.BezierEvaluationAlgorithm;
import pl.rtshadow.bezier.drawable.Surface;
import pl.rtshadow.bezier.util.Coordinate;

/**
 * Represents the algorithm to transform continuous Bezier curve into discrete set of pixel
 * locations.
 */
public interface PixelsEvaluator {
  /**
   * Computes concrete pixel locations, which will be used to draw Bezier curve on the
   * {@link Surface} (in other words - computes discrete representation of the curve)
   * 
   * @param controlPoints
   *          defines Bezier Curve
   * @param bezierEvaluationAlgorithm
   *          algorithm to evaluate Bezier curve in given point
   * @return collection of pixel locations
   */
  Collection<Coordinate> computePixelPositionsFrom(Iterable<Coordinate> controlPoints, BezierEvaluationAlgorithm bezierEvaluationAlgorithm);
}
