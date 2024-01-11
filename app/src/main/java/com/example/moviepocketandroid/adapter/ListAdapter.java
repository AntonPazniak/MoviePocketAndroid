/*
 *
 *  * ******************************************************
 *  *  Copyright (C) MoviePocket <prymakdn@gmail.com>
 *  *  This file is part of MoviePocket.
 *  *  MoviePocket can not be copied and/or distributed without the express
 *  *  permission of Danila Prymak, Alexander Trafimchyk and Anton Pozniak
 *  * *****************************************************
 *
 */

package com.example.moviepocketandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.models.list.MovieList;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private List<MovieList> lists;
    private OnItemClickListener onItemClickListener;

    public ListAdapter(List<MovieList> lists) {
        this.lists = lists;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int idList);
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        MovieList list = lists.get(position);
        holder.bind(list);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageViewPoster;
        private final TextView textViewTitle;
        private final TextView textViewGenre;
        private final Context context;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewGenre = itemView.findViewById(R.id.textViewGenre);
            context = itemView.getContext();
        }

        public void bind(MovieList list) {
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(itemView.getContext())
                    .load(list.getPoster())
                    .apply(requestOptions)
                    .into(imageViewPoster);
            textViewTitle.setText(list.getTitle());
            if (list.getGenres() != null && !list.getGenres().isEmpty()) {
                textViewGenre.setText(list.getGenres().get(0).getName());
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int idList = list.getId();
                        onItemClickListener.onItemClick(idList);
                    }
                }
            });
        }
    }
}
