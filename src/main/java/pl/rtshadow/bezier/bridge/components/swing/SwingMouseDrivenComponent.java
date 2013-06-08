/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.bridge.components.swing;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import com.google.common.base.Optional;

import pl.rtshadow.bezier.bridge.components.ExternalMouseDrivenComponent;
import pl.rtshadow.bezier.bridge.events.MouseAction;
import pl.rtshadow.bezier.bridge.events.MouseActionListener;
import pl.rtshadow.bezier.bridge.events.swing.SwingMouseActionData;
import pl.rtshadow.bezier.util.Coordinate;

public class SwingMouseDrivenComponent implements ExternalMouseDrivenComponent {
  private final Component component;
  private final Optional<Container> componentParent;

  public SwingMouseDrivenComponent(Component component, Container parent) {
    this.componentParent = Optional.of(parent);
    this.component = component;
  }

  public SwingMouseDrivenComponent(Component component) {
    this.componentParent = Optional.absent();
    this.component = component;
  }

  public void addMousePressedListener(final MouseActionListener mouseActionListener) {
    component.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        mouseActionListener.onMouseAction(new SwingMouseActionData(e));
      }
    });
  }

  public void addMouseDraggedListener(final MouseActionListener mouseActionListener) {
    component.addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseDragged(MouseEvent e) {
        mouseActionListener.onMouseAction(new SwingMouseActionData(e));
      }
    });
  }

  private void addMouseClickedListener(final MouseActionListener mouseActionListener) {
    component.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        mouseActionListener.onMouseAction(new SwingMouseActionData(e));
      }
    });
  }

  @Override
  public void addMouseActionListener(MouseAction action, MouseActionListener mouseActionListener) {
    switch (action) {
    case MOUSE_DRAGGED:
      addMouseDraggedListener(mouseActionListener);
      break;
    case MOUSE_PRESSED:
      addMousePressedListener(mouseActionListener);
      break;
    case MOUSE_CLICKED:
      addMouseClickedListener(mouseActionListener);
    }
  }

  @Override
  public Coordinate getCoordinates() {
    return new Coordinate(component.getLocation());
  }

  @Override
  public void setCoordinates(Coordinate coordinate) {
    component.setLocation(coordinate.getXAsInt(), coordinate.getYAsInt());
  }

  @Override
  public void remove() {
    deactivate();
    if (componentParent.isPresent()) {
      componentParent.get().remove(component);
    }
  }

  @Override
  public void deactivate() {
    component.setVisible(false);
  }
}
