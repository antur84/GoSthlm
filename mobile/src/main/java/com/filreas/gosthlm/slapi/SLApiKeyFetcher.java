package com.filreas.gosthlm.slapi;

import android.content.Context;
import android.content.res.Resources;

import java.util.HashMap;

public class SLApiKeyFetcher implements ISLApiKeyFetcher {

    private final Resources resources;
    private final Context context;
    private final HashMap<String, String> cachedKeys = new HashMap<>();

    public SLApiKeyFetcher(Resources resources, Context context) {

        this.resources = resources;
        this.context = context;
    }

    @Override
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
