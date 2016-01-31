package com.filreas.gosthlm.activities;

import android.content.Context;

import com.filreas.gosthlm.database.commands.AddOrUpdateFavouriteStationCommand;
import com.filreas.gosthlm.database.commands.CommandExecuter;
import com.filreas.gosthlm.database.helpers.DbHelperWrapper;
import com.filreas.gosthlm.database.helpers.FavouriteSiteHelper;
import com.filreas.gosthlm.database.model.FavouriteSite;
import com.filreas.gosthlm.database.queries.IDataSourceChanged;
import com.filreas.gosthlm.slapi.operations.location_finder.contract.request.response.Site;
import com.filreas.gosthlm.utils.OnItemClickListener;

public class FavouriteSiteSaveOnClickListener implements OnItemClickListener<Site> {

    private Context context;
    private IFavouriteSiteSaveOnClick callback;

    public FavouriteSiteSaveOnClickListener(
            Context context,
            IFavouriteSiteSaveOnClick callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void onClick(Site site) {
        final FavouriteSite favouriteSite = new FavouriteSite(
                -1,
                site.getName(),
                site.getSiteId(),
                site.getType(),
                site.getX(),
                site.getY());
        new CommandExecuter().execute(
                new AddOrUpdateFavouriteStationCommand(
                        new FavouriteSiteHelper(
                                new DbHelperWrapper(
                                        context)),
                        new IDataSourceChanged() {
                            @Override
                            public void dataSourceChanged() {
                                if (callback != null) {
                                    callback.siteSaved(favouriteSite);
                                }
                            }
                        },
                        favouriteSite));
    }
}
