package com.filreas.gosthlm.slapi.operations;

import java.util.Locale;

/**
 * Created by Andreas on 9/10/2015.
 */
public abstract class SLApiRequest {
    private final String key;
    private final String responseFormat;
    private ResponseCacheStrategy cacheStrategy;

    public SLApiRequest(ResponseFormat responseFormat, String key, ResponseCacheStrategy cacheStrategy) {
        this.cacheStrategy = cacheStrategy;
        this.key = key;
        this.responseFormat = responseFormat.toString().toLowerCase(Locale.US);
    }

    public ResponseCacheStrategy getCacheStrategy() {
        return cacheStrategy;
    }

    /***
     * Generate a unique cache key for storing responses.
     *
     * @return a unique string.
     */
    public abstract String getCacheKey();

    protected String getKey() {
        return key;
    }

    protected String getResponseFormat() {
        return responseFormat;
    }
}
