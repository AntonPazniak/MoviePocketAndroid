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

package com.example.moviepocketandroid.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListNavBarAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public ListNavBarAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

    }

    public void addFragmentAddTitle(Fragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @NonNull
    @Override
    public String getPageTitle(int position) {
        return titles.get(position);
    }


}
