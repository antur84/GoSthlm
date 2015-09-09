package com.filreas.slwear.slapi.contract.response;

import com.filreas.slwear.slapi.contract.response.extras.Departure;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Andreas on 9/7/2015.
 */
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
