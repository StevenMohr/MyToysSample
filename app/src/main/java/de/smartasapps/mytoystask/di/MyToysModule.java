package de.smartasapps.mytoystask.di;

import dagger.Module;
import dagger.Provides;
import de.smartasapps.mytoystask.overview.presenter.OverviewPresenter;
import de.smartasapps.mytoystask.overview.presenter.OverviewPresenterImpl;

@Module
public class MyToysModule {

    @Provides
    OverviewPresenter provideOverviewPresenter(OverviewPresenterImpl impl) {
        return impl;
    }
}
