/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve.transformations;

import java.util.List;

import pl.rtshadow.bezier.components.Coordinates;
import pl.rtshadow.bezier.util.BoundedIterable;

public interface BezierTransformation {
  List<Coordinates> apply(BoundedIterable<Coordinates> controlPoints);
}
