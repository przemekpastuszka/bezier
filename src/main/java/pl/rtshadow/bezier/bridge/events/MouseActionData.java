/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.bridge.events;

import pl.rtshadow.bezier.util.Coordinate;

public class MouseActionData {
  public enum ButtonPressed {
    LEFT,
    RIGHT,
    OTHER;
  }

  private final Coordinate mousePosition;
  private final ButtonPressed buttonPressed;

  public MouseActionData(Coordinate mousePosition, ButtonPressed buttonPressed) {
    this.mousePosition = mousePosition;
    this.buttonPressed = buttonPressed;
  }

  public ButtonPressed getButtonPressed() {
    return buttonPressed;
  }

  public Coordinate getMousePosition() {
    return mousePosition;
  }
}
