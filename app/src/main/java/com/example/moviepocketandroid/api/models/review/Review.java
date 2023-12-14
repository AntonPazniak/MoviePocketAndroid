package com.example.moviepocketandroid.api.models.review;


import com.example.moviepocketandroid.api.models.user.User;

import java.util.Date;

public class Review {

    private String title;
    private String content;
    private User user;
    private Date dataCreated;
    private Date dataUpdated;
    private int idMovie;
    private int id;
    private int[] likes;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getDataCreated() {
        return dataCreated;
    }

    public void setDataCreated(Date dataCreated) {
        this.dataCreated = dataCreated;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public int getId() {
        return id;
    }

    public int[] getLikes() {
        return likes;
    }

    public Date getDataUpdated() {
        return dataUpdated;
    }

    public void setDataUpdated(Date dataUpdated) {
        this.dataUpdated = dataUpdated;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLikes(int[] likes) {
        this.likes = likes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
