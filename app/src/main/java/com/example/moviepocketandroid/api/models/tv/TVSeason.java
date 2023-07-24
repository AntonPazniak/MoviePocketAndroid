package com.example.moviepocketandroid.api.models.tv;

public class TVSeason {
    private String airDate;
    private int episodeCount;
    private int id;
    private String name;
    private String overview;
    private String posterPath;
    private int seasonNumber;
    private double voteAverage;

    public TVSeason(String airDate, int episodeCount, int id, String name, String overview, String posterPath, int seasonNumber, double voteAverage) {
        this.airDate = airDate;
        this.episodeCount = episodeCount;
        this.id = id;
        this.name = name;
        this.overview = overview;
        this.posterPath = posterPath;
        this.seasonNumber = seasonNumber;
        this.voteAverage = voteAverage;
    }

    public String getAirDate() {
        return airDate;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public double getVoteAverage() {
        return voteAverage;
    }
}
