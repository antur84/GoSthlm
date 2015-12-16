package com.filreas.gosthlm.slapi.operations.real_time_station_info;

import android.util.LruCache;

import com.filreas.gosthlm.slapi.SLRestApiClient;
import com.filreas.gosthlm.slapi.operations.CacheType;
import com.filreas.gosthlm.slapi.operations.CachedHttpRequest;
import com.filreas.gosthlm.slapi.operations.ResponseCacheStrategy;
import com.filreas.gosthlm.slapi.operations.ResponseFormat;
import com.filreas.gosthlm.slapi.operations.SLRequestHandler;
import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.request.RealTimeRequest;
import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.RealTimeResponse;
import com.github.kevinsawicki.http.HttpRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SLRealTimeStationRequestHandlerTest {

    private SLRequestHandler<RealTimeRequest, RealTimeResponse> sut;
    private RealTimeRequest request;

    @Mock
    private HttpRequest responseMock;

    @Mock
    private SLRestApiClient apiClientMock;

    @Mock
    private LruCache<String, CachedHttpRequest> cacheMock;

    @Before
    public void setup() {
        request = new RealTimeRequest(ResponseFormat.JSON, "key", 1, 1, new ResponseCacheStrategy(CacheType.NONE, 0));
        sut = new SLRequestHandler<>(apiClientMock, RealTimeResponse.class, cacheMock);
    }

    @Test
    public void get_should_return_response_when_success() throws ParseException {

        when(responseMock.body()).thenReturn(new RealTimeBasicEmptyResponseTestJson().toString());
        when(apiClientMock.get(anyString())).thenReturn(responseMock);

        RealTimeResponse actual = sut.get(request);

        assertThat(actual.getExecutionTime(), is((long) 334));
        assertThat(actual.getMessage(), is(nullValue()));
        assertThat(actual.getResponseData(), is(notNullValue()));
        assertThat(actual.getStatusCode(), is(0));
        assertThat(actual.getResponseData().getBuses().size(), is(0));
        assertThat(actual.getResponseData().getMetros().size(), is(0));
        assertThat(actual.getResponseData().getShips().size(), is(0));
        assertThat(actual.getResponseData().getTrains().size(), is(0));
        assertThat(actual.getResponseData().getTrams().size(), is(0));
        assertThat(actual.getResponseData().getDataAge(), is(27));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy/hh/mm/ss");
        Date expected = sdf.parse("8/9/2015/23/25/17");
        assertThat(actual.getResponseData().getLatestUpdate().compareTo(expected), is(0));
        assertThat(actual.getResponseData().getStopPointDeviations().size(), is(0));
    }

    private class RealTimeBasicEmptyResponseTestJson {
        @Override
        public String toString() {
            return "{\"StatusCode\":0,\"Message\":null,\"ExecutionTime\":334,\"ResponseData\":{\"LatestUpdate\":\"2015-09-08T23:25:17\",\"DataAge\":27,\"Metros\":[],\"Buses\":[],\"Trains\":[],\"Trams\":[],\"Ships\":[],\"StopPointDeviations\":[]}}";
        }
    }
}
