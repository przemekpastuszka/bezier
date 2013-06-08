/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve.transformations;

import static com.google.common.collect.Lists.newArrayListWithCapacity;

import java.util.ArrayList;
import java.util.List;

/**
 * Blending method in elevation inversion process, proposed by Farin (1983)
 * Control points are blended using weighted average
 */
public class FarinDegreeReduction extends DegreeElevationInversion {
  @Override
  protected List<Double> getAlphas(int n) {
    ArrayList<Double> alphas = newArrayListWithCapacity(n);
    for(int i = 0; i <= n - 1; ++i) {
      alphas.add(i / ((double) n - 1));
    }
    return  alphas;
  }
}
