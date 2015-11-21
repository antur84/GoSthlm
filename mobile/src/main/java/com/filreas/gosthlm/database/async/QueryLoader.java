package com.filreas.gosthlm.database.async;

import android.content.AsyncTaskLoader;
import android.content.Context;

public class QueryLoader<T> extends AsyncTaskLoader<T> {

    private IGetCommand<T> command;

    public QueryLoader(Context context, IGetCommand<T> command) {
        super(context);
        this.command = command;
        onContentChanged();
    }

    @Override
    public T loadInBackground() {
        return command.get();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()) {
            forceLoad();
        }
    }
}
