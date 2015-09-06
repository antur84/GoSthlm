package com.filreas.slwear.slapi.contract;

import java.util.Locale;

/**
 * Created by Andreas on 9/6/2015.
 */
public class RealTimeRequest {
    private final int siteId;
    private String url ="api.sl.se/api2/realtimedepartures.%s?key=%s&siteid=%s&timewindow=%s";

    private String responseFormat;

    public RealTimeRequest(RealTimeResponseFormat responseFormat, int siteId){
        this.responseFormat = responseFormat.toString().toLowerCase(Locale.US);
        this.siteId = siteId;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, url, responseFormat, null, siteId, null);
    }
}
