package com.example.moviepocketandroid.api.models.movie;

import com.google.gson.annotations.SerializedName;

public class Episode {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("overview")
    private String overview;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("air_date")
    private String airDate;

    @SerializedName("episode_number")
    private int episodeNumber;

    @SerializedName("episode_type")
    private String episodeType;

    @SerializedName("production_code")
    private String productionCode;

    @SerializedName("runtime")
    private int runtime;

    @SerializedName("season_number")
    private int seasonNumber;

    @SerializedName("show_id")
    private int showId;

    @SerializedName("still_path")
    private String stillPath;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getAirDate() {
        return airDate;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public String getEpisodeType() {
        return episodeType;
    }

    public String getProductionCode() {
        return productionCode;
    }

    public int getRuntime() {
        return runtime;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public int getShowId() {
        return showId;
    }

    public String getStillPath() {
        return stillPath;
    }
}
