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

package com.example.moviepocketandroid.ui.review.detail;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.MP.MPAuthenticationApi;
import com.example.moviepocketandroid.api.MP.MPReviewApi;
import com.example.moviepocketandroid.api.models.review.Review;

public class DetailReviewFragment extends Fragment {

    private DetailReviewViewModel mViewModel;

    private TextView textTitle;
    private TextView textContent;
    private TextView textUsername;
    private TextView textDate;
    private ImageButton imageButton0, imageButton1;
    private int idReview;
    private Boolean isLikeOrDisButton;
    private Boolean authorship = false;
    private TextView textViewCountLikes, textViewCountDislikes;
    private ImageView imageViewAvatar;

    public static DetailReviewFragment newInstance() {
        return new DetailReviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetailReviewViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textUsername = view.findViewById(R.id.textViewUsername);
        textDate = view.findViewById(R.id.textViewDate);
        textTitle = view.findViewById(R.id.textViewTitle);
        textContent = view.findViewById(R.id.textViewContent);
        imageButton0 = view.findViewById(R.id.imageButton0);
        imageButton1 = view.findViewById(R.id.imageButton1);
        imageViewAvatar = view.findViewById(R.id.imageViewAvatar);

        textViewCountLikes = view.findViewById(R.id.textViewCountLikes);
        textViewCountDislikes = view.findViewById(R.id.textViewCountDislikes);

        imageButton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (authorship)
                    onDelButtonClick();
                else
                    onLikeOrDisButtonClick(true);
            }
        });

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (authorship)
                    onEditButtonClick();
                else
                    onLikeOrDisButtonClick(false);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                Bundle args = getArguments();
                if (args != null) {

                    Boolean isAuthentication = MPAuthenticationApi.checkAuth();
                    idReview = args.getInt("idReview");
                    Review review = MPReviewApi.getReviewById(idReview);
                    if (isAuthentication) {
                        authorship = MPReviewApi.getAuthorship(review.getId());
                        isLikeOrDisButton = MPReviewApi.getLike(idReview);
                    }

                    int[] count = MPReviewApi.getCountLikes(idReview);

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (review.getUser().getAvatar() != null) {
                                RequestOptions requestOptions = new RequestOptions()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL);
                                Glide.with(view.getContext())
                                        .load(review.getUser().getAvatar())
                                        .apply(requestOptions)
                                        .into(imageViewAvatar);
                            }
                            textUsername.setText(review.getUser().getUsername());
                            textDate.setText(review.getDataCreated().toLocalDate().toString());
                            textTitle.setText(review.getTitle());
                            textContent.setText(review.getContent());
                            textViewCountLikes.setText(String.valueOf(count[0]));
                            textViewCountDislikes.setText(String.valueOf(count[1]));

                            if (authorship) {
                                imageButton0.setImageResource(R.drawable.trash_pink);
                                imageButton1.setImageResource(R.drawable.edit_yellow);
                                imageButton0.setVisibility(View.VISIBLE);
                                imageButton1.setVisibility(View.VISIBLE);
                            } else if (isLikeOrDisButton != null) {
                                if (isLikeOrDisButton) {
                                    imageButton0.setImageResource(R.drawable.finger_like_pink);
                                } else {
                                    imageButton1.setImageResource(R.drawable.finger_dislike_pink);
                                }
                            } else if (!isAuthentication) {
                                imageButton0.setVisibility(View.GONE);
                                imageButton1.setVisibility(View.GONE);
                            }

                            imageViewAvatar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Bundle args = new Bundle();
                                    args.putString("username", review.getUser().getUsername());
                                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                    navController.navigate(R.id.action_detailReviewFragment_to_userPageFragment, args);
                                }

                            });


                        }
                    });
                }
            }
        }).start();
    }

    private void onEditButtonClick() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Bundle args = new Bundle();
                        args.putInt("idReview", idReview);
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                        navController.navigate(R.id.action_detailReviewFragment_to_newReviewFragment, args);
                    }
                });
            }
        }).start();
    }

    private void onDelButtonClick() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (MPReviewApi.delReviewMovie(idReview)) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                            navController.navigateUp();
                        }
                    });
                }
            }
        }).start();
    }

    private void onLikeOrDisButtonClick(Boolean likeOrDis) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MPReviewApi.setLike(idReview, likeOrDis);
                isLikeOrDisButton = MPReviewApi.getLike(idReview);
                int[] count = MPReviewApi.getCountLikes(idReview);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        textViewCountLikes.setText(String.valueOf(count[0]));
                        textViewCountDislikes.setText(String.valueOf(count[1]));
                        if (isLikeOrDisButton == null) {
                            imageButton0.setImageResource(R.drawable.finger_like_blue);
                            imageButton1.setImageResource(R.drawable.finger_dislike_blue);
                        } else if (isLikeOrDisButton) {
                            imageButton0.setImageResource(R.drawable.finger_like_pink);
                            imageButton1.setImageResource(R.drawable.finger_dislike_blue);
                        } else {
                            imageButton0.setImageResource(R.drawable.finger_like_blue);
                            imageButton1.setImageResource(R.drawable.finger_dislike_pink);
                        }

                    }
                });
            }
        }).start();
    }

}