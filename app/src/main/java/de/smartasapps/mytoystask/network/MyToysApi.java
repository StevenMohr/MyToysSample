package de.smartasapps.mytoystask.network;


import de.smartasapps.mytoystask.BuildConfig;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import rx.Observable;

public interface MyToysApi {

    @Headers(BuildConfig.AUTH_HEADER)
    @GET("navigation")
    Observable<NavigationEntryResponse> getNavigationEntries();

}
