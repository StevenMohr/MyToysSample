package de.smartasapps.mytoystask.overview.presenter;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import javax.inject.Inject;

import de.smartasapps.mytoystask.network.NavigationEntry;
import de.smartasapps.mytoystask.overview.model.NavigationEntryManager;
import de.smartasapps.mytoystask.overview.view.OverviewView;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class OverviewPresenterImpl extends MvpBasePresenter<OverviewView> implements OverviewPresenter {

    private boolean drawerOpened = false;

    private Subscription loadDataSub;

    @Inject
    NavigationEntryManager entryManager;

    @Inject
    OverviewPresenterImpl() {}

    @Override
    public void loadData() {
        loadDataSub = entryManager.getNavigationEntries().subscribe(new Action1<List<NavigationEntry>>() {
            @Override
            public void call(List<NavigationEntry> navigationEntries) {
                if (isViewAttached()) {
                    getView().setElementsForDrawer(navigationEntries);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    @Override
    public void attachView(OverviewView view) {
        super.attachView(view);
        loadData();
    }

    @Override
    public void hamburgerClicked() {
        if (!isViewAttached()) {
            return;
        }
        if (drawerOpened) {
            getView().closeDrawer();
            drawerOpened = false;
        } else {
            getView().openDrawer();
            drawerOpened = true;
        }
    }

    @Override
    public void closeDrawerClicked() {
        if (!isViewAttached()) {
            return;
        }
        getView().closeDrawer();
        drawerOpened = false;
    }

    @Override
    public void itemClicked(NavigationEntry entry) {
        throw new IllegalStateException("Not implemented");
    }
}
