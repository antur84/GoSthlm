package com.filreas.slwear.slapi.operations.real_time_station_info;

import com.filreas.slwear.slapi.SLRestApiClient;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.request.RealTimeRequest;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.request.RealTimeResponseFormat;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.response.RealTimeResponse;
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

/**
 * Created by Andreas on 9/8/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class SLRealTimeStationRequestHandlerTest {

    private SLRealTimeStationRequestHandler sut;
    private RealTimeRequest request;

    @Mock
    private HttpRequest responseMock;

    @Mock
    private SLRestApiClient apiClientMock;

    @Before
    public void setup() {
        request = new RealTimeRequest(RealTimeResponseFormat.JSON, "key", 1, 1);
        sut = new SLRealTimeStationRequestHandler(apiClientMock);
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
