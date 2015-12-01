package com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.extras;

import com.google.gson.annotations.SerializedName;

public class StopInfo {

    @SerializedName("GroupOfLine")
    private String groupOfLine;

    @SerializedName("StopAreaName")
    private String stopAreaName;

    @SerializedName("StopAreaNumber")
    private int stopAreaNumber;

    @SerializedName("TransportMode")
    private String transportMode;
}
