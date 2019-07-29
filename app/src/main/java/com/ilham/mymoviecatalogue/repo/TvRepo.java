package com.ilham.mymoviecatalogue.repo;

import android.arch.lifecycle.MutableLiveData;

import com.ilham.mymoviecatalogue.ApiInterface;
import com.ilham.mymoviecatalogue.items.Tv;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvRepo {
    public static String BASE_URL = "https://api.themoviedb.org";
    public static String CATEGORY = "popular";
    public static int PAGE = 1;
    public static String API_KEY = "ce7feeb6af94d9372180d04db1bc755d";
    public static String LANGUAGE = "en-US";

    public TvRepo() {

    }

    public MutableLiveData<Tv> getTv() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final MutableLiveData<Tv> refferAndInvitePojoMutableLiveData = new MutableLiveData<>();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Tv> call = apiInterface.listOfTv(CATEGORY, API_KEY, LANGUAGE, PAGE);
        call.enqueue(new Callback<Tv>() {

            @Override
            public void onResponse(Call<Tv> call, Response<Tv> response) {
                if (response.body() != null) {
                    refferAndInvitePojoMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Tv> call, Throwable t) {

            }
        });
        return refferAndInvitePojoMutableLiveData;
    }
}
