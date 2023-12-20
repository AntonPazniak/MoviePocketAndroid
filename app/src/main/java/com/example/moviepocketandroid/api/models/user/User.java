package com.example.moviepocketandroid.api.models.user;

import com.example.moviepocketandroid.util.Utils;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("avatar")
    private Long avatar;
    @SerializedName("username")
    private String username;
    @SerializedName("email")
    private String email;
    @SerializedName("bio")
    private String bio;


    public String getAvatar() {
        if (avatar != null)
            return Utils.MP_POSTER_PATH + avatar.toString();
        else
            return null;
    }

    public void setAvatar(Long avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
