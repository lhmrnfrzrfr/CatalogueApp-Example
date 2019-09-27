package com.ilham.mymoviecatalogue.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ilham.mymoviecatalogue.items.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.ilham.mymoviecatalogue.database.DatabaseContract.MovieColumns.BACKDROP;
import static com.ilham.mymoviecatalogue.database.DatabaseContract.MovieColumns.OVERVIEW;
import static com.ilham.mymoviecatalogue.database.DatabaseContract.MovieColumns.POSTER;
import static com.ilham.mymoviecatalogue.database.DatabaseContract.MovieColumns.RELEASED;
import static com.ilham.mymoviecatalogue.database.DatabaseContract.MovieColumns.SCORE;
import static com.ilham.mymoviecatalogue.database.DatabaseContract.MovieColumns.TABLE_MOVIE;
import static com.ilham.mymoviecatalogue.database.DatabaseContract.MovieColumns.TITLE;

public class MovieHelper {

    private static final String DATABASE_TABLE = TABLE_MOVIE;
    private static DatabaseHelper dataBaseHelper;
    private static MovieHelper INSTANCE;

    private static SQLiteDatabase database;

    public MovieHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public ArrayList<Movie.ResultsBean> query() {
        ArrayList<Movie.ResultsBean> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC",
                null);
        cursor.moveToFirst();
        Movie.ResultsBean movieItems;
        if (cursor.getCount() > 0) {
            do {
                movieItems = new Movie.ResultsBean();
                movieItems.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movieItems.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movieItems.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                movieItems.setBackdrop_path(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP)));
                movieItems.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movieItems.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(RELEASED)));
                movieItems.setVote_average(cursor.getDouble(cursor.getColumnIndexOrThrow(SCORE)));

                arrayList.add(movieItems);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Movie.ResultsBean movieItems) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(TITLE, movieItems.getTitle());
        initialValues.put(POSTER, movieItems.getPoster_path());
        initialValues.put(BACKDROP, movieItems.getBackdrop_path());
        initialValues.put(OVERVIEW, movieItems.getOverview());
        initialValues.put(RELEASED, movieItems.getRelease_date());
        initialValues.put(SCORE, movieItems.getVote_average());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int delete(int id) {
        return database.delete(TABLE_MOVIE, _ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }

    public boolean checkData(int id){
        Cursor cursor;
        cursor = database.rawQuery("select * from "+DATABASE_TABLE+" where "+_ID+" = "+id+"",null);
        cursor.moveToFirst();
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
