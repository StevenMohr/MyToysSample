package de.smartasapps.mytoystask.overview.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import de.smartasapps.mytoystask.di.DIProvider;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DIProvider.getInstance().inject(this);
    }
}
