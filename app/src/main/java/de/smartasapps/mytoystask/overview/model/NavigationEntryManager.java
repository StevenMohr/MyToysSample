package de.smartasapps.mytoystask.overview.model;

import java.util.List;

import javax.inject.Inject;

import de.smartasapps.mytoystask.network.MyToysApi;
import de.smartasapps.mytoystask.network.NavigationEntry;
import de.smartasapps.mytoystask.network.NavigationEntryResponse;
import rx.Observable;
import rx.functions.Func1;

public class NavigationEntryManager {

    @Inject
    protected MyToysApi myToysApi;

    @Inject
    NavigationEntryManager() { }

    public Observable<List<NavigationEntry>> getNavigationEntries() {
        return myToysApi.getNavigationEntries().flatMap(new Func1<NavigationEntryResponse, Observable<List<NavigationEntry>>>() {
            @Override
            public Observable<List<NavigationEntry>> call(NavigationEntryResponse navigationEntryResponse) {
                return Observable.just(navigationEntryResponse.navigationEntries);
            }
        });
    };
}
