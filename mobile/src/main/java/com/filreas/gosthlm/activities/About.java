package com.filreas.gosthlm.activities;

import android.os.Bundle;

import com.filreas.gosthlm.R;

/**
 * @author Filip G
 */

public class About extends MobileBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableHomeAsUpNavigation();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_about;
    }
}
