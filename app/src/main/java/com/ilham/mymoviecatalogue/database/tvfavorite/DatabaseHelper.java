package com.ilham.mymoviecatalogue.database.tvfavorite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbtvapp";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_NOTE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_TV,
            DatabaseContract.TvColumns._ID,
            DatabaseContract.TvColumns.TITLE_TV,
            DatabaseContract.TvColumns.POSTER_TV,
            DatabaseContract.TvColumns.BACKDROP_TV,
            DatabaseContract.TvColumns.OVERVIEW_TV,
            DatabaseContract.TvColumns.RELEASED_TV,
            DatabaseContract.TvColumns.SCORE_TV
    );

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_TV);
        onCreate(db);
    }
}
