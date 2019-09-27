package com.ilham.mymoviecatalogue.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ilham.mymoviecatalogue.R;
import com.ilham.mymoviecatalogue.adapter.FragmentPagerAdapter;
import com.ilham.mymoviecatalogue.fragment.FavoriteFragment;
import com.ilham.mymoviecatalogue.fragment.MovieFragment;
import com.ilham.mymoviecatalogue.fragment.TVFragment;

public class TabbedActivity extends AppCompatActivity {

    private BottomNavigationView tabLayout;
    private ViewPager viewPager;
    private FragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Bottom Navigation Declaration
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // The first fragment to show when open the app
        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.nav_movie);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()) {
                case R.id.nav_movie:
                    selectedFragment = new MovieFragment();
                    break;
                case R.id.nav_tv:
                    selectedFragment = new TVFragment();
                    break;
                case R.id.nav_favorites:
                    selectedFragment = new FavoriteFragment();
                    break;
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, selectedFragment)
                    .commit();
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
