/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.util;

public interface BoundedIterable<T> extends Iterable<T> {
  int getSize();
}
