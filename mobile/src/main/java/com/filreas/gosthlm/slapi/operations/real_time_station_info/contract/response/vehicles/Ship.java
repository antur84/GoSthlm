package com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.vehicles;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Andreas on 9/7/2015.
 */
public class Ship extends TransportType {

    @SerializedName("GroupOfLine")
    private String groupOfLine;
}