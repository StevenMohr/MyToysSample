package de.smartasapps.mytoystask.overview.model;

import java.util.List;

import javax.inject.Inject;

import de.smartasapps.mytoystask.network.NavigationEntry;
import rx.Observable;

public class NavigationEntryManager {

    @Inject
    NavigationEntryManager() { }

    public Observable<List<NavigationEntry>> getNavigationEntries() {
        return Observable.never();
    };
}
