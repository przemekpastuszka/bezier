/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayListWithCapacity;
import static com.google.common.collect.Lists.transform;
import static java.lang.Math.round;

import java.util.List;

import com.google.common.base.Function;

import pl.rtshadow.bezier.components.Coordinates;

public class DeCasteljauAlgorithm implements BezierEvaluationAlgorithm {

  @Override
  public Coordinates evaluatePoint(List<Coordinates> controlPoints, float argument) {
    checkArgument(controlPoints.size() >= 2);

    float x = evaluate(getXs(controlPoints), argument);
    float y = evaluate(getYs(controlPoints), argument);

    return new Coordinates(round(x), round(y));
  }

  private float evaluate(List<Float> coefficients, float argument) {
    List<Float> lastLevel = coefficients;

    int n = coefficients.size() - 1;
    for(int i = 1; i <= n; ++i) {
      lastLevel = computeNewLevel(lastLevel, argument);
    }

    return lastLevel.get(0);
  }

  private List<Float> computeNewLevel(List<Float> lastLevel, float argument) {
    List<Float> newLevel = newArrayListWithCapacity(lastLevel.size() - 1);

    for(int i = 0; i < lastLevel.size() - 1; ++i) {
      newLevel.add(lastLevel.get(i) * (1 - argument) + lastLevel.get(i + 1) * argument);
    }

    return newLevel;
  }

  private List<Float> getYs(List<Coordinates> controlPoints) {
    return transform(controlPoints, new Function<Coordinates, Float>() {
      @Override
      public Float apply(Coordinates input) {
        return (float) input.getY();
      }
    });
  }

  private List<Float> getXs(List<Coordinates> controlPoints) {
    return transform(controlPoints, new Function<Coordinates, Float>() {
      @Override
      public Float apply(Coordinates input) {
        return (float) input.getX();
      }
    });
  }
}
