/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve.evaluation;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayListWithCapacity;
import static pl.rtshadow.bezier.components.Coordinates.add;
import static pl.rtshadow.bezier.components.Coordinates.multiply;

import java.util.List;

import pl.rtshadow.bezier.components.Coordinates;

public class DeCasteljauAlgorithm implements BezierEvaluationAlgorithm {

  @Override
  public Coordinates evaluatePoint(List<Coordinates> controlPoints, double argument) {
    checkArgument(controlPoints.size() >= 2);

    return evaluate(controlPoints, argument);
  }

  private Coordinates evaluate(List<Coordinates> coefficients, double argument) {
    List<Coordinates> lastLevel = coefficients;

    int n = coefficients.size() - 1;
    for(int i = 1; i <= n; ++i) {
      lastLevel = computeNewLevel(lastLevel, argument);
    }

    return lastLevel.get(0);
  }

  private List<Coordinates> computeNewLevel(List<Coordinates> lastLevel, double argument) {
    List<Coordinates> newLevel = newArrayListWithCapacity(lastLevel.size() - 1);

    for(int i = 0; i < lastLevel.size() - 1; ++i) {
      newLevel.add(add(multiply(1 - argument, lastLevel.get(i)), multiply(argument, lastLevel.get(i + 1))));
    }

    return newLevel;
  }
}
