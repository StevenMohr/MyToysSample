package de.smartasapps.mytoystask.di;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.smartasapps.mytoystask.network.MyToysApi;
import de.smartasapps.mytoystask.network.NavigationEntryResponse;
import rx.Observable;

@Singleton
@Module
public class TestModule {
    @Singleton
    @Provides
    MyToysApi provideMyToysApi(final Gson gson) {
        return new MyToysApi() {
            @Override
            public Observable<NavigationEntryResponse> getNavigationEntries() {
                NavigationEntryResponse response = gson.fromJson(getBackendJson(), NavigationEntryResponse.class);
                return Observable.just(response);
            }
        };
    }

    private String getBackendJson() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/response.json");
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            int result = bis.read();
            while (result != -1) {
                buf.write((byte) result);
                result = bis.read();
            }

            return buf.toString();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to parse json", e);
        }
    }
}
