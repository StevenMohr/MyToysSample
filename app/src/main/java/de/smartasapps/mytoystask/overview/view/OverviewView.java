package de.smartasapps.mytoystask.overview.view;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import de.smartasapps.mytoystask.network.NavigationEntry;

public interface OverviewView extends MvpView {
    void openDrawer();
    void closeDrawer();
    void setElementsForDrawer(List<NavigationEntry> data);
    void setShownUrl(String url);

    void setDrawerHeader(String headerText);
}
