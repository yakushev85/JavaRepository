package com.alex.yakushev.app.torrentslistvisualizer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.yakushev.app.torrentslistvisualizer.model.MovieInfo;
import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.List;

/**
 * Created by Oleksandr on 10-Sep-17.
 */

public class YtsRecycleListAdapter extends RecyclerView.Adapter<YtsRecycleListAdapter.ViewHolder> {
    private List<MovieInfo> mMovies;
    private Context mContext;
    private MovieInfoOnClickListener onClickListener;

    public YtsRecycleListAdapter(List<MovieInfo> movies, Context context) {
        this.mMovies = movies;
        this.mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageListItem;
        TextView titleListItem;

        public ViewHolder(View itemView) {
            super(itemView);

            imageListItem = (ImageView) itemView.findViewById(R.id.imageListView);
            titleListItem = (TextView) itemView.findViewById(R.id.titleListView);
        }
    }

    @Override
    public YtsRecycleListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        return new YtsRecycleListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(YtsRecycleListAdapter.ViewHolder holder, int position) {
        holder.titleListItem.setText(mMovies.get(position).getTitle());
        Picasso.with(mContext)
                .load(mMovies.get(position).getMediumCoverImage())
                .into(holder.imageListItem);

        if (onClickListener != null) {
            holder.itemView.setOnClickListener(v -> onClickListener.onClick(mMovies.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public interface MovieInfoOnClickListener {
        void onClick(MovieInfo movieInfo);
    }

    public void setOnClickListener(MovieInfoOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
