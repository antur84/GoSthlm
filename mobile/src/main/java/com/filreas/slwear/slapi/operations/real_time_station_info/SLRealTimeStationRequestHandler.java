package com.filreas.slwear.slapi.operations.real_time_station_info;

import com.filreas.slwear.slapi.ISLRestApiClient;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.request.RealTimeRequest;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.response.RealTimeResponse;
import com.filreas.slwear.slapi.serializers.SLGsonThreadSafeSingleton;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;

/**
 * Created by Andreas on 9/8/2015.
 */
public class SLRealTimeStationRequestHandler implements ISLRealTimeStationRequestHandler {
    private final ISLRestApiClient apiClient;

    public SLRealTimeStationRequestHandler(ISLRestApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public RealTimeResponse get(RealTimeRequest request) {
        HttpRequest response = apiClient.get(request.toString());
        return SLGsonThreadSafeSingleton.getInstance().fromJson(new JsonReader(new StringReader(response.body())), RealTimeResponse.class);
    }
}
