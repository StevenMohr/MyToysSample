package de.smartasapps.mytoystask.overview.presenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import de.smartasapps.mytoystask.BuildConfig;
import de.smartasapps.mytoystask.network.NavigationEntry;
import de.smartasapps.mytoystask.network.NavigationEntryType;
import de.smartasapps.mytoystask.overview.model.NavigationEntryManager;
import de.smartasapps.mytoystask.overview.view.OverviewView;
import rx.Observable;
import rx.Subscription;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OverviewPresenterImplTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    OverviewView viewMock;

    @Mock
    NavigationEntryManager navigationEntryManagerMock;

    @Mock
    Subscription subscriptionMock;

    private OverviewPresenterImpl presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new OverviewPresenterImpl();
        presenter.entryManager = navigationEntryManagerMock;
        presenter.attachView(viewMock);

        when(viewMock.bindObservable(any(Observable.class))).thenAnswer(new Answer<Observable>() {
            @Override
            public Observable answer(InvocationOnMock invocation) throws Throwable {
                return invocation.getArgument(0);
            }
        });
    }

    @Test
    public void loadData_WithSection() throws Exception {
        List<NavigationEntry> rootList = new ArrayList<>();
        rootList.add(new NavigationEntry());
        NavigationEntry sectionEntry = new NavigationEntry();
        sectionEntry.type = NavigationEntryType.SECTION;
        sectionEntry.label = "label";
        sectionEntry.children = new ArrayList<>();
        sectionEntry.children.add(new NavigationEntry());
        rootList.add(sectionEntry);

        when(navigationEntryManagerMock.getNavigationEntries()).
                thenReturn(Observable.just(rootList));

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                List<NavigationEntry> list = invocation.getArgument(0);
                assertThat(list).hasSize(3);
                assertThat(list.get(1).type).isEqualTo(NavigationEntryType.SECTION);
                assertThat(list.get(1).label).isEqualTo("label");
                assertThat(list.get(1).children).isNull();
                return null;
            }
        }).when(viewMock).setElementsForDrawer(anyListOf(NavigationEntry.class));

        presenter.loadData();

        verify(navigationEntryManagerMock, atLeastOnce()).getNavigationEntries();
        assertThat(presenter.loadDataSub).isNotNull();
        verify(viewMock).setElementsForDrawer(anyListOf(NavigationEntry.class));
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
        when(navigationEntryManagerMock.getNavigationEntries()).
                thenReturn(Observable.<List<NavigationEntry>>just(new ArrayList<NavigationEntry>()));

        presenter.viewInitialized();

        verify(viewMock).setShownUrl(BuildConfig.HOMEPAGE);
        verify(navigationEntryManagerMock, atLeastOnce()).getNavigationEntries();
    }

    @Test
    public void drawerNavigationUpPressed_2ndLevel() throws Exception {

        NavigationEntry testEntry = new NavigationEntry();
        testEntry.children = new ArrayList<>();

        final String testLabel = "Test Label";
        testEntry.label = testLabel;

        // First one is the one we clicked on at the beginning ( = root level)
        presenter.parents.add(new NavigationEntry());

        presenter.parents.add(testEntry);

        //Last one is the element we're currently seeing
        presenter.parents.add(new NavigationEntry());

        presenter.drawerNavigationUpPressed();

        verify(viewMock).setDrawerHeader(testLabel);
        verify(viewMock).setElementsForDrawer(anyListOf(NavigationEntry.class));
    }

    @Test
    public void drawerNavigationUpPressed_1stLevel() throws Exception {

        List<NavigationEntry> rootLevel = new ArrayList<>();
        rootLevel.add(new NavigationEntry());
        rootLevel.add(new NavigationEntry());

        presenter.rootLevel = rootLevel;


        presenter.parents.add(new NavigationEntry());

        presenter.drawerNavigationUpPressed();

        verify(viewMock).setDrawerHeader("");
        verify(viewMock).setElementsForDrawer(rootLevel);
    }

    @Test
    public void detachView() throws Exception {
        presenter.loadDataSub = subscriptionMock;

        presenter.detachView(false);
        verify(subscriptionMock).unsubscribe();
    }

}