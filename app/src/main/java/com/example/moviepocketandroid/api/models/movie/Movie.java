/*
 *
 *  * ******************************************************
 *  *  Copyright (C) MoviePocket <prymakdn@gmail.com>
 *  *  This file is part of MoviePocket.
 *  *  MoviePocket can not be copied and/or distributed without the express
 *  *  permission of Danila Prymak, Alexander Trafimchyk and Anton Pozniak
 *  * *****************************************************
 *
 */

package com.example.moviepocketandroid.api.models.movie;

import com.example.moviepocketandroid.util.Utils;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Movie implements Serializable {
    private final String BASE_URL = "https://image.tmdb.org/t/p/w500";
    private final String STANDARD_POSTER_URL = Utils.BASS_POSTER_PATH;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("belongs_to_collection")
    private Object belongsToCollection;

    @SerializedName("budget")
    private long budget;

    @SerializedName("genres")
    private List<Genre> genres;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("id")
    private int id;

    @SerializedName("imdb_id")
    private String imdbId;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("overview")
    private String overview;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("production_companies")
    private List<ProductionCompany> productionCompanies;

    @SerializedName("production_countries")
    private List<ProductionCountry> productionCountries;

    @SerializedName("release_date")
    private LocalDate releaseDate;

    @SerializedName("revenue")
    private long revenue;

    @SerializedName("runtime")
    private int runtime;

    @SerializedName("spoken_languages")
    private List<SpokenLanguage> spokenLanguages;

    @SerializedName("status")
    private String status;

    @SerializedName("tagline")
    private String tagline;

    @SerializedName("title")
    private String title;

    @SerializedName("video")
    private boolean video;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("created_by")
    private List<Object> createdBy;

    @SerializedName("episode_run_time")
    private List<Integer> episodeRunTime;

    @SerializedName("first_air_date")
    private LocalDate firstAirDate;

    @SerializedName("in_production")
    private boolean inProduction;

    @SerializedName("languages")
    private List<String> languages;

    @SerializedName("last_air_date")
    private String lastAirDate;

    @SerializedName("last_episode_to_air")
    private Episode lastEpisodeToAir;

    @SerializedName("name")
    private String name;

    @SerializedName("next_episode_to_air")
    private Object nextEpisodeToAir;

    @SerializedName("networks")
    private List<ProductionCompany> networks;

    @SerializedName("number_of_episodes")
    private int numberOfEpisodes;

    @SerializedName("number_of_seasons")
    private int numberOfSeasons;

    @SerializedName("origin_country")
    private List<String> originCountry;

    @SerializedName("original_name")
    private String originalName;

    @SerializedName("seasons")
    private List<Season> seasons;

    @SerializedName("type")
    private String type;
    @SerializedName("genre_ids")
    private int[] genreIds;

    public String getPosterPath() {
        if (posterPath != null)
            return BASE_URL + posterPath;
        else
            return STANDARD_POSTER_URL;
    }

    public String getBackdropPath() {
        return BASE_URL + backdropPath;
    }

    public String getTitle() {
        if (title != null)
            return title;
        else
            return name;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getId() {
        return id;
    }

    public String getBASE_URL() {
        return BASE_URL;
    }

    public String getSTANDARD_POSTER_URL() {
        return STANDARD_POSTER_URL;
    }

    public boolean isAdult() {
        return adult;
    }

    public Object getBelongsToCollection() {
        return belongsToCollection;
    }

    public long getBudget() {
        return budget;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public long getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public List<SpokenLanguage> getSpokenLanguages() {
        return spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public boolean isVideo() {
        return video;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public List<Object> getCreatedBy() {
        return createdBy;
    }

    public List<Integer> getEpisodeRunTime() {
        return episodeRunTime;
    }

    public LocalDate getFirstAirDate() {
        return firstAirDate;
    }

    public boolean isInProduction() {
        return inProduction;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public Episode getLastEpisodeToAir() {
        return lastEpisodeToAir;
    }

    public String getName() {
        return name;
    }

    public Object getNextEpisodeToAir() {
        return nextEpisodeToAir;
    }

    public List<ProductionCompany> getNetworks() {
        return networks;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public String getOriginalName() {
        return originalName;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    private static String getGenreText(int genreId) {
        String genreText;
        switch (genreId) {
            case 28:
                genreText = "Action";
                break;
            case 12:
                genreText = "Adventure";
                break;
            case 16:
                genreText = "Animation";
                break;
            case 35:
                genreText = "Comedy";
                break;
            case 80:
                genreText = "Crime";
                break;
            case 99:
                genreText = "Documentary";
                break;
            case 18:
                genreText = "Drama";
                break;
            case 10751:
                genreText = "Family";
                break;
            case 14:
                genreText = "Fantasy";
                break;
            case 36:
                genreText = "History";
                break;
            case 27:
                genreText = "Horror";
                break;
            case 10402:
                genreText = "Music";
                break;
            case 9648:
                genreText = "Mystery";
                break;
            case 10749:
                genreText = "Romance";
                break;
            case 878:
                genreText = "Science Fiction";
                break;
            case 10770:
                genreText = "TV Movie";
                break;
            case 53:
                genreText = "Thriller";
                break;
            case 10752:
                genreText = "War";
                break;
            case 37:
                genreText = "Western";
                break;
            default:
                genreText = "Unknown Genre";
                break;
        }
        return genreText;
    }

    public List<String> getGenreIds() {
        List<String> genres = new ArrayList<>();
        for (int id : genreIds) {
            genres.add(getGenreText(id));
        }
        return genres;
    }
}
