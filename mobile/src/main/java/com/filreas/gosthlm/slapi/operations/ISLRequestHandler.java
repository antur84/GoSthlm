package com.filreas.gosthlm.slapi.operations;

import com.filreas.gosthlm.slapi.SLApiException;

public interface ISLRequestHandler<TRequest extends SLApiRequest, TResponse> {
    TResponse get(TRequest request) throws SLApiException;
}
