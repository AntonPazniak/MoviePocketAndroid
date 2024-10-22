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

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.MP.MPRatingApi;
import com.example.moviepocketandroid.api.models.movie.Movie;

import java.text.DecimalFormat;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private OnMovieClickListener onMovieClickListener;

    public interface OnMovieClickListener {
        void onMovieClick(int movieId);
    }

    public void setOnMovieClickListener(OnMovieClickListener listener) {
        this.onMovieClickListener = listener;
    }

    public MovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actor, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageMovie;
        private TextView textTitleMovie;
        private TextView textGenre;
        private TextView textRatingPoster;
        private Context context;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageMovie = itemView.findViewById(R.id.imageActor);
            textTitleMovie = itemView.findViewById(R.id.textNameActor);
            textGenre = itemView.findViewById(R.id.textGenre);
            textRatingPoster = itemView.findViewById(R.id.textRatingPoster);
            context = itemView.getContext();
        }

        @SuppressLint({"ResourceAsColor", "SetTextI18n"})
        public void bind(Movie movie) {
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(itemView.getContext())
                    .load(movie.getPosterPath())
                    .apply(requestOptions)
                    .into(imageMovie);
            textTitleMovie.setText(movie.getTitle());
            if (movie.getGenres() != null && !movie.getGenres().isEmpty()) {
                textGenre.setText(movie.getGenres().get(0).getName());
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    double rating = MPRatingApi.getRatingMovie(movie.getId());
                    DecimalFormat decimalFormat = new DecimalFormat("#.#");
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (rating >= 8) {
                                textRatingPoster.setBackgroundColor(ContextCompat
                                        .getColor(context, R.color.logoYellow));
                                textRatingPoster.setText(decimalFormat.format(rating));
                            } else if (rating > 4) {
                                textRatingPoster.setBackgroundColor(ContextCompat
                                        .getColor(context, R.color.logoBlue));
                                textRatingPoster.setText(decimalFormat.format(rating));
                            } else if (rating > 0) {
                                textRatingPoster.setBackgroundColor(ContextCompat
                                        .getColor(context, R.color.logoPink));
                                textRatingPoster.setText(decimalFormat.format(rating));
                            } else {
                                textRatingPoster.setBackgroundColor(ContextCompat
                                        .getColor(context, R.color.grey));
                                textRatingPoster.setText("NR");
                            }
                        }
                    });
                }
            }).start();
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
