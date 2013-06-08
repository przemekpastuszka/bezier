/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve.transformations;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newArrayListWithCapacity;
import static java.util.Collections.nCopies;
import static pl.rtshadow.bezier.components.Coordinates.add;
import static pl.rtshadow.bezier.components.Coordinates.multiply;

import java.util.List;

import pl.rtshadow.bezier.components.Coordinates;

public abstract class DegreeElevationInversion implements BezierTransformation {

  @Override
  public List<Coordinates> apply(List<Coordinates> controlPoints) {
    checkArgument(controlPoints.size() > 2);

    int n = controlPoints.size() - 1;

    List<Coordinates> bOneControlPoints = computeFirstPoints(controlPoints, n);
    List<Coordinates> bTwoControlPoints = computeSecondPoints(controlPoints, n);
    return blend(n, bOneControlPoints, bTwoControlPoints);
  }

  private List<Coordinates> computeFirstPoints(List<Coordinates> controlPoints, int n) {
    List<Coordinates> bOneControlPoints = newArrayListWithCapacity(n);
    bOneControlPoints.add(controlPoints.get(0));
    for (int i = 1; i <= n - 1; ++i) {
      Coordinates lastPoint = bOneControlPoints.get(i - 1);
      Coordinates nextPoint = multiply(1f / ((float) n - i),
          add(
              multiply(n, controlPoints.get(i)),
              multiply(-i, lastPoint)));

      bOneControlPoints.add(nextPoint);
    }
    return bOneControlPoints;
  }

  private List<Coordinates> computeSecondPoints(List<Coordinates> controlPoints, int n) {
    List<Coordinates> bTwoControlPoints = newArrayList(nCopies(n, new Coordinates(0, 0)));
    bTwoControlPoints.set(n - 1, controlPoints.get(n));
    for (int i = n - 1; i >= 1; --i) {
      Coordinates lastPoint = bTwoControlPoints.get(i);
      Coordinates nextPoint = multiply(1f / (float) i,
          add(
              multiply(n, controlPoints.get(i)),
              multiply(i - n, lastPoint)));

      bTwoControlPoints.set(i - 1, nextPoint);
    }
    return bTwoControlPoints;
  }

  private List<Coordinates> blend(int n, List<Coordinates> bOneControlPoints, List<Coordinates> bTwoControlPoints) {
    List<Coordinates> newControlPoints = newArrayListWithCapacity(n);
    List<Double> alphas = getAlphas(n);
    for (int i = 0; i <= n - 1; ++i) {
      Coordinates nextPoint =
          add(
              multiply(1 - alphas.get(i), bOneControlPoints.get(i)),
              multiply(alphas.get(i), bTwoControlPoints.get(i)));
      newControlPoints.add(nextPoint);
    }
    return newControlPoints;
  }

  protected abstract List<Double> getAlphas(int n);
}
