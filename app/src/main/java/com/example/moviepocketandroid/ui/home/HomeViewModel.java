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

package com.example.moviepocketandroid.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    private int selectedPopularMovieIndex;

    public int getSelectedPopularMovieIndex() {
        return selectedPopularMovieIndex;
    }

    public void setSelectedPopularMovieIndex(int index) {
        selectedPopularMovieIndex = index;
    }
}
