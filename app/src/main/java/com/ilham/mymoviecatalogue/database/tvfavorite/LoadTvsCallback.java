package com.ilham.mymoviecatalogue.database.tvfavorite;

import com.ilham.mymoviecatalogue.items.Tv;

import java.util.ArrayList;

public interface LoadTvsCallback {
    void preExecute();

    void postExecute(ArrayList<Tv.ResultsBean> tvs);
}