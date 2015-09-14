package com.filreas.gosthlm.slapi;

import com.github.kevinsawicki.http.HttpRequest;

/**
 * Created by Andreas on 9/8/2015.
 */
public interface ISLRestApiClient {
    HttpRequest get(String url);
}
