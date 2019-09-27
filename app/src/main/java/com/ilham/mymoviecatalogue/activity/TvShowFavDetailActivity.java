package com.ilham.mymoviecatalogue.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.ilham.mymoviecatalogue.adapter.ListTvAdapter;
import com.ilham.mymoviecatalogue.database.tvfavorite.TvHelper;
import com.ilham.mymoviecatalogue.items.Tv;

public class TvShowFavDetailActivity extends AppCompatActivity {

    // Default Keys Values
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_DELETE = 301;

    // Position Variable
    private int position;

    // Default Values
    public static final String EXTRA_TV = "extra_tv";
    public static final String EXTRA_POSITION = "extra_position";

    // Database Declaration
    private TvHelper tvHelper;

    // Adapter Declaration
    ListTvAdapter adapter;

    // Instance TV Items
    private Tv.ResultsBean tv;

    // Widget Variables Declaration
    TextView txtTitleDetail;
    TextView txtOverviewDetail;
    ImageView posterBanner;
    Button btnDislike;
    ImageButton btnBack;
    ProgressBar progressBar;
    RatingBar scoreDetailFavoriteTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tvshow_detail_favorite);

        // Movie Helper Instance
        tvHelper = TvHelper.getInstance(getApplicationContext());
        tvHelper.open();

        // Adapter Instance
        adapter = new ListTvAdapter();
        adapter.notifyDataSetChanged();

        // Translucent Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // Casting Data Variables
        txtTitleDetail = findViewById(R.id.txt_title_detail_favorite_tv);
        txtOverviewDetail = findViewById(R.id.txt_overviewDetail_favorite_tv);
        posterBanner = findViewById(R.id.poster_banner_favorite_tv);
        scoreDetailFavoriteTv = findViewById(R.id.score_detail_movie_favorite_tv);

        // Casting Button Variables
        btnBack = findViewById(R.id.btn_back_favorite_tv);
        btnDislike = findViewById(R.id.btn_dislike_movie_favorite_tv);

        // Progress Bar Declaration
        progressBar = findViewById(R.id.progressBar_detailMovie_favorite_tv);
        progressBar.bringToFront();

        // Menerima Intent Movie dan Positon
        tv = getIntent().getParcelableExtra(EXTRA_TV);
        position = getIntent().getIntExtra(EXTRA_POSITION, 0);

        // Mengisi data String
        txtTitleDetail.setText(tv.getName());
        txtOverviewDetail.setText(tv.getOverview());
        double score = tv.getVote_average() * 10;
        scoreDetailFavoriteTv.setRating((float) ((score * 5) / 100));

        // Mengisi data image
        String url = "https://image.tmdb.org/t/p/original" + tv.getBackdrop_path();
        Glide.with(TvShowFavDetailActivity.this)
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
                long result = tvHelper.deleteTv(tv.getId());
                if (result > 0) {
                    Intent intent = new Intent(TvShowFavDetailActivity.this, TabbedActivity.class);
                    intent.putExtra(EXTRA_POSITION, position);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent, REQUEST_UPDATE);
                    setResult(RESULT_DELETE);
                    finish();
                    String remove = getString(R.string.dislike);
                    Toast.makeText(TvShowFavDetailActivity.this, remove, Toast.LENGTH_SHORT).show();
                } else {
                    String failedRemove = getString(R.string.FailedDislike);
                    Toast.makeText(TvShowFavDetailActivity.this, failedRemove, Toast.LENGTH_SHORT).show();
                }
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
        TvShowFavDetailActivity.this.overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }
}
