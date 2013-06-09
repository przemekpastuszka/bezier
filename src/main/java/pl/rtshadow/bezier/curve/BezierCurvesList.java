/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Optional;

import pl.rtshadow.bezier.components.actions.ComponentAction;
import pl.rtshadow.bezier.components.listeners.ComponentActionListener;
import pl.rtshadow.bezier.drawable.Surface;

public class BezierCurvesList {
  private final List<BezierCurve> curves = newArrayList();
  private Optional<BezierCurve> activeCurve = Optional.absent();

  private final Collection<ComponentActionListener> listeners = newArrayList();

  public void add(BezierCurve curve) {
    curves.add(curve);

    if (!activeCurve.isPresent()) {
      setActiveCurve(curve);
    }

    curve.addOnChangeListener(new ComponentActionListener() {
      @Override
      public void onComponentAction(ComponentAction action) {
        notifyListeners(action);
      }
    });
  }

  private void notifyListeners(ComponentAction action) {
    for (ComponentActionListener listener : listeners) {
      listener.onComponentAction(action);
    }
  }

  public void setActiveCurve(BezierCurve curve) {
    activeCurve = Optional.of(curve);
  }

  public BezierCurve getActiveCurve() {
    checkState(activeCurve.isPresent());

    return activeCurve.get();
  }

  public void registerOnChangeListener(ComponentActionListener listener) {
    listeners.add(listener);
  }

  public void redrawAll(Surface surface) {
    surface.clear();

    for (BezierCurve curveComponent : curves) {
      curveComponent.draw(surface);
    }
  }
}
