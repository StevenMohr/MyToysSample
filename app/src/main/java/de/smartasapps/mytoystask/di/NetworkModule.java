package de.smartasapps.mytoystask.di;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.smartasapps.mytoystask.BuildConfig;
import de.smartasapps.mytoystask.network.MyToysApi;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
@Module
public class NetworkModule {
    @Singleton
    @Provides
    MyToysApi provideMyToysApi(Gson gson) {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BuildConfig.BASE_URL).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create(gson)).
                build();
        return retrofit.create(MyToysApi.class);
    }

}
