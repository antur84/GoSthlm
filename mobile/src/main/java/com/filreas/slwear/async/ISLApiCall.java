package com.filreas.slwear.async;

import com.filreas.slwear.slapi.operations.SLApiRequest;

/**
 * Created by Andreas on 9/12/2015.
 */
public interface ISLApiCall<TRequest extends SLApiRequest, TResponse> {
    TResponse perform(TRequest request);
}
