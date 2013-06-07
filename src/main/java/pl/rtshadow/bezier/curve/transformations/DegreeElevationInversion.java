package pl.rtshadow.bezier.curve.transformations;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newArrayListWithCapacity;
import static java.util.Collections.nCopies;
import static pl.rtshadow.bezier.components.Coordinates.add;
import static pl.rtshadow.bezier.components.Coordinates.multiply;

import java.util.List;

import pl.rtshadow.bezier.components.Coordinates;

public class DegreeElevationInversion implements BezierTransformation {

  @Override
  public List<Coordinates> apply(List<Coordinates> controlPoints) {
    checkArgument(controlPoints.size() > 2);

    int n = controlPoints.size() - 1;

    List<Coordinates> bOneControlPoints = computeFirstPoints(controlPoints, n);
    List<Coordinates> bTwoControlPoints = computeSecondPoints(controlPoints, n);
    return sumOfParts(n, bOneControlPoints, bTwoControlPoints);
  }

  private List<Coordinates> sumOfParts(int n, List<Coordinates> bOneControlPoints, List<Coordinates> bTwoControlPoints) {
    List<Coordinates> newControlPoints = bOneControlPoints.subList(0, n / 2);
    if (n % 2 == 1) {
      newControlPoints.add(
          multiply(0.5,
              add(
                  bOneControlPoints.get(n / 2),
                  bTwoControlPoints.get(n / 2))));
    }
    newControlPoints.addAll(bTwoControlPoints.subList(n - n / 2, n));

    return newControlPoints;
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

  private List<Coordinates> farinWeighedAverage(int n, List<Coordinates> bOneControlPoints, List<Coordinates> bTwoControlPoints) {
    List<Coordinates> newControlPoints = newArrayListWithCapacity(n);
    for (int i = 0; i <= n - 1; ++i) {
      Coordinates nextPoint =
          add(
              multiply(1f - i / ((float) n - 1f), bOneControlPoints.get(i)),
              multiply(i / ((float) n - 1f), bTwoControlPoints.get(i)));
      newControlPoints.add(nextPoint);
    }
    return newControlPoints;
  }
}
