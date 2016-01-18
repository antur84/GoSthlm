package com.filreas.shared.utils;

public class DataLayerUri {
    private static final String root = "/gosthlm/";
    public static final String REFRESH_ALL_DATA_ON_WATCH_REQUESTED = root + "liveupdate/refreshAll";
    public static final String REFRESH_ALL_DATA_ON_WATCH_COMPLETED = root + "liveupdate/refreshAllCompleted";
    public static final String FAVOURITE_SITE_UPDATE = root + "liveupdate/favourite/updated";
    public static final String FAVOURITE_SITE_UPDATE_FAILED = root + "liveupdate/favourite/updatefailed";
}
