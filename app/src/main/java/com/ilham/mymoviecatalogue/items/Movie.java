package com.ilham.mymoviecatalogue.items;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.ilham.mymoviecatalogue.database.favoritemovie.DatabaseContract;

import org.json.JSONObject;

import java.util.List;

import static android.provider.BaseColumns._ID;

    public class Movie implements Parcelable {
        /**
         * vote_count : 697
         * id : 420818
         * video : false
         * vote_average : 7.2
         * title : The Lion King
         * popularity : 523.663
         * poster_path : /dzBtMocZuJbjLOXvrl4zGYigDzh.jpg
         * original_language : en
         * original_title : The Lion King
         * genre_ids : [12,16,10751,18,28]
         * backdrop_path : /1TUg5pO1VZ4B0Q1amk3OlXvlpXV.jpg
         * adult : false
         * overview : Simba idolises his father, King Mufasa, and takes to heart his own royal destiny. But not everyone in the kingdom celebrates the new cub's arrival. Scar, Mufasa's brother—and former heir to the throne—has plans of his own. The battle for Pride Rock is ravaged with betrayal, tragedy and drama, ultimately resulting in Simba's exile. With help from a curious pair of newfound friends, Simba will have to figure out how to grow up and take back what is rightfully his.
         * release_date : 2019-07-12
         */

        @SerializedName("vote_count")
        private int vote_count;

        @SerializedName("id")
        private int id;

        @SerializedName("video")
        private boolean video;

        @SerializedName("vote_average")
        private double vote_average;

        @SerializedName("title")
        private String title;

        @SerializedName("popularity")
        private double popularity;

        @SerializedName("poster_path")
        private String poster_path;

        @SerializedName("original_language")
        private String original_language;

        @SerializedName("original_title")
        private String original_title;

        @SerializedName("backdrop_path")
        private String backdrop_path;

        @SerializedName("adult")
        private boolean adult;

        @SerializedName("overview")
        private String overview;

        @SerializedName("release_date")
        private String release_date;

        @SerializedName("genre_ids")
        private List<Integer> genre_ids;

        private String type;

        protected Movie(Parcel in) {
            vote_count = in.readInt();
            id = in.readInt();
            video = in.readByte() != 0;
            vote_average = in.readDouble();
            title = in.readString();
            popularity = in.readDouble();
            poster_path = in.readString();
            original_language = in.readString();
            original_title = in.readString();
            backdrop_path = in.readString();
            adult = in.readByte() != 0;
            overview = in.readString();
            release_date = in.readString();
        }

        public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
            @Override
            public Movie createFromParcel(Parcel in) {
                return new Movie(in);
            }

            @Override
            public Movie[] newArray(int size) {
                return new Movie[size];
            }
        };

        public Movie(int id, String title, String overview, String poster) {
            this.id = id;
            this.title = title;
            this.overview = overview;
            this.poster_path = poster;
        }

        public int getVote_count() {
            return vote_count;
        }

        public void setVote_count(int vote_count) {
            this.vote_count = vote_count;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isVideo() {
            return video;
        }

        public void setVideo(boolean video) {
            this.video = video;
        }

        public Double getVote_average() {
            return vote_average;
        }

        public void setVote_average(double vote_average) {
            this.vote_average = vote_average;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public double getPopularity() {
            return popularity;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public String getOriginal_language() {
            return original_language;
        }

        public void setOriginal_language(String original_language) {
            this.original_language = original_language;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public void setBackdrop_path(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        public boolean isAdult() {
            return adult;
        }

        public void setAdult(boolean adult) {
            this.adult = adult;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }
        private void setType(String type) {
            this.type = type;
        }

        public List<Integer> getGenre_ids() {
            return genre_ids;
        }

        public void setGenre_ids(List<Integer> genre_ids) {
            this.genre_ids = genre_ids;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(vote_count);
            dest.writeInt(id);
            dest.writeByte((byte) (video ? 1 : 0));
            dest.writeDouble(vote_average);
            dest.writeString(title);
            dest.writeDouble(popularity);
            dest.writeString(poster_path);
            dest.writeString(original_language);
            dest.writeString(original_title);
            dest.writeString(backdrop_path);
            dest.writeByte((byte) (adult ? 1 : 0));
            dest.writeString(overview);
            dest.writeString(release_date);
        }

        public Movie() {

        }

        public Movie(JSONObject result, String type) {
            try {
                if (type.equals("movie")) {
                    setTitle(result.getString("title"));
                    setRelease_date(result.getString("release_date"));
                } else {
                    setTitle(result.getString("name"));
                    setRelease_date(result.getString("first_air_date"));
                }
                setOverview(result.getString("overview"));
                setVote_average(Double.parseDouble(result.getString("vote_average")));
                setPoster_path(result.getString("poster_path"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            setType(type);
        }

        public Movie(int id, String title, String released, String poster, String overview, String backdrop, double score) {
            this.id = id;
            this.title = title;
            this.release_date = released;
            this.poster_path = poster;
            this.overview = overview;
            this.backdrop_path = backdrop;
            this.vote_average = score;
        }

        public Movie(Cursor cursor) {
            this.id = DatabaseContract.getColumnInt(cursor, _ID);
            this.title = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.TITLE);
            this.release_date = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.RELEASED);
            this.poster_path = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.POSTER);
            this.overview = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.OVERVIEW);
            this.backdrop_path = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.BACKDROP);
            this.vote_average = DatabaseContract.getColumnDouble(cursor, DatabaseContract.MovieColumns.SCORE);
        }

        public Movie(JSONObject object) {
            try {
                int id = object.getInt("id");
                String title = object.getString("title");
                String released = object.getString("release_date");
                String overview = object.getString("overview");
                String poster = object.getString("poster_path");
                String backdrop = object.getString("backdrop_path");
                double score = object.getDouble("vote_average");
                this.id = id;
                this.title = title;
                this.release_date = released;
                this.overview = overview;
                this.poster_path = poster;
                this.backdrop_path = backdrop;
                this.vote_average = score;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }