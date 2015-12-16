package com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.extras;

import com.google.gson.annotations.SerializedName;

public class Deviation {

    @SerializedName("Consequence")
    private String consequence;

    @SerializedName("ImportanceLevel")
    private int importanceLevel;

    @SerializedName("Text")
    private String text;

    public String getConsequence() {
        return consequence;
    }

    public int getImportanceLevel() {
        return importanceLevel;
    }

    public String getText() {
        return text;
    }
}
