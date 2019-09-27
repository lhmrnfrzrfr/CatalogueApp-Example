package com.ilham.mymoviecatalogue.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.ilham.mymoviecatalogue.activity.TvShowFavDetailActivity;
import com.ilham.mymoviecatalogue.items.Tv;

import java.util.ArrayList;

public class TvFavoriteAdapter extends RecyclerView.Adapter<TvFavoriteAdapter.TvFavoriteViewHolder> {

    private ArrayList<Tv.ResultsBean> listTvs = new ArrayList<>();

    private Activity activity;

    public TvFavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Tv.ResultsBean> getListTvs() {
        return listTvs;
    }

    public void setListTv(ArrayList<Tv.ResultsBean> listTvs) {
        if (listTvs.size() > 0) {
            this.listTvs.clear();
        }
        this.listTvs.addAll(listTvs);
        notifyDataSetChanged();
    }

    public void addItem(Tv.ResultsBean tvItems) {
        this.listTvs.add(tvItems);
        notifyItemInserted(listTvs.size() - 1);
    }

    public void removeItem(int position) {
        this.listTvs.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listTvs.size());
    }

    @NonNull
    @Override
    public TvFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_tv, parent, false);
        return new TvFavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvFavoriteAdapter.TvFavoriteViewHolder holder, int position) {

        double score = listTvs.get(position).getVote_average() * 10;
        holder.tvScore.setText(String.valueOf((int) score));

        String uri = "https://image.tmdb.org/t/p/original" + listTvs.get(position).getPoster_path();
        Glide.with(holder.itemView.getContext())
                .load(uri)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.pgTv.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.ivPoster);

        holder.itemFavoriteTv.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {

                Intent intent = new Intent(activity, TvShowFavDetailActivity.class);
                intent.putExtra(TvShowFavDetailActivity.EXTRA_POSITION, position);
                intent.putExtra(TvShowFavDetailActivity.EXTRA_TV, listTvs.get(position));
                activity.startActivityForResult(intent, TvShowFavDetailActivity.REQUEST_UPDATE);

                activity.overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listTvs.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    class TvFavoriteViewHolder extends RecyclerView.ViewHolder {
        final ImageView ivPoster;
        final ProgressBar pgTv;
        final ConstraintLayout itemFavoriteTv;
        final TextView tvScore;

        TvFavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.img_item_poster_favorite_tv);
            pgTv = itemView.findViewById(R.id.progressBar_item_favorite_tv);
            itemFavoriteTv = itemView.findViewById(R.id.cv_favorite_tv);
            tvScore = itemView.findViewById(R.id.tv_item_scoreAngkaHome_favorite_tv);
        }
    }
}
