package com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.vehicles;

import com.google.gson.annotations.SerializedName;

public class Train extends TransportType {

    @SerializedName("SecondaryDestinationName")
    private String secondaryDestinationName;

    @SerializedName("StopPointDesignation")
    private String stopPointDesignation;

    public String getStopPointDesignation() {
        return stopPointDesignation;
    }

    public String getSecondaryDestinationName() {
        return secondaryDestinationName;
    }
}
