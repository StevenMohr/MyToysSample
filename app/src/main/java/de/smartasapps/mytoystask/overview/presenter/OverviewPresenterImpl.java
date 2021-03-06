package de.smartasapps.mytoystask.overview.presenter;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import de.smartasapps.mytoystask.BuildConfig;
import de.smartasapps.mytoystask.network.NavigationEntry;
import de.smartasapps.mytoystask.network.NavigationEntryType;
import de.smartasapps.mytoystask.overview.model.NavigationEntryManager;
import de.smartasapps.mytoystask.overview.view.OverviewView;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

public class OverviewPresenterImpl extends MvpBasePresenter<OverviewView> implements OverviewPresenter {

    private boolean drawerOpened = false;

    Subscription loadDataSub;  //Would be replaced with RxLifecycle in a production app

    List<NavigationEntry> parents = new ArrayList<>(3);
    List<NavigationEntry> rootLevel;

    @Inject
    NavigationEntryManager entryManager;

    @Inject
    OverviewPresenterImpl() {
    }

    @Override
    public void viewInitialized() {
        if (isViewAttached()) {
            getView().setShownUrl(BuildConfig.HOMEPAGE);
            loadData();
        }
    }


    @Override
    public void loadData() {
        if (!isViewAttached()) {
            return;
        }
        loadDataSub = getView().bindObservable(entryManager.getNavigationEntries().
                flatMap(new Func1<List<NavigationEntry>, Observable<List<NavigationEntry>>>() {
                    @Override
                    public Observable<List<NavigationEntry>> call(List<NavigationEntry> navigationEntries) {
                        List<NavigationEntry> returnList = new ArrayList<>();
                        for (NavigationEntry entry : navigationEntries) {
                            returnList.add(entry);
                            if  (NavigationEntryType.SECTION.equals(entry.type)) {
                                returnList.addAll(entry.children);
                                entry.children = null;
                            }
                        }
                        return Observable.just(returnList);
                    }
                })).
                subscribe(new Action1<List<NavigationEntry>>() {
                    @Override
                    public void call(List<NavigationEntry> navigationEntries) {
                        if (isViewAttached()) {
                            rootLevel = navigationEntries;
                            getView().setElementsForDrawer(navigationEntries);
                            getView().setDrawerUpNavigationVisible(false);
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
        if (!isViewAttached()) {
            return;
        }
        if (entry.type == NavigationEntryType.NODE) {
            getView().setElementsForDrawer(entry.children);
            getView().setDrawerHeader(entry.label);
            getView().setDrawerUpNavigationVisible(true);
            parents.add(entry);
        } else {
            getView().setShownUrl(entry.url);
            getView().closeDrawer();
        }
    }

    @Override
    public void drawerNavigationUpPressed() {
        if (!isViewAttached()) {
            return;
        }
        if (parents.size() > 1) {
            parents.remove(parents.size() - 1);
            NavigationEntry parent = parents.remove(parents.size() - 1);
            getView().setDrawerHeader(parent.label);
            getView().setElementsForDrawer(parent.children);
        } else {
            parents.clear();
            getView().setDrawerUpNavigationVisible(false);
            getView().setDrawerHeader("");
            getView().setElementsForDrawer(rootLevel);
        }
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (loadDataSub != null) {
            loadDataSub.unsubscribe();
        }
    }
}
