package com.filreas.gosthlm;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
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
    private TextView pagerIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagerIndicator = (TextView) findViewById(R.id.pagerIndicator);
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

            if (viewPager.getCurrentItem() == 0) {
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
        int currentIndex = favouriteSites.indexOf(updatedSite);
        if (currentIndex < 0) {
            sitePagerAdapter.addItem(updatedSite);
            updatePageIndicator();
        } else {
            sitePagerAdapter.updateItem(viewPager, currentIndex, updatedSite);
        }
        viewPager.setVisibility(View.VISIBLE);
        pagerIndicator.setVisibility(View.VISIBLE);
        hideStartupProgressBarAndCenterText();
    }

    private void updatePageIndicator() {
        String currentPageText = String.format(
                getString(R.string.item_of_total),
                viewPager.getCurrentItem() + 1,
                favouriteSites.size());
        pagerIndicator.setText(currentPageText);
    }

    private void handleShakeEvent(int count) {
        GoSthlmLog.d("Detected (" + count + ") shake.");
        if (count >= 3) {
            mShakeDetector.resetShakeCount();
            refreshAllStationsAndDepartures();
        }
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        GoSthlmLog.d("--Enter Ambient--");
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
        removeShakeListener();
    }

    @Override
    public void onExitAmbient() {
        super.onExitAmbient();
        GoSthlmLog.d("--Exit Ambient--");
        updateDisplay();
        addShakeListener();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        GoSthlmLog.d("--Update Ambient--");
        refreshAllStationsAndDepartures();
    }

    @Override
    public void onResume() {
        super.onResume();
        addShakeListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        removeShakeListener();
    }

    private void updateDisplay() {
        SwipeRefreshLayout swipeDownToRefreshLayout = getSwipeDownToRefreshLayout();

        if (isAmbient()) {
            int black = ContextCompat.getColor(this, R.color.black);
            swipeDownToRefreshLayout.setBackgroundColor(black);
            setViewPagerHeaderColor(black);
            setViewPagerIndicatorVisibility(View.INVISIBLE);
        } else {
            int primaryDark = ContextCompat.getColor(this, R.color.primary_dark);
            int primary = ContextCompat.getColor(this, R.color.primary);
            swipeDownToRefreshLayout.setBackgroundColor(primaryDark);
            setViewPagerHeaderColor(primary);
            setViewPagerIndicatorVisibility(View.VISIBLE);
        }
    }

    private void setViewPagerHeaderColor(int black) {
        View currentView = sitePagerAdapter.getCurrentView();
        if (currentView != null) {
            View viewPagerHeader = currentView.findViewById(R.id.viewPager_header);
            if (viewPagerHeader != null) {
                viewPagerHeader.setBackgroundColor(black);
            }
        }
    }

    private void setViewPagerIndicatorVisibility(int visible) {
        TextView pagerIndicator = (TextView) findViewById(R.id.pagerIndicator);
        if (pagerIndicator != null) {
            pagerIndicator.setVisibility(visible);
        }
    }

    private void addShakeListener() {
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    private void removeShakeListener() {
        mSensorManager.unregisterListener(mShakeDetector);
    }
}