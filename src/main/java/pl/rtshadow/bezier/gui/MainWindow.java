/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.gui;

import static pl.rtshadow.bezier.inject.InjectionConfiguration.getInjector;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import pl.rtshadow.bezier.components.actions.ComponentAction;
import pl.rtshadow.bezier.components.listeners.ComponentActionListener;
import pl.rtshadow.bezier.curve.BezierCurve;
import pl.rtshadow.bezier.curve.BezierCurvesList;
import pl.rtshadow.bezier.curve.factory.BezierCurveFactory;
import pl.rtshadow.bezier.curve.transformations.BezierTransformation;
import pl.rtshadow.bezier.curve.transformations.FarinDegreeReduction;
import pl.rtshadow.bezier.curve.transformations.ForrestDegreeReduction;
import pl.rtshadow.bezier.curve.transformations.LeastSquaresReduction;
import pl.rtshadow.bezier.drawable.Surface;
import pl.rtshadow.bezier.drawable.swing.SwingSurface;

public class MainWindow {
  private JList curveChooser;
  private JComboBox comboBox1;
  private JButton reduceButton;
  private JPanel drawingArea;
  private JPanel mainArea;

  private Surface surface;

  private BezierCurvesList curves = new BezierCurvesList();
  private BezierCurveFactory curveFactory = getInjector().getInstance(BezierCurveFactory.class);

  private Map<String, BezierTransformation> transformationMap;

  public MainWindow() {
    reduceButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        BezierTransformation transformation = transformationMap.get(comboBox1.getSelectedItem());
        performTransformation(transformation);
      }
    });

    curves.add(curveFactory.createCurveFrom(Collections.EMPTY_LIST));

    curves.registerOnChangeListener(new ComponentActionListener() {
      @Override
      public void onComponentAction(ComponentAction action) {
        curves.redrawAll(surface);
      }
    });
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("MainWindow");
    frame.setContentPane(new MainWindow().mainArea);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

  private void performTransformation(BezierTransformation transformation) {
    BezierCurve oldCurve = curves.getActiveCurve();
    BezierCurve newCurve = curveFactory.createCurveFrom(oldCurve.transformation(transformation));

    curves.add(newCurve);
    oldCurve.deactivate();
    curves.setActiveCurve(newCurve);

    curves.redrawAll(surface);
  }

  private void createUIComponents() {
    prepareDrawArea();
    prepareComboBox();
  }

  private void prepareDrawArea() {
    SwingSurface swingSurface = getInjector().getInstance(SwingSurface.class);
    surface = swingSurface;
    drawingArea = swingSurface;

    // need to explicitly disable layout, so control points are not moved after invalidation
    swingSurface.setLayout(null);
  }

  private void prepareComboBox() {
    transformationMap = new HashMap<>();
    transformationMap.put("Farin method", new FarinDegreeReduction());
    transformationMap.put("Forrest method", new ForrestDegreeReduction());
    transformationMap.put("Least squares", new LeastSquaresReduction());

    comboBox1 = new JComboBox(transformationMap.keySet().toArray());
  }
}
