package de.smartasapps.mytoystask.overview.presenter;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;

import de.smartasapps.mytoystask.network.NavigationEntry;
import de.smartasapps.mytoystask.overview.view.OverviewView;

public interface OverviewPresenter extends MvpPresenter<OverviewView> {
    void viewInitialized();
    void loadData();
    void hamburgerClicked();
    void closeDrawerClicked();
    void itemClicked(NavigationEntry entry);
}
