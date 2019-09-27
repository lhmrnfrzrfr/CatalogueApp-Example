package com.ilham.mymoviecatalogue.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ilham.mymoviecatalogue.R;
import com.ilham.mymoviecatalogue.items.Movie;

import java.util.Objects;

import static com.ilham.mymoviecatalogue.database.favoritemovie.DatabaseContract.MovieColumns.BACKDROP;
import static com.ilham.mymoviecatalogue.database.favoritemovie.DatabaseContract.MovieColumns.OVERVIEW;
import static com.ilham.mymoviecatalogue.database.favoritemovie.DatabaseContract.MovieColumns.POSTER;
import static com.ilham.mymoviecatalogue.database.favoritemovie.DatabaseContract.MovieColumns.RELEASED;
import static com.ilham.mymoviecatalogue.database.favoritemovie.DatabaseContract.MovieColumns.SCORE;
import static com.ilham.mymoviecatalogue.database.favoritemovie.DatabaseContract.MovieColumns.TITLE;

public class MovieFavDetailActivity extends AppCompatActivity {

    // Default Keys Values
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_DELETE = 301;

    // Position Variable
    private int position;

    // Default Values
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_POSITION = "extra_position";

    // Instance Movie Items
    private Movie.ResultsBean movie;

    // Widget Variables Declaration
    TextView txtTitleDetail;
    TextView txtOverviewDetail;
    ImageView posterBanner;
    Button btnDislike;
    ImageButton btnBack;
    ProgressBar progressBar;
    RatingBar scoreDetailFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_favorite);

        // Translucent Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // Casting Data Variables
        txtTitleDetail = findViewById(R.id.txt_title_detail_favorite);
        txtOverviewDetail = findViewById(R.id.txt_overviewDetail_favorite);
        posterBanner = findViewById(R.id.poster_banner_favorite);
        scoreDetailFavorite = findViewById(R.id.score_detail_movie_favorite);

        // Casting Button Variables
        btnBack = findViewById(R.id.btn_back_favorite);
        btnDislike = findViewById(R.id.btn_dislike_movie_favorite);

        // Progress Bar Declaration
        progressBar = findViewById(R.id.progressBar_detailMovie_favorite);
        progressBar.bringToFront();

        // Menerima Intent Movie dan Positon
        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (movie != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        } else {
            movie = new Movie.ResultsBean();
        }

        Uri uri = getIntent().getData();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) movie = new Movie.ResultsBean(cursor);
                cursor.close();
            }
        }

        // Mengisi data String
        txtTitleDetail.setText(movie.getTitle());
        txtOverviewDetail.setText(movie.getOverview());
        double score = movie.getVote_average() * 10;
        scoreDetailFavorite.setRating((float) ((score * 5) / 100));

        // Mengisi data image
        String url = "https://image.tmdb.org/t/p/original" + movie.getBackdrop_path();
        Glide.with(MovieFavDetailActivity.this)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(posterBanner);

        // setOnClickListener untuk Button Back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
            }
        });

        // setOnClickListener untuk Button Dislike
        btnDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieFavDetailActivity.this, TabbedActivity.class);
                intent.putExtra(EXTRA_POSITION, position);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                ContentValues values = new ContentValues();
                values.put(TITLE, movie.getTitle());
                values.put(POSTER, movie.getPoster_path());
                values.put(OVERVIEW, movie.getOverview());
                values.put(SCORE, movie.getVote_average());
                values.put(RELEASED, movie.getRelease_date());
                values.put(BACKDROP, movie.getBackdrop_path());

                startActivityForResult(intent, REQUEST_UPDATE);
                setResult(RESULT_DELETE);

                getContentResolver().delete(Objects.requireNonNull(getIntent().getData()), null, null);

                finish();
                String remove = getString(R.string.dislike);
                Toast.makeText(MovieFavDetailActivity.this, remove, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // Animation onBackPressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MovieFavDetailActivity.this.overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }
}
