package com.filreas.gosthlm.datalayer;

import android.content.Context;

import com.filreas.gosthlm.activities.Main.DepartureSearch;
import com.filreas.gosthlm.database.helpers.DbHelperWrapper;
import com.filreas.gosthlm.database.helpers.FavouriteSiteHelper;
import com.filreas.gosthlm.database.helpers.TransportationOfChoiceHelper;
import com.filreas.gosthlm.database.model.FavouriteSite;
import com.filreas.gosthlm.database.model.TransportationOfChoice;
import com.filreas.gosthlm.database.queries.FavouriteSitesQuery;
import com.filreas.gosthlm.database.queries.IQuery;
import com.filreas.gosthlm.database.queries.IQueryCallback;
import com.filreas.gosthlm.database.queries.QueryExecuter;
import com.filreas.gosthlm.database.queries.TransportationOfChoiceQuery;
import com.filreas.gosthlm.slapi.SLApi;
import com.filreas.gosthlm.slapi.SLApiKeyFetcher;
import com.filreas.gosthlm.slapi.SLRestApiClient;
import com.filreas.gosthlm.utils.FavouriteSitesLiveUpdater;
import com.filreas.gosthlm.utils.IFavouriteSiteLiveUpdateCallback;
import com.filreas.gosthlm.utils.IFavouriteSitesLiveUpdater;
import com.filreas.shared.dto.FavouriteSiteLiveUpdateDto;
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

    private IFavouriteSitesLiveUpdater favouriteSitesLiveUpdater;

    public DataLayerListenerService(IFavouriteSitesLiveUpdater favouriteSitesLiveUpdater) {
        this();
        this.favouriteSitesLiveUpdater = favouriteSitesLiveUpdater;
    }

    public DataLayerListenerService() {
        super();
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        final String message = new String(messageEvent.getData());
        GoSthlmLog.d("onMessageReceived: " + message);

        if (messageEvent.getPath().equals(DataLayerUri.REFRESH_ALL_DATA_ON_WATCH_REQUESTED)) {
            final Context context = getApplicationContext();

            final GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(Wearable.API)
                    .build();

            ConnectionResult connectionResult =
                    googleApiClient.blockingConnect(30, TimeUnit.SECONDS);

            if (!connectionResult.isSuccess()) {
                GoSthlmLog.d("Failed to connect to GoogleApiClient.");
                return;
            }

            new QueryExecuter<>(new IQueryCallback<List<FavouriteSite>>() {
                @Override
                public void onQueryComplete(final List<FavouriteSite> favouriteSites) {
                    new QueryExecuter<>(new IQueryCallback<TransportationOfChoice>() {
                        @Override
                        public void onQueryComplete(
                                final TransportationOfChoice transportationOfChoice) {
                            final WearActions actions = new WearActions(googleApiClient, context);

                            verifyFavouriteSitesUpdater();
                            favouriteSitesLiveUpdater.updateAllOneAtATime(
                                    favouriteSites,
                                    transportationOfChoice,
                                    new IFavouriteSiteLiveUpdateCallback() {
                                        @Override
                                        public void onFavouriteSiteUpdated(FavouriteSiteLiveUpdateDto site) {
                                            actions.sendFavouriteSiteUpdate(site);
                                        }

                                        @Override
                                        public void allFavouriteSitesInBatchUpdated() {
                                            actions.notifyAllFavouriteSitesUpdated();
                                        }
                                    });
                        }
                    }).execute(
                            new TransportationOfChoiceQuery(
                                    new TransportationOfChoiceHelper(
                                            new DbHelperWrapper(context))));
                }
            }).execute(
                    new FavouriteSitesQuery(
                            new FavouriteSiteHelper(
                                    new DbHelperWrapper(context))));
        } else {
            super.onMessageReceived(messageEvent);
        }
    }

    private void verifyFavouriteSitesUpdater() {
        if (favouriteSitesLiveUpdater == null) {
            this.favouriteSitesLiveUpdater = new FavouriteSitesLiveUpdater(
                    new DepartureSearch(
                            new SLApi(new SLRestApiClient()),
                            new SLApiKeyFetcher(getResources(), getApplicationContext())));
        }
    }
}