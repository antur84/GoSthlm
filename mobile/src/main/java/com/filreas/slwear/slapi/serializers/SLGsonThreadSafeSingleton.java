package com.filreas.slwear.slapi.serializers;

/**
 * Created by Andreas on 9/10/2015.
 */
public class SLGsonThreadSafeSingleton {
    private static final SLGson instance = new SLGson();

    protected SLGsonThreadSafeSingleton() {
    }

    public static SLGson getInstance() {
        return instance;
    }
}
