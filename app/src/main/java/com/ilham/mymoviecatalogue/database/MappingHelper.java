package com.ilham.mymoviecatalogue.database;

import android.database.Cursor;

import com.ilham.mymoviecatalogue.items.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.ilham.mymoviecatalogue.database.favoritemovie.DatabaseContract.MovieColumns.BACKDROP;
import static com.ilham.mymoviecatalogue.database.favoritemovie.DatabaseContract.MovieColumns.OVERVIEW;
import static com.ilham.mymoviecatalogue.database.favoritemovie.DatabaseContract.MovieColumns.POSTER;
import static com.ilham.mymoviecatalogue.database.favoritemovie.DatabaseContract.MovieColumns.RELEASED;
import static com.ilham.mymoviecatalogue.database.favoritemovie.DatabaseContract.MovieColumns.SCORE;
import static com.ilham.mymoviecatalogue.database.favoritemovie.DatabaseContract.MovieColumns.TITLE;

public class MappingHelper {

    public static ArrayList<Movie> mapCursorToArrayList(Cursor moviesCursor) {

        ArrayList<Movie> moviesList = new ArrayList<>();

        if (moviesCursor != null) {
            while (moviesCursor.moveToNext()) {
                int id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(_ID));
                String title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(TITLE));
                String released = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(RELEASED));
                String overview = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(OVERVIEW));
                String poster = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(POSTER));
                String backdrop = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(BACKDROP));
                double score = moviesCursor.getDouble(moviesCursor.getColumnIndexOrThrow(SCORE));
                moviesList.add(new Movie(id, title, released, poster, overview, backdrop, score));
            }
        }

        return moviesList;
    }
}