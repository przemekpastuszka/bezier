/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve.draw;

import java.util.Collection;

import pl.rtshadow.bezier.curve.evaluation.BezierEvaluationAlgorithm;
import pl.rtshadow.bezier.util.Coordinate;

public interface PixelsEvaluator {
  Collection<Coordinate> computePixelPositionsFrom(Iterable<Coordinate> controlPoints, BezierEvaluationAlgorithm bezierEvaluationAlgorithm);
}
