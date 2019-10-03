package com.ilham.mymoviecatalogue.reminder;

import com.ilham.mymoviecatalogue.items.Movie;

import java.util.ArrayList;

public interface ReleaseMovieCallbacks {
    void onSuccess(ArrayList<Movie.ResultsBean> movies);
    void onFailure(boolean failure);
}
