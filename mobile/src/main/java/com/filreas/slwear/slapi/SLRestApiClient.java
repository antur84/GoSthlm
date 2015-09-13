package com.filreas.slwear.slapi;


import com.filreas.shared.utils.SLWearLog;
import com.github.kevinsawicki.http.HttpRequest;

/**
 * Created by Andreas on 9/8/2015.
 */
public class SLRestApiClient implements ISLRestApiClient {
    private static final String BASE_URL = "https://api.sl.se/api2/";

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    @Override
    public HttpRequest get(String url) {
        String requestUrl = getAbsoluteUrl(url);
        SLWearLog.d("request fired: " + requestUrl);
        return HttpRequest.get(requestUrl).acceptGzipEncoding().uncompress(true);
    }
}
