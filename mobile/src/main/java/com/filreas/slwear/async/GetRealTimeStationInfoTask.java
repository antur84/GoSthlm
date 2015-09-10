package com.filreas.slwear.async;

import android.os.AsyncTask;

import com.filreas.slwear.slapi.ISLApi;
import com.filreas.slwear.slapi.real_time_station_info.contract.request.RealTimeRequest;
import com.filreas.slwear.slapi.real_time_station_info.contract.response.RealTimeResponse;
import com.github.kevinsawicki.http.HttpRequest;

/**
 * Created by Andreas on 9/10/2015.
 */
public class GetRealTimeStationInfoTask extends AsyncTask<RealTimeRequest, Void, RealTimeResponse> {

    private ISLApi slApi;
    private IGetRealTimeStationInfoTaskResultHandler responseHandler;
    private HttpRequest.HttpRequestException exception;

    public GetRealTimeStationInfoTask(ISLApi slApi, IGetRealTimeStationInfoTaskResultHandler responseHandler) {
        this.slApi = slApi;
        this.responseHandler = responseHandler;
    }

    @Override
    protected RealTimeResponse doInBackground(RealTimeRequest... params) {
        if (params.length != 1) {
            throw new IllegalArgumentException("Only one request per task instance allowed");
        }

        try {
            return slApi.getRealTimeStationInfo(params[0]);
        } catch (HttpRequest.HttpRequestException exception) {
            this.exception = exception;
            return null;
        }
    }

    @Override
    protected void onPostExecute(RealTimeResponse realTimeResponse) {
        if (realTimeResponse == null) {
            responseHandler.onTaskComplete(new GetRealTimeStationInfoTaskResult(exception));
        } else {
            responseHandler.onTaskComplete(new GetRealTimeStationInfoTaskResult(realTimeResponse));
        }
    }
}