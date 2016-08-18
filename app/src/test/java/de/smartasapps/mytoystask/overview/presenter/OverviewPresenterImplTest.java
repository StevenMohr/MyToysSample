package de.smartasapps.mytoystask.overview.presenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import de.smartasapps.mytoystask.network.NavigationEntry;
import de.smartasapps.mytoystask.overview.model.NavigationEntryManager;
import de.smartasapps.mytoystask.overview.view.OverviewView;
import rx.Observable;

import static org.assertj.core.api.Fail.fail;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OverviewPresenterImplTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    OverviewView viewMock;

    @Mock
    NavigationEntryManager navigationEntryManagerMock;

    private OverviewPresenterImpl presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new OverviewPresenterImpl();
        presenter.entryManager = navigationEntryManagerMock;
        when(navigationEntryManagerMock.getNavigationEntries()).
                thenReturn(Observable.<List<NavigationEntry>>just(new ArrayList<NavigationEntry>()));
        presenter.attachView(viewMock);
    }

    @Test
    public void loadData() throws Exception {
        presenter.loadData();

        verify(navigationEntryManagerMock, Mockito.atLeastOnce()).getNavigationEntries();
        verify(viewMock, atLeastOnce()).setElementsForDrawer(anyListOf(NavigationEntry.class));
    }

    @Test
    public void hamburgerClicked() throws Exception {
        presenter.hamburgerClicked();
        verify(viewMock).openDrawer();

        presenter.hamburgerClicked();
        verify(viewMock).closeDrawer();
    }

    @Test
    public void closeDrawerClicked() throws Exception {
        presenter.closeDrawerClicked();

        verify(viewMock).closeDrawer();
    }

    @Test
    public void itemClicked() throws Exception {

        fail("Not implemented");
    }

}