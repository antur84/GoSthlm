package com.filreas.slwear.async;

/**
 * Created by Andreas on 9/10/2015.
 */
public class SLApiTaskResult<T> {
    private Exception exception;
    private T response;

    public SLApiTaskResult(T response) {
        this.response = response;
    }

    public SLApiTaskResult(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    public T getResponse() {
        return response;
    }
}
