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
