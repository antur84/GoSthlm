package com.filreas.slwear.slapi;

import com.filreas.slwear.slapi.operations.CacheType;
import com.filreas.slwear.slapi.operations.ResponseCacheStrategy;
import com.filreas.slwear.slapi.operations.ResponseFormat;
import com.filreas.slwear.slapi.operations.SLRequestHandler;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.request.RealTimeRequest;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.response.RealTimeResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by Andreas on 9/6/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class SLApiTest {

    SLApi sut;
    RealTimeRequest request;
    RealTimeResponse response;

    @Mock
    SLRequestHandler realTimeStationRequestHandler;

    @Before
    public void setup() {
        request = new RealTimeRequest(ResponseFormat.JSON, "key", 1, 1, new ResponseCacheStrategy(CacheType.NONE, 0));
        response = new RealTimeResponse();
        sut = new SLApi(realTimeStationRequestHandler);
    }

    @Test
    public void getRealTimeStationInfo_should_call_rest() {
        when(realTimeStationRequestHandler.get(request)).thenReturn(response);

        RealTimeResponse actual = sut.getRealTimeStationInfo(request);

        assertThat(actual, is(response));
    }
}
