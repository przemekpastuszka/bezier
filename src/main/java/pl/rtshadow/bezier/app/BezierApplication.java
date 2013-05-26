/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.app;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.LinkedList;

import pl.rtshadow.bezier.points.ControlPoint;
import pl.rtshadow.bezier.points.swing.SwingControlPoint;

public class BezierApplication {
  private static Collection<ControlPoint> controlPoints = new LinkedList<>();

  public static void main(String[] args) {
    JFrame frame = new JFrame("DragButton");
    frame.setLayout(null);
    addControlPoint(frame.getContentPane(), createComponent("1"));
    addControlPoint(frame.getContentPane(), createComponent("2"));
    addControlPoint(frame.getContentPane(), createComponent("3"));
    frame.setSize(300, 300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  private static void addControlPoint(Container contentPane, JComponent component) {
    SwingControlPoint swingControlPoint = new SwingControlPoint(component);
    swingControlPoint.addTo(contentPane);

    controlPoints.add(new SwingControlPoint(component));
  }

  private static JComponent createComponent(String text) {
    return new SomeButton(text);
  }

  private static class SomeButton extends JButton {
    public SomeButton(String text) {
      super(text);

      setDoubleBuffered(false);
      setMargin(new Insets(0, 0, 0, 0));
      setSize(25, 25);
      setPreferredSize(new Dimension(25, 25));
    }
  }
}
