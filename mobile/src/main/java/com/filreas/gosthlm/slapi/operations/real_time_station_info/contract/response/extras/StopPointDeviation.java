package com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.extras;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Andreas on 9/7/2015.
 */
public class StopPointDeviation {

    @SerializedName("StopInfo")
    private StopInfo stopInfo;

    @SerializedName("Deviation")
    private Deviation deviation;
}
