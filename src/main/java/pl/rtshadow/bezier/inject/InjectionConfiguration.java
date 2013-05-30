package pl.rtshadow.bezier.inject;

import javax.swing.*;

import java.awt.*;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import pl.rtshadow.bezier.bridge.components.swing.SwingMouseDrivenComponent;
import pl.rtshadow.bezier.bridge.events.MouseActionData;
import pl.rtshadow.bezier.components.InteractiveComponent;
import pl.rtshadow.bezier.components.InteractiveComponentsList;
import pl.rtshadow.bezier.components.MouseInteractiveMovableComponent;
import pl.rtshadow.bezier.components.factory.ComponentFactory;
import pl.rtshadow.bezier.components.factory.OnClickComponentFactory;

public class InjectionConfiguration extends AbstractModule {
  @Override
  protected void configure() {
  }

  @Provides
  @Singleton
  public JFrame provideFrame() {
    JFrame frame = new JFrame();
    frame.setLayout(null);
    frame.setSize(300, 300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    return frame;
  }

  @Provides
  public InteractiveComponentsList provideButtonsList(JFrame frame, ComponentFactory componentFactory) {
    return new InteractiveComponentsList(
        new OnClickComponentFactory(new SwingMouseDrivenComponent(frame), componentFactory));
  }

  @Provides
  public ComponentFactory provideButtonFactory(final JFrame frame) {
    return new ComponentFactory() {
      int itemCount = 0;

      @Override
      public InteractiveComponent createFromMouseData(MouseActionData data) {
        Component button = new SomeButton(itemCount++, data.getMousePosition().getX(), data.getMousePosition().getY());
        return new MouseInteractiveMovableComponent(
            new SwingMouseDrivenComponent(button, frame.getContentPane()));
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
