package com.filreas.gosthlm.datalayer;

import android.content.Context;

import com.filreas.gosthlm.database.helpers.DbHelperWrapper;
import com.filreas.gosthlm.database.helpers.FavouriteSiteHelper;
import com.filreas.gosthlm.database.model.FavouriteSite;
import com.filreas.gosthlm.database.queries.FavouriteSitesQuery;
import com.filreas.gosthlm.database.queries.IQueryCallback;
import com.filreas.gosthlm.database.queries.QueryExecuter;
import com.filreas.shared.utils.DataLayerUri;
import com.filreas.shared.utils.GoSthlmLog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class DataLayerListenerService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        final String message = new String(messageEvent.getData());
        GoSthlmLog.d("onMessageReceived: " + message);

        if (messageEvent.getPath().equals(DataLayerUri.REFRESH_ALL_DATA_ON_WATCH_REQUESTED)) {
            Context context = getApplicationContext();

            final GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(Wearable.API)
                    .build();

            ConnectionResult connectionResult =
                    googleApiClient.blockingConnect(30, TimeUnit.SECONDS);

            if (!connectionResult.isSuccess()) {
                GoSthlmLog.d("Failed to connect to GoogleApiClient.");
                return;
            }
            final WearActions actions = new WearActions(googleApiClient, context);
            FavouriteSitesQuery favouriteSitesQuery = new FavouriteSitesQuery(
                    new FavouriteSiteHelper(
                            new DbHelperWrapper(context)));


            new QueryExecuter<>(new IQueryCallback<List<FavouriteSite>>() {
                @Override
                public void onQueryComplete(List<FavouriteSite> result) {
                    if (result != null) {
                        actions.sendFavouriteSites(result);
                    }
                }
            }).execute(favouriteSitesQuery);
        } else {
            super.onMessageReceived(messageEvent);
        }
    }
}