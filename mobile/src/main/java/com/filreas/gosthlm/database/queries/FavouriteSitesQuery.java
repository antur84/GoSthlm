package com.filreas.gosthlm.database.queries;

import com.filreas.gosthlm.database.helpers.IFavouriteSiteDbHelper;
import com.filreas.gosthlm.database.model.FavouriteSite;

import java.util.List;

public class FavouriteSitesQuery implements IQuery<List<FavouriteSite>> {

    private final IFavouriteSiteDbHelper helper;

    public FavouriteSitesQuery(IFavouriteSiteDbHelper helper) {
        this.helper = helper;
    }

    @Override
    public List<FavouriteSite> get() {
        return helper.readAll();
    }
}
