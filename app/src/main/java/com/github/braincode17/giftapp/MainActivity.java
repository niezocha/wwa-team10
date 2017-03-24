package com.github.braincode17.giftapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.stormpath.sdk.Stormpath;
import com.stormpath.sdk.StormpathConfiguration;
import com.stormpath.sdk.StormpathLogger;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    private static String baseUrl = " https://ssl.allegro.pl/auth/oauth/token ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (BuildConfig.DEBUG) {
            Stormpath.setLogLevel(StormpathLogger.VERBOSE);
        }
        StormpathConfiguration stormpathConfiguration = new StormpathConfiguration.Builder().baseUrl(baseUrl).build();
        Stormpath.init(this, stormpathConfiguration);




    }
}
