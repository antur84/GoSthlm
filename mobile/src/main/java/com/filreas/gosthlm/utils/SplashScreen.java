package com.filreas.gosthlm.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.filreas.gosthlm.R;
import com.filreas.gosthlm.activities.Main.MobileMainActivity;

public class SplashScreen extends Activity {

    protected final int _splashTime = 1500; // time to display the splash screen in ms


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while ((waited < _splashTime)) {
                        sleep(100);
                        waited += 100;
                    }
                } catch (Exception ignored) {

                } finally {

                    startActivity(new Intent(SplashScreen.this,
                            MobileMainActivity.class));
                    finish();
                }
            }
        };
        splashTread.start();
    }
}