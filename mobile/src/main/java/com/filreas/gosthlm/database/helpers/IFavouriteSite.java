package com.filreas.gosthlm.database.helpers;

import com.filreas.gosthlm.database.model.FavouriteSite;

import java.util.List;

public interface IFavouriteSite extends ICrud<FavouriteSite> {
    List<FavouriteSite> readAll();
}
