package com.example.moviepocketandroid.api.models.list;

import com.example.moviepocketandroid.api.models.movie.Genre;
import com.example.moviepocketandroid.api.models.movie.Movie;
import com.example.moviepocketandroid.api.models.user.User;
import com.example.moviepocketandroid.util.Utils;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDateTime;
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
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("genres")
    private List<Genre> genres;
    @SerializedName("movies")
    private List<Movie> movies;
    @SerializedName("poster")
    private Long poster;
    @SerializedName("likeOrDis")
    private int[] likeOrDis;
    @SerializedName("user")
    private User user;
    @SerializedName("create")
    private LocalDateTime create;
    @SerializedName("update")
    private LocalDateTime update;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreate() {
        return create;
    }

    public void setCreate(LocalDateTime create) {
        this.create = create;
    }

    public LocalDateTime getUpdate() {
        return update;
    }

    public void setUpdate(LocalDateTime update) {
        this.update = update;
    }

    public String getPoster() {
        if (poster != null)
            return Utils.MP_POSTER_PATH + poster.toString();
        else
            return null;
    }

    public void setPoster(Long poster) {
        this.poster = poster;
    }
}
