package com.example.moviepocketandroid.util;

import static com.example.moviepocketandroid.animation.Animation.createAnimation;

import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import com.example.moviepocketandroid.R;

public class ButtonUntil {
    AnimationSet animation;
    private boolean isLikeButtonPressed = false;
    private boolean isEyeButtonPressed = false;
    private boolean isBackPackButtonPressed = false;
    private ImageView imageEye;
    private ImageView imageLike;
    private ImageView imageBackPack;

    public ButtonUntil(ImageView imageEye, ImageView imageLike, ImageView imageBackPack) {
        this.imageEye = imageEye;
        this.imageLike = imageLike;
        this.imageBackPack = imageBackPack;
        this.animation = createAnimation();

        imageEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEyeButtonPressed)
                    imageEye.setImageResource(R.drawable.eye_blue);
                else
                    imageEye.setImageResource(R.drawable.eye_green);
                isEyeButtonPressed = !isEyeButtonPressed;
                imageEye.startAnimation(animation);
            }
        });
        imageBackPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBackPackButtonPressed)
                    imageBackPack.setImageResource(R.drawable.backpack_blue);
                else
                    imageBackPack.setImageResource(R.drawable.backpack_yellow);
                isBackPackButtonPressed = !isBackPackButtonPressed;
                imageBackPack.startAnimation(animation);
            }
        });
        imageLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLikeButtonPressed)
                    imageLike.setImageResource(R.drawable.like_blue);
                else
                    imageLike.setImageResource(R.drawable.like_pink);
                isLikeButtonPressed = !isLikeButtonPressed;
                imageLike.startAnimation(animation);
            }
        });

    }
}
