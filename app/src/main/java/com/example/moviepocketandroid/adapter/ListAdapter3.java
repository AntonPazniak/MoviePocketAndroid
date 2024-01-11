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
import com.example.moviepocketandroid.ui.until.UserInfoUntil;

import java.util.List;

public class ListAdapter3 extends RecyclerView.Adapter<ListAdapter3.ListViewHolder> {

    private List<MovieList> lists;
    private OnItemClickListener onItemClickListener;

    public ListAdapter3(List<MovieList> lists) {
        this.lists = lists;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_3, parent, false);
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

    public interface OnItemClickListener {
        void onItemClick(int idList);
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageViewPoster;
        private final TextView textViewTitle;
        private final TextView textViewDate;
        private final Context context;
        private ImageView imageViewAvatar;
        private TextView textViewNickname;
        private TextView textViewCountLikes;
        private TextView textViewCountDislikes;
        private TextView textViewContent;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            context = itemView.getContext();
            imageViewAvatar = itemView.findViewById(R.id.imageViewAvatar);
            textViewNickname = itemView.findViewById(R.id.textViewNickname);
            textViewCountLikes = itemView.findViewById(R.id.textViewCountLikes);
            textViewCountDislikes = itemView.findViewById(R.id.textViewCountDislikes);
            textViewContent = itemView.findViewById(R.id.textViewContent);
        }

        public void bind(MovieList list) {
            if (list.getPoster() != null) {
                RequestOptions requestOptions = new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(itemView.getContext())
                        .load(list.getPoster())
                        .apply(requestOptions)
                        .into(imageViewPoster);
            }
            textViewTitle.setText(list.getTitle());
            textViewContent.setText(list.getContent());
            textViewDate.setText(list.getCreate().toLocalDate().toString());
            UserInfoUntil.setUserInfo(list.getUser(), context, imageViewAvatar);
            textViewNickname.setText(list.getUser().getUsername());
            textViewCountLikes.setText(String.valueOf(list.getLikeOrDis()[0]));
            textViewCountDislikes.setText(String.valueOf(list.getLikeOrDis()[1]));
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
