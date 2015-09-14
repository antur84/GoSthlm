package com.filreas.gosthlm.slapi.operations.location_finder.contract.request.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Andreas on 9/12/2015.
 */
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
