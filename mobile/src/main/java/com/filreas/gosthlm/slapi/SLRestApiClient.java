package com.filreas.gosthlm.slapi;


import com.filreas.shared.utils.GoSthlmLog;
import com.github.kevinsawicki.http.HttpRequest;

public class SLRestApiClient implements ISLRestApiClient {
    private static final String BASE_URL = "https://api.sl.se/api2/";

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    @Override
    public HttpRequest get(String url) {
        String requestUrl = getAbsoluteUrl(url);
        GoSthlmLog.d("request fired: " + requestUrl);
        return HttpRequest.get(requestUrl).acceptGzipEncoding().uncompress(true);
    }
}
