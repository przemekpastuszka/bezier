package pl.rtshadow.bezier.points.swing;

import static com.google.common.base.Preconditions.checkState;

import javax.swing.*;
import java.awt.*;

import com.google.common.base.Optional;

import pl.rtshadow.bezier.components.Coordinates;
import pl.rtshadow.bezier.components.MouseMoveableComponent;
import pl.rtshadow.bezier.components.RightClickRemovableComponent;
import pl.rtshadow.bezier.components.swing.SwingMouseOperableComponent;
import pl.rtshadow.bezier.listeners.MoveListener;
import pl.rtshadow.bezier.listeners.RemovalListener;
import pl.rtshadow.bezier.points.ControlPoint;

public class SwingControlPoint implements ControlPoint {
  private final MouseMoveableComponent swingMovingComponent;
  private final RightClickRemovableComponent rightClickRemovableComponent;
  private final JComponent jComponent;

  private Optional<Container> parent = Optional.absent();

  public SwingControlPoint(JComponent jComponent) {
    SwingMouseOperableComponent blah = new SwingMouseOperableComponent(jComponent);
    this.swingMovingComponent = new MouseMoveableComponent(blah);
    this.rightClickRemovableComponent = new RightClickRemovableComponent(blah);
    this.jComponent = jComponent;

    registerRemovalListener();
  }

  private void registerRemovalListener() {
    rightClickRemovableComponent.addRemovalListener(new RemovalListener() {
      @Override
      public void onRemoval(Object objectBeingRemoved) {
        if (parent.isPresent()) {
          parent.get().remove(jComponent);
          parent.get().revalidate();
          parent = Optional.absent();
        }
      }
    });
  }

  public void addTo(Container container) {
    checkState(!parent.isPresent(), "Already added to another container");

    container.add(jComponent);
    parent = Optional.of(container);
  }

  @Override
  public Coordinates getCoordinates() {
    return new Coordinates(jComponent.getLocation());
  }

  @Override
  public void setCoordinates(Coordinates coordinates) {
    // To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void addMoveListener(MoveListener moveListener) {
    swingMovingComponent.addMoveListener(moveListener);
  }

  @Override
  public void addRemovalListener(final RemovalListener removalListener) {
    rightClickRemovableComponent.addRemovalListener(new RemovalListener() {
      @Override
      public void onRemoval(Object objectBeingRemoved) {
        removalListener.onRemoval(this);
      }
    });
  }
}
