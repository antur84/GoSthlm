package com.filreas.gosthlm.activities.favourites;

import android.os.Bundle;

import com.filreas.gosthlm.R;
import com.filreas.gosthlm.activities.MobileBaseActivity;

public class Favourites extends MobileBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableHomeAsUpNavigation();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_favourites;
    }

}
