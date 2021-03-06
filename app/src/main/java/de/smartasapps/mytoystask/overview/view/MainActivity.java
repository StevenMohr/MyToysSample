package de.smartasapps.mytoystask.overview.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.smartasapps.mytoystask.R;
import de.smartasapps.mytoystask.di.DIProvider;
import de.smartasapps.mytoystask.network.NavigationEntry;
import de.smartasapps.mytoystask.overview.presenter.OverviewPresenter;
import de.smartasapps.mytoystask.overview.view.adapter.NavigationDrawerAdapter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends MvpActivity<OverviewView, OverviewPresenter> implements OverviewView, NavigationDrawerAdapter.ItemClickListener {
    @Inject
    OverviewPresenter overviewPresenter;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.webView)
    WebView webView;

    @BindView(R.id.categoryText)
    TextView categoryText;

    @BindView(R.id.drawerRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.backCarret)
    ImageView upNavigationButton;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        presenter.viewInitialized();
    }

    @NonNull
    @Override
    public OverviewPresenter createPresenter() {
        DIProvider.getInstance().inject(this);
        return overviewPresenter;
    }

    @Override
    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void setElementsForDrawer(List<NavigationEntry> data) {
        recyclerView.swapAdapter(new NavigationDrawerAdapter(data, this), true);
    }

    @Override
    public void setShownUrl(String url) {
        webView.loadUrl(url);
    }

    @Override
    public void setDrawerHeader(String headerText) {
        categoryText.setText(headerText);
    }

    @Override
    public void setDrawerUpNavigationVisible(boolean visible) {
        upNavigationButton.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public <T> Observable<T> bindObservable(final Observable<T> observable) {
        // In a real app this method would also bind the observable to the lifecycle of the Activity
        // in such a way that unsubscribe is called when the activity pauses or stops.
        return observable.compose(new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
            }
        });
    }

    @Override
    public void itemClicked(NavigationEntry entry) {
        presenter.itemClicked(entry);
    }

    @OnClick(R.id.clearButton)
    public void clearButtonClicked() {
        presenter.closeDrawerClicked();
    }

    @OnClick(R.id.backCarret)
    public void backClicked() {
        presenter.drawerNavigationUpPressed();
    }
}
