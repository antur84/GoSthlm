package com.filreas.gosthlm.slapi.operations;

import java.util.Date;

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
