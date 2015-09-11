package com.filreas.slwear.async;

import com.filreas.slwear.slapi.operations.real_time_station_info.contract.response.RealTimeResponse;

/**
 * Created by Andreas on 9/10/2015.
 */
public class GetRealTimeStationInfoTaskResult {
    private Exception exception;
    private RealTimeResponse response;

    public GetRealTimeStationInfoTaskResult(RealTimeResponse response) {
        this.response = response;
    }

    public GetRealTimeStationInfoTaskResult(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    public RealTimeResponse getResponse() {
        return response;
    }
}
