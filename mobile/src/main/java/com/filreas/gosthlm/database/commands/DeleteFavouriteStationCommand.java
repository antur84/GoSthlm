package com.filreas.gosthlm.database.commands;

import com.filreas.gosthlm.database.helpers.IFavouriteSiteDbHelper;
import com.filreas.gosthlm.database.model.FavouriteSite;
import com.filreas.gosthlm.database.queries.IDataSourceChanged;

public class DeleteFavouriteStationCommand implements ICommand {

    private final IFavouriteSiteDbHelper favouriteSiteHelper;
    private final IDataSourceChanged dataSourceChangedListener;
    private final FavouriteSite item;

    public DeleteFavouriteStationCommand(
            IFavouriteSiteDbHelper favouriteSiteHelper,
            IDataSourceChanged dataSourceChanged,
            FavouriteSite item) {

        this.favouriteSiteHelper = favouriteSiteHelper;
        this.dataSourceChangedListener = dataSourceChanged;
        this.item = item;
    }

    @Override
    public void execute() {
        FavouriteSite current = favouriteSiteHelper.getBySiteId(item.getSiteId());
        if (current != null) {
            favouriteSiteHelper.remove(item);
            if(dataSourceChangedListener != null) {
                dataSourceChangedListener.dataSourceChanged();
            }
        }
    }
}
