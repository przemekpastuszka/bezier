/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve.factory;

import static com.google.common.collect.Iterators.cycle;
import static java.awt.Color.BLACK;
import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;
import static java.awt.Color.ORANGE;
import static java.awt.Color.PINK;
import static java.awt.Color.RED;
import static java.awt.Color.YELLOW;
import static pl.rtshadow.bezier.inject.InjectionConfiguration.getInjector;

import java.awt.*;
import java.util.Collection;
import java.util.Iterator;

import pl.rtshadow.bezier.curve.BezierCurve;
import pl.rtshadow.bezier.util.Coordinate;

public class DefaultBezierCurveFactory implements BezierCurveFactory {
  private final static Iterator<Color> colors = cycle(BLACK, BLUE, RED, YELLOW, ORANGE, PINK, GREEN);

  @Override
  public BezierCurve createCurveFrom(Collection<Coordinate> points) {
    BezierCurve curve = getInjector().getInstance(BezierCurve.class);

    for (Coordinate point : points) {
      curve.addControlPoint(point);
    }

    curve.setColor(colors.next());

    return curve;
  }
}
