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

package com.example.moviepocketandroid.ui.until;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.models.movie.Movie;

public class MovieInfoUntil {

    private View view;
    private Movie movie;
    private TextView textCountry, textCategories, textMinutes, textOverview, textViewOverview;
    private TextView textTitlePopularMovie;
    private ImageView imageBackPopularMovie, imagePosterPopularMovie;
    private boolean isExpanded = false;


    public MovieInfoUntil(View view, Movie movie) {
        this.view = view;
        this.movie = movie;

        textOverview = view.findViewById(R.id.textOverview);
        textMinutes = view.findViewById(R.id.textMinutes);
        textCategories = view.findViewById(R.id.textCategories);
        textCountry = view.findViewById(R.id.textCountry);
        textTitlePopularMovie = view.findViewById(R.id.textTitlePopularMovie);
        imageBackPopularMovie = view.findViewById(R.id.imageBackMovie);
        imagePosterPopularMovie = view.findViewById(R.id.imagePosterMovie);
        textViewOverview = view.findViewById(R.id.textViewOverview);


    }


    public void setMovieInfo() {
        setPosterAndTitle();
        setInfo();
    }

    private void setPosterAndTitle() {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(view.getContext())
                .load(movie.getBackdropPath())
                .apply(requestOptions)
                .into(imageBackPopularMovie);
        Glide.with(view.getContext())
                .load(movie.getPosterPath())
                .apply(requestOptions)
                .into(imagePosterPopularMovie);
        textTitlePopularMovie.setText(movie.getTitle());
    }

    @SuppressLint("SetTextI18n")
    private void setInfo() {
        StringBuilder s = new StringBuilder();
        if (movie.getProductionCountries() != null && !movie.getProductionCountries().isEmpty()) {
            s.append(movie.getProductionCountries().get(0).getName());
            for (int i = 1; i < movie.getProductionCountries().size(); i++) {
                s.append(", ");
                s.append(movie.getProductionCountries().get(i).getName());
            }
            textCountry.setText(s);
        }
        StringBuilder genders = new StringBuilder();
        if (movie.getReleaseDate() != null) {
            int year = movie.getReleaseDate().getYear();
            if (movie.getId() > 0) {
                textMinutes.setText(year + ", " + movie.getRuntime() + " mins");
            } else {
                textMinutes.setText(year + ", Seasons: " + movie.getSeasons().size());
            }
        }

        if (movie.getGenres() != null && !movie.getGenres().isEmpty()) {
            genders.append(movie.getGenres().get(0).getName());
            for (int i = 1; i < movie.getGenres().size(); i++) {
                genders.append(", ");
                genders.append(movie.getGenres().get(i).getName());
            }
            textCategories.setText(genders);
        }
        if (!movie.getOverview().isEmpty()) {
            textViewOverview.setText("Description");
            textOverview.setText(movie.getOverview());

            textOverview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isExpanded) {
                        textOverview.setMaxLines(5);
                        textOverview.setEllipsize(TextUtils.TruncateAt.END);
                    } else {
                        textOverview.setMaxLines(Integer.MAX_VALUE);
                        textOverview.setEllipsize(null);
                    }
                    isExpanded = !isExpanded;
                }
            });

        }
    }

    public Movie getMovie() {
        return movie;
    }
}
