package com.ilham.mymoviecatalogue;

import com.ilham.mymoviecatalogue.items.Movie;
import com.ilham.mymoviecatalogue.items.Tv;
import com.ilham.mymoviecatalogue.viewModel.MovieResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.ilham.mymoviecatalogue.ApiUrl.SEARCH_MOVIE;

public interface ApiInterface {
    @GET("/3/tv/{category}")
    Call<Tv> listOfTv(
            @Path("category") String category,
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("/3/movie/{category}")
    Call<Movie> listOfMovie(
            @Path("category") String category,
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("search/movie")
    Call<MovieResult> getSearchMovies(@Query("api_key") String apiKey, @Query("query") String query);

}
