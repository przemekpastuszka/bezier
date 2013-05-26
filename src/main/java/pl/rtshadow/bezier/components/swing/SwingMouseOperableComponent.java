package pl.rtshadow.bezier.components.swing;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import pl.rtshadow.bezier.components.Coordinates;
import pl.rtshadow.bezier.events.MouseActionListener;
import pl.rtshadow.bezier.components.MouseOperableComponent;
import pl.rtshadow.bezier.events.swing.SwingMouseAction;

public class SwingMouseOperableComponent implements MouseOperableComponent {
  private final Component component;

  public SwingMouseOperableComponent(Component component) {
    this.component = component;
  }

  @Override
  public void addMousePressedListener(final MouseActionListener mouseActionListener) {
    component.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        mouseActionListener.action(new SwingMouseAction(e));
      }
    });
  }

  @Override
  public void addMouseDraggedListener(final MouseActionListener mouseActionListener) {
    component.addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseDragged(MouseEvent e) {
        mouseActionListener.action(new SwingMouseAction(e));
      }
    });
  }

  @Override
  public Coordinates getCoordinates() {
    return new Coordinates(component.getLocation());
  }

  @Override
  public void setCoordinates(Coordinates coordinates) {
    component.setLocation(coordinates.getX(), coordinates.getY());
  }
}
