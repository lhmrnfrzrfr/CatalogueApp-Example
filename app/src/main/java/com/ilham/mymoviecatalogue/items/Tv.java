package com.ilham.mymoviecatalogue.items;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.List;

import static android.provider.BaseColumns._ID;

    public class Tv implements Parcelable {
        /**
         * original_name : See No Evil: The Moors Murders
         * genre_ids : [18]
         * name : See No Evil: The Moors Murders
         * popularity : 516.856
         * origin_country : ["GB"]
         * vote_count : 13
         * first_air_date : 2006-05-14
         * backdrop_path : /7AKhSfJHnVi0zXQS4eJirHDs22p.jpg
         * original_language : en
         * id : 11634
         * vote_average : 4.5
         * overview : The dramatisation of one of the most notorious killing sprees in British history.
         * poster_path : /b71BaRjp9TwxUZodLGgSRIlkfL8.jpg
         */

        private List<Tv> results;
        private String original_name;
        private String name;
        private double popularity;
        private int vote_count;
        private String first_air_date;
        private String backdrop_path;
        private String original_language;
        private int id;
        private double vote_average;
        private String overview;
        private String poster_path;
        private List<Integer> genre_ids;
        private List<String> origin_country;
        private String type;

        public Tv(Parcel in) {
            original_name = in.readString();
            name = in.readString();
            popularity = in.readDouble();
            vote_count = in.readInt();
            first_air_date = in.readString();
            backdrop_path = in.readString();
            original_language = in.readString();
            id = in.readInt();
            vote_average = in.readDouble();
            overview = in.readString();
            poster_path = in.readString();
            origin_country = in.createStringArrayList();
        }

        public static final Parcelable.Creator<Tv> CREATOR = new Creator<Tv>() {
            @Override
            public Tv createFromParcel(Parcel in) {
                return new Tv(in);
            }

            @Override
            public Tv[] newArray(int size) {
                return new Tv[size];
            }
        };

        public Tv() {

        }

        public Tv(JSONObject result, String type) {
            try {
                if (type.equals("tv")) {
                    setName(result.getString("name"));
                    setFirst_air_date(result.getString("first_air_date"));
                } else {
                    setName(result.getString("title"));
                    setFirst_air_date(result.getString("release_date"));
                }
                setOverview(result.getString("overview"));
                setVote_average(Double.parseDouble(result.getString("vote_average")));
                setPoster_path(result.getString("poster_path"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            setType(type);
        }

        public List<Tv> getResults() {
            return results;
        }


        public String getOriginal_name() {
            return original_name;
        }

        public void setOriginal_name(String original_name) {
            this.original_name = original_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPopularity() {
            return popularity;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        public int getVote_count() {
            return vote_count;
        }

        public void setVote_count(int vote_count) {
            this.vote_count = vote_count;
        }

        public String getFirst_air_date() {
            return first_air_date;
        }

        public void setFirst_air_date(String first_air_date) {
            this.first_air_date = first_air_date;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public void setBackdrop_path(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        public String getOriginal_language() {
            return original_language;
        }

        public void setOriginal_language(String original_language) {
            this.original_language = original_language;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getVote_average() {
            return vote_average;
        }

        public void setVote_average(double vote_average) {
            this.vote_average = vote_average;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public List<Integer> getGenre_ids() {
            return genre_ids;
        }

        public void setGenre_ids(List<Integer> genre_ids) {
            this.genre_ids = genre_ids;
        }

        public List<String> getOrigin_country() {
            return origin_country;
        }

        public void setOrigin_country(List<String> origin_country) {
            this.origin_country = origin_country;
        }
        private void setType(String type) {
            this.type = type;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(original_name);
            dest.writeString(name);
            dest.writeDouble(popularity);
            dest.writeInt(vote_count);
            dest.writeString(first_air_date);
            dest.writeString(backdrop_path);
            dest.writeString(original_language);
            dest.writeInt(id);
            dest.writeDouble(vote_average);
            dest.writeString(overview);
            dest.writeString(poster_path);
            dest.writeStringList(origin_country);
        }

        public Tv(JSONObject object) {
            try {
                int id = object.getInt("id");
                String title = object.getString("title");
                String released = object.getString("release_date");
                String overview = object.getString("overview");
                String poster = object.getString("poster_path");
                String backdrop = object.getString("backdrop_path");
                double score = object.getDouble("vote_average");
                this.id = id;
                this.name = title;
                this.first_air_date = released;
                this.overview = overview;
                this.poster_path = poster;
                this.backdrop_path = backdrop;
                this.vote_average = score;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

