/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.bridge.events.swing;

import java.awt.event.MouseEvent;

import pl.rtshadow.bezier.bridge.events.MouseActionData;
import pl.rtshadow.bezier.util.Coordinate;

public class SwingMouseActionData extends MouseActionData {
  public SwingMouseActionData(MouseEvent event) {
    super(new Coordinate(event.getPoint()), retrievePressedButton(event.getButton()));
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
