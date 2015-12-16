package com.filreas.gosthlm.activities.Main;

import com.filreas.gosthlm.database.model.FavouriteSite;
import com.filreas.gosthlm.slapi.operations.real_time_station_info.contract.response.RealTimeResponse;

public interface OnDepartureSearchListener {
    void onSearchCompleted(FavouriteSite site, RealTimeResponse response);
}
