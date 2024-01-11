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

package com.example.moviepocketandroid.ui.review.all;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.ReviewAdapter;
import com.example.moviepocketandroid.api.MP.MPAuthenticationApi;
import com.example.moviepocketandroid.api.MP.MPReviewApi;
import com.example.moviepocketandroid.api.models.review.Review;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AllReviewFragment extends Fragment {

    private ReviewAdapter reviewAdapter;
    private RecyclerView recyclerView;
    private TextView titleTextView;
    private FloatingActionButton fabAdd;

    public AllReviewFragment(int idList) {
        Bundle args = new Bundle();
        args.putInt("idList", idList);
        setArguments(args);
    }

    public AllReviewFragment(int idPost, int post) {
        Bundle args = new Bundle();
        args.putInt("idPost", idPost);
        setArguments(args);
    }


    public AllReviewFragment() {
    }

    public static AllReviewFragment newInstance() {
        return new AllReviewFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        titleTextView = view.findViewById(R.id.textView);
        fabAdd = view.findViewById(R.id.fabAdd);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Bundle args = getArguments();
                if (args != null) {
                    int idMovie = args.getInt("idMovie", -1);
                    int idList = args.getInt("idList", -1);
                    int idPost = args.getInt("idPost", -1);
                    if (idMovie > -1) {
                        setReviewMovie(idMovie);
                    } else if (idList > -1) {
                        setReviewList(idList);
                    } else if (idPost > -1) {
                        setReviewPost(idPost);
                    }

                }
            }
        }).start();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review_all, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }


    private void setReviewMovie(int idMovie) {
        List<Review> reviews = MPReviewApi.getReviewAllByIdMovie(idMovie);
        Boolean isAuthentication = MPAuthenticationApi.checkAuth();
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (reviews.size() > 0) {
                    reviewAdapter = new ReviewAdapter(reviews);
                    recyclerView.setAdapter(reviewAdapter);
                    LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager2);
                    reviewAdapter.setOnReviewClickListener(new ReviewAdapter.OnReviewClickListener() {
                        @Override
                        public void onReviewClick(int idReview) {
                            Bundle args = new Bundle();
                            args.putInt("idReview", idReview);

                            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                            navController.navigate(R.id.action_allReviewFragment_to_detailReviewFragment, args);
                        }
                    });
                }
                if (isAuthentication) {
                    fabAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle args = new Bundle();
                            args.putInt("idMovie", idMovie);

                            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                            navController.navigate(R.id.action_allReviewFragment_to_newReviewFragment, args);
                        }
                    });
                } else {
                    fabAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                            navController.navigate(R.id.action_allReviewFragment_to_loginFragment);
                        }
                    });
                }
            }
        });
    }

    private void setReviewList(int idList) {
        List<Review> reviews = MPReviewApi.getReviewAllByIdList(idList);
        Boolean isAuthentication = MPAuthenticationApi.checkAuth();
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (reviews.size() > 0) {
                    reviewAdapter = new ReviewAdapter(reviews);
                    recyclerView.setAdapter(reviewAdapter);
                    LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager2);
                    reviewAdapter.setOnReviewClickListener(new ReviewAdapter.OnReviewClickListener() {
                        @Override
                        public void onReviewClick(int idReview) {
                            Bundle args = new Bundle();
                            args.putInt("idReview", idReview);

                            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                            navController.navigate(R.id.action_listFragment_to_detailReviewFragment, args);
                        }
                    });
                }
                if (isAuthentication) {
                    fabAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle args = new Bundle();
                            args.putInt("idList", idList);

                            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                            navController.navigate(R.id.action_listFragment_to_newReviewFragment, args);
                        }
                    });
                } else {
                    fabAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                            navController.navigate(R.id.action_listFragment_to_loginFragment);
                        }
                    });
                }
            }
        });
    }

    private void setReviewPost(int idPost) {
        List<Review> reviews = MPReviewApi.getReviewAllByIdPost(idPost);
        Boolean isAuthentication = MPAuthenticationApi.checkAuth();
        if (isAdded() && getContext() != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (reviews.size() > 0) {
                        reviewAdapter = new ReviewAdapter(reviews);
                        recyclerView.setAdapter(reviewAdapter);
                        LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager2);
                        reviewAdapter.setOnReviewClickListener(new ReviewAdapter.OnReviewClickListener() {
                            @Override
                            public void onReviewClick(int idReview) {
                                Bundle args = new Bundle();
                                args.putInt("idReview", idReview);

                                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                navController.navigate(R.id.action_postFragment_to_detailReviewFragment, args);
                            }
                        });
                    }
                    if (isAuthentication) {
                        fabAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bundle args = new Bundle();
                                args.putInt("idPost", idPost);

                                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                navController.navigate(R.id.action_postFragment_to_newReviewFragment, args);
                            }
                        });
                    } else {
                        fabAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                navController.navigate(R.id.action_postFragment_to_loginFragment);
                            }
                        });
                    }
                }
            });
        }
    }

}