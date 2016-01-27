package com.filreas.gosthlm;

import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.filreas.gosthlm.datalayer.IPhoneActionsCallback;
import com.filreas.gosthlm.datalayer.PhoneActions;
import com.filreas.gosthlm.datalayer.PhoneActionsCallbackResult;
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

import org.joda.time.LocalTime;
import org.joda.time.Seconds;

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
    private ProgressBar progressBar;
    private TextView barText;
    private TextView centerText;
    private LocalTime lastStartedRefresh;
    private int timeoutInSecondsForRefreshAll = 9;
    private boolean manualForceRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        barText = (TextView) findViewById(R.id.progressBarText);
        centerText = (TextView) findViewById(R.id.centerText);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                manualForceRefresh = true;
                refreshAllStationsAndDepartures();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoSthlmLog.d("onStart");
        if (!this.isResolvingError && !googleApiClient.isConnected()) {
            GoSthlmLog.d("Connectiong to Data Api in onStart method");
            googleApiClient.connect();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        GoSthlmLog.d("Connected to Google Api Service");
        Wearable.DataApi.addListener(googleApiClient, this);
        Wearable.MessageApi.addListener(googleApiClient, this);
        refreshAllStationsAndDepartures();
    }

    protected void refreshAllStationsAndDepartures() {
        GoSthlmLog.d("refreshAllStationsAndDepartures");
        PhoneActions phoneActions = new PhoneActions(googleApiClient);
        if (manualForceRefresh || !getSwipeDownToRefreshLayout().isRefreshing()) {
            manualForceRefresh = false;
            getSwipeDownToRefreshLayout().setRefreshing(true);

            phoneActions.refreshAll(new IPhoneActionsCallback() {
                @Override
                public void messageResult(PhoneActionsCallbackResult result) {
                    if (result == PhoneActionsCallbackResult.NO_CONNECTED_NODES) {
                        showErrorTextOnMainScreen(getText(R.string.no_connected_nodes));
                        stopSwipeRefreshSpinner();
                        return;
                    }
                    lastStartedRefresh = LocalTime.now();
                    runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (lastStartedRefresh != null) {
                                LocalTime now = LocalTime.now();
                                int secondsSinceUpdateStarted =
                                        Seconds.secondsBetween(lastStartedRefresh, now).getSeconds();
                                boolean updateDidNotCompleteInTime = secondsSinceUpdateStarted > timeoutInSecondsForRefreshAll;
                                GoSthlmLog.d("updateDidNotCompleteInTime? " + updateDidNotCompleteInTime + " sec: " + secondsSinceUpdateStarted);
                                if (updateDidNotCompleteInTime) {
                                    stopSwipeRefreshSpinner();
                                    Toast.makeText(
                                            WearBaseActivity.this,
                                            getText(R.string.refreshFailed),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }, (timeoutInSecondsForRefreshAll + 1) * 1000);

                }
            });
        }
    }

    private void stopSwipeRefreshSpinner() {
        getSwipeDownToRefreshLayout().setRefreshing(false);
    }


    private void runLater(Runnable runnable, int delayMillis) {
        new Handler(Looper.getMainLooper()).postDelayed(runnable, delayMillis);
    }

    @Override
    public void onConnectionSuspended(int i) {
        GoSthlmLog.d("Connected suspended: " + i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        GoSthlmLog.d("onPause");
        disconnectFromDataApi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GoSthlmLog.d("onResume");
        googleApiClient.connect();
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_DELETED) {
                GoSthlmLog.d("DataItem deleted: " + event.getDataItem().getUri());
            }
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                GoSthlmLog.d("DataItem changed: " + event.getDataItem().getUri());
                String path = event.getDataItem().getUri().getPath();
                if (path.equals(DataLayerUri.FAVOURITE_SITE_UPDATE) ||
                        path.equals(DataLayerUri.FAVOURITE_SITE_UPDATE_FAILED)) {
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


    protected SwipeRefreshLayout getSwipeDownToRefreshLayout() {
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
            runLater(new Runnable() {
                @Override
                public void run() {
                    lastStartedRefresh = null;
                    swipeLayout.setRefreshing(false);
                    Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    vibrator.vibrate(100);
                }
            }, 1);
        }
    }

    protected void hideMainProgressBar() {
        progressBar.setVisibility(View.GONE);
        barText.setVisibility(View.GONE);
        centerText.setVisibility(View.GONE);
    }

    protected void showErrorTextOnMainScreen(CharSequence text) {
        hideMainProgressBar();
        centerText.setText(text);
        centerText.setVisibility(View.VISIBLE);
    }

    private void disconnectFromDataApi() {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            GoSthlmLog.d("Disconnected from DataApi");
            Wearable.DataApi.removeListener(googleApiClient, this);
            Wearable.MessageApi.removeListener(googleApiClient, this);
            googleApiClient.disconnect();
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
