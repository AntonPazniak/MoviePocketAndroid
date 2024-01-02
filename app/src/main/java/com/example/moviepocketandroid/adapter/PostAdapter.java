package com.example.moviepocketandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.models.post.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> posts;
    private OnItemClickListener onItemClickListener;

    public PostAdapter(List<Post> postList) {
        this.posts = postList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);

        // Set data to UI elements
        holder.textTitle.setText(post.getTitle());
        holder.textDate.setText(post.getCreate().toLocalDate().toString());

        // Set click listener on the item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(post.getId());
                }
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }



    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textDate;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.textTitle);
            textDate = itemView.findViewById(R.id.textDate);
        }
    }
}
