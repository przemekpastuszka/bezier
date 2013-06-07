package pl.rtshadow.bezier.curve.transformations;

import static com.google.common.collect.Lists.newArrayListWithCapacity;

import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.SingularValueDecomposition;

import pl.rtshadow.bezier.components.Coordinates;

public class LeastSquaresReduction implements BezierTransformation {
  @Override
  public List<Coordinates> apply(List<Coordinates> controlPoints) {
    // M^T * M * Bnew = M^T * Bold

    int n = controlPoints.size() - 1;
    RealMatrix M = prepareMMatrix(n);
    DecompositionSolver solver = new SingularValueDecomposition(M).getSolver();

    RealVector targetXs = solver.solve(getXs(controlPoints));
    RealVector targetYs = solver.solve(getYs(controlPoints));

    List<Coordinates> newControlPoints = newArrayListWithCapacity(n);
    for (int i = 0; i < n; ++i) {
      newControlPoints.add(new Coordinates(targetXs.getEntry(i), targetYs.getEntry(i)));
    }

    return newControlPoints;
  }

  private RealVector getXs(List<Coordinates> coordinates) {
    double[] xs = new double[coordinates.size()];
    int i = 0;
    for (Coordinates coordinate : coordinates) {
      xs[i++] = coordinate.getX();
    }

    return new ArrayRealVector(xs);
  }

  private RealVector getYs(List<Coordinates> coordinates) {
    double[] xs = new double[coordinates.size()];
    int i = 0;
    for (Coordinates coordinate : coordinates) {
      xs[i++] = coordinate.getY();
    }

    return new ArrayRealVector(xs);
  }

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
