package com.ilham.mymoviecatalogue.viewModel;

import com.google.gson.annotations.SerializedName;
import com.ilham.mymoviecatalogue.items.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieResult {
    @SerializedName("results")
    private List<Movie.ResultsBean> resultsMovie;

    public MovieResult(List<Movie.ResultsBean> resultsMovie) {
        this.resultsMovie = resultsMovie;
    }


    public void setResultsMovie(List<Movie.ResultsBean> resultsMovie) {
        this.resultsMovie = resultsMovie;
    }

    @SerializedName("results")
    public List<Movie.ResultsBean> results;

    public List<Movie.ResultsBean> getResults() {
        return results;
    }
}
