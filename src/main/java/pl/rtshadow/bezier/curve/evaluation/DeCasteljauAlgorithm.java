/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve.evaluation;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newArrayListWithCapacity;
import static pl.rtshadow.bezier.util.Coordinates.add;
import static pl.rtshadow.bezier.util.Coordinates.multiply;

import java.util.List;

import pl.rtshadow.bezier.util.Coordinate;
import pl.rtshadow.bezier.util.BoundedIterable;

public class DeCasteljauAlgorithm implements BezierEvaluationAlgorithm {

  @Override
  public Coordinate evaluatePoint(Iterable<Coordinate> coefficients, double argument) {
    List<Coordinate> lastLevel = newArrayList(coefficients);

    checkArgument(lastLevel.size() >= 2);

    int n = lastLevel.size() - 1;
    for(int i = 1; i <= n; ++i) {
      lastLevel = computeNewLevel(lastLevel, argument);
    }

    return lastLevel.get(0);
  }

  private List<Coordinate> computeNewLevel(List<Coordinate> lastLevel, double argument) {
    List<Coordinate> newLevel = newArrayListWithCapacity(lastLevel.size() - 1);

    for(int i = 0; i < lastLevel.size() - 1; ++i) {
      newLevel.add(add(multiply(1 - argument, lastLevel.get(i)), multiply(argument, lastLevel.get(i + 1))));
    }

    return newLevel;
  }
}
