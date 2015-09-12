package com.filreas.slwear.slapi;

import android.util.LruCache;

import com.filreas.slwear.slapi.operations.CachedHttpRequest;
import com.filreas.slwear.slapi.operations.SLRequestHandler;
import com.filreas.slwear.slapi.operations.location_finder.contract.request.LocationFinderRequest;
import com.filreas.slwear.slapi.operations.location_finder.contract.request.response.LocationFinderResponse;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.request.RealTimeRequest;
import com.filreas.slwear.slapi.operations.real_time_station_info.contract.response.RealTimeResponse;

/**
 * Created by Andreas on 9/6/2015.
 */
public class SLApi implements ISLApi {

    private final LruCache<String, CachedHttpRequest> cache = new LruCache<>(20);
    private SLRequestHandler<RealTimeRequest, RealTimeResponse> realTimeStationRequestHandler;
    private SLRequestHandler<LocationFinderRequest, LocationFinderResponse> locationFinderRequestHandler;

    public SLApi(
            SLRequestHandler<RealTimeRequest, RealTimeResponse> realTimeStationRequestHandler,
            SLRequestHandler<LocationFinderRequest, LocationFinderResponse> locationFinderRequestHandler) {
        this.realTimeStationRequestHandler = realTimeStationRequestHandler;
        this.locationFinderRequestHandler = locationFinderRequestHandler;
    }

    public SLApi(ISLRestApiClient slRestApiClient) {
        this.realTimeStationRequestHandler = new SLRequestHandler<>(slRestApiClient, RealTimeResponse.class, cache);
        this.locationFinderRequestHandler = new SLRequestHandler<>(slRestApiClient, LocationFinderResponse.class, cache);
    }

    @Override
    public RealTimeResponse getRealTimeStationInfo(RealTimeRequest request) {
        return realTimeStationRequestHandler.get(request);
    }

    @Override
    public LocationFinderResponse getLocations(LocationFinderRequest request) {
        return locationFinderRequestHandler.get(request);
    }
}
