/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.bridge.events;

import pl.rtshadow.bezier.components.Coordinates;

public class MouseAction {
  public enum ButtonPressed {
    LEFT,
    RIGHT,
    OTHER;
  }

  private final Coordinates mousePosition;
  private final ButtonPressed buttonPressed;

  public MouseAction(Coordinates mousePosition, ButtonPressed buttonPressed) {
    this.mousePosition = mousePosition;
    this.buttonPressed = buttonPressed;
  }

  public ButtonPressed getButtonPressed() {
    return buttonPressed;
  }

  public Coordinates getMousePosition() {
    return mousePosition;
  }
}