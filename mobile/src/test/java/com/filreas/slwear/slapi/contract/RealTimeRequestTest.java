package com.filreas.slwear.slapi.contract;

import org.junit.Test;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Andreas on 9/6/2015.
 */
public class RealTimeRequestTest {

    private final int siteId = 123644323;

    @Test
    public void toString_Should_Return_Correct_Format_When_Given_XML(){
        RealTimeRequest sut = new RealTimeRequest(RealTimeResponseFormat.XML, siteId);
        assertThat(sut.toString(), containsString(".xml?"));
    }

    @Test
    public void toString_Should_Return_Correct_Format_When_Given_JSON(){
        RealTimeRequest sut = new RealTimeRequest(RealTimeResponseFormat.JSON, siteId);
        assertThat(sut.toString(), containsString(".json?"));
    }

    @Test
    public void toString_Should_Return_Correct_SiteId(){
        RealTimeRequest sut = new RealTimeRequest(RealTimeResponseFormat.JSON, siteId);
        assertThat(sut.toString(), containsString(String.format(Locale.US, "&siteid=%s&", siteId)));
    }
}
