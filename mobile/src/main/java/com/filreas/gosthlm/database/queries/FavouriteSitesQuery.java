package com.filreas.gosthlm.database.queries;

import com.filreas.gosthlm.database.helpers.IFavouriteSiteDbHelper;
import com.filreas.gosthlm.database.model.FavouriteSite;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FavouriteSitesQuery implements IQuery<List<FavouriteSite>> {

    private final IFavouriteSiteDbHelper helper;

    public FavouriteSitesQuery(IFavouriteSiteDbHelper helper) {
        this.helper = helper;
    }

    @Override
    public List<FavouriteSite> get() {
        List<FavouriteSite> favouriteSites = helper.readAll();
        Collections.sort(favouriteSites, new Comparator<FavouriteSite>() {
            @Override
            public int compare(FavouriteSite lhs, FavouriteSite rhs) {
                return lhs.getSortPosition() - rhs.getSortPosition();
            }
        });
        return favouriteSites;
    }
}
