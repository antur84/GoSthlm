package com.filreas.slwear.slapi;

import com.filreas.slwear.slapi.contract.request.RealTimeRequest;
import com.filreas.slwear.slapi.contract.response.RealTimeResponse;

/**
 * Created by Andreas on 9/8/2015.
 */
public interface ISLApi {
    RealTimeResponse getRealTimeStationInfo(RealTimeRequest request);
}
