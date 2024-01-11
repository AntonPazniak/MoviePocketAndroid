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

package com.example.moviepocketandroid.ui.post.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.MP.MPAuthenticationApi;
import com.example.moviepocketandroid.api.MP.MPPostApi;
import com.example.moviepocketandroid.api.TMDB.TMDBApi;
import com.example.moviepocketandroid.api.models.movie.Movie;
import com.example.moviepocketandroid.api.models.person.Person;
import com.example.moviepocketandroid.api.models.post.Post;
import com.example.moviepocketandroid.ui.until.AuthorAndRating;

public class PostMainFragment extends Fragment {

    private PostMainViewModel mViewModel;
    private int idPost;
    private Post post;
    private TextView textTitle;
    private TextView textContent;
    private Boolean isAuthentication;
    private View view, viewBackMovie;
    private ImageView imageBackMovie;
    private Movie movie;
    private Person person;


    public PostMainFragment(int idPost) {
        Bundle args = new Bundle();
        args.putInt("idPost", idPost);
        setArguments(args);
    }

    public PostMainFragment() {
    }

    public static PostMainFragment newInstance() {
        return new PostMainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            idPost = args.getInt("idPost");

            textTitle = view.findViewById(R.id.textTitle);
            textContent = view.findViewById(R.id.textContent);
            viewBackMovie = view.findViewById(R.id.viewBackMovie);
            imageBackMovie = view.findViewById(R.id.imageBackMovie);

            this.view = view;

            loadPostInf();
        }

    }

    private void loadPostInf() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                isAuthentication = MPAuthenticationApi.checkAuth();
                post = MPPostApi.getPostById(idPost);
                if (post != null) {
                    if (post.getIdMovie() != 0)
                        movie = TMDBApi.getInfoMovie(post.getIdMovie());
                    else if (post.getIdPerson() > 0)
                        person = TMDBApi.getPersonById(post.getIdPerson());
                    if (isAdded()) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                setPostInf();
                            }
                        });
                    }
                }
            }
        }).start();

    }

    private void setPostInf() {
        if (movie != null) {
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(requireContext())
                    .load(movie.getBackdropPath())
                    .apply(requestOptions)
                    .into(imageBackMovie);
            imageBackMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle args = new Bundle();
                    args.putInt("idMovie", movie.getId());

                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                    navController.navigate(R.id.action_postFragment_to_movieFragment, args);
                }
            });
        } else if (person != null) {
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(requireContext())
                    .load(person.getProfilePath())
                    .apply(requestOptions)
                    .into(imageBackMovie);
            imageBackMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle args = new Bundle();
                    args.putInt("idPerson", person.getId());

                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                    navController.navigate(R.id.action_postFragment_to_personFragment, args);
                }
            });
        }

        LinearLayout linearLayoutUser = view.findViewById(R.id.linearLayoutUser);

        linearLayoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("username", post.getUser().getUsername());

                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                navController.navigate(R.id.action_postFragment_to_userPageFragment, args);
            }
        });
        textTitle.setText(post.getTitle());
        textContent.setText(post.getContent());
        AuthorAndRating authorAndRating = new AuthorAndRating(view, post.getId(), post.getUser(), post.getLikeOrDis());
        authorAndRating.setPostRatingButtons(isAuthentication);
    }
}