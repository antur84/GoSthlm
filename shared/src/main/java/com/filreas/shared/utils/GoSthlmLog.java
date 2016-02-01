package com.filreas.shared.utils;

import android.util.Log;

import com.filreas.shared.BuildConfig;

public class GoSthlmLog {

    private static final String gosthlmTag = "gosthlm";

    public static void d(String msg) {
        if(BuildConfig.DEBUG) {
            Log.d(gosthlmTag, msg);
        }
    }

    public static void e(Exception e) {
        if(BuildConfig.DEBUG) {
            Log.e(gosthlmTag, Log.getStackTraceString(e));
        }
    }
}
