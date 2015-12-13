package com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response;

import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.extras.Departure;
import com.google.gson.annotations.SerializedName;

public class RealTimeResponse {

    @SerializedName("StatusCode")
    private int statusCode;

    @SerializedName("Message")
    private String message;

    @SerializedName("ExecutionTime")
    private long executionTime;

    @SerializedName("ResponseData")
    private Departure responseData;

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public Departure getResponseData() {
        return responseData;
    }
}
