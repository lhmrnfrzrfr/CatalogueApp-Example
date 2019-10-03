package com.ilham.mymoviecatalogue.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ilham.mymoviecatalogue.R;
import com.ilham.mymoviecatalogue.activity.MovieDetailActivity;
import com.ilham.mymoviecatalogue.items.Movie;

import java.util.ArrayList;
import java.util.List;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.CardViewViewHolder> {

    private Context context;
    private ArrayList<Movie.ResultsBean> listMovie = new ArrayList<>();

    public void setFilmArrayList(List<Movie.ResultsBean> films) {
        this.listMovie = listMovie;
        listMovie.clear();
        listMovie.addAll(films);
        notifyDataSetChanged();
    }

    public final boolean isAdded() {
        return true;
    }

    public void setData(ArrayList<Movie.ResultsBean> items) {
        listMovie.clear();
        listMovie.addAll(items);
        notifyDataSetChanged();
    }

    public ListMovieAdapter(Context context, ArrayList<Movie.ResultsBean> listMovie) {
        this.listMovie = listMovie;
        this.context = context;
    }


    public ArrayList<Movie.ResultsBean> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<Movie.ResultsBean> movieList) {
        this.listMovie = movieList;
        notifyDataSetChanged();
    }


    @Override
    public CardViewViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        final CardViewViewHolder viewHolder = new CardViewViewHolder(view);
        viewHolder.item_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra("EXTRA_MOVIE", listMovie.get(viewHolder.getAdapterPosition()));
                intent.putExtra("movie_id", listMovie.get(viewHolder.getAdapterPosition()).getId());
                intent.putExtra("movie_rate", listMovie.get(viewHolder.getAdapterPosition()).getVote_average());

                context.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewViewHolder holder, int i) {
        if (isAdded()) {

            Movie.ResultsBean movie = getListMovie().get(i);
            holder.tvName.setText(movie.getTitle());
            holder.tvRemarks.setText(movie.getOverview());

            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w185/" + movie.getPoster_path())
                    .into(holder.imgPhoto);
        }
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        CardView item_movie;
        ImageView imgPhoto;
        TextView tvName, tvRemarks;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            item_movie = itemView.findViewById(R.id.card_view);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvRemarks = itemView.findViewById(R.id.tv_item_remarks);
        }
    }
}