package com.filreas.gosthlm.slapi.operations.location_finder.contract.request.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationFinderResponse {

    @SerializedName("StatusCode")
    private int statusCode;

    @SerializedName("Message")
    private String message;

    @SerializedName("ExecutionTime")
    private long executionTime;

    @SerializedName("ResponseData")
    private List<Site> responseData;

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public List<Site> getResponseData() {
        return responseData;
    }
}
