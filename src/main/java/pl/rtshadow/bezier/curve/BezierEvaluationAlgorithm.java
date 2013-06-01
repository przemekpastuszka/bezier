/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve;

import java.util.List;

import pl.rtshadow.bezier.components.Coordinates;

public interface BezierEvaluationAlgorithm {
  Coordinates evaluatePoint(List<Coordinates> controlPoints, float argument);
}
