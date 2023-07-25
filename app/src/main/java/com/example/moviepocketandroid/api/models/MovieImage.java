package com.example.moviepocketandroid.api.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

public class MovieImage {
    private double aspectRatio;
    private int height;
    private String iso_639_1;
    private String filePath;
    private double voteAverage;
    private int voteCount;
    private int width;
    private final String URL = "https://image.tmdb.org/t/p/original";


    public MovieImage(double aspectRatio, int height, String iso_639_1, String filePath, double voteAverage, int voteCount, int width) {
        this.aspectRatio = aspectRatio;
        this.height = height;
        this.iso_639_1 = iso_639_1;
        this.filePath = URL+filePath;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.width = width;
    }


    public static MovieImage parseMovieImage(String movieImageJson) {
        MovieImage movieImage = null;
        try {
            JSONObject jsonObject = new JSONObject(movieImageJson);
            double aspectRatio = jsonObject.getDouble("aspect_ratio");
            int height = jsonObject.getInt("height");
            String iso_639_1 = jsonObject.isNull("iso_639_1") ? null : jsonObject.getString("iso_639_1");
            String filePath = jsonObject.getString("file_path");
            double voteAverage = jsonObject.getDouble("vote_average");
            int voteCount = jsonObject.getInt("vote_count");
            int width = jsonObject.getInt("width");
            //if (height<width) {
                movieImage = new MovieImage(aspectRatio, height, iso_639_1, filePath, voteAverage, voteCount, width);
            //}
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieImage;
    }

    public double getAspectRatio() {
        return aspectRatio;
    }

    public int getHeight() {
        return height;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public String getFilePath() {
        return filePath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public int getWidth() {
        return width;
    }
}
