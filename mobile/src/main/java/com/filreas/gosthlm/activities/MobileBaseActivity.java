package com.filreas.gosthlm.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.filreas.gosthlm.datalayer.MobileClient;
import com.filreas.gosthlm.slapi.ISLApi;
import com.filreas.gosthlm.slapi.ISLApiKeyFetcher;
import com.filreas.gosthlm.slapi.SLApi;
import com.filreas.gosthlm.slapi.SLApiKeyFetcher;
import com.filreas.gosthlm.slapi.SLRestApiClient;

public class MobileBaseActivity extends Activity {
    private MobileClient mobileClient;
    private static final String STATE_RESOLVING_ERROR = "resolving_error";
    protected ISLApi slApi;
    protected ISLApiKeyFetcher slApiKeyFetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        slApiKeyFetcher = new SLApiKeyFetcher(getResources(), this);
        slApi = new SLApi(new SLRestApiClient());

        boolean isResolvingError = savedInstanceState != null
                && savedInstanceState.getBoolean(STATE_RESOLVING_ERROR, false);
        mobileClient = new MobileClient(this, isResolvingError);
        mobileClient.connect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mobileClient.connect();
    }

    @Override
    protected void onStop() {
        mobileClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_RESOLVING_ERROR, mobileClient.getIsResolvingError());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mobileClient.onActivityResult(requestCode, resultCode);
    }

    public void onDialogDismissed() {
        mobileClient.setIsResolvingError(false);
    }

    public ISLApi getSLApi() {
        return slApi;
    }

    public ISLApiKeyFetcher getSLApiKeyFetcher() {
        return slApiKeyFetcher;
    }

    public MobileClient getMobileClient() {
        return mobileClient;
    }
}
