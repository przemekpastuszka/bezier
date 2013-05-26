package pl.rtshadow.bezier.components;

import java.awt.*;

public class Coordinates {
  private final int x;
  private final int y;

  public Coordinates(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Coordinates(Point point) {
    this(point.x, point.y);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

}
