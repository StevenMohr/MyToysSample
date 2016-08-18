package de.smartasapps.mytoystask.overview.view;

import android.app.Activity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.tools.ant.Main;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import de.smartasapps.mytoystask.BuildConfig;
import de.smartasapps.mytoystask.network.NavigationEntry;
import de.smartasapps.mytoystask.overview.presenter.OverviewPresenter;
import de.smartasapps.mytoystask.overview.view.adapter.NavigationDrawerAdapter;
import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    DrawerLayout drawerLayoutMock;

    @Mock
    RecyclerView recyclerViewMock;

    @Mock
    WebView webViewMock;

    @Mock
    OverviewPresenter overviewPresenterMock;

    @Mock
    TextView drawerHeaderMock;

    @Mock
    ImageView backButtonMock;

    MainActivity mainActivityUnderTest;

    @Before
    public void setUp() throws Exception {
        mainActivityUnderTest = new MainActivity();
        mainActivityUnderTest.drawerLayout = drawerLayoutMock;
        mainActivityUnderTest.setPresenter(overviewPresenterMock);
        mainActivityUnderTest.recyclerView = recyclerViewMock;
        mainActivityUnderTest.webView = webViewMock;
        mainActivityUnderTest.categoryText = drawerHeaderMock;
        mainActivityUnderTest.upNavigationButton = backButtonMock;
    }

    @Test
    public void openDrawer() throws Exception {
        mainActivityUnderTest.openDrawer();

        verify(drawerLayoutMock).openDrawer(GravityCompat.START);
    }

    @Test
    public void closeDrawer() throws Exception {
        mainActivityUnderTest.closeDrawer();

        verify(drawerLayoutMock).closeDrawer(GravityCompat.START);
    }

    @Test
    public void setElementsForDrawer() throws Exception {
        mainActivityUnderTest.setElementsForDrawer(new ArrayList<NavigationEntry>());

        verify(recyclerViewMock).swapAdapter(any(NavigationDrawerAdapter.class), eq(true));
    }

    @Test
    public void setShownUrl() throws Exception {
        String url = "http://example.com";
        mainActivityUnderTest.setShownUrl(url);

        verify(webViewMock).loadUrl(url);
    }

    @Test
    public void setDrawerHeader() throws Exception {
        final String testLabel = "Label";
        mainActivityUnderTest.setDrawerHeader(testLabel);

        verify(drawerHeaderMock).setText(testLabel);
    }

    @Test
    public void setDrawerUpNavigationVisible_Visible() throws Exception {
        mainActivityUnderTest.setDrawerUpNavigationVisible(true);

        verify(backButtonMock).setVisibility(View.VISIBLE);
    }

    @Test
    public void setDrawerUpNavigationVisible_Invisible() throws Exception {
        mainActivityUnderTest.setDrawerUpNavigationVisible(false);

        verify(backButtonMock).setVisibility(View.INVISIBLE);
    }

    @Test
    public void bindObservable() throws Exception {
        Observable<Void> observable = Observable.never();
        assertThat(mainActivityUnderTest.bindObservable(observable)).isNotNull();
    }

    @Test
    public void itemClicked() throws Exception {
        NavigationEntry navigationEntry = new NavigationEntry();
        mainActivityUnderTest.itemClicked(navigationEntry);

        verify(overviewPresenterMock).itemClicked(navigationEntry);
    }

    @Test
    public void clearButtonClicked() throws Exception {
        mainActivityUnderTest.clearButtonClicked();

        verify(overviewPresenterMock).closeDrawerClicked();
    }

    @Test
    public void backClicked() throws Exception {
        mainActivityUnderTest.backClicked();

        verify(overviewPresenterMock).drawerNavigationUpPressed();
    }

    @Test
    public void testOnCreate() throws Exception {
        Activity activity = Robolectric.buildActivity(MainActivity.class).create().start().
                resume().visible().get();
        assertThat(activity).isNotNull();
    }

}