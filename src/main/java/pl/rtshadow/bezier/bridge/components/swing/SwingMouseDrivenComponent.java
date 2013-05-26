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
import pl.rtshadow.bezier.bridge.events.MouseActionListener;
import pl.rtshadow.bezier.bridge.events.swing.SwingMouseAction;
import pl.rtshadow.bezier.components.Coordinates;

public class SwingMouseDrivenComponent implements ExternalMouseDrivenComponent {
  private final Component component;
  private final Optional<Container> componentParent;

  public SwingMouseDrivenComponent(Component component, Container parent) {
    this.componentParent = Optional.of(parent);
    this.component = component;
    parent.add(component);
  }

  public SwingMouseDrivenComponent(Component component) {
    this.componentParent = Optional.absent();
    this.component = component;
  }

  @Override
  public void addMousePressedListener(final MouseActionListener mouseActionListener) {
    component.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        mouseActionListener.onMouseAction(new SwingMouseAction(e));
      }
    });
  }

  @Override
  public void addMouseDraggedListener(final MouseActionListener mouseActionListener) {
    component.addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseDragged(MouseEvent e) {
        mouseActionListener.onMouseAction(new SwingMouseAction(e));
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

  @Override
  public void remove() {
    component.setVisible(false);
    if(componentParent.isPresent()) {
      componentParent.get().remove(component);
    }
  }
}
