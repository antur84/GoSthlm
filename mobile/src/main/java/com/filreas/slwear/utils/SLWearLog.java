package com.filreas.slwear.utils;

import android.util.Log;

/**
 * Created by Andreas on 9/13/2015.
 */
public class SLWearLog {

    private static String slwearTag = "slwear";

    public static void d(String msg) {
        Log.d(slwearTag, msg);
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg);
        Log.d(slwearTag, msg);
    }
}
