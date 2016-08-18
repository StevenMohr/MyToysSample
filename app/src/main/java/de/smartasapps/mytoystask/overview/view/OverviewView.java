package de.smartasapps.mytoystask.overview.view;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import de.smartasapps.mytoystask.network.NavigationEntry;
import rx.Observable;

public interface OverviewView extends MvpView {
    void openDrawer();

    void closeDrawer();

    void setElementsForDrawer(List<NavigationEntry> data);

    void setShownUrl(String url);

    void setDrawerHeader(String headerText);

    void setDrawerUpNavigationVisible(boolean visible);

    <T> Observable<T> bindObservable(Observable<T> observable);
}
