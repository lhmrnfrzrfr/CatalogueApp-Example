package com.ilham.mymoviecatalogue.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ilham.mymoviecatalogue.items.Movie;
import com.ilham.mymoviecatalogue.repo.MovieRepo;

public class MovieListViewModel extends ViewModel {

    private MutableLiveData<Movie> movieData;
    private MovieRepo movieModel;

    public MovieListViewModel() {
        movieModel = new MovieRepo();
    }

    public void init() {
        if (this.movieData != null) {
            return;
        }
        movieData = movieModel.getMovies();
    }

    public MutableLiveData<Movie> getMovies() {
        return this.movieData;
    }
}
