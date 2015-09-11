package com.filreas.slwear.slapi;

import com.filreas.slwear.slapi.operations.SLRequestHandler;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.request.RealTimeRequest;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.response.RealTimeResponse;

/**
 * Created by Andreas on 9/6/2015.
 */
public class SLApi implements ISLApi {

    private SLRequestHandler<RealTimeRequest, RealTimeResponse> realTimeStationRequestHandler;

    public SLApi(SLRequestHandler realTimeStationRequestHandler) {
        this.realTimeStationRequestHandler = realTimeStationRequestHandler;
    }

    @Override
    public RealTimeResponse getRealTimeStationInfo(RealTimeRequest request) {
        return realTimeStationRequestHandler.get(request);
    }
}
