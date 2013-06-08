/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve.transformations;

import java.util.List;

import pl.rtshadow.bezier.util.Coordinate;
import pl.rtshadow.bezier.util.BoundedIterable;

public interface BezierTransformation {
  List<Coordinate> apply(BoundedIterable<Coordinate> controlPoints);
}
