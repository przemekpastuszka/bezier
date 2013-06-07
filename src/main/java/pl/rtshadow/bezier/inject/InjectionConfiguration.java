package pl.rtshadow.bezier.inject;

import javax.swing.*;
import java.awt.*;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import pl.rtshadow.bezier.bridge.components.swing.SwingMouseDrivenComponent;
import pl.rtshadow.bezier.components.Coordinates;
import pl.rtshadow.bezier.components.InteractiveComponent;
import pl.rtshadow.bezier.components.InteractiveComponentsList;
import pl.rtshadow.bezier.components.MouseInteractiveMovableComponent;
import pl.rtshadow.bezier.components.factory.ComponentFactory;
import pl.rtshadow.bezier.components.factory.OnClickComponentFactory;
import pl.rtshadow.bezier.curve.evaluation.BezierEvaluationAlgorithm;
import pl.rtshadow.bezier.curve.evaluation.DeCasteljauAlgorithm;
import pl.rtshadow.bezier.drawable.Surface;
import pl.rtshadow.bezier.drawable.swing.SwingSurface;

public class InjectionConfiguration extends AbstractModule {
  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;

  @Override
  protected void configure() {
    bind(Surface.class).to(SwingSurface.class);
    bind(Container.class).to(JLayeredPane.class);
    bind(BezierEvaluationAlgorithm.class).to(DeCasteljauAlgorithm.class);
  }

  @Provides
  @Singleton
  public JLayeredPane provideLayeredPane() {
     return new JLayeredPane();
  }

  @Provides
  @Singleton
  public JFrame provideFrame(JLayeredPane pane) {
    JFrame frame = new JFrame();
    frame.setLayout(new BorderLayout());
    frame.setSize(WIDTH, HEIGHT);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.add(pane, BorderLayout.CENTER);
    frame.setVisible(true);
    return frame;
  }

  @Provides
  @Singleton
  public SwingSurface provideDrawingSurface(JLayeredPane container) {
    SwingSurface swingSurface = new SwingSurface(WIDTH, HEIGHT);
    container.add(swingSurface, new Integer(0), 0);
    return swingSurface;
  }

  @Provides
  public InteractiveComponentsList provideButtonsList(Container container, ComponentFactory componentFactory) {
    return new InteractiveComponentsList(
        new OnClickComponentFactory(new SwingMouseDrivenComponent(container), componentFactory));
  }

  @Provides
  public ComponentFactory provideButtonFactory(final JLayeredPane container) {
    return new ComponentFactory() {
      int itemCount = 0;

      @Override
      public InteractiveComponent createFromPosition(Coordinates coordinates) {
        Component button = new SomeButton(itemCount++, coordinates.getXAsInt(), coordinates.getYAsInt());
        container.add(button, 2 * (container.highestLayer() / 2) + 1, 0);
        return new MouseInteractiveMovableComponent(
            new SwingMouseDrivenComponent(button, container));
      }
    };
  }

  private static class SomeButton extends JButton {
    public SomeButton(int number, int x, int y) {
      super(Integer.toString(number));

      setDoubleBuffered(false);
      setLocation(x, y);
      setMargin(new Insets(0, 0, 0, 0));
      setSize(25, 25);
      setPreferredSize(new Dimension(25, 25));
    }
  }
}
