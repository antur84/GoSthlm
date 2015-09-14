package com.filreas.gosthlm.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.filreas.gosthlm.slapi.ISLApi;
import com.filreas.gosthlm.slapi.ISLApiKeyFetcher;
import com.filreas.gosthlm.slapi.SLApi;
import com.filreas.gosthlm.slapi.SLApiKeyFetcher;
import com.filreas.gosthlm.slapi.SLRestApiClient;

/**
 * Created by Andreas on 9/12/2015.
 */
public class BaseMobileActivity extends AppCompatActivity {
    protected ISLApi slApi;
    protected ISLApiKeyFetcher slApiKeyFetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        slApiKeyFetcher = new SLApiKeyFetcher(getResources(), this);
        slApi = new SLApi(new SLRestApiClient());
    }

    public ISLApi getSlApi() {
        return slApi;
    }

    public ISLApiKeyFetcher getSlApiKeyFetcher() {
        return slApiKeyFetcher;
    }
}
