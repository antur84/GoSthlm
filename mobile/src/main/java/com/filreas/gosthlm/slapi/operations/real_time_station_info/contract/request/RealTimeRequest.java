package com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.request;

import com.filreas.gosthlm.slapi.operations.ResponseCacheStrategy;
import com.filreas.gosthlm.slapi.operations.ResponseFormat;
import com.filreas.gosthlm.slapi.operations.SLApiRequest;

import java.util.Locale;

public class RealTimeRequest extends SLApiRequest {
    private final String url = "realtimedepartures.%s?key=%s&siteid=%s&timewindow=%s";
    private int siteId;
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

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }
}
