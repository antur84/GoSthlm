package com.filreas.gosthlm;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WearableListItemLayout extends LinearLayout
        implements WearableListView.OnCenterProximityListener {

    //private ImageView icon;
    private TextView lineNumberText;
    private TextView destinationText;
    private TextView timeText;

    private final float fadedTextAlpha;
    //private final int fadedIconAlpha;

    public WearableListItemLayout(Context context) {
        this(context, null);
    }

    public WearableListItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WearableListItemLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        fadedTextAlpha = 40 / 100f;
        //fadedIconAlpha = 122; // todo does this work?
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //icon = (ImageView) findViewById(R.id.departure_icon);
        lineNumberText = (TextView) findViewById(R.id.lineNumber);
        destinationText = (TextView) findViewById(R.id.departure_destination);
        timeText = (TextView) findViewById(R.id.departure_time);
    }

    @Override
    public void onCenterPosition(boolean animate) {
        destinationText.setAlpha(1f);
        timeText.setAlpha(1f);
        lineNumberText.setAlpha(1f);
        //icon.getDrawable().setAlpha(0);
    }

    @Override
    public void onNonCenterPosition(boolean animate) {
        //icon.getDrawable().setAlpha(fadedIconAlpha);
        lineNumberText.setAlpha(fadedTextAlpha);
        destinationText.setAlpha(fadedTextAlpha);
        timeText.setAlpha(fadedTextAlpha);
    }
}