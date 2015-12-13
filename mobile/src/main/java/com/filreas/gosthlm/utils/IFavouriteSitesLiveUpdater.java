package com.filreas.gosthlm.utils;

import com.filreas.gosthlm.database.model.FavouriteSite;
import com.filreas.gosthlm.database.model.TransportationOfChoice;

import java.util.List;

public interface IFavouriteSitesLiveUpdater {
    void updateAllOneAtATime(List<FavouriteSite> favouriteSites,
                             TransportationOfChoice transportationOfChoice,
                             IFavouriteSiteLiveUpdateCallback callback);
}
