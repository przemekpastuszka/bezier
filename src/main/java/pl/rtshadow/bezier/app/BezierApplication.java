/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.app;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.LinkedList;

import pl.rtshadow.bezier.bridge.components.ExternalMouseDrivenComponent;
import pl.rtshadow.bezier.bridge.components.swing.SwingMouseDrivenComponent;
import pl.rtshadow.bezier.components.InteractiveComponent;
import pl.rtshadow.bezier.components.MouseInteractiveComponent;

public class BezierApplication {
  private static Collection<InteractiveComponent> controlPoints = new LinkedList<>();

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
    ExternalMouseDrivenComponent swingMouseDrivenComponent = new SwingMouseDrivenComponent(component, contentPane);
    controlPoints.add(new MouseInteractiveComponent(swingMouseDrivenComponent));
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
