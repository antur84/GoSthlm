package com.filreas.gosthlm.database.queries;

import android.os.AsyncTask;

public class QueryExecuter<T> extends AsyncTask<IQuery<T>, Void, T> {

    private IQueryCallback<T> callback;

    public QueryExecuter(IQueryCallback<T> callback) {
        this.callback = callback;
    }

    @SafeVarargs
    @Override
    protected final T doInBackground(IQuery<T>... params) {
        if (params == null || params.length != 1) {
            throw new UnsupportedOperationException("can only do one query per QueryExecter");
        }

        return params[0].get();
    }

    @Override
    protected void onPostExecute(T result) {
        super.onPostExecute(result);

        if (callback != null) {
            callback.onQueryComplete(result);
        }
    }
}