package com.filreas.gosthlm.async;

import com.filreas.gosthlm.slapi.operations.SLApiRequest;

/**
 * Created by Andreas on 9/12/2015.
 */
public interface ISLApiCall<TRequest extends SLApiRequest, TResponse> {
    TResponse perform(TRequest request);
}
