package com.filreas.slwear.slapi.real_time_station_info.contract.response.vehicles;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Andreas on 9/7/2015.
 */
public class Train extends TransportType {

    @SerializedName("SecondaryDestinationName")
    private String secondaryDestinationName;

    @SerializedName("StopPointDesignation")
    private String stopPointDesignation;
}
