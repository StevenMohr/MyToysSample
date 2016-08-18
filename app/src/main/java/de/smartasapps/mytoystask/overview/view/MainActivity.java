package de.smartasapps.mytoystask.overview.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.util.List;

import javax.inject.Inject;

import de.smartasapps.mytoystask.di.DIProvider;
import de.smartasapps.mytoystask.network.NavigationEntry;
import de.smartasapps.mytoystask.overview.presenter.OverviewPresenter;

public class MainActivity extends MvpActivity<OverviewView, OverviewPresenter> implements OverviewView {
    @Inject
    OverviewPresenter overviewPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DIProvider.getInstance().inject(this);
    }

    @NonNull
    @Override
    public OverviewPresenter createPresenter() {
        return overviewPresenter;
    }

    @Override
    public void openDrawer() {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void closeDrawer() {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void setElementsForDrawer(List<NavigationEntry> data) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void setShownUrl(String url) {
        throw new IllegalStateException("Not implemented");
    }
}
