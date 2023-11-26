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
import com.example.moviepocketandroid.api.models.movie.Movie;

import java.util.List;

public class NowPlayingMovieAdapter extends RecyclerView.Adapter<NowPlayingMovieAdapter.NowPlayingMovieViewHolder> {

    private List<Movie> movies;
    private OnMovieClickListener onMovieClickListener;

    public NowPlayingMovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    public void setOnMovieClickListener(OnMovieClickListener listener) {
        this.onMovieClickListener = listener;
    }

    @NonNull
    @Override
    public NowPlayingMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_now_playing, parent, false);
        return new NowPlayingMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NowPlayingMovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public interface OnMovieClickListener {
        void onMovieClick(int movieId);
    }

    public class NowPlayingMovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewBack, imageViewPoster;
        private TextView textViewTitle;
        private TextView textViewCategories;
        private TextView textRatingPoster;

        public NowPlayingMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewBack = itemView.findViewById(R.id.imageViewBack);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewCategories = itemView.findViewById(R.id.textViewCategories);

        }

        public void bind(Movie movie) {
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(itemView.getContext())
                    .load(movie.getBackdropPath())
                    .apply(requestOptions)
                    .into(imageViewBack);
            Glide.with(itemView.getContext())
                    .load(movie.getPosterPath())
                    .apply(requestOptions)
                    .into(imageViewPoster);

            textViewTitle.setText(movie.getTitle());
            StringBuilder genders = new StringBuilder();
            if (movie.getGenres() != null) {
                genders.append(movie.getGenres().get(0));
                for (int i = 1; i < movie.getGenres().size(); i++) {
                    genders.append(", ");
                    genders.append(movie.getGenres().get(i));
                }
                textViewCategories.setText(genders);
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMovieClickListener != null) {
                        int movieId = movie.getId();
                        onMovieClickListener.onMovieClick(movieId);
                    }
                }
            });
        }
    }
}

