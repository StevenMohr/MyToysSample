package de.smartasapps.mytoystask.overview.model;

import org.assertj.core.api.Assertions.*;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import de.smartasapps.mytoystask.network.MyToysApi;
import de.smartasapps.mytoystask.network.NavigationEntry;
import de.smartasapps.mytoystask.network.NavigationEntryResponse;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;

public class NavigationEntryManagerTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    MyToysApi myToysApi;

    @Test
    public void getNavigationEntries() throws Exception {
        NavigationEntryManager manager = new NavigationEntryManager();
        manager.myToysApi = myToysApi;
        List<NavigationEntry> navigationEntryList = new ArrayList<>();
        navigationEntryList.add(new NavigationEntry());
        navigationEntryList.add(new NavigationEntry());

        NavigationEntryResponse response = new NavigationEntryResponse();
        response.navigationEntries = navigationEntryList;

        Mockito.when(myToysApi.getNavigationEntries()).thenReturn(Observable.just(response));
        TestSubscriber<List<NavigationEntry>> testSubscriber = new TestSubscriber<>();

        manager.getNavigationEntries().subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();

        assertThat(testSubscriber.getOnNextEvents().get(0)).isEqualTo(navigationEntryList);
    }

}