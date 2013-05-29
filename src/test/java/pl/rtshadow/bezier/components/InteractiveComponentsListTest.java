package pl.rtshadow.bezier.components;

import static com.google.common.collect.HashMultimap.create;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
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
import pl.rtshadow.bezier.components.listeners.ComponentActionListener;

@RunWith(MockitoJUnitRunner.class)
public class InteractiveComponentsListTest {
  @Mock
  private ComponentFactory componentFactory;
  @Mock
  private InteractiveComponent interactiveComponent;
  @InjectMocks
  private InteractiveComponentsList componentsList;

  private Collection<ComponentFactory.ComponentCreationListener> componentCreationListeners;
  private Multimap<ComponentAction, ComponentActionListener> componentActionListeners = create();

  @Before
  public void setup() {
    componentCreationListeners = retrieveComponentCreationListeners();
  }

  @Test
  public void isEmptyWhenNoCreationActions() {
    assertThat(componentsList).isEmpty();
  }

  @Test
  public void addsItemOnCreation() {
    createObject(interactiveComponent);

    assertThat(componentsList).containsOnly(interactiveComponent);
  }

  @Test
  public void removesItemOnRemoval() {
    createObject(interactiveComponent);
    retrieveComponentActionListeners(REMOVED);

    notifyComponentActionListeners(REMOVED);

    assertThat(componentsList).isEmpty();
  }

  private void notifyComponentActionListeners(ComponentAction action) {
    for (ComponentActionListener listener : componentActionListeners.get(action)) {
      listener.onComponentAction(action);
    }
  }

  private void createObject(InteractiveComponent component) {
    for (ComponentFactory.ComponentCreationListener listener : componentCreationListeners) {
      listener.onCreation(component);
    }
  }

  private List<ComponentFactory.ComponentCreationListener> retrieveComponentCreationListeners() {
    ArgumentCaptor<ComponentFactory.ComponentCreationListener> captor = ArgumentCaptor.forClass(ComponentFactory.ComponentCreationListener.class);
    verify(componentFactory, atLeastOnce()).addComponentCreationListener(captor.capture());
    return captor.getAllValues();
  }

  private void retrieveComponentActionListeners(ComponentAction action) {
    ArgumentCaptor<ComponentActionListener> captor = ArgumentCaptor.forClass(ComponentActionListener.class);

    verify(interactiveComponent).addListener(eq(action), captor.capture());
    componentActionListeners.putAll(action, captor.getAllValues());
  }
}
