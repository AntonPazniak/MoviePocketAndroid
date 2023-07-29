package com.example.moviepocketandroid.adapter;

import android.graphics.drawable.GradientDrawable;
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
import com.example.moviepocketandroid.api.models.tv.TVSeries;

import java.text.DecimalFormat;
import java.util.List;

public class TVSeriesAdapter extends RecyclerView.Adapter<TVSeriesAdapter.MovieViewHolder> {

    private List<TVSeries> tvSeries;
    private TVSeriesAdapter.OnMovieClickListener onMovieClickListener;

    public interface OnMovieClickListener {
        void onMovieClick(int movieId);
    }

    public void setOnMovieClickListener(TVSeriesAdapter.OnMovieClickListener listener) {
        this.onMovieClickListener = listener;
    }

    public TVSeriesAdapter(List<TVSeries> tvSeries) {
        this.tvSeries = tvSeries;
    }

    @NonNull
    @Override
    public TVSeriesAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actor, parent, false);
        return new TVSeriesAdapter.MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVSeriesAdapter.MovieViewHolder holder, int position) {
        TVSeries tv = tvSeries.get(position);
        holder.bind(tv);
        int desiredWidthDp = 120;
        int desiredHeightDp = 200;
        float density = holder.itemView.getResources().getDisplayMetrics().density;
        int desiredWidthPx = (int) (desiredWidthDp * density);
        int desiredHeightPx = (int) (desiredHeightDp * density);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.width = desiredWidthPx;
        layoutParams.height = desiredHeightPx;
        holder.itemView.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return tvSeries.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageMovie;
        private TextView textTitleMovie;
        private TextView textGenre;
        private TextView textRatingPoster;


        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageMovie = itemView.findViewById(R.id.imageActor);
            textTitleMovie = itemView.findViewById(R.id.textNameActor);
            textGenre = itemView.findViewById(R.id.textGenre);
            textRatingPoster = itemView.findViewById(R.id.textRatingPoster);
        }

        public void bind(TVSeries tv) {
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(itemView.getContext())
                    .load(tv.getPosterPath())
                    .apply(requestOptions)
                    .into(imageMovie);
            textTitleMovie.setText(tv.getName());
            if (!tv.getGenres().isEmpty())
                textGenre.setText(tv.getGenres().get(0));
            double rating = tv.getVoteAverage();
            DecimalFormat decimalFormat = new DecimalFormat("#.#");

            int backgroundColor;
            if (rating >= 8) {
                backgroundColor = android.graphics.Color.parseColor("#F1B36E");
            } else if (rating >= 5) {
                backgroundColor = android.graphics.Color.parseColor("#75FBE2");
            } else {
                backgroundColor = android.graphics.Color.parseColor("#E4416A");
            }

            GradientDrawable backgroundDrawable = new GradientDrawable();
            backgroundDrawable.setColor(backgroundColor);

            textRatingPoster.setBackground(backgroundDrawable);
            textRatingPoster.setText(decimalFormat.format(rating));

            // Set click listener for the movie item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMovieClickListener != null) {
                        int movieId = tv.getId();
                        onMovieClickListener.onMovieClick(movieId);
                    }
                }
            });
        }
    }
}
