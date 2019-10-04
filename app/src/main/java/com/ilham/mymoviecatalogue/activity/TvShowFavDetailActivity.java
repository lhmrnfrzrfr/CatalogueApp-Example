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

    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_DELETE = 301;

    private int position;

    public static final String EXTRA_TV = "extra_tv";
    public static final String EXTRA_POSITION = "extra_position";

    private TvHelper tvHelper;

    ListTvAdapter adapter;

    private Tv tv;

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

        tvHelper = TvHelper.getInstance(getApplicationContext());
        tvHelper.open();

        adapter.notifyDataSetChanged();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        txtTitleDetail = findViewById(R.id.txt_title_detail_favorite_tv);
        txtOverviewDetail = findViewById(R.id.txt_overviewDetail_favorite_tv);
        posterBanner = findViewById(R.id.poster_banner_favorite_tv);
        scoreDetailFavoriteTv = findViewById(R.id.score_detail_movie_favorite_tv);

        btnBack = findViewById(R.id.btn_back_favorite_tv);
        btnDislike = findViewById(R.id.btn_dislike_movie_favorite_tv);

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

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
            }
        });

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        TvShowFavDetailActivity.this.overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }
}
