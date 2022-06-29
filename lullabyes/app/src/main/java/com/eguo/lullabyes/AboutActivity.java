package com.eguo.lullabyes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {
TextView versionApp;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        versionApp = (TextView) findViewById(R.id.text_version_app);
        versionApp.setText("Version:  " + BuildConfig.VERSION_NAME);
    }
}
