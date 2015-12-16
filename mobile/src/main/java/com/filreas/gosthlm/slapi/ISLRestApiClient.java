package com.filreas.gosthlm.slapi;

import com.github.kevinsawicki.http.HttpRequest;

public interface ISLRestApiClient {
    HttpRequest get(String url);
}
