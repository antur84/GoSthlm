package com.filreas.gosthlm.database.helpers;

public interface ICrud<T> {
    void create(T item);

    void update(T item);

    T read();

    void delete(T item);
}
