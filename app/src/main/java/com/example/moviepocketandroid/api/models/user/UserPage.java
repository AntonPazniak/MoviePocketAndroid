package com.example.moviepocketandroid.api.models.user;


import com.example.moviepocketandroid.api.models.MovieList;
import com.example.moviepocketandroid.api.models.Rating;
import com.example.moviepocketandroid.api.models.review.Review;

import java.util.Date;
import java.util.List;


public class UserPage {

    private String username;
    private String bio;
    private Date created;
    private List<MovieList> movieLL;
    private List<Review> reviewList;
    private List<Integer> likeMovie;
    private List<Integer> dislikeMovie;
    private List<Integer> watchedMovie;
    private List<Integer> toWatchMovie;
    private List<Rating> ratingMovie;

    public UserPage(String username, String bio, Date created, List<MovieList> movieLL, List<Review> reviewList, List<Integer> likeMovie, List<Integer> dislikeMovie, List<Integer> watchedMovie, List<Integer> toWatchMovie, List<Rating> ratingMovie) {
        this.username = username;
        this.bio = bio;
        this.created = created;
        this.movieLL = movieLL;
        this.reviewList = reviewList;
        this.likeMovie = likeMovie;
        this.dislikeMovie = dislikeMovie;
        this.watchedMovie = watchedMovie;
        this.toWatchMovie = toWatchMovie;
        this.ratingMovie = ratingMovie;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<MovieList> getMovieLL() {
        return movieLL;
    }

    public void setMovieLL(List<MovieList> movieLL) {
        this.movieLL = movieLL;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public List<Integer> getLikeMovie() {
        return likeMovie;
    }

    public void setLikeMovie(List<Integer> likeMovie) {
        this.likeMovie = likeMovie;
    }

    public List<Integer> getDislikeMovie() {
        return dislikeMovie;
    }

    public void setDislikeMovie(List<Integer> dislikeMovie) {
        this.dislikeMovie = dislikeMovie;
    }

    public List<Integer> getWatchedMovie() {
        return watchedMovie;
    }

    public void setWatchedMovie(List<Integer> watchedMovie) {
        this.watchedMovie = watchedMovie;
    }

    public List<Integer> getToWatchMovie() {
        return toWatchMovie;
    }

    public void setToWatchMovie(List<Integer> toWatchMovie) {
        this.toWatchMovie = toWatchMovie;
    }

    public List<Rating> getRatingMovie() {
        return ratingMovie;
    }

    public void setRatingMovie(List<Rating> ratingMovie) {
        this.ratingMovie = ratingMovie;
    }
}
