package com.filreas.slwear;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.RelativeLayout;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends WearableActivity {

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    private RelativeLayout mainRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
        if (isAmbient()) {
            mainRelativeLayout.setBackgroundColor(getResources().getColor(android.R.color.black, getTheme()));
        } else {
            mainRelativeLayout.setBackground(null);
        }
    }
}
