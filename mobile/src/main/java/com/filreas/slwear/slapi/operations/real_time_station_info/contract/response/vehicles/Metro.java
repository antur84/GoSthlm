package com.filreas.slwear.slapi.operations.real_time_station_info.contract.response.vehicles;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Andreas on 9/7/2015.
 */
public class Metro {
    @SerializedName("DepartureGroupId")
    private int departureGroupId;

    @SerializedName("Destination")
    private String destination;

    @SerializedName("DisplayTime")
    private String displayTime;

    @SerializedName("GroupOfLine")
    private String groupOfLine;

    @SerializedName("GroupOfLineId")
    private int groupOfLineId;

    @SerializedName("JourneyDirection")
    private int journeyDirection;

    @SerializedName("LineNumber")
    private String lineNumber;

    @SerializedName("PlatformMessage")
    private String platformMessage;

    @SerializedName("SiteId")
    private int siteId;

    @SerializedName("StopAreaName")
    private String stopAreaName;

    @SerializedName("TransportMode")
    private String transportMode;
}
