/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.app;

import javax.swing.*;
import java.awt.*;

import com.google.inject.Guice;
import com.google.inject.Injector;

import pl.rtshadow.bezier.bridge.components.ExternalMouseDrivenComponent;
import pl.rtshadow.bezier.bridge.components.swing.SwingMouseDrivenComponent;
import pl.rtshadow.bezier.bridge.events.MouseAction;
import pl.rtshadow.bezier.bridge.events.MouseActionData;
import pl.rtshadow.bezier.bridge.events.MouseActionListener;
import pl.rtshadow.bezier.components.InteractiveComponentsList;
import pl.rtshadow.bezier.curve.BezierCurveComponent;
import pl.rtshadow.bezier.curve.transformations.DegreeElevationInversion;
import pl.rtshadow.bezier.inject.InjectionConfiguration;

public class BezierApplication {
  private static InteractiveComponentsList components;

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        showGui();
      }
    });
  }

  private static void showGui() {
    Injector injector = Guice.createInjector(new InjectionConfiguration());
    JFrame frame = injector.getInstance(JFrame.class);


    final BezierCurveComponent bezierCurve = injector.getInstance(BezierCurveComponent.class);
    Container rawSurface = injector.getInstance(Container.class);
    ExternalMouseDrivenComponent surface = new SwingMouseDrivenComponent(rawSurface);

    surface.addMouseActionListener(MouseAction.MOUSE_CLICKED, new MouseActionListener() {
      @Override
      public void onMouseAction(MouseActionData action) {
        if(action.getButtonPressed() == MouseActionData.ButtonPressed.RIGHT) {
          bezierCurve.transform(new DegreeElevationInversion());
        }
      }
    });
  }
}
