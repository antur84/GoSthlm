package com.filreas.slwear.slapi;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by Andreas on 9/6/2015.
 */
public class SLApiKeyFetcher {

    private Resources resources;
    private Context context;
    private String cachedKey;

    public SLApiKeyFetcher(Resources resources, Context context) {

        this.resources = resources;
        this.context = context;
    }

    public String getKey() {

        if (cachedKey == null) {
            int idOfApiKey = resources.getIdentifier("slapikey", "string", context.getPackageName());
            String key = resources.getString(idOfApiKey);
            cachedKey = key;
        }

        return cachedKey;
    }
}
