/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve.draw;

import static java.util.Collections.EMPTY_LIST;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.when;
import static pl.rtshadow.bezier.util.Coordinates.areNeighbours;
import static pl.rtshadow.bezier.util.Coordinates.getDistance;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import pl.rtshadow.bezier.curve.evaluation.BezierEvaluationAlgorithm;
import pl.rtshadow.bezier.util.Coordinate;

@RunWith(MockitoJUnitRunner.class)
public class DensePixelEvaluatorTest {
  private static final int SCALE_FACTOR = 500;
  private static final double DELTA = 0.001;

  @Mock
  BezierEvaluationAlgorithm evaluationAlgorithm;

  PixelsEvaluator pixelsEvaluator = new DensePixelEvaluator();

  @Before
  public void setup() {
    when(evaluationAlgorithm.evaluatePoint(any(Iterable.class), anyDouble())).then(new Answer<Coordinate>() {
      @Override
      public Coordinate answer(InvocationOnMock invocation) throws Throwable {
        double t = (double) invocation.getArguments()[1];

        return new Coordinate(SCALE_FACTOR * t, SCALE_FACTOR * t);
      }
    });
  }

  @Test
  public void doesNotGenerateGapBetweenPixels() {
    Collection<Coordinate> pixelLocations = pixelsEvaluator.computePixelPositionsFrom(EMPTY_LIST, evaluationAlgorithm);

    for(Coordinate location : pixelLocations) {
      assertThat(countNeighbours(location, pixelLocations)).isPositive();
    }
  }

  @Test
  public void containsFirstAndLastPoints() {
    Collection<Coordinate> pixelLocations = pixelsEvaluator.computePixelPositionsFrom(EMPTY_LIST, evaluationAlgorithm);

    assertThat(containsWithDelta(pixelLocations, new Coordinate(0, 0)));
    assertThat(containsWithDelta(pixelLocations, new Coordinate(SCALE_FACTOR, SCALE_FACTOR)));
  }

  private int countNeighbours(Coordinate location, Collection<Coordinate> pixelLocations) {
    int neighbours = 0;

    for(Coordinate neighbourCandidate : pixelLocations) {
      if(areDifferent(location, neighbourCandidate) && areNeighbours(location, neighbourCandidate)) {
         ++neighbours;
      }
    }

    return neighbours;
  }

  private boolean areDifferent(Coordinate a, Coordinate b) {
    return !a.equals(b);
  }

  private boolean containsWithDelta(Collection<Coordinate> pixelLocations, Coordinate location) {
     for(Coordinate candidate : pixelLocations) {
        if(getDistance(location, candidate) <= DELTA) {
          return true;
        }
     }
    return false;
  }
}
