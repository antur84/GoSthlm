package com.filreas.gosthlm;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WearableListItemLayout extends LinearLayout
        implements WearableListView.OnCenterProximityListener {

    //private ImageView icon;
    private TextView destinationText;

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
        destinationText = (TextView) findViewById(R.id.departure_destination);
    }

    @Override
    public void onCenterPosition(boolean animate) {
        destinationText.setAlpha(1f);
        setTextSizeOfRow(18);
        //icon.getDrawable().setAlpha(0);
    }

    @Override
    public void onNonCenterPosition(boolean animate) {
        //icon.getDrawable().setAlpha(fadedIconAlpha);
        destinationText.setAlpha(fadedTextAlpha);
        setTextSizeOfRow(13);
    }

    private void setTextSizeOfRow(int size) {
        destinationText.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }
}