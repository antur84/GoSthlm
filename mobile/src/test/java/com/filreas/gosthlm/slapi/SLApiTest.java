package com.filreas.gosthlm.slapi;

import com.filreas.gosthlm.slapi.operations.CacheType;
import com.filreas.gosthlm.slapi.operations.ResponseCacheStrategy;
import com.filreas.gosthlm.slapi.operations.ResponseFormat;
import com.filreas.gosthlm.slapi.operations.SLRequestHandler;
import com.filreas.gosthlm.slapi.operations.location_finder.contract.request.LocationFinderRequest;
import com.filreas.gosthlm.slapi.operations.location_finder.contract.request.response.LocationFinderResponse;
import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.request.RealTimeRequest;
import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.RealTimeResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SLApiTest {

    @Mock
    SLRequestHandler realTimeStationRequestHandlerMock;
    private SLApi sut;
    private RealTimeRequest request;
    private RealTimeResponse response;
    private ResponseCacheStrategy cacheStrategy;
    @Mock
    private SLRequestHandler locationFinderMock;

    @Before
    public void setup() {
        cacheStrategy = new ResponseCacheStrategy(CacheType.NONE, 0);
        request = new RealTimeRequest(ResponseFormat.JSON, "key", 1, 1, cacheStrategy);
        response = new RealTimeResponse();
        sut = new SLApi(realTimeStationRequestHandlerMock, locationFinderMock);
    }

    @Test
    public void getRealTimeStationInfo_should_call_rest() throws SLApiException {
        when(realTimeStationRequestHandlerMock.get(request)).thenReturn(response);

        RealTimeResponse actual = sut.getRealTimeStationInfo(request);

        assertThat(actual, is(response));
    }

    @Test
    public void getLocations_should_call_rest() throws SLApiException {
        LocationFinderRequest request = new LocationFinderRequest(ResponseFormat.JSON, "key", "aa", cacheStrategy);
        LocationFinderResponse expected = new LocationFinderResponse();
        when(locationFinderMock.get(request)).thenReturn(expected);

        LocationFinderResponse actual = sut.getLocations(request);

        assertThat(actual, is(expected));
    }
}
