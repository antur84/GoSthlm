package com.filreas.slwear.slapi.operations.real_time_station_info;

import com.filreas.slwear.slapi.operations.real_time_station_info.contract.request.RealTimeRequest;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.response.RealTimeResponse;

/**
 * Created by Andreas on 9/8/2015.
 */
public interface ISLRealTimeStationRequestHandler {
    RealTimeResponse get(RealTimeRequest request);
}
