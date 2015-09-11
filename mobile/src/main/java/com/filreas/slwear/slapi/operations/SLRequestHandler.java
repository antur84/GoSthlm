package com.filreas.slwear.slapi.operations;

import android.util.LruCache;

import com.filreas.slwear.slapi.ISLRestApiClient;
import com.filreas.slwear.slapi.serializers.SLGsonThreadSafeSingleton;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.Date;

/**
 * Created by Andreas on 9/10/2015.
 */
public class SLRequestHandler<TRequest extends SLApiRequest, TResponse> implements ISLRequestHandler<TRequest, TResponse> {
    final long ONE_MINUTE_IN_MILLIS = 60000;
    private final ISLRestApiClient apiClient;
    private Class<TResponse> responseClass;
    private LruCache<String, CachedHttpRequest> cache;

    public SLRequestHandler(ISLRestApiClient apiClient, Class<TResponse> responseClass, LruCache<String, CachedHttpRequest> cache) {
        this.apiClient = apiClient;
        this.responseClass = responseClass;
        this.cache = cache;
    }

    @Override
    public TResponse get(TRequest request) {
        String response;
        ResponseCacheStrategy cacheStrategy = request.getCacheStrategy();
        if (cacheStrategy.getType() == CacheType.ABSOLUTE_EXPIRATION) {
            String cacheKey = request.getCacheKey();
            CachedHttpRequest cachedHttpRequest = cache.get(cacheKey);
            if (cachedHttpRequest == null) {
                AddToCache(cacheKey, apiClient);
            } else {
                Date cachedDatePlusExpirationTime = new Date(cachedHttpRequest.getTimeCreated().getTime() + (cacheStrategy.getMinutes() * ONE_MINUTE_IN_MILLIS));
                Date currentTime = new Date();
                if (currentTime.after(cachedDatePlusExpirationTime)) {
                    cache.remove(cacheKey);
                    AddToCache(cacheKey, apiClient);
                }
            }
            response = cache.get(cacheKey).getValue();
        } else {
            response = apiClient.get(request.toString()).body();
        }
        return SLGsonThreadSafeSingleton.getInstance().fromJson(new JsonReader(new StringReader(response)), responseClass);
    }

    private void AddToCache(String cacheKey, ISLRestApiClient apiClient) {
        HttpRequest httpRequest = this.apiClient.get(apiClient.toString());
        CachedHttpRequest value = new CachedHttpRequest(httpRequest.body());
        cache.put(cacheKey, value);
    }

}
