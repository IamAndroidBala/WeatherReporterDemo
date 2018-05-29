package com.weather.weatherreporter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.weather.weatherreporter.R;

/**
 * This is startup activity of this app
 * A thread is used to delay the activity for 2 seconds
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    Intent country = new Intent(getApplicationContext(),CountryListActivity.class);
                    startActivity(country);
                    finish();
                }catch (Exception e){

                }
            }
        }).start();

    }
}
