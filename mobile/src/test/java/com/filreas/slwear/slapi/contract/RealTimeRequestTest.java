package com.filreas.slwear.slapi.contract;

import com.filreas.slwear.slapi.real_time_station_info.contract.request.RealTimeRequest;
import com.filreas.slwear.slapi.real_time_station_info.contract.request.RealTimeResponseFormat;

import org.junit.Test;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Andreas on 9/6/2015.
 */
public class RealTimeRequestTest {

    private final int siteId = 123644323;
    private final String key = "123-321-123-321";
    private final int timeWindow = 60;

    @Test
    public void toString_Should_Return_Correct_Format_When_Given_XML(){
        RealTimeRequest sut = new RealTimeRequest(RealTimeResponseFormat.XML, key, siteId, timeWindow);
        assertThat(sut.toString(), containsString(".xml?"));
    }

    @Test
    public void toString_Should_Return_Correct_Format_When_Given_JSON(){
        RealTimeRequest sut = new RealTimeRequest(RealTimeResponseFormat.JSON, key, siteId, timeWindow);
        assertThat(sut.toString(), containsString(".json?"));
    }

    @Test
    public void toString_Should_Return_Correct_SiteId(){
        RealTimeRequest sut = new RealTimeRequest(RealTimeResponseFormat.JSON, key, siteId, timeWindow);
        assertThat(sut.toString(), containsString(String.format(Locale.US, "&siteid=%s&", siteId)));
    }

    @Test
    public void toString_Should_Return_Correct_Key(){
        RealTimeRequest sut = new RealTimeRequest(RealTimeResponseFormat.JSON, key, siteId, timeWindow);
        assertThat(sut.toString(), containsString(String.format(Locale.US, "?key=%s&", key)));
    }

    @Test
    public void toString_Should_Return_Correct_timeWindow(){
        RealTimeRequest sut = new RealTimeRequest(RealTimeResponseFormat.JSON, key, siteId, timeWindow);
        assertThat(sut.toString(), containsString(String.format(Locale.US, "&timewindow=%s", timeWindow)));
    }

    @Test(expected=IllegalArgumentException.class)
    public void ctor_should_throw_on_invalid_timeWindow(){
        RealTimeRequest sut = new RealTimeRequest(RealTimeResponseFormat.JSON, key, siteId, 61);
    }
}
