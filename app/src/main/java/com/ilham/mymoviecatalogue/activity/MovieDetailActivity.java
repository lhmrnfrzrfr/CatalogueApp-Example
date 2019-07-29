package com.ilham.mymoviecatalogue.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ilham.mymoviecatalogue.R;
import com.ilham.mymoviecatalogue.items.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MovieDetailActivity extends AppCompatActivity {
    TextView tv_name, tv_genre, tv_duration, tv_rating, tv_year, tv_desc;
    ImageView img_photo, img_cover;
    ProgressDialog progressBar;
    private static final String STATE_TITLE = "state_title";
    private static final String STATE_DESC = "state_desc";
    private static final String STATE_PHOTO = "state_photo";
    private static final String STATE_COVER = "state_cover";
    private static final String STATE_YEAR = "state_year";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        progressBar = new ProgressDialog(this);
        //mendapatkan id film
        Integer movieId = getIntent().getExtras().getInt("movie_id");
        Movie.ResultsBean movie = getIntent().getParcelableExtra("EXTRA_MOVIE");
        String numberAsString = Integer.toString(movieId);
        //jika state (masih) kosong maka...
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

        tv_name = findViewById(R.id.item_nama);
        tv_genre = findViewById(R.id.item_genre);
        tv_duration = findViewById(R.id.item_duration);
        tv_rating = findViewById(R.id.item_rating);
        tv_year = findViewById(R.id.item_year);
        tv_desc = findViewById(R.id.item_synopsis);
        img_photo = findViewById(R.id.item_photo);
        img_cover = findViewById(R.id.item_cover);

        String name = getIntent().getExtras().getString("movie_name");
        String description = getIntent().getExtras().getString("movie_desc");
        String imgPhoto = getIntent().getExtras().getString("movie_post");
        String imgCover = getIntent().getExtras().getString("movie_cover");
        String year = getIntent().getExtras().getString("movie_year");
        Double rate = movie.getVote_average();
        String rating = new DecimalFormat("#.#").format(rate);
        tv_rating.setText(rating);

        tv_name.setText(movie.getTitle());
        tv_desc.setText(movie.getOverview());
        tv_year.setText(movie.getRelease_date());


        Glide.with(MovieDetailActivity.this)
                .load("https://image.tmdb.org/t/p/w185/" + movie.getPoster_path())
                .into(img_photo);
        Glide.with(MovieDetailActivity.this)
                .load("https://image.tmdb.org/t/p/w342/" + movie.getBackdrop_path())
                .into(img_cover);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    public void getDetail(final String numberAsString) {
        String url = "https://api.themoviedb.org/3/movie/" + numberAsString + "?api_key=ce7feeb6af94d9372180d04db1bc755d&language=en-US";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                showDialogLoad(false);
                try {
                    String response = new String(responseBody);
                    JSONObject object = new JSONObject(response);
                    String duration = object.getString("runtime");
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
        Movie.ResultsBean movie = getIntent().getParcelableExtra("EXTRA_MOVIE");
        outState.putString(STATE_TITLE, movie.getTitle());
        outState.putString(STATE_DESC, movie.getOverview());
        outState.putString(STATE_PHOTO, movie.getPoster_path());
        outState.putString(STATE_COVER, movie.getBackdrop_path());
        outState.putString(STATE_YEAR, movie.getRelease_date());
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
}
