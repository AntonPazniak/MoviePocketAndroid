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

package com.example.moviepocketandroid.ui.list.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.ListNavBarAdapter;
import com.example.moviepocketandroid.api.MP.MPListApi;
import com.example.moviepocketandroid.api.models.list.MovieList;
import com.example.moviepocketandroid.ui.list.add.listMovieAddFragment;
import com.example.moviepocketandroid.ui.list.edit.ListEditFragment;
import com.example.moviepocketandroid.ui.list.movie.ListMovieFragment;
import com.example.moviepocketandroid.ui.review.all.AllReviewFragment;
import com.google.android.material.tabs.TabLayout;

public class ListFragment extends Fragment {

    private ListViewModel mViewModel;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int idList;
    private MovieList list;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ListViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            idList = args.getInt("idList");

            tabLayout = view.findViewById(R.id.tabLayout);
            viewPager = view.findViewById(R.id.viewPager);

            tabLayout.setupWithViewPager(viewPager);

            ListNavBarAdapter listNavBarAdapter = new ListNavBarAdapter(getChildFragmentManager(),
                    FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Boolean authorship = MPListApi.getAuthorship(idList);

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listNavBarAdapter.addFragmentAddTitle(new ListMovieFragment(idList), "Movies");
                            listNavBarAdapter.addFragmentAddTitle(new AllReviewFragment(idList), "Reviews");
                            if (authorship) {
                                listNavBarAdapter.addFragmentAddTitle(new ListEditFragment(idList), "Edit");
                                listNavBarAdapter.addFragmentAddTitle(new listMovieAddFragment(idList), "Add");
                            }
                            viewPager.setAdapter(listNavBarAdapter);
                        }
                    });

                }
            }).start();


        }
    }
}