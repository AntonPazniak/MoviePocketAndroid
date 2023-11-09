package com.example.moviepocketandroid.util;

import static com.example.moviepocketandroid.animation.Animation.createAnimation;

import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.MP.MPAssessmentApi;

public class ButtonUntil {
    private AnimationSet animation;
    private boolean isLikeButtonPressed = false;
    private boolean isEyeButtonPressed = false;
    private boolean isBackPackButtonPressed = false;
    private boolean isBinocularsButtonPressed = false;
    private ImageView imageEye, imageLike, imageBackPack, imageBinoculars;
    private MPAssessmentApi mpAssessmentAPI;
    private int idMovie;

    public ButtonUntil(ImageView imageEye, ImageView imageLike, ImageView imageBackPack, int idMovie) {
        this.imageEye = imageEye;
        this.imageLike = imageLike;
        this.imageBackPack = imageBackPack;
        this.animation = createAnimation();
        this.idMovie = idMovie;

        mpAssessmentAPI = new MPAssessmentApi();

        new Thread(new Runnable() {
            @Override
            public void run() {
                isLikeButtonPressed = !mpAssessmentAPI.getFavoriteMovie(idMovie);
                isBackPackButtonPressed = !mpAssessmentAPI.getToWatchMovie(idMovie);
                isEyeButtonPressed = !mpAssessmentAPI.getWatchedMovie(idMovie);
                eyeButtonPressed();
                backPackButtonPressed();
                likeButtonPressed();
            }
        }).start();

        imageEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mpAssessmentAPI.postWatchedMovie(idMovie);
                    }
                }).start();
                eyeButtonPressed();
            }
        });
        imageBackPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mpAssessmentAPI.postToWatchMovie(idMovie);
                    }
                }).start();
                backPackButtonPressed();
            }
        });
        imageLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mpAssessmentAPI.postFavoriteMovie(idMovie);
                    }
                }).start();
                likeButtonPressed();
            }
        });
    }

    public ButtonUntil(ImageView imageBackPack, ImageView imageBinoculars, int idMovie) {
        this.imageBackPack = imageBackPack;
        this.imageBinoculars = imageBinoculars;
        this.animation = createAnimation();
        this.idMovie = idMovie;

        imageBackPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backPackButtonPressed();
            }
        });

        imageBinoculars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binocularsButtonPressed();
            }
        });

    }

    private void binocularsButtonPressed() {
        if (isBinocularsButtonPressed)
            imageBinoculars.setImageResource(R.drawable.binoculars_blue);
        else
            imageBinoculars.setImageResource(R.drawable.binoculars_pink);
        isBinocularsButtonPressed = !isBinocularsButtonPressed;
        imageBinoculars.startAnimation(animation);
    }

    private void backPackButtonPressed() {
        if (isBackPackButtonPressed)
            imageBackPack.setImageResource(R.drawable.backpack_blue);
        else
            imageBackPack.setImageResource(R.drawable.backpack_yellow);
        isBackPackButtonPressed = !isBackPackButtonPressed;
        imageBackPack.startAnimation(animation);
    }

    private void likeButtonPressed() {
        if (isLikeButtonPressed)
            imageLike.setImageResource(R.drawable.like_blue);
        else
            imageLike.setImageResource(R.drawable.like_pink);
        isLikeButtonPressed = !isLikeButtonPressed;
        imageLike.startAnimation(animation);
    }

    private void eyeButtonPressed() {
        if (isEyeButtonPressed)
            imageEye.setImageResource(R.drawable.eye_blue);
        else
            imageEye.setImageResource(R.drawable.eye_green);
        isEyeButtonPressed = !isEyeButtonPressed;
        imageEye.startAnimation(animation);
    }

    private void login() {

    }


}
