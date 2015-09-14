package com.filreas.gosthlm.async;

/**
 * Created by Andreas on 9/10/2015.
 */
public interface ISLApiTaskResponseHandler<T> {
    void onTaskComplete(SLApiTaskResult<T> result);
}

