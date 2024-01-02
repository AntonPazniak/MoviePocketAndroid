package com.example.moviepocketandroid.ui.until;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.animation.Animation;
import com.example.moviepocketandroid.api.MP.MPListApi;
import com.example.moviepocketandroid.api.MP.MPPostApi;
import com.example.moviepocketandroid.api.models.user.User;

public class AuthorAndRating {

    private ImageView imageViewAvatar;
    private TextView textViewNickname;
    private ImageView imageViewLike, imageViewDislike;
    private TextView textViewCountLikes, textViewCountDislikes;
    private AnimationSet animation;
    private View view;
    private int id;
    private Boolean isLike = false;
    private Boolean isDislike = false;
    private int[] likes;

    public AuthorAndRating(View view, int id, User user, int[] likes) {
        this.imageViewAvatar = view.findViewById(R.id.imageViewAvatar);
        this.textViewNickname = view.findViewById(R.id.textViewNickname);
        this.imageViewLike = view.findViewById(R.id.imageViewLike);
        this.imageViewDislike = view.findViewById(R.id.imageViewDislike);
        this.textViewCountLikes = view.findViewById(R.id.textViewCountLikes);
        this.textViewCountDislikes = view.findViewById(R.id.textViewCountDislikes);
        this.id = id;
        this.likes = likes;
        this.animation = Animation.createAnimation();
        this.view = view;

        UserInfoUntil.setUserInfo(user, view.getContext(), imageViewAvatar);
        textViewNickname.setText(user.getUsername());
        textViewCountLikes.setText(String.valueOf(likes[0]));
        textViewCountDislikes.setText(String.valueOf(likes[1]));
    }

    public void setListRatingButtons(Boolean isAuthentication) {
        if (isAuthentication) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Boolean rating = MPListApi.getLike(id);

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (rating == null) {
                                setListLis();
                            } else if (!rating) {
                                isDislike = true;
                                imageViewDislike.setImageResource(R.drawable.finger_dislike_pink);
                                setListLis();
                            } else if (rating) {
                                isLike = true;
                                imageViewLike.setImageResource(R.drawable.finger_like_pink);
                                setListLis();
                            }
                        }
                    });

                }
            }).start();
        } else {
            setListLisNoAut();
        }
    }

    public void setPostRatingButtons(Boolean isAuthentication) {
        if (isAuthentication) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Boolean rating = MPPostApi.getLike(id);

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (rating == null) {
                                setPostLis();
                            } else if (!rating) {
                                isDislike = true;
                                imageViewDislike.setImageResource(R.drawable.finger_dislike_pink);
                                setPostLis();
                            } else if (rating) {
                                isLike = true;
                                imageViewLike.setImageResource(R.drawable.finger_like_pink);
                                setPostLis();
                            }
                        }
                    });

                }
            }).start();
        } else {
            setPostLisNoAut();
        }
    }

    private void setListLis() {
        imageViewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MPListApi.setLike(id, true);
                    }
                }).start();
                likeButtonPressed();
            }
        });
        imageViewDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MPListApi.setLike(id, false);
                    }
                }).start();
                dislikeButtonPressed();
            }
        });
    }

    private void setPostLis() {
        imageViewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MPPostApi.setLike(id, true);
                    }
                }).start();
                likeButtonPressed();
            }
        });
        imageViewDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MPPostApi.setLike(id, false);
                    }
                }).start();
                dislikeButtonPressed();
            }
        });
    }

    private void dislikeButtonPressed() {
        if (isDislike) {
            imageViewDislike.setImageResource(R.drawable.finger_dislike_blue);
            likes[1] -= 1;
        } else {
            imageViewDislike.setImageResource(R.drawable.finger_dislike_pink);
            imageViewLike.setImageResource(R.drawable.finger_like_blue);
            likes[1] += 1;
            if (isLike) {
                isLike = false;
                likes[0] -= 1;
            }
        }
        isDislike = !isDislike;
        imageViewDislike.startAnimation(animation);
        updateLikesCount(likes[0], likes[1]);
    }

    private void likeButtonPressed() {
        if (isLike) {
            imageViewLike.setImageResource(R.drawable.finger_like_blue);
            likes[0] -= 1;
        } else {
            imageViewLike.setImageResource(R.drawable.finger_like_pink);
            imageViewDislike.setImageResource(R.drawable.finger_dislike_blue);
            likes[0] += 1;
            if (isDislike) {
                isDislike = false;
                likes[1] -= 1;
            }
        }
        isLike = !isLike;
        imageViewLike.startAnimation(animation);
        updateLikesCount(likes[0], likes[1]);
    }

    private void updateLikesCount(int likeCount, int dislikeCount) {
        textViewCountLikes.setText(String.valueOf(likeCount));
        textViewCountDislikes.setText(String.valueOf(dislikeCount));
    }


    private void setPostLisNoAut() {
        imageViewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController((Activity) view.getContext(), R.id.nav_host_fragment_activity_main2);
                navController.navigate(R.id.action_postFragment_to_loginFragment);

            }
        });
        imageViewDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController((Activity) view.getContext(), R.id.nav_host_fragment_activity_main2);
                navController.navigate(R.id.action_postFragment_to_loginFragment);
            }
        });
    }

    private void setListLisNoAut() {
        imageViewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController((Activity) view.getContext(), R.id.nav_host_fragment_activity_main2);
                navController.navigate(R.id.action_listFragment_to_loginFragment);
            }
        });
        imageViewDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController((Activity) view.getContext(), R.id.nav_host_fragment_activity_main2);
                navController.navigate(R.id.action_listFragment_to_loginFragment);
            }
        });
    }


}
