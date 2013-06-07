package pl.rtshadow.bezier.components.factory;

import pl.rtshadow.bezier.components.Coordinates;
import pl.rtshadow.bezier.components.InteractiveComponent;

public interface ComponentFactory {
    InteractiveComponent createFromPosition(Coordinates coordinates);
}
