/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.app;

import com.google.inject.Guice;
import com.google.inject.Injector;

import pl.rtshadow.bezier.components.InteractiveComponentsList;
import pl.rtshadow.bezier.inject.InjectionConfiguration;

public class BezierApplication {
  private static InteractiveComponentsList components;

  public static void main(String[] args) {
    Injector injector = Guice.createInjector(new InjectionConfiguration());
    components = injector.getInstance(InteractiveComponentsList.class);
  }
}
