package com.example.moviepocketandroid.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.RatingAdapter;
import com.example.moviepocketandroid.api.MP.MPRatingApi;
import com.google.android.material.button.MaterialButton;

public class RatingDialog {

    private View view;
    private RecyclerView ratingRecyclerView;
    private RatingAdapter ratingAdapter;
    private int lastRating;
    private int idMovie;
    private ImageView starImage;
    private Context context;
    private Dialog ratingDialog;
    private MaterialButton buttonDel;
    private MPRatingApi mpRatingApi;

    public RatingDialog(View view, int idMovie, int rating) {
        this.view = view;
        this.context = view.getContext();
        this.lastRating = rating;
        this.idMovie = idMovie;
        this.starImage = view.findViewById(R.id.imageStar);
        this.ratingDialog = new Dialog(context, R.style.TransparentDialog);
        ratingDialog.setContentView(R.layout.dialog_rating);
        this.buttonDel = ratingDialog.findViewById(R.id.buttonDel);
        this.mpRatingApi = new MPRatingApi();

        setRatingDialog();
    }

    @SuppressLint("SetTextI18n")
    private void setRatingDialog() {


        starImage.setVisibility(View.VISIBLE);

        if (lastRating != 0) {
            starImage.setImageResource(R.drawable.star_yellow);
        } else {
            buttonDel.setVisibility(View.GONE);
        }

        ratingAdapter = new RatingAdapter();
        ratingRecyclerView = ratingDialog.findViewById(R.id.recyclerView);
        ratingRecyclerView.setAdapter(ratingAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        ratingRecyclerView.setLayoutManager(layoutManager);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(ratingRecyclerView);
        starImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingAdapter.setRatingClickListener(new RatingAdapter.OnRatingClickListener() {
                    @Override
                    public void onRatingClick(int rating) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mpRatingApi.postRatingUserByIdMovie(idMovie, rating);
                                lastRating = rating;
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        starImage.setImageResource(R.drawable.star_yellow);
                                        ratingDialog.dismiss();
                                        buttonDel.setVisibility(View.VISIBLE);
                                    }
                                });

                            }

                        }).start();
                    }
                });

                ratingRecyclerView.smoothScrollToPosition(lastRating + 1);
                ratingDialog.show();

            }
        });

        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mpRatingApi.delRatingUserByIdMovie(idMovie);
                        lastRating = 0;
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                starImage.setImageResource(R.drawable.star_blue);
                                ratingDialog.dismiss();
                                buttonDel.setVisibility(View.GONE);
                            }
                        });

                    }

                }).start();
            }
        });
    }
}