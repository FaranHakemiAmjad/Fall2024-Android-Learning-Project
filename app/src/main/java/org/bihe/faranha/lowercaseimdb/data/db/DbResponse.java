package org.bihe.faranha.lowercaseimdb.data.db;

public interface DbResponse<T> {

    void onSuccess(T t);

    void onError(Error error);
}
