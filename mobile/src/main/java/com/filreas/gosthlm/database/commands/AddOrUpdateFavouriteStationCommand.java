package com.filreas.gosthlm.database.commands;

import com.filreas.gosthlm.database.helpers.IFavouriteSiteDbHelper;
import com.filreas.gosthlm.database.model.FavouriteSite;
import com.filreas.gosthlm.database.queries.IDataSourceChanged;

public class AddOrUpdateFavouriteStationCommand implements ICommand {

    private IFavouriteSiteDbHelper favouriteSiteHelper;
    private IDataSourceChanged dataSourceChangedListeners;
    private FavouriteSite item;

    public AddOrUpdateFavouriteStationCommand(
            IFavouriteSiteDbHelper favouriteSiteHelper,
            IDataSourceChanged dataSourceChanged,
            FavouriteSite item) {

        this.favouriteSiteHelper = favouriteSiteHelper;
        this.dataSourceChangedListeners = dataSourceChanged;
        this.item = item;
    }

    @Override
    public void execute() {
        FavouriteSite current = favouriteSiteHelper.getBySiteId(item.getSiteId());
        if (current == null) {
            favouriteSiteHelper.create(item);
            dataSourceChangedListeners.dataSourceChanged();
        } else {
            if (current.compareTo(item) != 0) {
                favouriteSiteHelper.update(item);
            }
        }
    }
}
