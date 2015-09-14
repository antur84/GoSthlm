package com.filreas.gosthlm.slapi.operations.location_finder.contract.request.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Andreas on 9/12/2015.
 */
public class Site {
    @SerializedName("Name")
    private String name;

    @SerializedName("SiteId")
    private int siteId;

    @SerializedName("Type")
    private String type;

    @SerializedName("X")
    private String x;

    @SerializedName("Y")
    private String y;

    public String getName() {
        return name;
    }

    public int getSiteId() {
        return siteId;
    }

    public String getType() {
        return type;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }
}
