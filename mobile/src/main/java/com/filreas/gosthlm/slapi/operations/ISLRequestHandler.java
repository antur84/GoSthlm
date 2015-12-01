package com.filreas.gosthlm.slapi.operations;

public interface ISLRequestHandler<TRequest extends SLApiRequest, TResponse> {
    TResponse get(TRequest request);
}
