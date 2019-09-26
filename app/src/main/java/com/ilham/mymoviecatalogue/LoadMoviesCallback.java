package com.ilham.mymoviecatalogue;

import android.database.Cursor;

public interface LoadMoviesCallback {
    void preExecute();
    void postExecute(Cursor movies);
}
