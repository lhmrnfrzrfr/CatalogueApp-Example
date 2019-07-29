package com.ilham.mymoviecatalogue.fragment;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ilham.mymoviecatalogue.R;
import com.ilham.mymoviecatalogue.adapter.ListMovieAdapter;
import com.ilham.mymoviecatalogue.items.Movie;
import com.ilham.mymoviecatalogue.viewModel.MovieListViewModel;

import java.util.ArrayList;

public class MovieFragment extends Fragment {

    private View v;
    private MovieListViewModel mViewModel;
    private RecyclerView myrecyclerview;
    private ListMovieAdapter listMovieAdapter;
    private ArrayList<Movie.ResultsBean> listMovie = new ArrayList<>();
    private ProgressDialog progressBar;

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_movie, viewGroup, false);
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
        mViewModel.init();
        mViewModel.getMovies().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                listMovie.addAll(movie.getResults());
                listMovieAdapter.notifyDataSetChanged();
                showDialogLoad(false);
            }
        });
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
}