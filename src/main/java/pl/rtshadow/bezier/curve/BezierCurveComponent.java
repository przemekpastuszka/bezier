/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve;

import static pl.rtshadow.bezier.components.actions.ComponentAction.ADDED;
import static pl.rtshadow.bezier.components.actions.ComponentAction.MOVED;
import static pl.rtshadow.bezier.components.actions.ComponentAction.REMOVED;

import com.google.inject.Inject;

import pl.rtshadow.bezier.components.InteractiveComponentsList;
import pl.rtshadow.bezier.components.actions.ComponentAction;
import pl.rtshadow.bezier.components.listeners.ComponentActionListener;
import pl.rtshadow.bezier.drawable.Surface;

public class BezierCurveComponent {
  private final BezierCurve curve;

  @Inject
  public BezierCurveComponent(final Surface surface, InteractiveComponentsList controlPoints) {
    this.curve = new BezierCurve(controlPoints);

    controlPoints.addListenerOnEachComponent(MOVED, redrawCurve(surface));
    controlPoints.addListenerOnEachComponent(REMOVED, redrawCurve(surface));
    controlPoints.addListenerOnEachComponent(ADDED, redrawCurve(surface));
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
