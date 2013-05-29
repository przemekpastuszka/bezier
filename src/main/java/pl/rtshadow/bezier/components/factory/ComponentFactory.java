package pl.rtshadow.bezier.components.factory;

import pl.rtshadow.bezier.bridge.events.MouseActionData;
import pl.rtshadow.bezier.components.InteractiveComponent;

public interface ComponentFactory {
    InteractiveComponent createFromMouseData(MouseActionData data);
}
