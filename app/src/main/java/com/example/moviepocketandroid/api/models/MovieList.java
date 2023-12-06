package com.example.moviepocketandroid.api.models;

import com.example.moviepocketandroid.api.models.movie.Genre;
import com.example.moviepocketandroid.api.models.movie.Movie;
import com.example.moviepocketandroid.util.Utils;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieList implements Serializable {
    private int id;
    private String title;
    private String content;
    private List<Genre> genres;
    private List<Movie> movies;
    private int[] likeOrDis;
    private String username;
    private LocalDate create;
    private LocalDate update;
    private Long poster;

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

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
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

    public LocalDate getCreate() {
        return create;
    }

    public void setCreate(LocalDate create) {
        this.create = create;
    }

    public LocalDate getUpdate() {
        return update;
    }

    public void setUpdate(LocalDate update) {
        this.update = update;
    }

    public String getPoster() {
        return Utils.MP_POSTER_PATH + poster.toString();
    }

    public void setPoster(Long poster) {
        this.poster = poster;
    }
}
