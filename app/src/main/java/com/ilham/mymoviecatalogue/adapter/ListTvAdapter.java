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
import com.ilham.mymoviecatalogue.activity.TvShowDetailActivity;
import com.ilham.mymoviecatalogue.items.Tv;

import java.util.ArrayList;

public class ListTvAdapter extends RecyclerView.Adapter<ListTvAdapter.CardViewViewHolder> {

    private Context context;
    private ArrayList<Tv.ResultsBean> listTv;

    public ListTvAdapter() {

    }

    public final boolean isAdded() {
        return true;
    }

    public ListTvAdapter(Context context, ArrayList<Tv.ResultsBean> listTv) {
        this.listTv = listTv;
        this.context = context;
    }


    public ArrayList<Tv.ResultsBean> getListTv() {
        return listTv;
    }

    public void setListTv(ArrayList<Tv.ResultsBean> tvList) {
        this.listTv = tvList;
        notifyDataSetChanged();
    }


    @Override
    public CardViewViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tv, viewGroup, false);
        final CardViewViewHolder viewHolder = new CardViewViewHolder(view);
        viewHolder.item_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TvShowDetailActivity.class);
                intent.putExtra("EXTRA_TV", listTv.get(viewHolder.getAdapterPosition()));
                intent.putExtra("tv_id", listTv.get(viewHolder.getAdapterPosition()).getId());
                intent.putExtra("tv_rate", listTv.get(viewHolder.getAdapterPosition()).getVote_average());

                context.startActivity(intent);
            }
        });
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CardViewViewHolder holder, int i) {

        if (isAdded()) {
            Tv.ResultsBean tv = getListTv().get(i);
            holder.tvName.setText(tv.getName());
            holder.tvRemarks.setText(tv.getOverview());

            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w185/" + tv.getPoster_path())
                    .into(holder.imgPhoto);
        }
    }

    @Override
    public int getItemCount() {
        return getListTv().size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        CardView item_tv;
        ImageView imgPhoto;
        TextView tvName, tvRemarks;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            item_tv = itemView.findViewById(R.id.card_view);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvRemarks = itemView.findViewById(R.id.tv_item_remarks);
        }
    }
}