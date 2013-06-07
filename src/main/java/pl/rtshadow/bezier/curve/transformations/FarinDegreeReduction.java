package pl.rtshadow.bezier.curve.transformations;

import static com.google.common.collect.Lists.newArrayListWithCapacity;

import java.util.ArrayList;
import java.util.List;

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
