package com.filreas.gosthlm.database.queries;

import android.content.AsyncTaskLoader;
import android.content.Context;

public class QueryLoader<T> extends AsyncTaskLoader<T> {

    private final IQuery<T> query;

    public QueryLoader(
            Context context,
            IQuery<T> query) {
        super(context);

        this.query = query;
        onContentChanged();
    }

    public QueryLoader(
            Context context,
            IQuery<T> query,
            IDataSourceCallbackListener dataSourceCallbackListener) {
        this(context, query);
        dataSourceCallbackListener.setOnDataChangedListener(new IDataSourceChanged() {

            @Override
            public void dataSourceChanged() {
                onContentChanged();
            }
        });
    }

    @Override
    public T loadInBackground() {
        return query.get();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()) {
            forceLoad();
        }
    }
}
