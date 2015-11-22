package com.filreas.gosthlm.activities.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.filreas.gosthlm.R;

public class Splash extends Activity {

    protected boolean _active = true;
    protected int _splashTime = 3000; // time to display the splash screen in ms


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(100);
                        if (_active) {
                            waited += 100;
                        }
                    }
                } catch (Exception e) {

                } finally {

                    startActivity(new Intent(Splash.this,
                            MobileMainActivity.class));
                    finish();
                }
            };
        };
        splashTread.start();
    }
}