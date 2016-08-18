package de.smartasapps.mytoystask;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.espresso.web.assertion.WebViewAssertions;
import android.support.test.espresso.web.model.Atoms;
import android.support.test.espresso.web.sugar.Web;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.smartasapps.mytoystask.di.DIProvider;
import de.smartasapps.mytoystask.di.DaggerTestComponent;
import de.smartasapps.mytoystask.overview.view.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainTest {

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<MainActivity>(MainActivity.class) {
        @Override
        protected void beforeActivityLaunched() {
            DIProvider.setGraph(DaggerTestComponent.create());
            super.beforeActivityLaunched();
        }
    };

    @Test
    public void testDrawer() {
        onView(withId(R.id.drawer_layout)).perform(open());
        onView(withText("Kategorien")).perform(click());
        onView(withText("Mode & Schuhe")).perform(click());
        onView(withText("Bademode")).check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testDrawer_BackNavigation() {
        onView(withId(R.id.drawer_layout)).perform(open());
        onView(withText("Kategorien")).perform(click());
        onView(withText("Mode & Schuhe")).perform(click());
        onView(withText("Bademode")).check(matches(ViewMatchers.isDisplayed()));

        onView(withId(R.id.backCarret)).perform(click());
        onView(withText("Mode & Schuhe")).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.backCarret)).perform(click());
        onView(withText("Sortiment")).check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testDrawer_CloseButton() {
        onView(withId(R.id.drawer_layout)).perform(open());
        onView(withId(R.id.clearButton)).perform(click());
        onView(withId(R.id.drawerRecyclerView)).check(matches(not(isDisplayed())));
    }

    @Test
    public void testDrawer_ClickLink() {
        onView(withId(R.id.drawer_layout)).perform(open());
        onView(withText("Alle Angebote im Überblick")).perform(click());
        Web.onWebView().check(WebViewAssertions.webMatches(Atoms.getCurrentUrl(), containsString("www.mytoys.de/sale/")));
    }

}
