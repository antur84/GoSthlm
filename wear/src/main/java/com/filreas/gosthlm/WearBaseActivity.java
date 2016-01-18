package com.filreas.gosthlm;

import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.wearable.activity.WearableActivity;

import com.filreas.gosthlm.datalayer.PhoneActions;
import com.filreas.shared.dto.FavouriteSiteLiveUpdateDto;
import com.filreas.shared.utils.DataLayerUri;
import com.filreas.shared.utils.DtoSerializer;
import com.filreas.shared.utils.GoSthlmLog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

import java.io.IOException;

public abstract class WearBaseActivity extends WearableActivity
        implements DataApi.DataListener,
        MessageApi.MessageListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String DIALOG_ERROR = "dialog_error";
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    private GoogleApiClient googleApiClient;
    private boolean isResolvingError;
    private SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        Wearable.MessageApi.addListener(googleApiClient, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!this.isResolvingError) {
            googleApiClient.connect();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        GoSthlmLog.d("Connected to Google Api Service");
        Wearable.DataApi.addListener(googleApiClient, this);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshAllStationsAndDepartures();
            }
        });
        refreshAllStationsAndDepartures();
    }

    private void refreshAllStationsAndDepartures() {
        GoSthlmLog.d("onRefresh");
        PhoneActions phoneActions = new PhoneActions(googleApiClient);
        phoneActions.refreshAll();
    }

    @Override
    public void onConnectionSuspended(int i) {
        GoSthlmLog.d("Connected suspended: " + i);
    }

    @Override
    protected void onStop() {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            Wearable.DataApi.removeListener(googleApiClient, this);
            googleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_DELETED) {
                GoSthlmLog.d("DataItem deleted: " + event.getDataItem().getUri());
            }
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                GoSthlmLog.d("DataItem changed: " + event.getDataItem().getUri());
                if(event.getDataItem().getUri().getPath().equals(DataLayerUri.FAVOURITE_SITE_UPDATE)){
                    try {
                        FavouriteSiteLiveUpdateDto updatedSite =
                                (FavouriteSiteLiveUpdateDto) DtoSerializer.convertFromBytes(
                                        event.getDataItem().getData());
                        this.updateScreenInfo(updatedSite);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                if(event.getDataItem().getUri().getPath().equals(DataLayerUri.FAVOURITE_SITE_UPDATE_FAILED)){
                    try {
                        FavouriteSiteLiveUpdateDto updatedSite =
                                (FavouriteSiteLiveUpdateDto) DtoSerializer.convertFromBytes(
                                        event.getDataItem().getData());
                        this.updateScreenInfo(updatedSite);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    protected SwipeRefreshLayout getSwipeLayout() {
        return swipeLayout;
    }

    protected abstract int getLayoutResource();

    protected abstract void updateScreenInfo(FavouriteSiteLiveUpdateDto updatedSite);

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        GoSthlmLog.d("onConnectionFailed: " + result);
        if (this.isResolvingError) {
            return;
        }

        if (result.hasResolution()) {
            try {
                this.isResolvingError = true;
                result.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                // There was an error with the resolution intent. Try again.
                googleApiClient.connect();
            }
        } else {
            // Critical error.
            showErrorDialog(result.getErrorCode());
            this.isResolvingError = true;
        }
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if (messageEvent.getPath().equals(DataLayerUri.REFRESH_ALL_DATA_ON_WATCH_COMPLETED)) {
            GoSthlmLog.d("onMessageReceived REFRESH_ALL_DATA_ON_WATCH_COMPLETED");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    swipeLayout.setRefreshing(false);
                }
            });
        }
    }

    private void showErrorDialog(int errorCode) {
        ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        dialogFragment.setArguments(args);
        dialogFragment.show(this.getFragmentManager(), "errordialog");
    }
}
