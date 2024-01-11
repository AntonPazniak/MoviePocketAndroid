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

package com.example.moviepocketandroid.api.models.user;


import com.example.moviepocketandroid.util.Utils;

import java.io.Serializable;
import java.time.LocalDateTime;


public class UserPage implements Serializable {

    private String username;
    private String bio;
    private LocalDateTime created;
    private Long avatar;
//    private List<MovieList> lists;
//    private List<Movie> likeMovie;
//    private List<Movie> dislikeMovie;
//    private List<Movie> watchedMovie;
//    private List<Movie> toWatchMovie;

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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getAvatar() {
        if (avatar != null)
            return Utils.MP_POSTER_PATH + avatar.toString();
        else
            return null;
    }

    public void setAvatar(Long avatar) {
        this.avatar = avatar;
    }
}
