package com.filreas.gosthlm.database.helpers;

import com.filreas.gosthlm.database.async.IGetCommand;
import com.filreas.gosthlm.database.model.FavouriteSite;

import java.util.List;

public class GetFavouriteSitesCommand implements IGetCommand<List<FavouriteSite>> {

    private IFavouriteSite helper;

    public GetFavouriteSitesCommand(IFavouriteSite helper){
        this.helper = helper;
    }

    @Override
    public List<FavouriteSite> get() {
        return helper.readAll();
    }
}
