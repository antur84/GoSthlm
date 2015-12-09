package com.filreas.gosthlm.database.helpers;

import com.filreas.gosthlm.database.model.FavouriteSite;

import java.util.List;

public interface IFavouriteSiteDbHelper {
    void create(FavouriteSite favouriteSite);

    void update(FavouriteSite favouriteSite);

    void remove(FavouriteSite favouriteSite);

    List<FavouriteSite> readAll();

    FavouriteSite getBySiteId(int id);
}
