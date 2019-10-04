package com.ilham.mymoviecatalogue.database.tvfavorite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ilham.mymoviecatalogue.items.Tv;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.ilham.mymoviecatalogue.database.tvfavorite.DatabaseContract.TABLE_TV;
import static com.ilham.mymoviecatalogue.database.tvfavorite.DatabaseContract.TvColumns.BACKDROP_TV;
import static com.ilham.mymoviecatalogue.database.tvfavorite.DatabaseContract.TvColumns.OVERVIEW_TV;
import static com.ilham.mymoviecatalogue.database.tvfavorite.DatabaseContract.TvColumns.POSTER_TV;
import static com.ilham.mymoviecatalogue.database.tvfavorite.DatabaseContract.TvColumns.RELEASED_TV;
import static com.ilham.mymoviecatalogue.database.tvfavorite.DatabaseContract.TvColumns.SCORE_TV;
import static com.ilham.mymoviecatalogue.database.tvfavorite.DatabaseContract.TvColumns.TITLE_TV;

public class TvHelper {
    private static final String DATABASE_TABLE = TABLE_TV;
    private static DatabaseHelper dataBaseHelper;
    private static TvHelper INSTANCE;

    private static SQLiteDatabase database;

    public TvHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static TvHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvHelper(context);
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

    public ArrayList<Tv> getAllTvs() {
        ArrayList<Tv> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Tv tvItems;
        if (cursor.getCount() > 0) {
            do {
                tvItems = new Tv();
                tvItems.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                tvItems.setName(cursor.getString(cursor.getColumnIndexOrThrow(TITLE_TV)));
                tvItems.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_TV)));
                tvItems.setBackdrop_path(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP_TV)));
                tvItems.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW_TV)));
                tvItems.setFirst_air_date(cursor.getString(cursor.getColumnIndexOrThrow(RELEASED_TV)));
                tvItems.setVote_average(cursor.getDouble(cursor.getColumnIndexOrThrow(SCORE_TV)));

                arrayList.add(tvItems);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertTv(Tv tvItems) {
        ContentValues args = new ContentValues();
        args.put(_ID, tvItems.getId());
        args.put(TITLE_TV, tvItems.getName());
        args.put(POSTER_TV, tvItems.getPoster_path());
        args.put(BACKDROP_TV, tvItems.getBackdrop_path());
        args.put(OVERVIEW_TV, tvItems.getOverview());
        args.put(RELEASED_TV, tvItems.getFirst_air_date());
        args.put(SCORE_TV, tvItems.getVote_average());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public boolean checkData(int id) {
        Cursor cursor;
        cursor = database.rawQuery("select * from " + DATABASE_TABLE + " where " + _ID + " = " + id + "", null);
        cursor.moveToFirst();
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public int deleteTv(int id) {
        return database.delete(TABLE_TV, _ID + " = '" + id + "'", null);
    }
}


