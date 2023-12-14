package com.example.moviepocketandroid.adapter;

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
import com.example.moviepocketandroid.api.models.review.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> reviews;
    private OnReviewClickListener onReviewClickListener;

    public void setOnReviewClickListener(OnReviewClickListener listener) {
        this.onReviewClickListener = listener;
    }

    public ReviewAdapter(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.bind(review);

        // Set click listener for the review item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReviewClickListener != null) {
                    int reviewId = Math.toIntExact(review.getId());
                    onReviewClickListener.onReviewClick(reviewId);
                }
            }
        });
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    public interface OnReviewClickListener {
        void onReviewClick(int reviewId);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle;
        private TextView textContent;
        private TextView textUsername;
        private TextView textDate;
        private ImageView imageViewAvatar;
        private int idReview;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textViewTitle);
            textContent = itemView.findViewById(R.id.textViewContent);
            textUsername = itemView.findViewById(R.id.textViewUsername);
            textDate = itemView.findViewById(R.id.textViewDate);
            imageViewAvatar = itemView.findViewById(R.id.imageViewAvatar);
        }

        public void bind(Review review) {
            textTitle.setText(review.getTitle());
            textContent.setText(review.getContent());
            textUsername.setText(review.getUser().getUsername());
            textDate.setText(review.getDataCreated().toString());
            idReview = Math.toIntExact(review.getId());
            if (review.getUser().getAvatar() != null) {
                RequestOptions requestOptions = new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(itemView.getContext())
                        .load(review.getUser().getAvatar())
                        .apply(requestOptions)
                        .into(imageViewAvatar);
            }
        }
    }
}
