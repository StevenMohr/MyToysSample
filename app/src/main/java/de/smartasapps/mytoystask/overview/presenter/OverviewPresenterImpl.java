package de.smartasapps.mytoystask.overview.presenter;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import de.smartasapps.mytoystask.network.NavigationEntry;
import de.smartasapps.mytoystask.overview.view.OverviewView;

public class OverviewPresenterImpl extends MvpBasePresenter<OverviewView> implements OverviewPresenter {
    @Override
    public void loadData() {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void hamburgerClicked() {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void closeDrawerClicked() {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void itemClicked(NavigationEntry entry) {
        throw new IllegalStateException("Not implemented");
    }
}
