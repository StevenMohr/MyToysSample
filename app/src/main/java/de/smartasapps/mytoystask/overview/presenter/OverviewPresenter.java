package de.smartasapps.mytoystask.overview.presenter;

import de.smartasapps.mytoystask.network.NavigationEntry;

public interface OverviewPresenter {
    void loadData();
    void hamburgerClicked();
    void closeDrawerClicked();
    void itemClicked(NavigationEntry entry);
}
