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
import com.ilham.mymoviecatalogue.adapter.ListTvAdapter;
import com.ilham.mymoviecatalogue.items.Tv;
import com.ilham.mymoviecatalogue.viewModel.TvListViewModel;

import java.util.ArrayList;
import java.util.Locale;

public class TVFragment extends Fragment implements SearchView.OnQueryTextListener, View.OnClickListener{

    private View v;
    private TvListViewModel mViewModel;
    private RecyclerView myrecyclerview;
    private ListTvAdapter listTvAdapter;
    private ArrayList<Tv> listTv = new ArrayList<Tv>();
    private ProgressDialog progressBar;
    private final String type = "movie";


    public static TVFragment newInstance() {
        return new TVFragment();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tv, viewGroup, false);

        android.widget.SearchView searchView = v.findViewById(R.id.sv_tvs);
        searchView.setOnQueryTextListener(this);
        myrecyclerview = v.findViewById(R.id.rv_fragtv);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        listTvAdapter = new ListTvAdapter(getContext(), listTv);
        myrecyclerview.setAdapter(listTvAdapter);
        progressBar = new ProgressDialog(getContext());
        showDialogLoad(true);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(TvListViewModel.class);
        mViewModel.init();
        mViewModel.getTv().observe(this, new Observer<Tv>() {
            @Override
            public void onChanged(@Nullable Tv tv) {
                listTv.addAll(tv.getResults());
                listTvAdapter.notifyDataSetChanged();
                showDialogLoad(false);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listTv = new ArrayList<>();
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

    private void showDialogFail(boolean state) {
        if (state) {
            progressBar.setTitle(getResources().getString(R.string.error));
            progressBar.setMessage(getResources().getString(R.string.error_message));
            progressBar.setCanceledOnTouchOutside(false);
            progressBar.show();
        } else {
            progressBar.hide();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mViewModel.searchMovies(newText,type,getCurrentLanguage());
        mViewModel.getMovies().observe(this, getMovies);
        return false;
    }

    private String getCurrentLanguage() {
        Locale current = ConfigurationCompat.getLocales(getResources().getConfiguration()).get(0);
        if (current.getLanguage().equals("in")) {
            return "id";
        }
        return current.getLanguage();
    }

    private final Observer<ArrayList<Tv>> getMovies = new Observer<ArrayList<Tv>>() {
        @Override
        public void onChanged(ArrayList<Tv> tvs) {
            if (tvs != null) {
                listTvAdapter.setData(tvs);
                showDialogLoad(false);
            }
        }
    };
}
