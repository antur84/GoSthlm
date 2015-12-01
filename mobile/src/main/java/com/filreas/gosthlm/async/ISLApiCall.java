package com.filreas.gosthlm.async;

import com.filreas.gosthlm.slapi.operations.SLApiRequest;

public interface ISLApiCall<TRequest extends SLApiRequest, TResponse> {
    TResponse perform(TRequest request);
}
