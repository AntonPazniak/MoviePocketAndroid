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

package com.example.moviepocketandroid.ui.feed.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.PostAdapter2;
import com.example.moviepocketandroid.api.MP.MPPostApi;
import com.example.moviepocketandroid.api.models.post.Post;

import java.util.List;

public class FeedPostFragment extends Fragment {

    private FeedPostViewModel mViewModel;
    private RecyclerView recyclerViewPosts;
    private List<Post> posts;

    public static FeedPostFragment newInstance() {
        return new FeedPostFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed_post, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FeedPostViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewPosts = view.findViewById(R.id.recyclerViewPosts);
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    posts = MPPostApi.getLastPosts();

                    if (isAdded() && posts != null && !posts.isEmpty()) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                PostAdapter2 postAdapter = new PostAdapter2(posts);
                                recyclerViewPosts.setAdapter(postAdapter);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
                                recyclerViewPosts.setLayoutManager(layoutManager);
                                postAdapter.setOnItemClickListener(new PostAdapter2.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int idPost) {
                                        Bundle args = new Bundle();
                                        args.putInt("idPost", idPost);
                                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                        navController.navigate(R.id.action_feedFragment_to_postFragment, args);
                                    }
                                });


                            }
                        });
                    }
                }
            }).start();
        } catch (IllegalStateException e) {
            onDestroy();
        }
    }
}