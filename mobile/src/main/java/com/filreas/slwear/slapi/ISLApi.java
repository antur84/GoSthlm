package com.filreas.slwear.slapi;

import com.filreas.slwear.slapi.operations.location_finder.contract.request.LocationFinderRequest;
import com.filreas.slwear.slapi.operations.location_finder.contract.request.response.LocationFinderResponse;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.request.RealTimeRequest;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.response.RealTimeResponse;

/**
 * Created by Andreas on 9/8/2015.
 */
public interface ISLApi {
    RealTimeResponse getRealTimeStationInfo(RealTimeRequest request);

    LocationFinderResponse getLocations(LocationFinderRequest request);
}
