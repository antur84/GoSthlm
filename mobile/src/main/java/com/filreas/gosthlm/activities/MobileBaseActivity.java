package com.filreas.gosthlm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.filreas.gosthlm.R;
import com.filreas.gosthlm.activities.favourites.Favourites;
import com.filreas.gosthlm.datalayer.MobileClient;
import com.filreas.gosthlm.slapi.ISLApi;
import com.filreas.gosthlm.slapi.ISLApiKeyFetcher;
import com.filreas.gosthlm.slapi.SLApi;
import com.filreas.gosthlm.slapi.SLApiKeyFetcher;
import com.filreas.gosthlm.slapi.SLRestApiClient;

public abstract class MobileBaseActivity extends AppCompatActivity {
    private MobileClient mobileClient;
    private static final String STATE_RESOLVING_ERROR = "resolving_error";
    protected ISLApi slApi;
    protected ISLApiKeyFetcher slApiKeyFetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        initToolBar();

        slApiKeyFetcher = new SLApiKeyFetcher(getResources(), this);
        slApi = new SLApi(new SLRestApiClient());

        boolean isResolvingError = savedInstanceState != null
                && savedInstanceState.getBoolean(STATE_RESOLVING_ERROR, false);
        mobileClient = new MobileClient(this, isResolvingError);
        mobileClient.connect();
    }

    protected abstract int getLayoutResource();

    private void initToolBar() {
        Toolbar topToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_favourites:
                startActivity(new Intent(this, Favourites.class));
                return true;
            case R.id.action_help:
                startActivity(new Intent(this, Help.class));
                return true;
            case R.id.action_about:
                startActivity(new Intent(this, About.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    protected void enableHomeAsUpNavigation() {
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
