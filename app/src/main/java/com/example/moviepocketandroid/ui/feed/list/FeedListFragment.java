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

package com.example.moviepocketandroid.ui.feed.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.ListAdapter2;
import com.example.moviepocketandroid.api.MP.MPAuthenticationApi;
import com.example.moviepocketandroid.api.MP.MPListApi;
import com.example.moviepocketandroid.api.models.list.MovieList;

import java.util.List;

public class FeedListFragment extends Fragment {

    private FeedListViewModel mViewModel;
    private RecyclerView recyclerView;
    private List<MovieList> lists;
    private LinearLayout linearLayoutNewLis;

    public static FeedListFragment newInstance() {
        return new FeedListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FeedListViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerViewLists);
        linearLayoutNewLis = view.findViewById(R.id.linearLayoutNewLis);
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    lists = MPListApi.getLastLists();
                    Boolean isAuthentication = MPAuthenticationApi.checkAuth();

                    if (isAdded() && lists != null && !lists.isEmpty()) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ListAdapter2 listAdapter = new ListAdapter2(lists);
                                recyclerView.setAdapter(listAdapter);

                                LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(layoutManager);

                                if (isAuthentication) {
                                    linearLayoutNewLis.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Bundle args = new Bundle();
                                            args.putInt("newList", 1);
                                            args.putInt("feed", 1);
                                            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                            navController.navigate(R.id.action_feedFragment_to_newReviewFragment, args);
                                        }
                                    });
                                } else {
                                    linearLayoutNewLis.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                            navController.navigate(R.id.action_feedFragment_to_loginFragment);
                                        }
                                    });
                                }

                                listAdapter.setOnItemClickListener(new ListAdapter2.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int idList) {
                                        Bundle args = new Bundle();
                                        args.putInt("idList", idList);
                                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                        navController.navigate(R.id.action_feedFragment_to_listFragment, args);
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