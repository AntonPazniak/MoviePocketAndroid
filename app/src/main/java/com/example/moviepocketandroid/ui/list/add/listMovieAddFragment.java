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

package com.example.moviepocketandroid.ui.list.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.ListNavBarAdapter;
import com.example.moviepocketandroid.ui.list.add.search.ListMovieAddSearchFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

public class listMovieAddFragment extends Fragment {

    private ListMovieAddViewModel mViewModel;
    private int idList;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SearchView searchView;
    private MaterialToolbar toolbar;
    private ListNavBarAdapter listNavBarAdapter;
    private ListMovieAddSearchFragment movieFragment;
    private ListMovieAddSearchFragment tvFragment;
    private ListMovieAddSearchFragment delFragment;

    public listMovieAddFragment(int idList) {
        Bundle args = new Bundle();
        args.putInt("idList", idList);
        setArguments(args);
    }

    public listMovieAddFragment() {
    }

    public static listMovieAddFragment newInstance() {
        return new listMovieAddFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_movie_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = view.findViewById(R.id.searchView);
        searchView = (SearchView) toolbar.getMenu().findItem(R.id.search_menu).getActionView();


        Bundle args = getArguments();
        if (args != null) {
            idList = args.getInt("idList");

            tabLayout = view.findViewById(R.id.tabLayout);
            viewPager = view.findViewById(R.id.viewPager);

            tabLayout.setupWithViewPager(viewPager);
            listNavBarAdapter = new ListNavBarAdapter(getChildFragmentManager(),
                    FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            movieFragment = new ListMovieAddSearchFragment(idList);
            tvFragment = new ListMovieAddSearchFragment(idList);
            delFragment = new ListMovieAddSearchFragment(idList, 0);

            listNavBarAdapter.addFragmentAddTitle(movieFragment, "Movie");
            listNavBarAdapter.addFragmentAddTitle(tvFragment, "TVs");
            listNavBarAdapter.addFragmentAddTitle(delFragment, "Delete");


            viewPager.setAdapter(listNavBarAdapter);

            setupSearchView();
        }
    }


    private void setupSearchView() {
        if (searchView instanceof SearchView) {
            SearchView sv = (SearchView) searchView;
            sv.setQueryHint("Search...");
            sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    movieFragment.updateMovie(query);
                    tvFragment.updateTVs(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return true;
                }
            });
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        removeFragments();
    }

    private void removeFragments() {
        if (movieFragment != null) {
            getChildFragmentManager().beginTransaction().remove(movieFragment).commit();
            movieFragment = null;
        }

        if (tvFragment != null) {
            getChildFragmentManager().beginTransaction().remove(tvFragment).commit();
            tvFragment = null;
        }

        if (delFragment != null) {
            getChildFragmentManager().beginTransaction().remove(delFragment).commit();
            delFragment = null;
        }
    }


}