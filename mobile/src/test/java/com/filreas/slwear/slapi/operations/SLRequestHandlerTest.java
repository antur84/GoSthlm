package com.filreas.slwear.slapi.operations;

import android.util.LruCache;

import com.filreas.slwear.slapi.ISLRestApiClient;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.request.RealTimeRequest;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.response.RealTimeResponse;
import com.github.kevinsawicki.http.HttpRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Andreas on 9/10/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class SLRequestHandlerTest {

    @Mock
    private ISLRestApiClient apiClientMock;

    @Mock
    private HttpRequest httpRequestMock;

    @Mock
    private LruCache<String, CachedHttpRequest> cacheMock;

    private SLRequestHandler<RealTimeRequest, RealTimeResponse> sut;

    @Before
    public void setup() {
        sut = new SLRequestHandler<>(apiClientMock, RealTimeResponse.class, cacheMock);
    }

    @Test
    public void get_should_return_data_from_web_if_no_cache_strategy() {
        RealTimeRequest request = new RealTimeRequest(ResponseFormat.JSON, "key", 1, 1, new ResponseCacheStrategy(CacheType.NONE, 0));
        when(apiClientMock.get(anyString())).thenReturn(httpRequestMock);
        when(httpRequestMock.body()).thenReturn("{}");

        RealTimeResponse actual = sut.get(request);
        verify(apiClientMock, times(1)).get(anyString());
        assertThat(actual, is(notNullValue()));
    }

    @Test
    public void get_should_return_data_from_web_and_store_in_cache_if_cache_strategy_exists_but_cache_is_empty() {
        RealTimeRequest request = new RealTimeRequest(ResponseFormat.JSON, "key", 1, 1, new ResponseCacheStrategy(CacheType.ABSOLUTE_EXPIRATION, 1));
        when(apiClientMock.get(anyString())).thenReturn(httpRequestMock);
        String response = "{}";
        when(httpRequestMock.body()).thenReturn(response);
        when(cacheMock.get(request.getCacheKey())).thenReturn(null).thenReturn(new CachedHttpRequest(response));
        RealTimeResponse actual = sut.get(request);
        verify(apiClientMock, times(1)).get(anyString());
        verify(cacheMock, times(1)).put(eq(request.getCacheKey()), any(CachedHttpRequest.class));
        assertThat(actual, is(notNullValue()));
    }

    @Test
    public void get_should_return_data_from_cache_if_cache_strategy_exists_and_cache_has_the_value() {
        RealTimeRequest request = new RealTimeRequest(ResponseFormat.JSON, "key", 1, 1, new ResponseCacheStrategy(CacheType.ABSOLUTE_EXPIRATION, 1));
        String response = "{}";
        when(cacheMock.get(request.getCacheKey())).thenReturn(new CachedHttpRequest(response));
        RealTimeResponse actual = sut.get(request);
        verify(apiClientMock, never()).get(anyString());
        assertThat(actual, is(notNullValue()));
    }

    @Test
    public void get_should_return_data_from_web_if_cache_strategy_exists_and_cache_has_the_value_but_it_expired() {
        RealTimeRequest request = new RealTimeRequest(ResponseFormat.JSON, "key", 1, 1, new ResponseCacheStrategy(CacheType.ABSOLUTE_EXPIRATION, -1));
        when(apiClientMock.get(anyString())).thenReturn(httpRequestMock);
        String response = "{}";
        when(httpRequestMock.body()).thenReturn(response);
        when(cacheMock.get(request.getCacheKey())).thenReturn(new CachedHttpRequest(response));
        RealTimeResponse actual = sut.get(request);
        verify(cacheMock, times(1)).remove(request.getCacheKey());
        verify(apiClientMock, times(1)).get(anyString());
        assertThat(actual, is(notNullValue()));
    }
}
