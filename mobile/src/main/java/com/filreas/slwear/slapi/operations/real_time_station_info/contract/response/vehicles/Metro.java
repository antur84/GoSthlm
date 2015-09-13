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

    public int getDepartureGroupId() {
        return departureGroupId;
    }

    public String getDestination() {
        return destination;
    }

    public String getDisplayTime() {
        return displayTime;
    }

    public String getGroupOfLine() {
        return groupOfLine;
    }

    public int getGroupOfLineId() {
        return groupOfLineId;
    }

    public int getJourneyDirection() {
        return journeyDirection;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public String getPlatformMessage() {
        return platformMessage;
    }

    public int getSiteId() {
        return siteId;
    }

    public String getStopAreaName() {
        return stopAreaName;
    }

    public String getTransportMode() {
        return transportMode;
    }
}
