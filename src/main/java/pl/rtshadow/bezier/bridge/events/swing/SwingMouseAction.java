/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.bridge.events.swing;

import java.awt.event.MouseEvent;

import pl.rtshadow.bezier.components.Coordinates;
import pl.rtshadow.bezier.bridge.events.MouseAction;

public class SwingMouseAction extends MouseAction {
  public SwingMouseAction(MouseEvent event) {
    super(new Coordinates(event.getPoint()), retrievePressedButton(event.getButton()));
  }

  private static ButtonPressed retrievePressedButton(int event) {
    if(event == MouseEvent.BUTTON1) {
      return ButtonPressed.LEFT;
    }
    if(event == MouseEvent.BUTTON3) {
      return ButtonPressed.RIGHT;
    }
    return ButtonPressed.OTHER;
  }
}
