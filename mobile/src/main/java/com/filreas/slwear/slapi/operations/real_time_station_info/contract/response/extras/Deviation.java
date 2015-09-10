package com.filreas.slwear.slapi.operations.real_time_station_info.contract.response.extras;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Andreas on 9/7/2015.
 */
public class Deviation {

    @SerializedName("Consequence")
    private String consequence;

    @SerializedName("ImportanceLevel")
    private int importanceLevel;

    @SerializedName("Text")
    private String text;
}
