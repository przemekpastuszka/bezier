package pl.rtshadow.bezier.curve.transformations;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.nCopies;

import java.util.ArrayList;
import java.util.List;

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
