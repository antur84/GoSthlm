package com.filreas.gosthlm.async;

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
