package com.filreas.gosthlm.async;

import com.filreas.gosthlm.slapi.SLApiException;
import com.filreas.gosthlm.slapi.operations.CacheType;
import com.filreas.gosthlm.slapi.operations.ResponseCacheStrategy;
import com.filreas.gosthlm.slapi.operations.ResponseFormat;
import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.request.RealTimeRequest;
import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.RealTimeResponse;
import com.github.kevinsawicki.http.HttpRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SLApiRequestTaskTest {

    private SLApiRequestTask<RealTimeRequest, RealTimeResponse> sut;
    private RealTimeRequest realTimeRequest;
    private RealTimeResponse realTimeResponse;

    @Captor
    private ArgumentCaptor<SLApiTaskResult<RealTimeResponse>> realTimeResponseCaptor;

    @Mock
    private ISLApiCall<RealTimeRequest, RealTimeResponse> slApiCall;

    @Mock
    private ISLApiTaskResponseHandler<RealTimeResponse> responseHandler;

    @Before
    public void setup() {
        realTimeResponse = new RealTimeResponse();
        realTimeRequest = new RealTimeRequest(ResponseFormat.JSON, "key", 1, 1, new ResponseCacheStrategy(CacheType.NONE, 0));
        sut = new SLApiRequestTask<>(slApiCall, responseHandler);
    }

    @Test(expected = IllegalArgumentException.class)
    public void doInBackground_should_throw_on_multiple_request_objects() {
        sut.doInBackground(realTimeRequest, realTimeRequest);
    }

    @Test
    public void doInBackground_should_call_slApi_with_first_param_and_return_result() throws SLApiException {
        when(slApiCall.perform(realTimeRequest)).thenReturn(realTimeResponse);
        RealTimeResponse actual = sut.doInBackground(realTimeRequest);

        assertThat(actual, is(realTimeResponse));
    }

    @Test
    public void doInBackground_should_call_slApi_and_return_null_on_HttpRequestException() throws SLApiException {
        when(slApiCall.perform(realTimeRequest)).thenThrow(HttpRequest.HttpRequestException.class);
        RealTimeResponse actual = sut.doInBackground(realTimeRequest);

        assertThat(actual, is(nullValue()));
    }

    @Test
    public void onPostExecute_should_call_responseHandler_with_parameter_containing_response() {
        sut.onPostExecute(realTimeResponse);
        verify(responseHandler).onTaskComplete(realTimeResponseCaptor.capture());
        assertThat(realTimeResponseCaptor.getValue().getResponse(), is(realTimeResponse));
    }

    @Test
    public void onPostExecute_should_call_responseHandler_with_parameter_containing_exception_if_response_null() throws SLApiException {
        when(slApiCall.perform(realTimeRequest)).thenThrow(HttpRequest.HttpRequestException.class);

        sut.doInBackground(realTimeRequest);
        sut.onPostExecute(null);

        ArgumentCaptor<SLApiTaskResult> result = ArgumentCaptor.forClass(SLApiTaskResult.class);
        verify(responseHandler).onTaskComplete(result.capture());
        assertThat(result.getValue().getException(), isA(Exception.class));
    }

    @Test
    public void onCancelled_should_not_call_responseHandler() {
        sut.onCancelled(null);
        verify(responseHandler, never()).onTaskComplete(Mockito.any(SLApiTaskResult.class));
    }
}
