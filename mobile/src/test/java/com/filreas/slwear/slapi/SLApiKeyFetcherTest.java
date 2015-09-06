package com.filreas.slwear.slapi;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.when;

/**
 * Created by Andreas on 9/6/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class SLApiKeyFetcherTest {

    private SLApiKeyFetcher sut;
    private SLApiKeyResourceMock resourceMock;

    @Mock
    private Context contextMock;
    private String fakePackageName;

    @Before
    public void setup(){

        fakePackageName = "thePackageName";
        when(contextMock.getPackageName()).thenReturn(fakePackageName);

        resourceMock = new SLApiKeyResourceMock();
        sut = new SLApiKeyFetcher(resourceMock, contextMock);
    }

    @Test
    public void getKeyShouldReadFromResource(){
        assertThat(sut.getKey(), is(SLApiKeyResourceMock.FakeKey));
    }
}
