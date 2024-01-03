package com.example.moviepocketandroid.api.models.movie;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImageMovie implements Serializable {
    private final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500";
    @SerializedName("aspect_ratio")
    private double aspectRatio;
    @SerializedName("height")
    private int height;
    @SerializedName("iso_639_1")
    private String iso6391;
    @SerializedName("file_path")
    private String filePath;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("width")
    private int width;

    public double getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getFilePath() {
        return POSTER_BASE_URL + filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
