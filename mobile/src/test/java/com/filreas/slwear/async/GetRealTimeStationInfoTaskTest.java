package com.filreas.slwear.async;

import com.filreas.slwear.slapi.ISLApi;
import com.filreas.slwear.slapi.real_time_station_info.contract.request.RealTimeRequest;
import com.filreas.slwear.slapi.real_time_station_info.contract.request.RealTimeResponseFormat;
import com.filreas.slwear.slapi.real_time_station_info.contract.response.RealTimeResponse;
import com.github.kevinsawicki.http.HttpRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Andreas on 9/10/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetRealTimeStationInfoTaskTest {

    private GetRealTimeStationInfoTask sut;

    @Mock
    private ISLApi slApi;
    @Mock
    private IGetRealTimeStationInfoTaskResultHandler responseHandler;
    private RealTimeRequest realTimeRequest;
    private com.filreas.slwear.slapi.real_time_station_info.contract.response.RealTimeResponse realTimeResponse;

    @Before
    public void setup() {
        realTimeResponse = new RealTimeResponse();
        realTimeRequest = new RealTimeRequest(RealTimeResponseFormat.JSON, "key", 1, 1);
        sut = new GetRealTimeStationInfoTask(slApi, responseHandler);
    }

    @Test(expected = IllegalArgumentException.class)
    public void doInBackground_should_throw_on_multiple_request_objects() {
        sut.doInBackground(realTimeRequest, realTimeRequest);
    }

    @Test()
    public void doInBackground_should_call_slApi_with_first_param_and_return_result() {
        when(slApi.getRealTimeStationInfo(realTimeRequest)).thenReturn(realTimeResponse);
        RealTimeResponse actual = sut.doInBackground(realTimeRequest);

        assertThat(actual, is(realTimeResponse));
    }

    @Test()
    public void doInBackground_should_call_slApi_and_return_null_on_HttpRequestException() {
        when(slApi.getRealTimeStationInfo(realTimeRequest)).thenThrow(HttpRequest.HttpRequestException.class);
        RealTimeResponse actual = sut.doInBackground(realTimeRequest);

        assertThat(actual, is(nullValue()));
    }

    @Test()
    public void onPostExecute_should_call_responseHandler_with_parameter_containing_response() {
        sut.onPostExecute(realTimeResponse);

        ArgumentCaptor<GetRealTimeStationInfoTaskResult> result = ArgumentCaptor.forClass(GetRealTimeStationInfoTaskResult.class);
        verify(responseHandler).onTaskComplete(result.capture());
        assertThat(result.getValue().getResponse(), is(realTimeResponse));
    }

    @Test()
    public void onPostExecute_should_call_responseHandler_with_parameter_containing_exception_if_response_null() {
        when(slApi.getRealTimeStationInfo(realTimeRequest)).thenThrow(HttpRequest.HttpRequestException.class);

        sut.doInBackground(realTimeRequest);
        sut.onPostExecute(null);

        ArgumentCaptor<GetRealTimeStationInfoTaskResult> result = ArgumentCaptor.forClass(GetRealTimeStationInfoTaskResult.class);
        verify(responseHandler).onTaskComplete(result.capture());
        assertThat(result.getValue().getException(), isA(Exception.class));
    }
}
