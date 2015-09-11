package com.filreas.slwear.slapi.operations;

import java.util.Date;

/**
 * Created by Andreas on 9/11/2015.
 */
public class CachedHttpRequest {
    private String value;
    private Date timeCreated;

    public CachedHttpRequest(String value) {
        this.value = value;
        timeCreated = new Date();
    }

    public String getValue() {
        return value;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }
}
