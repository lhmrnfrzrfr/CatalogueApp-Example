package com.ilham.mymoviecatalogue.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ilham.mymoviecatalogue.CustomOnItemClickListener;
import com.ilham.mymoviecatalogue.R;
import com.ilham.mymoviecatalogue.activity.MovieFavDetailActivity;
import com.ilham.mymoviecatalogue.items.Movie;

import java.util.ArrayList;

import static com.ilham.mymoviecatalogue.database.DatabaseContract.MovieColumns.CONTENT_URI;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.MovieFavoriteViewHolder> {

    private ArrayList<Movie.ResultsBean> listMovies = new ArrayList<>();
    private Activity activity;

    public MovieFavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Movie.ResultsBean> getListMovies() {
        return listMovies;
    }

    public void setListMovie(ArrayList<Movie.ResultsBean> listMovies) {
        if (listMovies.size() > 0) {
            this.listMovies.clear();
        }
        this.listMovies.addAll(listMovies);
        notifyDataSetChanged();
    }

    public void addItem(Movie.ResultsBean movieItems) {
        this.listMovies.add(movieItems);
        notifyItemInserted(listMovies.size() - 1);
    }

    public void removeItem(int position) {
        this.listMovies.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listMovies.size());
    }

    @NonNull
    @Override
    public MovieFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_movie, parent, false);
        return new MovieFavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieFavoriteAdapter.MovieFavoriteViewHolder holder, int position) {

        double score = getListMovies().get(position).getVote_average() * 10;
        holder.tvScore.setText(String.valueOf((int) score));

        String uri = "https://image.tmdb.org/t/p/original" + getListMovies().get(position).getPoster_path();
        Glide.with(holder.itemView.getContext())
                .load(uri)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.pgMovie.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.ivPoster);

        // Intent To Detail Activity
        holder.itemFavoriteMovie.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                // Define and Start Intent
                Intent intent = new Intent(activity, MovieFavDetailActivity.class);

                Uri uri = Uri.parse(CONTENT_URI + "/" + getListMovies().get(position).getId());
                intent.setData(uri);

                intent.putExtra(MovieFavDetailActivity.EXTRA_POSITION, position);
                intent.putExtra(MovieFavDetailActivity.EXTRA_MOVIE, listMovies.get(position));
                activity.startActivityForResult(intent, MovieFavDetailActivity.REQUEST_UPDATE);

                // Intent Transition Animation
                activity.overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    class MovieFavoriteViewHolder extends RecyclerView.ViewHolder {
        final ImageView ivPoster;
        final ProgressBar pgMovie;
        final ConstraintLayout itemFavoriteMovie;
        final TextView tvScore;

        MovieFavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvScore = itemView.findViewById(R.id.tv_item_scoreAngkaHome_favorite_movie);
            ivPoster = itemView.findViewById(R.id.img_item_poster_favorite_movie);
            pgMovie = itemView.findViewById(R.id.progressBar_item_favorite_movie);
            itemFavoriteMovie = itemView.findViewById(R.id.cv_favorite_movie);
        }
    }
}