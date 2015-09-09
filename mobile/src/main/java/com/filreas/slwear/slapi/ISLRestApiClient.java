package com.filreas.slwear.slapi;

import com.github.kevinsawicki.http.HttpRequest;

/**
 * Created by Andreas on 9/8/2015.
 */
public interface ISLRestApiClient {
    HttpRequest get(String url);
}
