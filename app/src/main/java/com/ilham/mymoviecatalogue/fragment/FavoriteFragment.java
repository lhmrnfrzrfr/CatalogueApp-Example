package com.ilham.mymoviecatalogue.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ilham.mymoviecatalogue.R;
import com.ilham.mymoviecatalogue.adapter.FragmentPagerAdapter;

import java.util.Objects;

public class FavoriteFragment extends Fragment {

    public FavoriteFragment() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbarFavorite = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbarFavorite);

        TabLayout tabLayoutFavorite = view.findViewById(R.id.tablayout_favorite);
        ViewPager viewPagerFavorite = view.findViewById(R.id.viewpager_favorite);
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getChildFragmentManager());

        adapter.AddFragment(new MovieFavoriteFragment(), getString(R.string.movie));
        adapter.AddFragment(new TvFavoriteFragment(), getString(R.string.tvshows));

        viewPagerFavorite.setAdapter(adapter);
        tabLayoutFavorite.setupWithViewPager(viewPagerFavorite);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }


}
