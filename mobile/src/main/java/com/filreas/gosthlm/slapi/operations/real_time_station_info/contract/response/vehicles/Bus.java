package com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.vehicles;

import com.google.gson.annotations.SerializedName;

public class Bus extends TransportType {

    @SerializedName("GroupOfLine")
    private String groupOfLine;

    @SerializedName("StopPointDesignation")
    private String stopPointDesignation;

    public String getGroupOfLine() {
        return groupOfLine;
    }

    public String getStopPointDesignation() {
        return stopPointDesignation;
    }
}
