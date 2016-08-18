package de.smartasapps.mytoystask.overview.presenter;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import javax.inject.Inject;

import de.smartasapps.mytoystask.BuildConfig;
import de.smartasapps.mytoystask.network.NavigationEntry;
import de.smartasapps.mytoystask.overview.model.NavigationEntryManager;
import de.smartasapps.mytoystask.overview.view.OverviewView;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class OverviewPresenterImpl extends MvpBasePresenter<OverviewView> implements OverviewPresenter {

    private boolean drawerOpened = false;

    private Subscription loadDataSub;

    @Inject
    NavigationEntryManager entryManager;

    @Inject
    OverviewPresenterImpl() {}

    @Override
    public void viewInitialized() {
        if (isViewAttached()) {
            getView().setShownUrl(BuildConfig.HOMEPAGE);
            loadData();
        }
    }

    @Override
    public void loadData() {
        loadDataSub = entryManager.getNavigationEntries().
                observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.io()).
                subscribe(new Action1<List<NavigationEntry>>() {
            @Override
            public void call(List<NavigationEntry> navigationEntries) {
                if (isViewAttached()) {
                    getView().setElementsForDrawer(navigationEntries);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("MyToysExample", "Error when trying to get navigation elements", throwable);
            }
        });
    }

    @Override
    public void attachView(OverviewView view) {
        super.attachView(view);
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
