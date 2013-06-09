/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.inject;

import javax.swing.*;
import java.awt.*;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;

import pl.rtshadow.bezier.bridge.components.swing.SwingMouseDrivenComponent;
import pl.rtshadow.bezier.components.InteractiveComponent;
import pl.rtshadow.bezier.components.InteractiveComponentsList;
import pl.rtshadow.bezier.components.MouseInteractiveMovableComponent;
import pl.rtshadow.bezier.components.factory.ComponentFactory;
import pl.rtshadow.bezier.components.factory.OnClickComponentFactory;
import pl.rtshadow.bezier.curve.evaluation.BezierEvaluationAlgorithm;
import pl.rtshadow.bezier.curve.evaluation.DeCasteljauAlgorithm;
import pl.rtshadow.bezier.curve.factory.BezierCurveFactory;
import pl.rtshadow.bezier.curve.factory.DefaultBezierCurveFactory;
import pl.rtshadow.bezier.drawable.Surface;
import pl.rtshadow.bezier.drawable.swing.SwingSurface;
import pl.rtshadow.bezier.util.Coordinate;

public class InjectionConfiguration extends AbstractModule {
  private final static Injector injector = Guice.createInjector(new InjectionConfiguration());

  public static Injector getInjector() {
    return injector;
  }

  @Override
  protected void configure() {
    bind(Surface.class).to(SwingSurface.class);
    bind(SwingSurface.class).toInstance(new SwingSurface());
    bind(BezierCurveFactory.class).to(DefaultBezierCurveFactory.class);
    bind(BezierEvaluationAlgorithm.class).to(DeCasteljauAlgorithm.class);

    bind(Container.class).to(SwingSurface.class);
  }

  @Provides
  public InteractiveComponentsList provideButtonsList(Container container, ComponentFactory componentFactory) {
    return new InteractiveComponentsList(
        new OnClickComponentFactory(new SwingMouseDrivenComponent(container), componentFactory));
  }

  @Provides
  public ComponentFactory provideButtonFactory(final Container container) {
    return new ComponentFactory() {
      int itemCount = 0;

      @Override
      public InteractiveComponent createFromPosition(Coordinate coordinate) {
        Component button = new SomeButton(itemCount++, coordinate.getXAsInt(), coordinate.getYAsInt());
        container.add(button);
        return new MouseInteractiveMovableComponent(
            new SwingMouseDrivenComponent(button, container));
      }
    };
  }

  public static class SomeButton extends JButton {
    public SomeButton(int number, int x, int y) {
      super(Integer.toString(number));

      setDoubleBuffered(false);
      super.setLocation(x, y);
      setMargin(new Insets(0, 0, 0, 0));
      setSize(25, 25);
      setPreferredSize(new Dimension(25, 25));
    }
  }
}
