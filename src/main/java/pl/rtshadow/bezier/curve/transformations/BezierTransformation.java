/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve.transformations;

import java.util.List;

import pl.rtshadow.bezier.util.BoundedIterable;
import pl.rtshadow.bezier.util.Coordinate;

/**
 * Represents functional transformation (i.e. without changing internal state) on Bezier curve
 */
public interface BezierTransformation {

  /**
   * @param controlPoints
   * @return control points after transformation, forming new Bezier curve
   */
  List<Coordinate> apply(BoundedIterable<Coordinate> controlPoints);
}
