package com.filreas.gosthlm.activities.Main;

import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.RealTimeResponse;

/**
 * Created by Andreas on 9/13/2015.
 */
public interface OnDepartureSearchListener {
    void onSearchCompleted(RealTimeResponse response);
}
