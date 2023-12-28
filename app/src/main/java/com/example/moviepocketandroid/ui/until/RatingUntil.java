package com.example.moviepocketandroid.ui.until;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.MP.MPRatingApi;
import com.example.moviepocketandroid.api.models.movie.Movie;
import com.example.moviepocketandroid.api.models.rating.Rating;

import java.text.DecimalFormat;

public class RatingUntil {

    private TextView textRating, textVoteCount;
    private Movie movie;
    private int idMovie;
    private Context context;

    public RatingUntil(View view, int idMovie) {
        this.textRating = view.findViewById(R.id.textRating);
        this.textVoteCount = view.findViewById(R.id.textVoteCount);
        this.context = view.getContext();
        this.idMovie = idMovie;
    }

    public void setRating() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Rating rating = new Rating(
                        MPRatingApi.getRatingMovie(idMovie),
                        MPRatingApi.getRatingMovieCount(idMovie));
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        DecimalFormat decimalFormat = new DecimalFormat("#.#");

                        if (rating.getRating() >= 8) {
                            textRating.setTextColor(ContextCompat
                                    .getColor(context, R.color.logoYellow));
                            textRating.setText(decimalFormat.format(rating.getRating()));
                        } else if (rating.getRating() > 4) {
                            textRating.setTextColor(ContextCompat
                                    .getColor(context, R.color.logoBlue));
                            textRating.setText(decimalFormat.format(rating.getRating()));
                        } else if (rating.getRating() > 0) {
                            textRating.setTextColor(ContextCompat
                                    .getColor(context, R.color.logoPink));
                            textRating.setText(decimalFormat.format(rating.getRating()));
                        } else {
                            textRating.setTextColor(ContextCompat
                                    .getColor(context, R.color.grey));
                            textRating.setText(R.string.nr);
                        }
                        textVoteCount.setText(context.getString(R.string.votes) + " " + rating.getCount());

                    }
                });

            }
        }).start();
    }

}
