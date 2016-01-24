package com.filreas.gosthlm;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.filreas.shared.dto.FavouriteSiteLiveUpdateDto;
import com.filreas.shared.utils.GoSthlmLog;

import java.util.ArrayList;
import java.util.List;

public class WearMainActivity extends WearBaseActivity {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeEventListener mShakeDetector;
    private ViewPager viewPager;
    private StationViewPagerAdapter sitePagerAdapter;

    List<FavouriteSiteLiveUpdateDto> favouriteSites;
    private View touchTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAmbientEnabled();
        initRefreshOnShake();
        initStationsViewPageAdapter();
    }

    private void initStationsViewPageAdapter() {
        favouriteSites = new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.pager);
        sitePagerAdapter = new StationViewPagerAdapter(
                WearMainActivity.this,
                favouriteSites,
                new ISwipeToRefreshEnabler() {
                    @Override
                    public void onSwipeToRefreshEnabled(boolean enable) {
                        getSwipeDownToRefreshLayout().setEnabled(enable);
                    }
                });
        viewPager.setAdapter(sitePagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int previousScrollState = ViewPager.SCROLL_STATE_IDLE;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updatePageIndicator();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (previousScrollState == ViewPager.SCROLL_STATE_IDLE) {
                    if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                        touchTarget = viewPager;
                    }
                } else {
                    if (state == ViewPager.SCROLL_STATE_IDLE || state == ViewPager.SCROLL_STATE_SETTLING) {
                        touchTarget = null;
                    }
                }

                previousScrollState = state;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (touchTarget != null) {
            boolean wasProcessed = touchTarget.onTouchEvent(ev);

            if (!wasProcessed) {
                touchTarget = null;
            }

            if(viewPager.getCurrentItem() == 0){
                return false; // allow for exit app swipe on first page
            }

            return wasProcessed;
        }

        return super.dispatchTouchEvent(ev);
    }

    private void initRefreshOnShake() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeEventListener();
        mShakeDetector.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            @Override
            public void onShake(int count) {
                handleShakeEvent(count);
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.viewpager_main;
    }

    @Override
    protected void updateScreenInfo(final FavouriteSiteLiveUpdateDto updatedSite) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int currentIndex = favouriteSites.indexOf(updatedSite);
                if (currentIndex < 0) {
                    sitePagerAdapter.addItem(updatedSite);
                    updatePageIndicator();
                } else {
                    sitePagerAdapter.updateItem(viewPager, currentIndex, updatedSite);
                }
                hideMainProgressBar();
            }
        });
    }

    private void updatePageIndicator() {
        TextView indicator = (TextView) findViewById(R.id.pagerIndicator);
        String currentPageText = String.format(
                getString(R.string.item_of_total),
                viewPager.getCurrentItem() + 1,
                favouriteSites.size());
        indicator.setText(currentPageText);
    }

    private void handleShakeEvent(int count) {
        GoSthlmLog.d("Detected shake, refreshing view.");
        refreshAllStationsAndDepartures();
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        GoSthlmLog.d("--Enter Ambient--");
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        GoSthlmLog.d("--Update Ambient--");
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onResume() {
        GoSthlmLog.d("--On Resume--");
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        GoSthlmLog.d("--On Paus--");
        mSensorManager.unregisterListener(mShakeDetector);
    }

    @Override
    public void onExitAmbient() {
        GoSthlmLog.d("--Exit Ambient--");
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
        GoSthlmLog.d("--Update Display--");
        if (isAmbient()) {
        }
    }
}