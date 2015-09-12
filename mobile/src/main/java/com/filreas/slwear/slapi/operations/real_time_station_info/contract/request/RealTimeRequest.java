package com.filreas.slwear.slapi.operations.real_time_station_info.contract.request;

import com.filreas.slwear.slapi.operations.ResponseCacheStrategy;
import com.filreas.slwear.slapi.operations.ResponseFormat;
import com.filreas.slwear.slapi.operations.SLApiRequest;

import java.util.Locale;

/**
 * Created by Andreas on 9/6/2015.
 */
public class RealTimeRequest extends SLApiRequest {
    private final int siteId;
    private final String url = "realtimedepartures.%s?key=%s&siteid=%s&timewindow=%s";

    private int timeWindow;

    public RealTimeRequest(ResponseFormat responseFormat, String key, int siteId, int timeWindow, ResponseCacheStrategy cacheStrategy) {
        super(responseFormat, key, cacheStrategy);
        this.timeWindow = timeWindow;
        this.siteId = siteId;

        if (timeWindow > 60) {
            throw new IllegalArgumentException("timeWindow can't be greater than 60");
        }
    }

    @Override
    public String toString() {
        return String.format(Locale.US, url, getResponseFormat(), getKey(), siteId, timeWindow);
    }

    @Override
    public String getCacheKey() {
        return String.format(Locale.US, "%s%s%s%s", RealTimeRequest.class.getCanonicalName(), getResponseFormat(), getKey(), siteId);
    }
}
