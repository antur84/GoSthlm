package com.filreas.gosthlm.utils;

import com.filreas.shared.dto.FavouriteSiteLiveUpdateDto;

public interface IFavouriteSiteLiveUpdateCallback {
    void onFavouriteSiteUpdated(FavouriteSiteLiveUpdateDto site);

    void allFavouriteSitesInBatchUpdated();
}
