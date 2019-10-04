package com.ilham.mymoviecatalogue.fragment;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.os.ConfigurationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.ilham.mymoviecatalogue.R;
import com.ilham.mymoviecatalogue.adapter.ListMovieAdapter;
import com.ilham.mymoviecatalogue.items.Movie;
import com.ilham.mymoviecatalogue.viewModel.MovieListViewModel;

import java.util.ArrayList;
import java.util.Locale;

public class MovieFragment extends Fragment implements SearchView.OnQueryTextListener, View.OnClickListener {

    private View v;
    private MovieListViewModel mViewModel;
    private RecyclerView myrecyclerview;
    private ListMovieAdapter listMovieAdapter;
    private ArrayList<Movie> listMovie = new ArrayList<>();
    private ProgressDialog progressBar;
    private final String type = "movie";

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_movie, viewGroup, false);

        //SearchView searchView = v.findViewById(R.id.sv_movies);
        android.widget.SearchView searchView = v.findViewById(R.id.sv_movies);
        searchView.setOnQueryTextListener(this);
        myrecyclerview = v.findViewById(R.id.rv_fragmovie);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        listMovieAdapter = new ListMovieAdapter(getContext(), listMovie);
        myrecyclerview.setAdapter(listMovieAdapter);
        progressBar = new ProgressDialog(getContext());
        showDialogLoad(true);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(MovieListViewModel.class);
        //mViewModel.init();
        mViewModel.setMovies(getCurrentLanguage(), "popular");
        mViewModel.getMovies().observe(this, getMovies);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listMovie = new ArrayList<>();
    }

    public void showDialogLoad(boolean state) {
        if (state) {
            progressBar.setTitle(getResources().getString(R.string.load));
            progressBar.setMessage(getResources().getString(R.string.load_message));
            progressBar.setCanceledOnTouchOutside(false);
            progressBar.show();
        } else {
            progressBar.hide();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mViewModel.searchMovies(newText, type, getCurrentLanguage());
        mViewModel.getMovies().observe(this, getMovies);
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    private String getCurrentLanguage() {
        Locale current = ConfigurationCompat.getLocales(getResources().getConfiguration()).get(0);
        if (current.getLanguage().equals("in")) {
            return "id";
        }
        return current.getLanguage();
    }

    private final Observer<ArrayList<Movie>> getMovies = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                listMovieAdapter.setData(movies);
                showDialogLoad(false);
            }
        }
    };
}
