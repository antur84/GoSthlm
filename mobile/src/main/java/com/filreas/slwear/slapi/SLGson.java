package com.filreas.slwear.slapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Andreas on 9/9/2015.
 */
public class SLGson {
    private final GsonBuilder gsonBuilder = new GsonBuilder();
    private final Gson gson;

    public SLGson() {
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        gson = gsonBuilder.create();
    }

    public Gson getInstance() {
        return gson;
    }
}
