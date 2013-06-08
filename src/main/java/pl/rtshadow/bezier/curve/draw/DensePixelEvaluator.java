/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve.draw;

import static com.google.common.collect.Lists.newArrayList;
import static pl.rtshadow.bezier.util.Coordinates.areNeighbours;

import java.util.Collection;
import java.util.List;

import pl.rtshadow.bezier.curve.evaluation.BezierEvaluationAlgorithm;
import pl.rtshadow.bezier.util.Coordinate;

/**
 * Computes set of discrete points on the Bezier curve in such way, that each two consecutive
 * points are neighbours, i.e. when drawn on the surface, they'll occupy neighbour pixels.
 * This way of discretizing the curve helps avoid ugly "gaps" in the drawn curve.
 */
public class DensePixelEvaluator implements PixelsEvaluator {
  private final static double INITIAL_STEP_VALUE = 0.01;

  /**
   * Algorithm idea:
   * Use 'step' to increase t argument for curve evaluation
   * Each time "gap" is seen (i.e. next computed location is not a neighbour to the previous one)
   * decrease the 'step', and retry.
   * 
   */
  @Override
  public Collection<Coordinate> computePixelPositionsFrom(Iterable<Coordinate> controlPoints, BezierEvaluationAlgorithm evaluationAlgorithm) {
    List<Coordinate> pixelsPositions = newArrayList();

    double step = INITIAL_STEP_VALUE;

    Coordinate previousPosition = evaluationAlgorithm.evaluatePoint(controlPoints, 0);
    pixelsPositions.add(previousPosition);

    for (double t = 0; t <= 1 - step; t += step) {
      while (true) {
        Coordinate nextPositionCandidate = evaluationAlgorithm.evaluatePoint(controlPoints, t + step);
        if (areNeighbours(previousPosition, nextPositionCandidate)) {
          pixelsPositions.add(nextPositionCandidate);
          previousPosition = nextPositionCandidate;
          break;
        }

        step /= 2;
      }

    }
    return pixelsPositions;
  }
}
