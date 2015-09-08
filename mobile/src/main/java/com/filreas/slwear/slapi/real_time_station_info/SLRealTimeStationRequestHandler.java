package com.filreas.slwear.slapi.real_time_station_info;

import com.filreas.slwear.slapi.ISLRestApiClient;
import com.filreas.slwear.slapi.SLGson;
import com.filreas.slwear.slapi.contract.request.RealTimeRequest;
import com.filreas.slwear.slapi.contract.response.RealTimeResponse;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;

/**
 * Created by Andreas on 9/8/2015.
 */
public class SLRealTimeStationRequestHandler implements ISLRealTimeStationRequestHandler {

    private static SLGson gson = new SLGson();
    private final ISLRestApiClient apiClient;

    public SLRealTimeStationRequestHandler(ISLRestApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public RealTimeResponse get(RealTimeRequest request) {
        HttpRequest response = apiClient.get(request.toString());
        return gson.getInstance().fromJson(new JsonReader(new StringReader(response.body())), RealTimeResponse.class);
    }
}
