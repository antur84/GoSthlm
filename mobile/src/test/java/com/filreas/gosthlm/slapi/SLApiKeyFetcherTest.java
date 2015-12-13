package com.filreas.gosthlm.slapi;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SLApiKeyFetcherTest {

    private SLApiKeyFetcher sut;
    private SLApiKeyResourceMock resourceMock;

    @Mock
    private Context contextMock;

    @Before
    public void setup(){

        String fakePackageName = "thePackageName";
        when(contextMock.getPackageName()).thenReturn(fakePackageName);

        resourceMock = new SLApiKeyResourceMock();
        sut = new SLApiKeyFetcher(resourceMock, contextMock);
    }

    @Test
    public void getKey_should_read_from_resource() {
        assertThat(sut.getKey("slapikey"), is(SLApiKeyResourceMock.FakeKey));
    }

    @Test
    public void getKey_consecutive_calls_should_return_cached_key() {
        String first = sut.getKey("slapikey");
        String second = sut.getKey("slapikey");

        assertThat(resourceMock.numberOfCallsToGetString(), is(1));
        assertThat(first, is(second));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getKey_throws_if_no_such_key() {
        sut.getKey("nope");
    }
}
