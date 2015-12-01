package com.filreas.gosthlm.async;

public interface ISLApiTaskResponseHandler<T> {
    void onTaskComplete(SLApiTaskResult<T> result);
}

