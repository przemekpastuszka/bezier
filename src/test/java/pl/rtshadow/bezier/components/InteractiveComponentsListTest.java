package pl.rtshadow.bezier.components;

import static com.google.common.collect.HashMultimap.create;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.rtshadow.bezier.components.actions.ComponentAction.REMOVED;

import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Multimap;

import pl.rtshadow.bezier.components.actions.ComponentAction;
import pl.rtshadow.bezier.components.factory.ActionBasedComponentFactory;
import pl.rtshadow.bezier.components.listeners.ComponentActionListener;
import pl.rtshadow.bezier.util.Coordinate;

@RunWith(MockitoJUnitRunner.class)
public class InteractiveComponentsListTest {
  private final static Coordinate ZERO_COORDINATE = new Coordinate(0, 0);

  @Mock
  private ActionBasedComponentFactory actionBasedComponentFactory;
  @Mock
  private InteractiveComponent interactiveComponent;
  @InjectMocks
  private InteractiveComponentsList componentsList;

  private Collection<ActionBasedComponentFactory.ComponentCreationListener> componentCreationListeners;
  private Multimap<ComponentAction, ComponentActionListener> componentActionListeners = create();

  @Before
  public void setup() {
    componentCreationListeners = retrieveComponentCreationListeners();

    when(interactiveComponent.getCoordinates()).thenReturn(ZERO_COORDINATE);
  }

  @Test
  public void isEmptyWhenNoCreationActions() {
    assertThat(componentsList).isEmpty();
  }

  @Test
  public void addsItemOnCreation() {
    createObject(interactiveComponent);

    assertThat(componentsList.iterator()).containsOnly(ZERO_COORDINATE);
  }

  @Test
  public void removesItemOnRemoval() {
    createObject(interactiveComponent);
    retrieveComponentActionListeners(REMOVED);

    notifyComponentActionListeners(REMOVED);

    assertThat(componentsList.iterator()).isEmpty();
  }

  private void notifyComponentActionListeners(ComponentAction action) {
    for (ComponentActionListener listener : componentActionListeners.get(action)) {
      listener.onComponentAction(action);
    }
  }

  private void createObject(InteractiveComponent component) {
    for (ActionBasedComponentFactory.ComponentCreationListener listener : componentCreationListeners) {
      listener.onCreation(component);
    }
  }

  private List<ActionBasedComponentFactory.ComponentCreationListener> retrieveComponentCreationListeners() {
    ArgumentCaptor<ActionBasedComponentFactory.ComponentCreationListener> captor = ArgumentCaptor.forClass(ActionBasedComponentFactory.ComponentCreationListener.class);
    verify(actionBasedComponentFactory, atLeastOnce()).addComponentCreationListener(captor.capture());
    return captor.getAllValues();
  }

  private void retrieveComponentActionListeners(ComponentAction action) {
    ArgumentCaptor<ComponentActionListener> captor = ArgumentCaptor.forClass(ComponentActionListener.class);

    verify(interactiveComponent).addListener(eq(action), captor.capture());
    componentActionListeners.putAll(action, captor.getAllValues());
  }
}
