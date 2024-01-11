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

package com.example.moviepocketandroid.ui.user.page;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.ListAdapter;
import com.example.moviepocketandroid.adapter.PostAdapter;
import com.example.moviepocketandroid.api.MP.MPListApi;
import com.example.moviepocketandroid.api.MP.MPPostApi;
import com.example.moviepocketandroid.api.MP.MPUserApi;
import com.example.moviepocketandroid.api.models.list.MovieList;
import com.example.moviepocketandroid.api.models.post.Post;
import com.example.moviepocketandroid.api.models.user.UserPage;

import java.util.List;

public class UserPageFragment extends Fragment {

    private UserPageViewModel mViewModel;
    private TextView textViewUsername, textViewDate, textViewBio;
    private ImageView imageViewAvatar;
    private View view;
    private List<MovieList> lists;
    private List<Post> posts;
    private TextView textViewList, textViewPost;
    private RecyclerView recyclerViewList, recyclerViewPost;


    public static UserPageFragment newInstance() {
        return new UserPageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_page, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UserPageViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        textViewUsername = view.findViewById(R.id.textViewUsername);
        textViewDate = view.findViewById(R.id.textViewDate);
        textViewBio = view.findViewById(R.id.textViewBio);
        imageViewAvatar = view.findViewById(R.id.imageViewAvatar);
        View listView = view.findViewById(R.id.listView);
        View postView = view.findViewById(R.id.postView);

        textViewList = listView.findViewById(R.id.textView);
        recyclerViewList = listView.findViewById(R.id.recyclerView);

        textViewPost = postView.findViewById(R.id.textView);
        recyclerViewPost = postView.findViewById(R.id.recyclerView);


        Bundle args = getArguments();
        if (args != null) {
            String username = args.getString("username");
            loadUserDetails(username);
        }

    }

    private void loadUserDetails(String username) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserPage userPage = MPUserApi.getUserByUsername(username);
                lists = MPListApi.getAllByUser(username);
                posts = MPPostApi.getPostsByUser(username);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (userPage != null) {
                            if (userPage.getAvatar() != null) {
                                RequestOptions requestOptions = new RequestOptions()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL);
                                Glide.with(view.getContext())
                                        .load(userPage.getAvatar())
                                        .apply(requestOptions)
                                        .into(imageViewAvatar);
                            }
                            textViewUsername.setText(userPage.getUsername());
                            textViewDate.setText(userPage.getCreated().toLocalDate().toString());
                            textViewBio.setText(userPage.getBio());
                            setList();
                            setPost();
                        }
                    }
                });
            }
        }).start();
    }


    @SuppressLint("SetTextI18n")
    private void setList() {
        if (lists != null && !lists.isEmpty()) {
            textViewList.setText("Movie lists");
            ListAdapter listAdapter = new ListAdapter(lists);
            recyclerViewList.setAdapter(listAdapter);
            LinearLayoutManager layoutManager1 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerViewList.setLayoutManager(layoutManager1);
            listAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int idList) {
                    Bundle args = new Bundle();
                    args.putInt("idList", idList);
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                    navController.navigate(R.id.action_userPageFragment_to_listFragment, args);
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    private void setPost() {
        try {
            if (posts != null && !posts.isEmpty()) {
                textViewPost.setText("All Posts");
                PostAdapter postAdapter = new PostAdapter(posts);
                recyclerViewPost.setAdapter(postAdapter);
                GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 3, GridLayoutManager.HORIZONTAL, false);
                recyclerViewPost.setLayoutManager(layoutManager);

                postAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int idPost) {
                        Bundle args = new Bundle();
                        args.putInt("idPost", idPost);
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                        navController.navigate(R.id.action_userPageFragment_to_postFragment, args);
                    }
                });
            }

        } catch (IllegalStateException e) {
            onDestroy();
        }
    }

}