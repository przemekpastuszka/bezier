/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.app;

import javax.swing.*;

import com.google.inject.Guice;
import com.google.inject.Injector;

import pl.rtshadow.bezier.components.InteractiveComponentsList;
import pl.rtshadow.bezier.curve.BezierCurveComponent;
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


    BezierCurveComponent bezierCurve = injector.getInstance(BezierCurveComponent.class);
  }
}
