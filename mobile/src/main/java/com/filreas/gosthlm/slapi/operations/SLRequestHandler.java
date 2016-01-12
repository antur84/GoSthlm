package com.filreas.gosthlm.slapi.operations;

import android.util.LruCache;

import com.filreas.gosthlm.slapi.ISLRestApiClient;
import com.filreas.gosthlm.slapi.SLApiException;
import com.filreas.gosthlm.slapi.serializers.SLGsonThreadSafeSingleton;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.Date;

public class SLRequestHandler<TRequest extends SLApiRequest, TResponse> implements ISLRequestHandler<TRequest, TResponse> {
    final long ONE_MINUTE_IN_MILLIS = 60000;
    private final ISLRestApiClient apiClient;
    private final Class<TResponse> responseClass;
    private final LruCache<String, CachedHttpRequest> cache;

    public SLRequestHandler(ISLRestApiClient apiClient, Class<TResponse> responseClass, LruCache<String, CachedHttpRequest> cache) {
        this.apiClient = apiClient;
        this.responseClass = responseClass;
        this.cache = cache;
    }

    @Override
    public TResponse get(TRequest request) throws SLApiException{
        String response;
        ResponseCacheStrategy cacheStrategy = request.getCacheStrategy();
        if (cacheStrategy.getType() == CacheType.ABSOLUTE_EXPIRATION) {
            String cacheKey = request.getCacheKey();
            CachedHttpRequest cachedHttpRequest = cache.get(cacheKey);
            if (cachedHttpRequest == null) {
                AddToCache(cacheKey, request);
            } else {
                Date cachedDatePlusExpirationTime = new Date(cachedHttpRequest.getTimeCreated().getTime() + (cacheStrategy.getMinutes() * ONE_MINUTE_IN_MILLIS));
                Date currentTime = new Date();
                if (currentTime.after(cachedDatePlusExpirationTime)) {
                    cache.remove(cacheKey);
                    AddToCache(cacheKey, request);
                }
            }
            response = cache.get(cacheKey).getValue();
        } else {
            response = apiClient.get(request.toString()).body();
        }
        try {
            return SLGsonThreadSafeSingleton.getInstance().fromJson(
                    new JsonReader(
                            new StringReader(response)), responseClass);
        } catch (com.google.gson.JsonSyntaxException jsonSyntaxException) {
            throw new SLApiException(response, jsonSyntaxException.getCause());
        }
    }

    private void AddToCache(String cacheKey, TRequest request) {
        HttpRequest httpRequest = apiClient.get(request.toString());
        CachedHttpRequest value = new CachedHttpRequest(httpRequest.body());
        cache.put(cacheKey, value);
    }

}
