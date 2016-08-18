package de.smartasapps.mytoystask.di;

import android.os.Build;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.smartasapps.mytoystask.BuildConfig;
import de.smartasapps.mytoystask.network.MyToysApi;
import de.smartasapps.mytoystask.overview.presenter.OverviewPresenter;
import de.smartasapps.mytoystask.overview.presenter.OverviewPresenterImpl;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class MyToysModule {

    @Singleton
    @Provides
    OverviewPresenter provideOverviewPresenter(OverviewPresenterImpl impl) {
        return impl;
    }

    @Singleton
    @Provides
    MyToysApi provideMyToysApi() {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BuildConfig.BASE_URL).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        return retrofit.create(MyToysApi.class);

    }
}
