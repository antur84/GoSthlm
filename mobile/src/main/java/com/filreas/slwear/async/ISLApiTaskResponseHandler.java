package com.filreas.slwear.async;

/**
 * Created by Andreas on 9/10/2015.
 */
public interface ISLApiTaskResponseHandler<T> {
    void onTaskComplete(SLApiTaskResult<T> result);
}

