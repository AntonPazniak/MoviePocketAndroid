package com.example.moviepocketandroid.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieList {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    @SerializedName("categoriesList")
    private List<String> categoriesList;

    @SerializedName("idMovies")
    private List<Integer> idMovies;

    @SerializedName("likeOrDis")
    private List<Integer> likeOrDis;

    @SerializedName("username")
    private String username;

    @SerializedName("create")
    private String create;

    @SerializedName("update")
    private String update;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<String> categoriesList) {
        this.categoriesList = categoriesList;
    }

    public List<Integer> getIdMovies() {
        return idMovies;
    }

    public void setIdMovies(List<Integer> idMovies) {
        this.idMovies = idMovies;
    }

    public List<Integer> getLikeOrDis() {
        return likeOrDis;
    }

    public void setLikeOrDis(List<Integer> likeOrDis) {
        this.likeOrDis = likeOrDis;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
}
