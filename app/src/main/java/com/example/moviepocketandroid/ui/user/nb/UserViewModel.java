package com.example.moviepocketandroid.ui.user.nb;

import androidx.lifecycle.ViewModel;

import com.example.moviepocketandroid.api.models.Movie;

import java.util.List;

public class UserViewModel extends ViewModel {
    private List<Movie> toWatchMovies;
    private List<Movie> favoriteMovies;
    private List<Movie> watchedMovies;

    public List<Movie> getToWatchMovies() {
        return toWatchMovies;
    }

    public void setToWatchMovies(List<Movie> toWatchMovies) {
        this.toWatchMovies = toWatchMovies;
    }

    public List<Movie> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void setFavoriteMovies(List<Movie> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }

    public List<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(List<Movie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }
}
