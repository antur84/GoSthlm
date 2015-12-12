package com.filreas.gosthlm;

import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.wearable.activity.WearableActivity;

import com.filreas.gosthlm.datalayer.PhoneActions;
import com.filreas.shared.dto.DeparturesDto;
import com.filreas.shared.utils.DtoSerializer;
import com.filreas.shared.utils.GoSthlmLog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.io.IOException;

public abstract class WearBaseActivity extends WearableActivity implements DataApi.DataListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

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
        Wearable.NodeApi.getConnectedNodes(googleApiClient).setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
            @Override
            public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                GoSthlmLog.d("loller? " + getConnectedNodesResult.getNodes().size());
            }
        });
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GoSthlmLog.d("onRefresh");
                // call phone for fresh data.
                PhoneActions phoneActions = new PhoneActions(googleApiClient);
                phoneActions.refreshAll();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(false);
                    }

                }, 3000);
            }
        });
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
            } else if (event.getType() == DataEvent.TYPE_CHANGED) {
                GoSthlmLog.d("DataItem changed: " + event.getDataItem().getUri());

                try {
                    DeparturesDto departure = (DeparturesDto) DtoSerializer.convertFromBytes(event.getDataItem().getData());
                    this.updateScreenInfo(departure);

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected abstract int getLayoutResource();

    protected abstract void updateScreenInfo(DeparturesDto departuresDto);

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

    private void showErrorDialog(int errorCode) {
        ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        dialogFragment.setArguments(args);
        dialogFragment.show(this.getFragmentManager(), "errordialog");
    }
}
