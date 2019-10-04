package com.ilham.mymoviecatalogue.fragment;

import android.content.Intent;
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

import com.ilham.mymoviecatalogue.R;
import com.ilham.mymoviecatalogue.activity.TvShowDetailActivity;
import com.ilham.mymoviecatalogue.activity.TvShowFavDetailActivity;
import com.ilham.mymoviecatalogue.adapter.TvFavoriteAdapter;
import com.ilham.mymoviecatalogue.database.tvfavorite.LoadTvsCallback;
import com.ilham.mymoviecatalogue.database.tvfavorite.TvHelper;
import com.ilham.mymoviecatalogue.items.Tv;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

public class TvFavoriteFragment extends Fragment implements LoadTvsCallback {

    // Widgets, Array, Adapter, Helper Variable Declaration
    RecyclerView rvFavoriteTvs;
    ArrayList<Tv> tvItems;
    TvFavoriteAdapter adapter;
    TvHelper tvHelper;

    // Default Value
    private static final String EXTRA_STATE = "EXTRA_STATE";

    // Empty Constructor
    public TvFavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // MovieItems Instance
        tvItems = new ArrayList<>();

        // Cast Recyclerview
        rvFavoriteTvs = view.findViewById(R.id.rv_tv_favorite);

        // Layout Manager
        rvFavoriteTvs.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        // Divider between item list
        DividerItemDecoration itemDecorator = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.HORIZONTAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.divider)));
        rvFavoriteTvs.addItemDecoration(itemDecorator);
        rvFavoriteTvs.setHasFixedSize(true);

        // MovieHelper Instance
        tvHelper = new TvHelper(getActivity());

        // Open MovieHelper
        tvHelper.open();

        // Adapter Instance
        adapter = new TvFavoriteAdapter(getActivity());
        adapter.notifyDataSetChanged();

        // Set Adapter
        rvFavoriteTvs.setAdapter(adapter);

        // SavedInstanceState
        if (savedInstanceState == null) {
            new LoadTvsAsync(tvHelper, this).execute();
        } else {
            ArrayList<Tv> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListTv(list);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListTvs());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_favorite, container, false);
    }

    @Override
    public void preExecute() {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Loading Progress
            }
        });
    }

    @Override
    public void postExecute(ArrayList<Tv> tvs) {
        adapter.setListTv(tvs);
    }

    private static class LoadTvsAsync extends AsyncTask<Void, Void, ArrayList<Tv>> {

        private final WeakReference<TvHelper> weakNoteHelper;
        private final WeakReference<LoadTvsCallback> weakCallback;

        private LoadTvsAsync(TvHelper tvHelper, LoadTvsCallback callback) {
            weakNoteHelper = new WeakReference<>(tvHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Tv> doInBackground(Void... voids) {
            return weakNoteHelper.get().getAllTvs();
        }

        @Override
        protected void onPostExecute(ArrayList<Tv> tvs) {
            super.onPostExecute(tvs);
            weakCallback.get().postExecute(tvs);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == TvShowDetailActivity.REQUEST_ADD) {
                if (resultCode == TvShowDetailActivity.RESULT_ADD) {
                    Tv tvItems = data.getParcelableExtra(TvShowDetailActivity.EXTRA_TV);
                    adapter.addItem(tvItems);
                    rvFavoriteTvs.smoothScrollToPosition(adapter.getItemCount() - 1);
                }
            } else if (requestCode == TvShowFavDetailActivity.REQUEST_UPDATE) {
                if (resultCode == TvShowFavDetailActivity.RESULT_DELETE) {
                    int position = data.getIntExtra(TvShowFavDetailActivity.EXTRA_POSITION, 0);
                    adapter.removeItem(position);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tvHelper.close();
    }
}
