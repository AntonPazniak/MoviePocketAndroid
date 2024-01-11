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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.models.post.Post;
import com.example.moviepocketandroid.ui.until.UserInfoUntil;

import java.util.List;

public class PostAdapter2 extends RecyclerView.Adapter<PostAdapter2.PostViewHolder> {

    private List<Post> posts;
    private OnItemClickListener onItemClickListener;

    public PostAdapter2(List<Post> posts) {
        this.posts = posts;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_2, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int postId);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewDate;
        private final Context context;
        private ImageView imageViewAvatar;
        private TextView textViewNickname;
        private TextView textViewCountLikes;
        private TextView textViewCountDislikes;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            context = itemView.getContext();
            imageViewAvatar = itemView.findViewById(R.id.imageViewAvatar);
            textViewNickname = itemView.findViewById(R.id.textViewNickname);
            textViewCountLikes = itemView.findViewById(R.id.textViewCountLikes);
            textViewCountDislikes = itemView.findViewById(R.id.textViewCountDislikes);
        }

        public void bind(Post post) {
            textViewTitle.setText("");
            textViewDate.setText("");
            imageViewAvatar.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.user_pink));
            textViewNickname.setText("");
            textViewCountLikes.setText("");
            textViewCountDislikes.setText("");
            textViewTitle.setText(post.getTitle());
            textViewDate.setText(post.getCreate().toLocalDate().toString());
            UserInfoUntil.setUserInfo(post.getUser(), context, imageViewAvatar);
            textViewNickname.setText(post.getUser().getUsername());
            textViewCountLikes.setText(String.valueOf(post.getLikeOrDis()[0]));
            textViewCountDislikes.setText(String.valueOf(post.getLikeOrDis()[1]));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int postId = post.getId();
                        onItemClickListener.onItemClick(postId);
                    }
                }
            });
        }
    }
}
