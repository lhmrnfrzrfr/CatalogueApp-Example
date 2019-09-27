package com.ilham.mymoviecatalogue.activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.ilham.mymoviecatalogue.R;
import com.ilham.mymoviecatalogue.database.favoritemovie.MovieHelper;
import com.ilham.mymoviecatalogue.items.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

import static android.provider.BaseColumns._ID;
import static com.ilham.mymoviecatalogue.database.favoritemovie.DatabaseContract.MovieColumns.BACKDROP;
import static com.ilham.mymoviecatalogue.database.favoritemovie.DatabaseContract.MovieColumns.CONTENT_URI;
import static com.ilham.mymoviecatalogue.database.favoritemovie.DatabaseContract.MovieColumns.OVERVIEW;
import static com.ilham.mymoviecatalogue.database.favoritemovie.DatabaseContract.MovieColumns.POSTER;
import static com.ilham.mymoviecatalogue.database.favoritemovie.DatabaseContract.MovieColumns.RELEASED;
import static com.ilham.mymoviecatalogue.database.favoritemovie.DatabaseContract.MovieColumns.SCORE;
import static com.ilham.mymoviecatalogue.database.favoritemovie.DatabaseContract.MovieColumns.TITLE;

public class MovieDetailActivity extends AppCompatActivity {
    TextView tv_name, tv_genre, tv_duration, tv_rating, tv_year, tv_desc;
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

    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_POSITION = "extra_position";

    private Movie.ResultsBean movie;
    private ArrayList<Movie.ResultsBean> movieResults;
    private int position;
    Integer movie_id;

    public MovieHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        progressBar = new ProgressDialog(this);
        //mendapatkan id film
        Integer movieId = getIntent().getExtras().getInt("movie_id");
        final Movie.ResultsBean movie = getIntent().getParcelableExtra("EXTRA_MOVIE");
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

        helper = new MovieHelper(this);
        helper.open();

        tv_name = findViewById(R.id.item_nama);
        tv_genre = findViewById(R.id.item_genre);
        tv_duration = findViewById(R.id.item_duration);
        tv_rating = findViewById(R.id.item_rating);
        tv_year = findViewById(R.id.item_year);
        tv_desc = findViewById(R.id.item_synopsis);
        img_photo = findViewById(R.id.item_photo);
        img_cover = findViewById(R.id.item_cover);
        movie_id = movie.getId();

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

        MaterialFavoriteButton materialFavoriteButton = (MaterialFavoriteButton) findViewById(R.id.favorite_button);
        if (helper.checkData(movie_id)) {
            materialFavoriteButton.setFavorite(true);
            materialFavoriteButton.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            if (favorite == true) {
                                SharedPreferences.Editor editor = getSharedPreferences("com.ilham.mymoviecatalogue.MovieDetailActivity", MODE_PRIVATE).edit();
                                editor.putBoolean("Favorite Added", true);
                                editor.apply();
                                Intent intent = new Intent();
                                intent.putExtra(EXTRA_MOVIE, movie);
                                intent.putExtra(EXTRA_POSITION, position);

                                ContentValues values = new ContentValues();
                                values.put(_ID, movie_id);
                                values.put(TITLE, movie.getTitle());
                                values.put(POSTER, movie.getPoster_path());
                                values.put(OVERVIEW, movie.getOverview());
                                values.put(SCORE, movie.getVote_average());
                                values.put(RELEASED, movie.getRelease_date());
                                values.put(BACKDROP, movie.getBackdrop_path());

                                setResult(RESULT_ADD, intent);

                                getContentResolver().insert(CONTENT_URI, values);

                                String successLike = getString(R.string.like);
                                Snackbar.make(buttonView, successLike,
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                SharedPreferences.Editor editor = getSharedPreferences("com.ilham.mymoviecatalogue.MovieDetailActivity", MODE_PRIVATE).edit();
                                editor.putBoolean("Favorite Removed", true);
                                editor.apply();
                                getContentResolver().delete(Uri.parse(CONTENT_URI + "/" + movie_id), null, null);
                                Snackbar.make(buttonView, "Removed from Favorite",
                                        Snackbar.LENGTH_SHORT).show();
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
                                SharedPreferences.Editor editor = getSharedPreferences("com.ilham.mymoviecatalogue.activity.MovieDetailActivity", MODE_PRIVATE).edit();
                                editor.putBoolean("Favorite Added", true);
                                editor.apply();
                                Intent intent = new Intent();
                                intent.putExtra(EXTRA_MOVIE, movie);
                                intent.putExtra(EXTRA_POSITION, position);

                                ContentValues values = new ContentValues();
                                values.put(_ID, movie.getId());
                                values.put(TITLE, movie.getTitle());
                                values.put(POSTER, movie.getPoster_path());
                                values.put(OVERVIEW, movie.getOverview());
                                values.put(SCORE, movie.getVote_average());
                                values.put(RELEASED, movie.getRelease_date());
                                values.put(BACKDROP, movie.getBackdrop_path());

                                setResult(RESULT_ADD, intent);

                                getContentResolver().insert(CONTENT_URI, values);

                                String successLike = getString(R.string.like);
                                Snackbar.make(buttonView, successLike,
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                SharedPreferences.Editor editor = getSharedPreferences("com.ilham.mymoviecatalogue.activity.MovieDetailActivity", MODE_PRIVATE).edit();
                                editor.putBoolean("Favorite Removed", true);
                                editor.apply();
                                getContentResolver().delete(Uri.parse(CONTENT_URI + "/" + movie_id), null, null);
                                Snackbar.make(buttonView, "Removed from Favorite",
                                        Snackbar.LENGTH_SHORT).show();
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

    public void addFav() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_MOVIE, movie);
        intent.putExtra(EXTRA_POSITION, position);

        ContentValues values = new ContentValues();
        values.put(TITLE, movie.getTitle());
        values.put(POSTER, movie.getPoster_path());
        values.put(OVERVIEW, movie.getOverview());
        values.put(SCORE, movie.getVote_average());
        values.put(RELEASED, movie.getRelease_date());
        values.put(BACKDROP, movie.getBackdrop_path());

        setResult(RESULT_ADD, intent);

        getContentResolver().insert(CONTENT_URI, values);

        String successLike = getString(R.string.like);
        Toast.makeText(MovieDetailActivity.this, successLike, Toast.LENGTH_SHORT).show();
    }

    public void delFav() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_POSITION, position);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        ContentValues values = new ContentValues();
        values.put(TITLE, movie.getTitle());
        values.put(POSTER, movie.getPoster_path());
        values.put(OVERVIEW, movie.getOverview());
        values.put(SCORE, movie.getVote_average());
        values.put(RELEASED, movie.getRelease_date());
        values.put(BACKDROP, movie.getBackdrop_path());

        getContentResolver().delete(Objects.requireNonNull(getIntent().getData()), null, null);

        finish();
        String remove = getString(R.string.dislike);
        Toast.makeText(MovieDetailActivity.this, remove, Toast.LENGTH_SHORT).show();
    }
}
