package com.filreas.slwear.slapi.contract;

import com.filreas.slwear.slapi.operations.CacheType;
import com.filreas.slwear.slapi.operations.ResponseCacheStrategy;
import com.filreas.slwear.slapi.operations.ResponseFormat;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.request.RealTimeRequest;

import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Andreas on 9/6/2015.
 */
public class RealTimeRequestTest {

    private final int siteId = 123644323;
    private final String key = "123-321-123-321";
    private final int timeWindow = 60;
    private ResponseCacheStrategy cacheStrategy;

    @Before
    public void setup() {
        cacheStrategy = new ResponseCacheStrategy(CacheType.ABSOLUTE_EXPIRATION, 5);
    }

    @Test
    public void toString_should_return_correct_format_when_given_XML() {
        RealTimeRequest sut = new RealTimeRequest(ResponseFormat.XML, key, siteId, timeWindow, cacheStrategy);
        assertThat(sut.toString(), containsString(".xml?"));
    }

    @Test
    public void toString_should_return_correct_format_when_given_JSON() {
        RealTimeRequest sut = new RealTimeRequest(ResponseFormat.JSON, key, siteId, timeWindow, cacheStrategy);
        assertThat(sut.toString(), containsString(".json?"));
    }

    @Test
    public void toString_should_return_correct_siteId() {
        RealTimeRequest sut = new RealTimeRequest(ResponseFormat.JSON, key, siteId, timeWindow, cacheStrategy);
        assertThat(sut.toString(), containsString(String.format(Locale.US, "&siteid=%s&", siteId)));
    }

    @Test
    public void toString_should_return_correct_key() {
        RealTimeRequest sut = new RealTimeRequest(ResponseFormat.JSON, key, siteId, timeWindow, cacheStrategy);
        assertThat(sut.toString(), containsString(String.format(Locale.US, "?key=%s&", key)));
    }

    @Test
    public void toString_should_return_correct_timeWindow() {
        RealTimeRequest sut = new RealTimeRequest(ResponseFormat.JSON, key, siteId, timeWindow, cacheStrategy);
        assertThat(sut.toString(), containsString(String.format(Locale.US, "&timewindow=%s", timeWindow)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ctor_should_throw_on_invalid_timeWindow() {
        RealTimeRequest sut = new RealTimeRequest(ResponseFormat.JSON, key, siteId, 61, cacheStrategy);
    }

    @Test
    public void getCacheKey_should_return_string_using_className_and_parameters() {
        RealTimeRequest sut = new RealTimeRequest(ResponseFormat.JSON, key, siteId, timeWindow, cacheStrategy);
        assertThat(sut.getCacheKey(), is(RealTimeRequest.class.getCanonicalName() + "json" + key + siteId));
    }
}
