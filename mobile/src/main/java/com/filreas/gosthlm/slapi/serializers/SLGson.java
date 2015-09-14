package com.filreas.gosthlm.slapi.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Type;

/**
 * Created by Andreas on 9/9/2015.
 */
public class SLGson {
    private final GsonBuilder gsonBuilder = new GsonBuilder();
    private final Gson gson;

    protected SLGson() {
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        gson = gsonBuilder.create();
    }

    public <T> T fromJson(JsonReader reader, Type typeOfT) {
        return gson.fromJson(reader, typeOfT);
    }
}
