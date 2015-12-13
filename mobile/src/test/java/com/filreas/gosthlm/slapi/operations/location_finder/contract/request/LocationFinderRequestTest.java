package com.filreas.gosthlm.slapi.operations.location_finder.contract.request;

import com.filreas.gosthlm.slapi.operations.ResponseFormat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LocationFinderRequestTest {

    private final String key = "355";
    private final String searchString = "Solna";
    private com.filreas.gosthlm.slapi.operations.ResponseCacheStrategy cacheStrategy;
    private LocationFinderRequest sut;

    @Before
    public void setup() {
        sut = new LocationFinderRequest(ResponseFormat.JSON, key, searchString, cacheStrategy, true, 10);
    }

    @Test
    public void getCacheKey_should_return_string_using_className_and_parameters() {
        assertThat(sut.getCacheKey(), is(LocationFinderRequest.class.getCanonicalName() + "json" + key + searchString + true + 10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ctor_should_throw_on_negative_max_results() {
        new LocationFinderRequest(ResponseFormat.JSON, key, searchString, cacheStrategy, true, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ctor_should_throw_on_too_large_max_results() {
        new LocationFinderRequest(ResponseFormat.JSON, key, searchString, cacheStrategy, true, 51);
    }

    @Test
    public void toString_should_return_correct_format_when_given_XML() {
        sut = new LocationFinderRequest(ResponseFormat.XML, key, searchString, cacheStrategy, true, 10);
        assertThat(sut.toString(), containsString(".xml?"));
    }

    @Test
    public void toString_should_return_correct_format_when_given_JSON() {
        assertThat(sut.toString(), containsString(".json?"));
    }

    @Test
    public void toString_should_return_correct_searchString() {
        assertThat(sut.toString(), containsString(String.format(Locale.US, "&searchstring=%s&", searchString)));
    }

    @Test
    public void toString_should_return_correct_key() {
        assertThat(sut.toString(), containsString(String.format(Locale.US, "?key=%s&", key)));
    }

    @Test
    public void toString_should_return_correct_stationResults() {
        assertThat(sut.toString(), containsString(String.format(Locale.US, "stationsonly=%s&", true)));
    }

    @Test
    public void toString_should_return_correct_maxResults() {
        assertThat(sut.toString(), containsString(String.format(Locale.US, "maxresults=%s", 10)));
    }
}
