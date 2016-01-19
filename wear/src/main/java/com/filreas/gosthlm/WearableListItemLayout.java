package com.filreas.gosthlm;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WearableListItemLayout extends LinearLayout
        implements WearableListView.OnCenterProximityListener {

    private TextView destinationText;

    private final float fadedTextAlpha;

    public WearableListItemLayout(Context context) {
        this(context, null);
    }

    public WearableListItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WearableListItemLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        fadedTextAlpha = 40 / 100f;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        destinationText = (TextView) findViewById(R.id.departure_destination);
    }

    @Override
    public void onCenterPosition(boolean animate) {
        float value = 1.307692307692308f;

        if (animate) {
            destinationText.animate().scaleX(value).scaleY(value).alpha(1f).start();
        }
    }

    @Override
    public void onNonCenterPosition(boolean animate) {
        if (animate) {
            destinationText.animate().scaleX(1.0f).scaleY(1.0f).alpha(fadedTextAlpha).start();
        }
    }
}