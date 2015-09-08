package com.filreas.slwear.slapi.contract.response;

import com.filreas.slwear.slapi.contract.response.extras.Departure;

/**
 * Created by Andreas on 9/7/2015.
 */
public class RealTimeResponse {
    private int statusCode;
    private String message;
    private long ExecutionTime;
    private Departure ResponseData;
}
