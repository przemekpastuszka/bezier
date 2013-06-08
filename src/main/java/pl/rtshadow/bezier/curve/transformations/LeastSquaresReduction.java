/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.curve.transformations;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayListWithCapacity;

import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.SingularValueDecomposition;

import pl.rtshadow.bezier.util.BoundedIterable;
import pl.rtshadow.bezier.util.Coordinate;

/**
 * Performs degree reduction of Bezier curve using matrix representation of
 * inversion of degree elevation process.
 * 
 * Elevation to higher degree curve with B coeffs from lower degree curve
 * with B' coeffs can be represented as linear algebra equation:
 * M * B' = B
 * In case of degree reduction B' is unknown variable
 * 
 * This is most expensive of implemented methods (in terms of big O notation), but most accurate
 */
public class LeastSquaresReduction implements BezierTransformation {

  /**
   * Uses SVD decomposition of M matrix to perform the computations
   */
  @Override
  public List<Coordinate> apply(BoundedIterable<Coordinate> controlPoints) {
    checkArgument(controlPoints.getSize() > 2);

    int n = controlPoints.getSize() - 1;

    RealMatrix M = prepareMMatrix(n);
    DecompositionSolver solver = new SingularValueDecomposition(M).getSolver();
    RealVector targetXs = solver.solve(getXs(controlPoints));
    RealVector targetYs = solver.solve(getYs(controlPoints));

    return join(targetXs, targetYs, n);
  }

  private List<Coordinate> join(RealVector targetXs, RealVector targetYs, int n) {
    List<Coordinate> newControlPoints = newArrayListWithCapacity(n);
    for (int i = 0; i < n; ++i) {
      newControlPoints.add(new Coordinate(targetXs.getEntry(i), targetYs.getEntry(i)));
    }
    return newControlPoints;
  }

  private RealVector getXs(BoundedIterable<Coordinate> coordinates) {
    double[] xs = new double[coordinates.getSize()];
    int i = 0;
    for (Coordinate coordinate : coordinates) {
      xs[i++] = coordinate.getX();
    }

    return new ArrayRealVector(xs);
  }

  private RealVector getYs(BoundedIterable<Coordinate> coordinates) {
    double[] xs = new double[coordinates.getSize()];
    int i = 0;
    for (Coordinate coordinate : coordinates) {
      xs[i++] = coordinate.getY();
    }

    return new ArrayRealVector(xs);
  }

  /**
   * This is a matrix representation of the equation described in javadoc
   * to {@link DegreeElevationInversion}.
   */
  private RealMatrix prepareMMatrix(int n) {
    RealMatrix M = new Array2DRowRealMatrix(n + 1, n);

    M.setEntry(0, 0, 1);
    for (int i = 1; i < n; ++i) {
      double alpha = ((double) i) / n;
      M.setEntry(i, i - 1, alpha);
      M.setEntry(i, i, 1 - alpha);
    }
    M.setEntry(n, n - 1, 1);

    return M;
  }
}
