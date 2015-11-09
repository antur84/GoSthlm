package com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.vehicles;

import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.extras.Deviation;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by Andreas on 9/7/2015.
 */
public class TransportType {

    @SerializedName("SiteId")
    private int siteId;

    @SerializedName("TransportMode")
    private String transportMode;

    @SerializedName("StopAreaName")
    private String stopAreaName;

    @SerializedName("StopAreaNumber")
    private int stopAreaNumber;

    @SerializedName("StopPointNumber")
    private int stopPointNumber;

    @SerializedName("LineNumber")
    private String lineNumber;

    @SerializedName("Destination")
    private String destination;

    @SerializedName("TimeTabledDateTime")
    private Date timeTabledDateTime;

    @SerializedName("ExpectedDateTime")
    private Date expectedDateTime;

    @SerializedName("DisplayTime")
    private String displayTime;

    @SerializedName("Deviations")
    private List<Deviation> deviations;

    @SerializedName("JourneyDirection")
    private int journeyDirection;

    public String getDestination() {
        return destination;
    }

    public String getDisplayTime() {
        return displayTime;
    }
}
