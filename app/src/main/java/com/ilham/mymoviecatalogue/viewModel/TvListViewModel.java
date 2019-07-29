package com.ilham.mymoviecatalogue.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ilham.mymoviecatalogue.items.Tv;
import com.ilham.mymoviecatalogue.repo.TvRepo;

public class TvListViewModel extends ViewModel {

    private MutableLiveData<Tv> tvData;
    private TvRepo tvModel;

    public TvListViewModel() {
        tvModel = new TvRepo();
    }

    public void init() {
        if (this.tvData != null) {
            return;
        }
        tvData = tvModel.getTv();
    }

    public MutableLiveData<Tv> getTv() {
        return this.tvData;
    }
}

