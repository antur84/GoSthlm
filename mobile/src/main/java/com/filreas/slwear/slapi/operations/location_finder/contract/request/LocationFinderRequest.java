package com.filreas.slwear.slapi.operations.location_finder.contract.request;

import com.filreas.slwear.slapi.operations.ResponseCacheStrategy;
import com.filreas.slwear.slapi.operations.ResponseFormat;
import com.filreas.slwear.slapi.operations.SLApiRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

/**
 * Created by Andreas on 9/12/2015.
 */
public class LocationFinderRequest extends SLApiRequest {

    private final String url = "typeahead.%s?key=%s&searchstring=%s&stationsonly=%s&maxresults=%s";
    private String searchString;
    private boolean stationsOnly;
    private int maxResults;

    public LocationFinderRequest(ResponseFormat responseFormat,
                                 String key,
                                 String searchString,
                                 ResponseCacheStrategy cacheStrategy) {

        super(responseFormat, key, cacheStrategy);
        this.searchString = searchString;
        this.stationsOnly = true;
        this.maxResults = 10;
    }

    public LocationFinderRequest(ResponseFormat responseFormat,
                                 String key,
                                 String searchString,
                                 ResponseCacheStrategy cacheStrategy,
                                 boolean stationsOnly,
                                 int maxResults) {

        this(responseFormat, key, searchString, cacheStrategy);

        if (maxResults < 1 || maxResults > 50) {
            throw new IllegalArgumentException("maxResults must be between 1 and 50");
        }
        this.stationsOnly = stationsOnly;
        this.maxResults = maxResults;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, url, getResponseFormat(), getKey(), getSearchStringSafe(), stationsOnly, maxResults);
    }

    @Override
    public String getCacheKey() {
        return String.format(Locale.US, "%s%s%s%s%s%s", LocationFinderRequest.class.getCanonicalName(), getResponseFormat(), getKey(), getSearchStringSafe(), stationsOnly, maxResults);
    }

    public String getSearchStringSafe() {
        if (searchString != null) {
            searchString = searchString.trim();
        }
        String encodedSearchString = null;
        try {
            encodedSearchString = URLEncoder.encode(searchString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            encodedSearchString = searchString.replace(" ", "%20");
        }

        return encodedSearchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
