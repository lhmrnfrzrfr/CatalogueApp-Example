package com.ilham.mymoviecatalogue.search;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;

import com.ilham.mymoviecatalogue.ApiClient;
import com.ilham.mymoviecatalogue.ApiInterface;
import com.ilham.mymoviecatalogue.R;
import com.ilham.mymoviecatalogue.adapter.ListMovieAdapter;
import com.ilham.mymoviecatalogue.items.Movie;
import com.ilham.mymoviecatalogue.viewModel.MovieResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class carimovie extends AppCompatActivity {
    RecyclerView recyclerView;
    private final static String API_KEY = "ce7feeb6af94d9372180d04db1bc755d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carimovie);
        recyclerView = findViewById(R.id.rv_carimovie);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getResources().getString(R.string.carimovie));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_cari, menu);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<MovieResult> call = apiService.getSearchMovies(API_KEY, query);
                call.enqueue(new Callback<MovieResult>() {
                    @Override
                    public void onResponse(Call<MovieResult>call, Response<MovieResult> response) {
                        List<Movie.ResultsBean> movies = response.body().getResults();
                        recyclerView.setAdapter(new ListMovieAdapter(getApplicationContext(), movies));
                    }

                    @Override
                    public void onFailure(Call<MovieResult>call, Throwable t) {
                        // Log error here since request failed

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
}