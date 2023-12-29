package com.example.moviepocketandroid.ui.until;

import static com.example.moviepocketandroid.animation.Animation.createAnimation;

import android.app.Activity;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.MP.MPAssessmentApi;

public class ButtonUntil {
    private AnimationSet animation;
    private boolean isLikeButtonPressed = false;
    private boolean isEyeButtonPressed = false;
    private boolean isBackPackButtonPressed = false;
    private boolean isBinocularsButtonPressed = false;
    private ImageView imageEye, imageLike, imageBackPack, imageBinoculars;
    private int idMovie;
    private View view;

    public ButtonUntil(View view, int idMovie, boolean release, boolean aunt) {
        this.imageEye = view.findViewById(R.id.imageEye);
        this.imageLike = view.findViewById(R.id.imageLike);
        this.imageBackPack = view.findViewById(R.id.imageBackPack);
        this.imageBinoculars = view.findViewById(R.id.imageBinoculars);
        this.idMovie = idMovie;
        this.animation = createAnimation();
        this.view = view;
        if (release) {
            imageEye.setVisibility(View.VISIBLE);
            imageLike.setVisibility(View.VISIBLE);
            imageBackPack.setVisibility(View.VISIBLE);
            imageBinoculars.setVisibility(View.GONE);
            setButtonsMovieReleased(aunt);
        } else {
            imageBinoculars.setVisibility(View.VISIBLE);
            imageBackPack.setVisibility(View.VISIBLE);
            imageEye.setVisibility(View.GONE);
            imageLike.setVisibility(View.GONE);
            setButtonsMovieNotReleased(aunt);
        }

    }

    private void setButtonsMovieNotReleased(boolean aunt) {
        if (aunt) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    isBinocularsButtonPressed = !MPAssessmentApi.getMovieTracing(idMovie);
                    isBackPackButtonPressed = !MPAssessmentApi.getToWatchMovie(idMovie);
                    backPackButtonPressed();
                    binocularsButtonPressed();
                }
            }).start();
            imageBackPack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MPAssessmentApi.postToWatchMovie(idMovie);
                        }
                    }).start();
                    backPackButtonPressed();
                }
            });

            imageBinoculars.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MPAssessmentApi.postMovieTracing(idMovie);
                        }
                    }).start();
                    binocularsButtonPressed();
                }
            });
        } else {
            setButtonsNotAunt(imageBackPack);
            setButtonsNotAunt(imageBinoculars);
        }
    }

    private void setButtonsMovieReleased(boolean aunt) {
        if (aunt) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    isLikeButtonPressed = !MPAssessmentApi.getFavoriteMovie(idMovie);
                    isBackPackButtonPressed = !MPAssessmentApi.getToWatchMovie(idMovie);
                    isEyeButtonPressed = !MPAssessmentApi.getWatchedMovie(idMovie);
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
                            MPAssessmentApi.postWatchedMovie(idMovie);
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
                            MPAssessmentApi.postToWatchMovie(idMovie);
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
                            MPAssessmentApi.postFavoriteMovie(idMovie);
                        }
                    }).start();
                    likeButtonPressed();
                }
            });
        } else {
            setButtonsNotAunt(imageBackPack);
            setButtonsNotAunt(imageEye);
            setButtonsNotAunt(imageLike);
        }
    }

    private void setButtonsNotAunt(ImageView imageView) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController((Activity) view.getContext(), R.id.nav_host_fragment_activity_main2);
                navController.navigate(R.id.action_movieFragment_to_loginFragment);
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


}
