package com.filreas.gosthlm.database.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class BasicCrud<T> extends SQLiteOpenHelper implements ICrud<T> {

    protected BasicCrud(Context context, String databaseName, int databaseVersion) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void create(T item) {
        throw new UnsupportedOperationException("create not implemented");
    }

    @Override
    public void update(T item) {
        throw new UnsupportedOperationException("update not implemented");
    }

    @Override
    public T read() {
        throw new UnsupportedOperationException("read not implemented");
    }

    @Override
    public void delete(T item) {
        throw new UnsupportedOperationException("delete not implemented");
    }
}
