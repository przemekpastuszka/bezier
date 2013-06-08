/*
 *  Copyright Pastuszka Przemyslaw, University of Wroclaw, Poland (c) 2013.
 */

package pl.rtshadow.bezier.components.factory;

import pl.rtshadow.bezier.util.Coordinate;
import pl.rtshadow.bezier.components.InteractiveComponent;

public interface ComponentFactory {
    InteractiveComponent createFromPosition(Coordinate coordinate);
}
