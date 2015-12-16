package com.filreas.gosthlm.slapi.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Type;

public class SLGson {
    private final Gson gson;

    protected SLGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        gson = gsonBuilder.create();
    }

    public <T> T fromJson(JsonReader reader, Type typeOfT) {
        return gson.fromJson(reader, typeOfT);
    }
}
