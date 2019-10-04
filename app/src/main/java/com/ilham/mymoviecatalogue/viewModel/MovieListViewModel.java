package com.ilham.mymoviecatalogue.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.ilham.mymoviecatalogue.items.Movie;
import com.ilham.mymoviecatalogue.repo.MovieRepo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieListViewModel extends ViewModel {

    private static final String API_KEY = "ce7feeb6af94d9372180d04db1bc755d";
    private final MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<ArrayList<Movie>>();
    private MutableLiveData<Movie> movieData;
    private MovieRepo movieModel;
    private String lang;
    private String query;

    public MovieListViewModel() {
        movieModel = new MovieRepo();
    }


    public void setMovies(final String language, String category) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();
        final String url = "https://api.themoviedb.org/3/movie" + "/" + category + "?api_key=" + API_KEY + "&language=" + language;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movies = list.getJSONObject(i);
                        Movie movie = new Movie(movies);
                        listItems.add(movie);
                    }
                    listMovies.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }


    public void searchMovies(final String text, final String type, String language) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();
        final String url = "https://api.themoviedb.org/3/search/" + type + "?api_key=" + API_KEY + "&language=" + language + "en-US&query=" + text;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movies = list.getJSONObject(i);
                        Movie movie = new Movie(movies);
                        listItems.add(movie);
                    }
                    listMovies.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());

            }
        });
    }

    public MutableLiveData<ArrayList<Movie>> getMovies() {
        return listMovies;
    }

}