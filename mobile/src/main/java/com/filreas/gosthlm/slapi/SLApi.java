package com.filreas.gosthlm.slapi;

import android.util.LruCache;

import com.filreas.gosthlm.slapi.operations.CachedHttpRequest;
import com.filreas.gosthlm.slapi.operations.SLRequestHandler;
import com.filreas.gosthlm.slapi.operations.location_finder.contract.request.LocationFinderRequest;
import com.filreas.gosthlm.slapi.operations.location_finder.contract.request.response.LocationFinderResponse;
import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.request.RealTimeRequest;
import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.RealTimeResponse;

public class SLApi implements ISLApi {

    private final SLRequestHandler<RealTimeRequest, RealTimeResponse> realTimeStationRequestHandler;
    private final SLRequestHandler<LocationFinderRequest, LocationFinderResponse> locationFinderRequestHandler;

    public SLApi(
            SLRequestHandler<RealTimeRequest, RealTimeResponse> realTimeStationRequestHandler,
            SLRequestHandler<LocationFinderRequest, LocationFinderResponse> locationFinderRequestHandler) {
        this.realTimeStationRequestHandler = realTimeStationRequestHandler;
        this.locationFinderRequestHandler = locationFinderRequestHandler;
    }

    public SLApi(ISLRestApiClient slRestApiClient) {
        LruCache<String, CachedHttpRequest> cache = new LruCache<>(20);
        this.realTimeStationRequestHandler = new SLRequestHandler<>(slRestApiClient, RealTimeResponse.class, cache);
        this.locationFinderRequestHandler = new SLRequestHandler<>(slRestApiClient, LocationFinderResponse.class, cache);
    }

    @Override
    public RealTimeResponse getRealTimeStationInfo(RealTimeRequest request) throws SLApiException{
        return realTimeStationRequestHandler.get(request);
    }

    @Override
    public LocationFinderResponse getLocations(LocationFinderRequest request) throws SLApiException {
        return locationFinderRequestHandler.get(request);
    }
}
