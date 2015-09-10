package com.filreas.slwear.slapi.real_time_station_info.contract.request;

import java.util.Locale;

/**
 * Created by Andreas on 9/6/2015.
 */
public class RealTimeRequest {
    private final int siteId;
    private final String url = "realtimedepartures.%s?key=%s&siteid=%s&timewindow=%s";

    private final String responseFormat;
    private final String key;
    private int timeWindow;

    public RealTimeRequest(RealTimeResponseFormat responseFormat, String key, int siteId, int timeWindow) {
        this.key = key;
        this.timeWindow = timeWindow;
        this.responseFormat = responseFormat.toString().toLowerCase(Locale.US);
        this.siteId = siteId;

        if (timeWindow > 60) {
            throw new IllegalArgumentException("timeWindow can't be greater than 60");
        }
    }

    @Override
    public String toString() {
        return String.format(Locale.US, url, responseFormat, key, siteId, timeWindow);
    }
}
