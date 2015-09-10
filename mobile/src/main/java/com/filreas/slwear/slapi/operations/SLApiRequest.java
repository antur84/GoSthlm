package com.filreas.slwear.slapi.operations;

/**
 * Created by Andreas on 9/10/2015.
 */
public abstract class SLApiRequest {
    private ResponseCacheStrategy cacheStrategy;

    public SLApiRequest(ResponseCacheStrategy cacheStrategy) {
        this.cacheStrategy = cacheStrategy;
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
}
