package com.example.moviepocketandroid.api.models;

import java.util.Date;
import java.util.List;

public class MovieList {

    private int id;
    private String title;
    private String content;
    private List<String> categoriesList;
    private List<Integer> idMovies;
    private int[] likeOrDis;
    private String username;
    private Date create;
    private Date update;

    public MovieList(int id, String title, String content, List<String> categoriesList, List<Integer> idMovies, int[] likeOrDis, String username, Date create, Date update) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.categoriesList = categoriesList;
        this.idMovies = idMovies;
        this.likeOrDis = likeOrDis;
        this.username = username;
        this.create = create;
        this.update = update;
    }

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

    public int[] getLikeOrDis() {
        return likeOrDis;
    }

    public void setLikeOrDis(int[] likeOrDis) {
        this.likeOrDis = likeOrDis;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreate() {
        return create;
    }

    public void setCreate(Date create) {
        this.create = create;
    }

    public Date getUpdate() {
        return update;
    }

    public void setUpdate(Date update) {
        this.update = update;
    }
}
