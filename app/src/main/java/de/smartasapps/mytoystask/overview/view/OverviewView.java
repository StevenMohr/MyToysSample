package de.smartasapps.mytoystask.overview.view;

import java.util.List;

import de.smartasapps.mytoystask.network.NavigationEntry;

public interface OverviewView {
    void openDrawer();
    void closeDrawer();
    void setElementsForDrawer(List<NavigationEntry> data);
    void setShownUrl(String url);
}
