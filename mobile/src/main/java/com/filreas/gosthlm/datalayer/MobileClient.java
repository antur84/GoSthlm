package com.filreas.gosthlm.datalayer;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.os.Bundle;

import com.filreas.gosthlm.activities.BaseMobileActivity;
import com.filreas.shared.utils.GoSthlmLog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

public class MobileClient {

    private static final String DIALOG_ERROR = "dialog_error";
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    private final GoogleApiClient googleApiClient;
    private Activity activity;
    private boolean isResolvingError = false;

    public MobileClient(final Activity activity, boolean isResolvingError) {
        this.activity = activity;
        this.isResolvingError = isResolvingError;

        googleApiClient = new GoogleApiClient.Builder(activity.getApplicationContext())
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                        GoSthlmLog.d("onConnected: " + connectionHint);
                        // Connected to Google Play services!
                        // The good stuff goes here.
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                        GoSthlmLog.d("onConnectionSuspended: " + cause);
                        // The connection has been interrupted.
                        // Disable any UI components that depend on Google APIs
                        // until onConnected() is called.
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        GoSthlmLog.d("onConnectionFailed: " + result);
                        if (MobileClient.this.isResolvingError) {
                            return;
                        }

                        if (result.hasResolution()) {
                            try {
                                MobileClient.this.isResolvingError = true;
                                result.startResolutionForResult(MobileClient.this.activity, REQUEST_RESOLVE_ERROR);
                            } catch (IntentSender.SendIntentException e) {
                                // There was an error with the resolution intent. Try again.
                                googleApiClient.connect();
                            }
                        } else {
                            // Critical error.
                            showErrorDialog(result.getErrorCode());
                            MobileClient.this.isResolvingError = true;
                        }
                    }
                })

                .addApiIfAvailable(Wearable.API)
                .build();
    }

    private void showErrorDialog(int errorCode) {
        ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        dialogFragment.setArguments(args);
        dialogFragment.show(this.activity.getFragmentManager(), "errordialog");
    }

    public boolean getIsResolvingError() {
        return isResolvingError;
    }

    public void setIsResolvingError(boolean isResolvingError) {
        this.isResolvingError = isResolvingError;
    }

    public void onActivityResult(int requestCode, int resultCode) {
        if (requestCode == REQUEST_RESOLVE_ERROR) {
            setIsResolvingError(false);
            if (resultCode == Activity.RESULT_OK) {
                if (!googleApiClient.isConnecting() && !googleApiClient.isConnected()) {
                    googleApiClient.connect();
                }
            }
        }
    }

    public static class ErrorDialogFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int errorCode = this.getArguments().getInt(DIALOG_ERROR);
            return GoogleApiAvailability.getInstance().getErrorDialog(
                    this.getActivity(), errorCode, REQUEST_RESOLVE_ERROR);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            ((BaseMobileActivity) getActivity()).onDialogDismissed();
        }
    }

    public void connect() {
        if (!isResolvingError) {
            googleApiClient.connect();
        }
    }

    public void disconnect() {
        googleApiClient.disconnect();
    }
}
