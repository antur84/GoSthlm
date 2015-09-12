package com.filreas.slwear.async;

import android.os.AsyncTask;

import com.filreas.slwear.slapi.operations.SLApiRequest;
import com.github.kevinsawicki.http.HttpRequest;

/**
 * Created by Andreas on 9/10/2015.
 */
public class SLApiRequestTask<TRequest extends SLApiRequest, TResponse> extends AsyncTask<TRequest, Void, TResponse> {

    private ISLApiCall<TRequest, TResponse> slApiCall;
    private ISLApiTaskResponseHandler responseHandler;
    private HttpRequest.HttpRequestException exception;

    public SLApiRequestTask(ISLApiCall<TRequest, TResponse> slApiCall, ISLApiTaskResponseHandler responseHandler) {
        this.slApiCall = slApiCall;
        this.responseHandler = responseHandler;
    }

    @Override
    protected TResponse doInBackground(TRequest... params) {
        if (params.length != 1) {
            throw new IllegalArgumentException("Only one request per task instance allowed");
        }

        try {
            return slApiCall.perform(params[0]);
        } catch (HttpRequest.HttpRequestException exception) {
            this.exception = exception;
            return null;
        }
    }

    @Override
    protected void onPostExecute(TResponse response) {
        if (response == null) {
            responseHandler.onTaskComplete(new SLApiTaskResult<>(exception));
        } else {
            responseHandler.onTaskComplete(new SLApiTaskResult<>(response));
        }
    }

    @Override
    protected void onCancelled(TResponse response) {
        // do nothing
    }
}