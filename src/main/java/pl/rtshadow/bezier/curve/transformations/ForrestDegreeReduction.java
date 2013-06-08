/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve.transformations;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.nCopies;

import java.util.ArrayList;
import java.util.List;

/**
 * Original blending method for elevation inversion, proposed by Forrest (1972)
 * Just take first half of first control points and second half of second control points
 * and concatenate them together. In case of odd number of control points,
 * let the midpoint be the average of midpoints of first and second set.
 */
public class ForrestDegreeReduction extends DegreeElevationInversion {
  @Override
  protected List<Double> getAlphas(int n) {
    ArrayList<Double> alphas = newArrayList(nCopies(n / 2, new Double(0)));
    if (n % 2 == 1) {
      alphas.add(0.5);
    }
    alphas.addAll(nCopies(n - n / 2, new Double(1)));
    return alphas;
  }
}
