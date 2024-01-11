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

package com.example.moviepocketandroid.adapter.search;

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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private SearchAdapter.OnMovieClickListener onMovieClickListener;


    public interface OnMovieClickListener {
        void onMovieClick(int movieId);
    }

    public void setOnMovieClickListener(SearchAdapter.OnMovieClickListener listener) {
        this.onMovieClickListener = listener;
    }

    public SearchAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public SearchAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new SearchAdapter.MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MovieViewHolder holder, int position) {
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
        private TextView textGenres;
        private TextView textYear;
        private TextView textRatingPoster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageMovie = itemView.findViewById(R.id.imagePosterMovieSearch);
            textTitleMovie = itemView.findViewById(R.id.textTitleMovieSearch);
            textYear = itemView.findViewById(R.id.textYear);
            textGenres = itemView.findViewById(R.id.textGenres);
            textRatingPoster = itemView.findViewById(R.id.textRatingPoster);
        }

        public void bind(Movie movie) {
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(itemView.getContext())
                    .load(movie.getPosterPath())
                    .apply(requestOptions)
                    .into(imageMovie);
            textTitleMovie.setText(movie.getTitle());

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
                                        .getColor(itemView.getContext(), R.color.logoYellow));
                                textRatingPoster.setText(decimalFormat.format(rating));
                            } else if (rating > 4) {
                                textRatingPoster.setBackgroundColor(ContextCompat
                                        .getColor(itemView.getContext(), R.color.logoBlue));
                                textRatingPoster.setText(decimalFormat.format(rating));
                            } else if (rating > 0) {
                                textRatingPoster.setBackgroundColor(ContextCompat
                                        .getColor(itemView.getContext(), R.color.logoPink));
                                textRatingPoster.setText(decimalFormat.format(rating));
                            } else {
                                textRatingPoster.setBackgroundColor(ContextCompat
                                        .getColor(itemView.getContext(), R.color.grey));
                                textRatingPoster.setText(R.string.nr);
                            }
                        }
                    });
                }
            }).start();

            if (movie.getReleaseDate() != null) {
                textYear.setText(String.valueOf(movie.getReleaseDate().getYear()));
            } else if (movie.getFirstAirDate() != null) {
                textYear.setText(String.valueOf(movie.getFirstAirDate().getYear()));
            }
            StringBuilder genders = new StringBuilder();
            List<String> genres = movie.getGenreIds();
            if (!genres.isEmpty()) {
                genders.append(genres.get(0));
                for (int i = 1; i < genres.size(); i++) {
                    genders.append(",");
                    genders.append(genres.get(i));
                }
                textGenres.setText(genders);
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