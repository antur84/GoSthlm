package com.filreas.slwear.slapi;

import com.filreas.slwear.slapi.contract.request.RealTimeRequest;
import com.filreas.slwear.slapi.contract.response.RealTimeResponse;
import com.filreas.slwear.slapi.real_time_station_info.SLRealTimeStationRequestHandler;

/**
 * Created by Andreas on 9/6/2015.
 */
public class SLApi implements ISLApi {

    private SLRealTimeStationRequestHandler realTimeStationRequestHandler;

    public SLApi(SLRealTimeStationRequestHandler realTimeStationRequestHandler) {

        this.realTimeStationRequestHandler = realTimeStationRequestHandler;
    }

    @Override
    public RealTimeResponse getRealTimeStationInfo(RealTimeRequest request) {
        return realTimeStationRequestHandler.get(request);
    }
}
