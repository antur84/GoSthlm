package com.filreas.shared.utils;

import android.util.Log;

/**
 * Created by Andreas on 9/13/2015.
 */
public class GoSthlmLog {

    private static String gosthlmTag = "gosthlm";

    public static void d(String msg) {
        Log.d(gosthlmTag, msg);
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg);
        Log.d(gosthlmTag, msg);
    }
}
