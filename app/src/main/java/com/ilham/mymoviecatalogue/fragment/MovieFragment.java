package com.ilham.mymoviecatalogue.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.ilham.mymoviecatalogue.ApiClient;
import com.ilham.mymoviecatalogue.ApiInterface;
import com.ilham.mymoviecatalogue.R;
import com.ilham.mymoviecatalogue.adapter.ListMovieAdapter;
import com.ilham.mymoviecatalogue.items.Movie;
import com.ilham.mymoviecatalogue.viewModel.MovieListViewModel;
import com.ilham.mymoviecatalogue.viewModel.MovieResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ilham.mymoviecatalogue.ApiUrl.API_KEY;
import static com.ilham.mymoviecatalogue.ApiUrl.LANGUAGE_ENGLISH;

public class MovieFragment extends Fragment {

    private View v;
    private MovieListViewModel mViewModel;
    private RecyclerView myrecyclerview;
    private ListMovieAdapter listMovieAdapter;
    private ArrayList<Movie.ResultsBean> listMovie = new ArrayList<>();
    private ProgressDialog progressBar;
    private static final String API_KEY = "ce7feeb6af94d9372180d04db1bc755d";

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Cast Layout
        final LinearLayout mainLayout = view.findViewById(R.id.container_fragment_movie);

        // Cast Widget
        final EditText etSearch = view.findViewById(R.id.search_movie);

        // OnEditorActionListener for Edit Text


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listMovie = new ArrayList<>();
        setHasOptionsMenu(true);
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