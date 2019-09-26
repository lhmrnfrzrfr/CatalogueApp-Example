package com.ilham.mymoviecatalogue.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ilham.mymoviecatalogue.LoadMoviesCallback;
import com.ilham.mymoviecatalogue.R;
import com.ilham.mymoviecatalogue.activity.DetailMovieFavoriteActivity;
import com.ilham.mymoviecatalogue.activity.MovieDetailActivity;
import com.ilham.mymoviecatalogue.adapter.MovieFavoriteAdapter;
import com.ilham.mymoviecatalogue.database.MappingHelper;
import com.ilham.mymoviecatalogue.items.Movie;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import static com.ilham.mymoviecatalogue.database.DatabaseContract.MovieColumns.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoriteFragment extends Fragment implements LoadMoviesCallback {

    RecyclerView rvFavoriteMovies;
    MovieFavoriteAdapter adapter;

    private static final String EXTRA_STATE = "EXTRA_STATE";

    public MovieFavoriteFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvFavoriteMovies = view.findViewById(R.id.rv_movie_favorite);

        rvFavoriteMovies.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        DividerItemDecoration itemDecorator = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.HORIZONTAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.divider)));
        rvFavoriteMovies.addItemDecoration(itemDecorator);
        rvFavoriteMovies.setHasFixedSize(true);

        adapter = new MovieFavoriteAdapter(getActivity());

        rvFavoriteMovies.setAdapter(adapter);

        if (savedInstanceState == null) {
            new LoadMoviesAsync(Objects.requireNonNull(getActivity()).getApplicationContext(), this).execute();
        } else {
            ArrayList<Movie.ResultsBean> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListMovie(list);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListMovies());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false);
    }

    @Override
    public void preExecute() {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    @Override
    public void postExecute(Cursor movies) {
        ArrayList<Movie.ResultsBean> listMovies = MappingHelper.mapCursorToArrayList(movies);
        if (listMovies.size() > 0) {
            adapter.setListMovie(listMovies);
        } else {
            adapter.setListMovie(new ArrayList<Movie.ResultsBean>());
            Toast.makeText(getActivity(), "Tidak ada data saat ini", Toast.LENGTH_SHORT).show();
        }
    }

    private static class LoadMoviesAsync extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMoviesCallback> weakCallback;

        private LoadMoviesAsync(Context context, LoadMoviesCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == MovieDetailActivity.REQUEST_ADD) {
                if (resultCode == MovieDetailActivity.RESULT_ADD) {
                    Movie.ResultsBean movieItems = data.getParcelableExtra(MovieDetailActivity.EXTRA_MOVIE);
                    adapter.addItem(movieItems);
                    rvFavoriteMovies.smoothScrollToPosition(adapter.getItemCount() - 1);
                }
            } else if (requestCode == DetailMovieFavoriteActivity.REQUEST_UPDATE) {
                if (resultCode == DetailMovieFavoriteActivity.RESULT_DELETE) {
                    int position = data.getIntExtra(DetailMovieFavoriteActivity.EXTRA_POSITION, 0);
                    adapter.removeItem(position);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
