/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve;

import static pl.rtshadow.bezier.components.actions.ComponentAction.ADDED;
import static pl.rtshadow.bezier.components.actions.ComponentAction.MOVED;
import static pl.rtshadow.bezier.components.actions.ComponentAction.REMOVED;

import java.util.List;

import com.google.inject.Inject;

import pl.rtshadow.bezier.components.Coordinates;
import pl.rtshadow.bezier.components.InteractiveComponentsList;
import pl.rtshadow.bezier.components.actions.ComponentAction;
import pl.rtshadow.bezier.components.factory.ComponentFactory;
import pl.rtshadow.bezier.components.listeners.ComponentActionListener;
import pl.rtshadow.bezier.curve.evaluation.BezierEvaluationAlgorithm;
import pl.rtshadow.bezier.curve.transformations.BezierTransformation;
import pl.rtshadow.bezier.drawable.Surface;

public class BezierCurveComponent {
  private final BezierCurve curve;
  private final ComponentFactory factory;
  private final InteractiveComponentsList controlPoints;

  @Inject
  public BezierCurveComponent(
      final Surface surface,
      InteractiveComponentsList controlPoints,
      BezierEvaluationAlgorithm evaluationAlgorithm,
      ComponentFactory factory) {

    this.curve = new BezierCurve(controlPoints, evaluationAlgorithm);
    this.factory = factory;
    this.controlPoints = controlPoints;

    controlPoints.addListenerOnEachComponent(MOVED, redrawCurve(surface));
    controlPoints.addListenerOnEachComponent(REMOVED, redrawCurve(surface));
    controlPoints.addListenerOnEachComponent(ADDED, redrawCurve(surface));
  }

  public void transform(BezierTransformation transformation) {
    if (controlPoints.size() > 2) {

      List<Coordinates> newPoints = curve.transformation(transformation);

      factory.reset();
      controlPoints.clear();
      for (Coordinates point : newPoints) {
        controlPoints.add(factory.createFromPosition(point));
      }
    }
  }

  private ComponentActionListener redrawCurve(final Surface surface) {
    return new ComponentActionListener() {
      @Override
      public void onComponentAction(ComponentAction action) {
        surface.clear();
        curve.draw(surface);
      }
    };
  }
}
