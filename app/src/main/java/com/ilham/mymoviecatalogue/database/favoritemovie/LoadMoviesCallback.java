package com.ilham.mymoviecatalogue.database.favoritemovie;

import android.database.Cursor;

public interface LoadMoviesCallback {
    void preExecute();

    void postExecute(Cursor movies);
}
