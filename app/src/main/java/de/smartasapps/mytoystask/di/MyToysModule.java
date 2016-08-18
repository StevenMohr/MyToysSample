package de.smartasapps.mytoystask.di;

import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.smartasapps.mytoystask.BuildConfig;
import de.smartasapps.mytoystask.network.MyToysApi;
import de.smartasapps.mytoystask.network.NavigationEntry;
import de.smartasapps.mytoystask.network.NavigationEntryConverter;
import de.smartasapps.mytoystask.network.NavigationEntryType;
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
    Gson provideGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(NavigationEntryType.class, new NavigationEntryConverter());
        return builder.create();
    }


}
