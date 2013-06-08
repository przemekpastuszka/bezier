/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve.factory;

import java.util.Collection;

import pl.rtshadow.bezier.curve.BezierCurve;
import pl.rtshadow.bezier.util.Coordinate;

public interface BezierCurveFactory {
  BezierCurve createCurveFrom(Collection<Coordinate> points);
}
