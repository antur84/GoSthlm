package com.filreas.gosthlm.slapi.serializers;

public class SLGsonThreadSafeSingleton {
    private static final SLGson instance = new SLGson();

    protected SLGsonThreadSafeSingleton() {
    }

    public static SLGson getInstance() {
        return instance;
    }
}
