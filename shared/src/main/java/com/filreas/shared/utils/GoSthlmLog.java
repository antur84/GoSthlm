package com.filreas.shared.utils;

import android.util.Log;

public class GoSthlmLog {

    private static final String gosthlmTag = "gosthlm";

    public static void d(String msg) {
        Log.d(gosthlmTag, msg);
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg);
        Log.d(gosthlmTag, msg);
    }

    public static void e(Exception e) {
        Log.e(gosthlmTag, Log.getStackTraceString(e));
    }
}
