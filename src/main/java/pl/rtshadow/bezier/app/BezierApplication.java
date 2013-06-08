/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.app;

import static java.awt.Color.BLACK;
import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;
import static java.awt.Color.ORANGE;
import static java.awt.Color.PINK;
import static java.awt.Color.RED;
import static java.awt.Color.YELLOW;
import static pl.rtshadow.bezier.components.actions.ComponentAction.ADDED;
import static pl.rtshadow.bezier.components.actions.ComponentAction.MOVED;
import static pl.rtshadow.bezier.components.actions.ComponentAction.REMOVED;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;

import pl.rtshadow.bezier.bridge.components.ExternalMouseDrivenComponent;
import pl.rtshadow.bezier.bridge.components.swing.SwingMouseDrivenComponent;
import pl.rtshadow.bezier.bridge.events.MouseAction;
import pl.rtshadow.bezier.bridge.events.MouseActionData;
import pl.rtshadow.bezier.bridge.events.MouseActionListener;
import pl.rtshadow.bezier.util.Coordinate;
import pl.rtshadow.bezier.components.InteractiveComponentsList;
import pl.rtshadow.bezier.components.actions.ComponentAction;
import pl.rtshadow.bezier.components.listeners.ComponentActionListener;
import pl.rtshadow.bezier.curve.BezierCurve;
import pl.rtshadow.bezier.curve.evaluation.BezierEvaluationAlgorithm;
import pl.rtshadow.bezier.curve.transformations.LeastSquaresReduction;
import pl.rtshadow.bezier.drawable.Surface;
import pl.rtshadow.bezier.inject.InjectionConfiguration;

public class BezierApplication {
  private static InteractiveComponentsList components;
  private static Injector injector;

  private static Surface surface;
  private static List<BezierCurve> curves = Lists.newArrayList();

  private static Iterator<Color> colors = Iterators.cycle(BLACK, BLUE, RED, YELLOW, ORANGE, PINK, GREEN);

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        showGui();
      }
    });
  }

  private static void showGui() {
    injector = Guice.createInjector(new InjectionConfiguration());
    JFrame frame = injector.getInstance(JFrame.class);

    surface = injector.getInstance(Surface.class);

    curves.add(bezierCurveFrom(Collections.EMPTY_LIST));

    Container rawSurface = injector.getInstance(Container.class);
    ExternalMouseDrivenComponent surface = new SwingMouseDrivenComponent(rawSurface);

    surface.addMouseActionListener(MouseAction.MOUSE_CLICKED, new MouseActionListener() {
      @Override
      public void onMouseAction(MouseActionData action) {
        if (action.getButtonPressed() == MouseActionData.ButtonPressed.RIGHT) {
          transformOldCurve();
        }
      }
    });
  }

  private static void transformOldCurve() {
    BezierCurve lastCurve = curves.get(curves.size() - 1);

    lastCurve.deactivate();
    BezierCurve newCurve = bezierCurveFrom(lastCurve.transformation(new LeastSquaresReduction()));
    newCurve.draw(surface);

    curves.add(newCurve);
  }

  private static BezierCurve bezierCurveFrom(Collection<Coordinate> points) {
    InteractiveComponentsList interactivePoints = injector.getInstance(InteractiveComponentsList.class);
    for (Coordinate point : points) {
      interactivePoints.add(point);
    }

    interactivePoints.addListenerOnEachComponent(MOVED, new CurveChangedListener());
    interactivePoints.addListenerOnEachComponent(REMOVED, new CurveChangedListener());
    interactivePoints.addListenerOnEachComponent(ADDED, new CurveChangedListener());

    return new BezierCurve(
        interactivePoints,
        injector.getInstance(BezierEvaluationAlgorithm.class), colors.next());
  }

  private static class CurveChangedListener implements ComponentActionListener {
    @Override
    public void onComponentAction(ComponentAction action) {
      redrawAll();
    }
  }

  private static void redrawAll() {
    surface.clear();

    for (BezierCurve curveComponent : curves) {
      curveComponent.draw(surface);
    }
  }
}
