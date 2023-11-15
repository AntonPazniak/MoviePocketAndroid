package com.example.moviepocketandroid.api.models.review;


import java.util.Date;

public class Review {

    private String title;
    private String content;
    private String username;
    private Date dataCreated;
    private Date dataUpdated;
    private int idMovie;
    private int id;
    private int[] likes;

    public Review(String title, String content, String username, Date dataCreated, Date dataUpdated, int idMovie, int id, int[] likes) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.dataCreated = dataCreated;
        this.dataUpdated = dataUpdated;
        this.idMovie = idMovie;
        this.id = id;
        this.likes = likes;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
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

    public void setUsername(String username) {
        this.username = username;
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
}
