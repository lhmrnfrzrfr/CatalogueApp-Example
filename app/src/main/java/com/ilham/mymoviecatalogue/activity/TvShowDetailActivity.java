package com.ilham.mymoviecatalogue.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.ilham.mymoviecatalogue.R;
import com.ilham.mymoviecatalogue.database.tvfavorite.TvHelper;
import com.ilham.mymoviecatalogue.items.Tv;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TvShowDetailActivity extends AppCompatActivity {
    TextView tv_name, tv_genre, tv_duration, tv_rating, tv_desc, tv_year;
    ImageView img_photo, img_cover;
    ProgressDialog progressBar;
    private static final String STATE_TITLE = "state_title";
    private static final String STATE_DESC = "state_desc";
    private static final String STATE_PHOTO = "state_photo";
    private static final String STATE_COVER = "state_cover";
    private static final String STATE_YEAR = "state_year";

    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_DELETE = 301;

    public static final String EXTRA_TV = "extra_tv";
    public static final String EXTRA_POSITION = "extra_position";

    private Tv.ResultsBean tv;
    private ArrayList<Tv.ResultsBean> tvResults;
    private int position;
    Integer tv_id;

    private TvHelper tvHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        progressBar = new ProgressDialog(this);
        final Tv.ResultsBean tv = getIntent().getParcelableExtra("EXTRA_TV");

        Integer tvId = getIntent().getExtras().getInt("tv_id");
        String numberAsString = Integer.toString(tvId);

        getDetail(numberAsString);
        if (savedInstanceState == null) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showDialogLoad(true);
                }
            }, 20);
            getDetail(numberAsString);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);

        collapsingToolbarLayout.setCollapsedTitleTextColor(
                ContextCompat.getColor(this, R.color.graylight));
        collapsingToolbarLayout.setExpandedTitleColor(
                ContextCompat.getColor(this, R.color.transparent));

        tvHelper = new TvHelper(this);
        tvHelper.open();

        tv_name = findViewById(R.id.item_nama);
        tv_genre = findViewById(R.id.item_genre);
        tv_duration = findViewById(R.id.item_duration);
        tv_rating = findViewById(R.id.item_rating);
        tv_desc = findViewById(R.id.item_synopsis);
        tv_year = findViewById(R.id.item_year);
        img_photo = findViewById(R.id.item_photo);
        img_cover = findViewById(R.id.item_cover);
        tv_id = tvId;

        Double rate = getIntent().getExtras().getDouble("tv_rate");
        String rating = new DecimalFormat("#.#").format(rate);

        tv_name.setText(tv.getName());
        tv_desc.setText(tv.getOverview());
        tv_rating.setText(rating);
        tv_year.setText(tv.getFirst_air_date());
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w185/" + tv.getPoster_path())
                .into(img_photo);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342/" + tv.getBackdrop_path())
                .into(img_cover);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        MaterialFavoriteButton materialFavoriteButton = (MaterialFavoriteButton) findViewById(R.id.favorite_button);
        if (tvHelper.checkData(tv_id)) {
            materialFavoriteButton.setFavorite(true);
            materialFavoriteButton.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            if (favorite == true) {
                                SharedPreferences.Editor editor = getSharedPreferences("com.ilham.mymoviecatalogue.activity.TvShowDetailActivity", MODE_PRIVATE).edit();
                                editor.putBoolean("Favorite Added", true);
                                editor.apply();
                                Intent intent = new Intent();
                                intent.putExtra(EXTRA_TV, tv);
                                intent.putExtra(EXTRA_POSITION, position);

                                long result = tvHelper.insertTv(tv);
                                tv.setId((int) result);
                                setResult(RESULT_ADD, intent);

                                String successLike = getString(R.string.like);
                                Snackbar.make(buttonView, successLike,
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                SharedPreferences.Editor editor = getSharedPreferences("com.ilham.mymoviecatalogue.activity.TvShowDetailActivity", MODE_PRIVATE).edit();
                                editor.putBoolean("Favorite Removed", true);
                                editor.apply();
                                long result = tvHelper.deleteTv(tv.getId());
                                if (result > 0) {
                                    setResult(RESULT_DELETE);
                                    String unLike = getString(R.string.dislike);
                                    Snackbar.make(buttonView, unLike,
                                            Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
            );
        } else {
            materialFavoriteButton.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            if (favorite == true) {
                                SharedPreferences.Editor editor = getSharedPreferences("com.ilham.mymoviecatalogue.activity.TvShowDetailActivity", MODE_PRIVATE).edit();
                                editor.putBoolean("Favorite Added", true);
                                editor.apply();
                                Intent intent = new Intent();
                                intent.putExtra(EXTRA_TV, tv);
                                intent.putExtra(EXTRA_POSITION, position);

                                long result = tvHelper.insertTv(tv);
                                tv.setId((int) result);
                                setResult(RESULT_ADD, intent);

                                String successLike = getString(R.string.like);
                                Snackbar.make(buttonView, successLike,
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                SharedPreferences.Editor editor = getSharedPreferences("com.ilham.mymoviecatalogue.activity.TvShowDetailActivity", MODE_PRIVATE).edit();
                                editor.putBoolean("Favorite Removed", true);
                                editor.apply();
                                long result = tvHelper.deleteTv(tv.getId());
                                if (result > 0) {
                                    setResult(RESULT_DELETE);
                                    String unLike = getString(R.string.dislike);
                                    Snackbar.make(buttonView, unLike,
                                            Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
            );
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void getDetail(final String numberAsString) {
        String url = "https://api.themoviedb.org/3/tv/" + numberAsString + "?api_key=ce7feeb6af94d9372180d04db1bc755d&language=en-US";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                showDialogLoad(false);
                try {
                    String response = new String(responseBody);
                    JSONObject object = new JSONObject(response);
                    String duration = object.getString("episode_run_time");
                    tv_duration.setText(duration + " Min");

                    JSONArray jsonArray = object.getJSONArray("genres");
                    List<String> genreList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String genreName = jsonObject.getString("name");
                        genreList.add(genreName);
                    }
                    String genres = TextUtils.join(", ", genreList);
                    tv_genre.setText(genres);
                    showDialogFail(false);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                showDialogFail(true);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Tv.ResultsBean tv = getIntent().getParcelableExtra("EXTRA_TV");
        outState.putString(STATE_TITLE, tv.getName());
        outState.putString(STATE_DESC, tv.getOverview());
        outState.putString(STATE_PHOTO, tv.getPoster_path());
        outState.putString(STATE_COVER, tv.getBackdrop_path());
        outState.putString(STATE_YEAR, tv.getFirst_air_date());
        outState.putString("duration", tv_duration.getText().toString());
        outState.putString("genre", tv_genre.getText().toString());
        outState.putString("rating", tv_rating.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String name = savedInstanceState.getString(STATE_TITLE);
        String desc = savedInstanceState.getString(STATE_DESC);
        String photo = savedInstanceState.getString(STATE_PHOTO);
        String cover = savedInstanceState.getString(STATE_COVER);
        String year = savedInstanceState.getString(STATE_YEAR);
        tv_name.setText(name);
        tv_desc.setText(desc);
        tv_year.setText(year);
        tv_duration.setText(savedInstanceState.getString("duration"));
        tv_genre.setText(savedInstanceState.getString("genre"));
        tv_rating.setText(savedInstanceState.getString("rating"));
        Glide.with(this).load("https://image.tmdb.org/t/p/w185/" + photo).into(img_photo);
        Glide.with(this).load("https://image.tmdb.org/t/p/w342/" + cover).into(img_cover);

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

    public void delFav() {
        tvHelper.deleteTv(tv.getId());
    }
}
