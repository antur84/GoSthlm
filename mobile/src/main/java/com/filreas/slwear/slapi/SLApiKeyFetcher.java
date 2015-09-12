package com.filreas.slwear.slapi;

import android.content.Context;
import android.content.res.Resources;

import java.util.HashMap;

/**
 * Created by Andreas on 9/6/2015.
 */
public class SLApiKeyFetcher {

    private Resources resources;
    private Context context;
    private HashMap<String, String> cachedKeys = new HashMap<>();

    public SLApiKeyFetcher(Resources resources, Context context) {

        this.resources = resources;
        this.context = context;
    }

    public String getKey(String key) {

        if (!cachedKeys.containsKey(key)) {
            int idOfApiKey = resources.getIdentifier(key, "string", context.getPackageName());
            if (idOfApiKey == 0) {
                throw new IllegalArgumentException("no resource with key [" + key + "] was found in slapikey.xml");
            }
            String value = resources.getString(idOfApiKey);
            cachedKeys.put(key, value);
        }

        return cachedKeys.get(key);
    }
}
