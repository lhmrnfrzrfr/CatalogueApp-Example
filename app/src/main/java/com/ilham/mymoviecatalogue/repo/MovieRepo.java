package com.ilham.mymoviecatalogue.repo;

import android.arch.lifecycle.MutableLiveData;

import com.ilham.mymoviecatalogue.ApiInterface;
import com.ilham.mymoviecatalogue.items.Movie;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepo {
    public static String BASE_URL = "https://api.themoviedb.org";
    public static String CATEGORY = "upcoming";
    public static int PAGE = 1;
    public static String API_KEY = "ce7feeb6af94d9372180d04db1bc755d";
    public static String LANGUAGE = "en-US";
    public static final String query = "the";


    public MovieRepo() {
    }

    public MutableLiveData<Movie> getMovies() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final MutableLiveData<Movie> refferAndInvitePojoMutableLiveData = new MutableLiveData<>();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Movie> call = apiInterface.listOfMovie(CATEGORY, API_KEY, LANGUAGE, PAGE);
        call.enqueue(new Callback<Movie>() {

            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.body() != null) {
                    refferAndInvitePojoMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
        return refferAndInvitePojoMutableLiveData;
    }

}
