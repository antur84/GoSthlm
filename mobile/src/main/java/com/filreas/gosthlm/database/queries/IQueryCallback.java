package com.filreas.gosthlm.database.queries;

public interface IQueryCallback<T> {
    void onQueryComplete(T result);
}
