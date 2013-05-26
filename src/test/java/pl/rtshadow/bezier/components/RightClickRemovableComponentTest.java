package pl.rtshadow.bezier.components;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.rtshadow.bezier.events.MouseAction;
import pl.rtshadow.bezier.events.MouseActionListener;
import pl.rtshadow.bezier.listeners.RemovalListener;

@RunWith(MockitoJUnitRunner.class)
public class RightClickRemovableComponentTest {
  @Mock
  private RemovalListener removalListener;
  @Mock
  private MouseOperableComponent mouseOperableComponent;

  private RightClickRemovableComponent removableComponent;
  private List<MouseActionListener> registeredListeners;

  @Before
  public void setup() {
    removableComponent = new RightClickRemovableComponent(mouseOperableComponent);

    registeredListeners = retrieveRegisteredListeners();
  }

  @Test
  public void notifiesListenersOnRightClick() {
     removableComponent.addRemovalListener(removalListener);

     notifyAll(new MouseAction(new Coordinates(0, 0), MouseAction.ButtonPressed.RIGHT));

     verify(removalListener).onRemoval(any());
  }

  @Test
  public void doesNothingForLeftClick() {
    removableComponent.addRemovalListener(removalListener);

    notifyAll(new MouseAction(new Coordinates(0, 0), MouseAction.ButtonPressed.LEFT));

    verify(removalListener, never()).onRemoval(any());
  }

  private List<MouseActionListener> retrieveRegisteredListeners() {
    ArgumentCaptor<MouseActionListener> captor = ArgumentCaptor.forClass(MouseActionListener.class);

    verify(mouseOperableComponent).addMousePressedListener(captor.capture());
    return captor.getAllValues();
  }

  private void notifyAll(MouseAction mouseAction) {
    for(MouseActionListener listener : registeredListeners) {
      listener.action(mouseAction);
    }
  }
}
