package com.ilham.mymoviecatalogue.database.tvfavorite;

import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_TV = "tv";

    public static final class TvColumns implements BaseColumns {
        public static String TITLE_TV = "title_tv";
        public static String POSTER_TV = "poster_tv";
        public static String BACKDROP_TV = "backdrop_tv";
        public static String OVERVIEW_TV = "overview_tv";
        public static String RELEASED_TV = "released_tv";
        public static String SCORE_TV = "score_tv";
    }
}
