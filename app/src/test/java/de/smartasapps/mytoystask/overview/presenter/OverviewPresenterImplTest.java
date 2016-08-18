package de.smartasapps.mytoystask.overview.presenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import de.smartasapps.mytoystask.BuildConfig;
import de.smartasapps.mytoystask.network.NavigationEntry;
import de.smartasapps.mytoystask.network.NavigationEntryType;
import de.smartasapps.mytoystask.overview.model.NavigationEntryManager;
import de.smartasapps.mytoystask.overview.view.OverviewView;
import rx.Observable;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
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
        //verify(viewMock, atLeastOnce()).setElementsForDrawer(anyListOf(NavigationEntry.class));
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
    public void itemClicked_Link() throws Exception {
        NavigationEntry testEntry = new NavigationEntry();
        final String testUrl = "http://example.com/";
        testEntry.url = testUrl;
        testEntry.type = NavigationEntryType.LINK;

        presenter.itemClicked(testEntry);

        verify(viewMock).setShownUrl(testUrl);
    }

    @Test
    public void itemClicked_Node() throws Exception {
        NavigationEntry testEntry = new NavigationEntry();
        final String label = "HiFi";
        final List<NavigationEntry> children = new ArrayList<>();
        children.add(new NavigationEntry());
        testEntry.label = label;
        testEntry.children = children;
        testEntry.type = NavigationEntryType.NODE;

        presenter.itemClicked(testEntry);

        verify(viewMock).setElementsForDrawer(children);
        verify(viewMock).setDrawerHeader(label);
    }

    @Test
    public void viewInitialized() {
        presenter.viewInitialized();

        verify(viewMock).setShownUrl(BuildConfig.HOMEPAGE);
        verify(navigationEntryManagerMock, Mockito.atLeastOnce()).getNavigationEntries();
    }

    @Test
    public void drawerNavigationUpPressed() throws Exception {
        presenter.drawerNavigationUpPressed();

        verify(viewMock).setDrawerHeader(anyString());
        verify(viewMock).setElementsForDrawer(anyListOf(NavigationEntry.class));
    }

}